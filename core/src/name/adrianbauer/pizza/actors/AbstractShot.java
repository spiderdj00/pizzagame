package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.utils.Array;
import name.adrianbauer.RectangularActor;

import static name.adrianbauer.pizza.PizzaGame.GAME_HEIGHT;


public abstract class AbstractShot extends RectangularActor {

    public AbstractShot(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * If shot moves out on top of the screen
     */
    public boolean isDead() {
        if (getY() >= GAME_HEIGHT) {
            return true;
        }
        return false;
    }

    public static void removeDeadShots(Array<AbstractShot> shots) {
        Array.ArrayIterator<AbstractShot> iterShot = shots.iterator();
        while (iterShot.hasNext()) {
            AbstractShot shot = iterShot.next();
            if (shot.isDead()) {
                shot.remove();
                iterShot.remove();
            }
        }
    }
}