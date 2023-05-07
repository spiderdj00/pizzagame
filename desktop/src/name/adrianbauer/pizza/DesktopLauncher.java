package name.adrianbauer.pizza;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static name.adrianbauer.pizza.PizzaGame.GAME_HEIGHT;
import static name.adrianbauer.pizza.PizzaGame.GAME_WIDTH;

// Please note that on macOS your application needs to be
// started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);

		config.setTitle("Pizza Hawaii");

		// Uncomment/comment to switch between full screen and window mode
		config.setWindowSizeLimits(GAME_WIDTH, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		new Lwjgl3Application(new PizzaGame(), config);
	}

}
