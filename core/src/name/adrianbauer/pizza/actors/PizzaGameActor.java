package name.adrianbauer.pizza.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import name.adrianbauer.RectangularActor;
import name.adrianbauer.pizza.Assets;

import java.util.Iterator;

public class PizzaGameActor extends RectangularActor {

    public Pizza player1;
    public Pizza player2;

    final Array<Pineapple> pineapples = new Array<>();
    long lastPineappleTime = 0;

    public ScoreBoard scoreBoard;

    public PizzaGameActor(int width, int height) {
        super(0, 0, width, height);

        player1 = new Pizza(50);
        addActor(player1);

        player2 = new Pizza(getWidth() - 50);
        addActor(player2);

        scoreBoard = new ScoreBoard();
        addActor(scoreBoard);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        lastPineappleTime = spawnPineapples(lastPineappleTime);
        checkPineapples();

        checkPlayerHitsAndBounds();

        player1.fireShots();
        player2.fireShots();

        scoreBoard.scorePlayer1 = checkPineappleHits(true, player1.normalShots, scoreBoard.scorePlayer1);
        scoreBoard.scorePlayer1 = checkPineappleHits(false, player1.powerShots, scoreBoard.scorePlayer1);

        scoreBoard.scorePlayer2 = checkPineappleHits(true, player2.normalShots, scoreBoard.scorePlayer2);
        scoreBoard.scorePlayer2 = checkPineappleHits(false, player2.powerShots, scoreBoard.scorePlayer2);
    }

    protected void checkPlayerHitsAndBounds() {
        // Restrict players to left and right edge of screen
        if (player1.getX() <= 0) {
            player1.setX(0);
        } else if (player1.getX() + player1.getWidth() >= getWidth()) {
            player1.setX(getWidth() - player1.getWidth());
        }

        if (player2.getX() <= 0) {
            player2.setX(0);
        } else if (player2.getX() + player2.getWidth() >= getWidth()) {
            player2.setX(getWidth() - player2.getWidth());
        }

        // Detect player collision
        if (Pizza.checkHit(player1, player2)) {
            Assets.pizzaCollision.play();
        }
    }

    protected long spawnPineapples(long lastTime) {
        // Spawn a pineapple every 200 milliseconds
        if (TimeUtils.nanoTime() - lastTime > 200000000f) {
            float randomX = MathUtils.random(0, getWidth());
            Pineapple pineapple = new Pineapple(randomX, getHeight());
            addActor(pineapple);
            pineapples.add(pineapple);
            return TimeUtils.nanoTime();
        }
        return lastTime;
    }

    protected void checkPineapples() {
        // Look at each pineapple
        Iterator<Pineapple> iterPineapple = pineapples.iterator();
        while (iterPineapple.hasNext()) {
            Pineapple pineapple = iterPineapple.next();

            if (pineapple.isDead()) {
                iterPineapple.remove();
                continue;
            }

            if (pineapple.isHitAndRemoved(player1)) {
                iterPineapple.remove();
                scoreBoard.scorePlayer1--;
                Assets.pineappleHitBad.play();
                continue;
            }

            if (pineapple.isHitAndRemoved(player2)) {
                iterPineapple.remove();
                scoreBoard.scorePlayer2--;
                Assets.pineappleHitBad.play();
            }
        }
    }

    protected int checkPineappleHits(boolean removeShotOnHit, Array<AbstractShot> shots, int currentScore) {
        Iterator<Pineapple> iterPineapple = pineapples.iterator();
        pineappleCheck:
        while (iterPineapple.hasNext()) {
            Pineapple pineapple = iterPineapple.next();

            Iterator<AbstractShot> iterShot = shots.iterator();
            while (iterShot.hasNext()) {
                AbstractShot shot = iterShot.next();

                if (pineapple.isHitAndRemoved(shot)) {
                    iterPineapple.remove();

                    currentScore++;
                    Assets.pineappleHitGood.play();

                    if (removeShotOnHit) {
                        iterShot.remove();
                        shot.remove();
                    }

                    continue pineappleCheck;
                }

            }
        }
        return currentScore;
    }

}