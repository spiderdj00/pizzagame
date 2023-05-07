package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import name.adrianbauer.pizza.Assets;

import static name.adrianbauer.pizza.PizzaGame.GAME_WIDTH;

public class ScoreBoard extends Actor {

    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        Assets.font.draw(batch, scorePlayer1 + " Points", 40, 15);
        Assets.font.draw(batch, scorePlayer2 + " Points", GAME_WIDTH-80, 15);
    }

}