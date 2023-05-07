package name.adrianbauer.pizza;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import name.adrianbauer.GamepadScreen;
import name.adrianbauer.pizza.actors.PizzaGameActor;
import name.adrianbauer.pizza.actors.Pizza;

import java.util.ArrayList;
import java.util.List;

import static name.adrianbauer.pizza.PizzaGame.GAME_HEIGHT;
import static name.adrianbauer.pizza.PizzaGame.GAME_WIDTH;

/**
 * Game screen and stage management and player controller mapping.
 */
public class PizzaGameScreen extends GamepadScreen {

    final Stage stage;
    final PizzaGameActor pizzaGameActor;
    final List<String> players = new ArrayList<>();

    public PizzaGameScreen() {
        // Set background color
        super(new Color(0.2f, 0.1f, 0.1f, 0));

        stage = new Stage(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
        pizzaGameActor = new PizzaGameActor(GAME_WIDTH, GAME_HEIGHT);
        stage.addActor(pizzaGameActor);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        render(delta, stage);
    }

    @Override
    public void onInputDown(Controller controller, GamepadInput input) {
        Pizza pizza = getPlayerActor(controller);
        switch (input) {
            case DPAD_LEFT:
                pizza.currentMovingState = Pizza.MovingState.LEFT;
                break;
            case DPAD_RIGHT:
                pizza.currentMovingState = Pizza.MovingState.RIGHT;
                break;

            case R1:
                pizza.currentShootingState = Pizza.ShootingState.NORMAL_SHOT;
                break;

            case R2:
                pizza.currentShootingState = Pizza.ShootingState.POWER_SHOT;
                break;

            case SHARE:
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void onInputUp(Controller controller, GamepadInput input) {
        Pizza pizza = getPlayerActor(controller);
        switch (input) {
            case DPAD_LEFT:
            case DPAD_RIGHT:
                pizza.currentMovingState = Pizza.MovingState.STOP;
                break;

            case R1:
            case R2:
                pizza.currentShootingState = Pizza.ShootingState.STOP;
                break;
        }
    }

    protected Pizza getPlayerActor(Controller controller) {
        String playerId = controller.getUniqueId();
        int index = players.indexOf(playerId);
        if (index == -1) {
            players.add(controller.getUniqueId());
            index = 0;
        }
        return index == 0 ? pizzaGameActor.player1 : pizzaGameActor.player2;
    }

}