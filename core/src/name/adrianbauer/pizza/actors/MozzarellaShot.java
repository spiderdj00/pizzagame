package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import name.adrianbauer.pizza.Assets;

import static name.adrianbauer.pizza.PizzaGame.GAME_HEIGHT;


public class MozzarellaShot extends AbstractShot {

    public MozzarellaShot(float x, float y) {
        super(x, y, 24, 24);

        addAction(Actions.parallel(
                Actions.moveTo(getX(), GAME_HEIGHT, 1.0f),
                Actions.rotateBy(250f, 1.0f))
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // WHITE tint color means no color/transparent
        batch.setColor(Color.WHITE);
        drawCentered(batch, Assets.mozzarella);
    }

}