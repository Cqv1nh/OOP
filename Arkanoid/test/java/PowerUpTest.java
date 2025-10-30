import engine.KeyboardManager;
import engine.MouseManager;
import entities.*;
import managers.GameStateManager;
import managers.LevelState2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AssetManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTest {
    GameStateManager gsm;
    LevelState2 level;
    private static final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        KeyboardManager km = new KeyboardManager();
        MouseManager mm = new MouseManager();
        AssetManager.loadImages();
        gsm = new GameStateManager(km, mm);
        gsm.loadLevel(1);
        gsm.setState("level");

        level = (LevelState2) gsm.getCurrentState();
    }

    @Test
    void extraLifePowerUpTest() {
        ExtraLifePowerUp extraLifePowerUp =  new ExtraLifePowerUp(50, 50,5, 5, 1);

        extraLifePowerUp.applyEffect(level);

        assertEquals(4, level.getLives());
    }

    @Test
    void fastBallPowerUpTest() {
        FastBallPowerUp fastBallPowerUp = new FastBallPowerUp(50, 50, 5, 5, 7);

        fastBallPowerUp.applyEffect(level);

        List<Ball> ballList = level.getBalls();

        for (Ball ball : ballList) {
            assertEquals(-6.25, ball.getDx(), DELTA);
            assertEquals(-6.25, ball.getDy(), DELTA);
        }

        fastBallPowerUp.removeEffect(level);
        for (Ball ball : ballList) {
            assertEquals(-5, ball.getDx(), DELTA);
            assertEquals(-5, ball.getDy(), DELTA);
        }

    }

    @Test
    void expandPaddlePowerUp() {
        ExpandPaddlePowerUp expandPaddlePowerUp = new ExpandPaddlePowerUp(50, 50, 5, 5, 7);

        expandPaddlePowerUp.applyEffect(level);

        Paddle paddle = level.getPaddle();

        assertEquals(120, paddle.getWidth());

        expandPaddlePowerUp.removeEffect(level);

        assertEquals(100, paddle.getWidth());
    }
}
