package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import name.adrianbauer.RectangularActor;
import name.adrianbauer.pizza.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Pineapple extends RectangularActor {

    public Pineapple(float x, float y) {
        super(x, y - 64 / 2f, 64, 64);

        setColor(Color.WHITE);

        // Move from top to bottom across screen
        addAction(moveTo(x, -getHeight(), MathUtils.random(4f, 4f)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        drawCentered(batch, Assets.pineapple);
    }

    /**
     * Remove any pineapple that is off the bottom of the screen
     */
    public boolean isDead() {
        if (getY() + getHeight() <= 0) {
            remove();
            return true;
        }
        return false;
    }

    /**
     *  Remove when hit by player
     */
    public boolean isHitAndRemoved(Pizza pizza) {
        if (getBounds().overlaps(pizza.getBounds())) {
            explode();
            // Also rotate the pizza
            pizza.addAction(Actions.rotateBy(360f, 0.5f));
            return true;
        }
        return false;
    }

    /**
     * Remove when hit by shot
     */
    public boolean isHitAndRemoved(AbstractShot shot) {
        if (getBounds().overlaps(shot.getBounds())) {
            // Decide which way to spin the pineapple
            crashAndRemove(getX() > shot.getX(), getY() > shot.getY());
            return true;
        }
        return false;
    }

    protected void crashAndRemove(boolean front, boolean above) {
        clearActions();

        int rnd = MathUtils.random(0, 3);
        if (rnd == 0)
            setColor(Color.SALMON);
        if (rnd == 1)
            setColor(Color.RED);
        if (rnd == 2)
            setColor(Color.GREEN);
        if (rnd == 3)
            setColor(Color.PURPLE);

        addAction(Actions.fadeOut(3f));

        if (front && above)
            addAction(sequence(parallel(Actions.rotateBy(-360, .5f), Actions.moveBy(200, 200, .5f)), Actions.removeActor(this)));
        if (front && !above)
            addAction(sequence(parallel(Actions.rotateBy(360, .5f), Actions.moveBy(200, -200, .5f)), Actions.removeActor(this)));
        if (!front && above)
            addAction(sequence(parallel(Actions.rotateBy(360, .5f), Actions.moveBy(-200, 200, .5f)), Actions.removeActor(this)));
        if (!front && !above)
            addAction(sequence(parallel(Actions.rotateBy(-360, .5f), Actions.moveBy(-200, -200, .5f)), Actions.removeActor(this)));
    }

    protected void explode() {
        clearActions();

        setColor(Color.BLACK);

        addAction(Actions.sequence(
                Actions.rotateBy(360f, 0.5f),
                Actions.removeActor(this)
        ));
    }
}