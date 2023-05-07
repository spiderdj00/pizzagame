package name.adrianbauer;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Use this as a superclass to make a screen with gamepad controls.
 */
public abstract class GamepadScreen extends ScreenAdapter implements ControllerListener {

    public enum GamepadInput {
        UNKNOWN(-1),
        A(1),
        B(0),
        Y(2),
        X(3),
        SHARE(4),
        START(6),
        L1(9),
        R1(10),
        R2(1005),
        DPAD_UP(11),
        DPAD_DOWN(12),
        DPAD_LEFT(13),
        DPAD_RIGHT(14);

        final public int code;

        GamepadInput(int code) {
            this.code = code;
        }

        static GamepadInput getInput(int code) {
            for (GamepadInput userInput : values()) {
                if (userInput.code == code) {
                    return userInput;
                }
            }
            return UNKNOWN;
        }
    }

    final Color backgroundColor;

    public GamepadScreen(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    abstract public void onInputDown(Controller controller, GamepadInput input);

    abstract public void onInputUp(Controller controller, GamepadInput input);

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Controllers.addListener(this);
    }

    @Override
    public void hide() {
        Controllers.removeListener(this);
    }

    @Override
    public void connected(Controller controller) {
        System.out.println("Connected controller: " + controller.getUniqueId());
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Disconnected controller: " + controller.getUniqueId());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        //System.out.println("DOWN buttonCode = " + buttonCode);
        onInputDown(controller, GamepadInput.getInput(buttonCode));
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        //System.out.println("UP buttonCode = " + buttonCode);
        onInputUp(controller, GamepadInput.getInput(buttonCode));
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {
        //System.out.println("AXIS moved = " + axisIndex + " => " + value);
        if (axisIndex == 5) {
            if (value == 1) {
                onInputDown(controller, GamepadInput.getInput(1005));
            } else if (value == 0) {
                onInputUp(controller, GamepadInput.getInput(1005));
            }
        }
        return false;
    }

    /**
     * For convenience this clears the background, then executes stage actions, then draws.
     */
    protected void render(float delta, Stage stage) {
        ScreenUtils.clear(backgroundColor);
        stage.act(delta);
        stage.draw();
    }

}
