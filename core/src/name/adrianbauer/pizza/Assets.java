package name.adrianbauer.pizza;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Always dispose any loaded assets!
 */
public class Assets {

    //public static TextureAtlas atlas;
    public static BitmapFont font;

    static Texture pizzaSliceTexture;
    public static TextureRegion pizzaSlice;
    static Texture pineappleTexture;
    public static TextureRegion pineapple;

    static Texture mozzarellaTexture;
    public static TextureRegion mozzarella;

    static Texture tomatoShotTexture;
    public static TextureRegion tomatoShot;

    public static Sound pineappleHitGood;
    public static Sound pineappleHitBad;

    public static Sound pizzaCollision;
    public static Sound mozzarellaShot;
    public static Sound tomatoShotSound;

    public static void load() {
        //atlas = new TextureAtlas(Gdx.files.internal("images.atlas"));
        //car = atlas.findRegion("car");
        //road = atlas.findRegion("road");

        font = new BitmapFont();

        pizzaSliceTexture= new Texture(Gdx.files.internal("pizza.png"));
        pizzaSlice = new TextureRegion(pizzaSliceTexture);

        pineappleTexture = new Texture(Gdx.files.internal("pineapple.png"));
        pineapple = new TextureRegion(pineappleTexture);

        mozzarellaTexture = new Texture(Gdx.files.internal("mozzarella.png"));
        mozzarella = new TextureRegion(mozzarellaTexture);

        tomatoShotTexture = new Texture(Gdx.files.internal("tomatoShot.png"));
        tomatoShot = new TextureRegion(tomatoShotTexture);

        pineappleHitGood = Gdx.audio.newSound(Gdx.files.internal("pineappleHitGood.wav"));
        pineappleHitBad = Gdx.audio.newSound(Gdx.files.internal("pineappleHitBad.wav"));

        pizzaCollision = Gdx.audio.newSound(Gdx.files.internal("pizzaCollision.wav"));
        mozzarellaShot = Gdx.audio.newSound(Gdx.files.internal("mozzarellaShot.wav"));
        tomatoShotSound = Gdx.audio.newSound(Gdx.files.internal("tomatoShot.wav"));

    }

    public static void dispose() {
        //atlas.dispose();
        font.dispose();
        pizzaSliceTexture.dispose();
        pineappleTexture.dispose();
        mozzarellaTexture.dispose();
        tomatoShotTexture.dispose();
        pineappleHitGood.dispose();
        pineappleHitBad.dispose();
        pizzaCollision.dispose();
        mozzarellaShot.dispose();
        tomatoShotSound.dispose();
    }
}