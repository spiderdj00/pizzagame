package name.adrianbauer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

/**
 * An actor with @{@link Rectangle} bounds for hit detection.
 */
public class RectangularActor extends Group {

    private final Rectangle bounds = new Rectangle();

    public RectangularActor(float x, float y, int width, int height) {
        setBounds(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.set(getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
    }

    protected void drawCentered(Batch batch, TextureRegion textureRegion) {
        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Convenience method so we can use Group everywhere.
     */
    public RunnableAction removeFromParent(final Actor a) {
        return new RunnableAction() {
            @Override
            public void run() {
                System.out.println("Removing = " + a);
                a.getParent().removeActor(a);
            }
        };
    }

}