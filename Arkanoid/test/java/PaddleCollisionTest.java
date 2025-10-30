import engine.CollisionHandler;
import engine.KeyboardManager;
import engine.MouseManager;
import entities.Ball;
import managers.GameStateManager;
import managers.LevelState2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AssetManager;
import util.Constants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleCollisionTest {
    GameStateManager gsm;
    LevelState2 level;
    private static final double DELTA = 0.0001;
    List<Ball> balls;
    Ball ball;
    CollisionHandler collisionHandler;
    @BeforeEach
    void setUp() {
        KeyboardManager km = new KeyboardManager();
        MouseManager mm = new MouseManager();
        AssetManager.loadImages();
        gsm = new GameStateManager(km, mm);
        gsm.loadLevel(1);
        gsm.setState("level");

        level = (LevelState2) gsm.getCurrentState();

        level.setBallLaunched(true);
        balls = level.getBalls();
        collisionHandler = new CollisionHandler();

        ball = balls.getFirst();
    }

    @Test
    void testUpperPaddleCollision() {
        ball.setY(Constants.INIT_PADDLE_Y - 5);
        ball.setX(Constants.INIT_PADDLE_X + Constants.PADDLE_WIDTH / 2);
        ball.setDirectionY(1);
        ball.move();
        collisionHandler.handleCollisions(level, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        assertTrue(ball.getDy() < 0);
    }

    @Test
    void testLeftPaddleCollision() {
        ball.setY(Constants.INIT_PADDLE_Y + 3);
        ball.setX(Constants.INIT_PADDLE_X - 3);
        ball.setDirectionY(0);
        ball.setDirectionX(1);

        ball.move();
        collisionHandler.handleCollisions(level, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        assertTrue(ball.getDx() < 0);
    }

}
