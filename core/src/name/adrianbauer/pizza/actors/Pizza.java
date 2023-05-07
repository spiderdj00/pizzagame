package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import name.adrianbauer.RectangularActor;
import name.adrianbauer.pizza.Assets;

import static name.adrianbauer.pizza.actors.Pizza.ShootingState.*;


public class Pizza extends RectangularActor {

    final Array<AbstractShot> normalShots = new Array<>();
    long lastNormalShot = 0;
    final Array<AbstractShot> powerShots = new Array<>();
    long lastPowerShot = 0;
    Action powerShotLoadingAction;

    public enum MovingState {
        STOP,
        LEFT,
        RIGHT
    }

    public enum ShootingState {
        STOP,
        NORMAL_SHOT,
        POWER_SHOT
    }

    public MovingState currentMovingState = MovingState.STOP;
    public ShootingState currentShootingState = ShootingState.STOP;

    public Pizza(float x) {
        super(x, 20, 64, 64);
        setColor(Color.WHITE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        switch (currentMovingState) {
            case LEFT:
                setX(getX() - 200 * delta);
                break;
            case RIGHT:
                setX(getX() + 200 * delta);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(getColor());
        drawCentered(batch, Assets.pizzaSlice);
    }

    static public boolean checkHit(Pizza p1, Pizza p2) {
        if (p1.getX() + p1.getWidth() >= p2.getX()) {
            p1.currentMovingState = MovingState.STOP;
            p1.addAction(Actions.moveBy(-50, 0, 0.5f));
            p2.currentMovingState = MovingState.STOP;
            p2.addAction(Actions.moveBy(50, 0, 0.5f));
            return true;
        }
        return false;
    }

    public void fireShots() {

        AbstractShot.removeDeadShots(normalShots);
        AbstractShot.removeDeadShots(powerShots);

        if (currentShootingState == NORMAL_SHOT && TimeUtils.nanoTime() - lastNormalShot > 600000000f) {

            // No power shot while shooting normal shots
            if (lastPowerShot > 0) {
                lastPowerShot = 0;
                removeAction(powerShotLoadingAction);
                powerShotLoadingAction = null;
                setColor(Color.WHITE);
            }

            // Make a normal shot and add it to parent actor (PizzaGameActor)
            MozzarellaShot shot = new MozzarellaShot(getX() + getWidth() / 2 - 12, getY() + getHeight());
            getParent().addActor(shot);
            normalShots.add(shot);
            lastNormalShot = TimeUtils.nanoTime();
            Assets.mozzarellaShot.play();

        } else if (currentShootingState == POWER_SHOT && lastPowerShot == 0) {
            // Start powering up the shot
            lastPowerShot = TimeUtils.nanoTime();

            powerShotLoadingAction = Actions.color(Color.RED, 3f);
            addAction(powerShotLoadingAction);

        } else if (currentShootingState == STOP && lastPowerShot > 0 && TimeUtils.nanoTime() - lastPowerShot > 1500000000f) {

            // Size of shot depends on how long the powerup time is
            long powerupTimeMillis = Math.min((TimeUtils.nanoTime() - lastPowerShot) / 1000000, 3000);
            float scale = 1.0f + ((powerupTimeMillis - 1500) / ((3000 - 1500) / 100f)) / 50;

            // Make a power shot and add it to parent actor (PizzaGameActor)
            TomatoShot shot = new TomatoShot(getX() + getWidth() / 2 - 12, getY() + getHeight(), scale);
            getParent().addActor(shot);

            powerShots.add(shot);
            Assets.tomatoShotSound.play();

            // Reset
            lastPowerShot = 0;
            removeAction(powerShotLoadingAction);
            powerShotLoadingAction = null;
            setColor(Color.WHITE);
        }
    }

}