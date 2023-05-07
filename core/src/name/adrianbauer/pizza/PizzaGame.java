package name.adrianbauer.pizza;

import com.badlogic.gdx.Game;

public class PizzaGame extends Game {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 480;

    PizzaGameScreen pizzaGameScreen;

    @Override
    public void create() {
        Assets.load();
        pizzaGameScreen = new PizzaGameScreen();
        setScreen(pizzaGameScreen);
    }

    @Override
    public void dispose() {
        Assets.dispose();
        pizzaGameScreen.dispose();
    }
}
