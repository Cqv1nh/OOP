import engine.CollisionHandler;
import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AudioManager;
import util.Constants;

import static org.junit.jupiter.api.Assertions.*;

public class WallCollisionTest {
    private static final double DELTA = 0.0001;
    private Ball ball;
    private Paddle paddle;
    private CollisionHandler collisionHandler;


    @BeforeEach
    void setUp() {
        double testSpeed = 10.0;
        double testDirX = 0.8;
        double testDirY = 0.8;
        double testRadius = 5.0;

        ball = new Ball(testSpeed, testDirX, testDirY, testRadius); // (x, y, radius, dx, dy)
        paddle = new Paddle(90, 110, 50, 10, 0, 0, "NONE"); // (x, y, width, height)
        collisionHandler = new CollisionHandler();
        AudioManager.loadSound("/sounds/sfx_ball_hit.wav", "ball_hit");
    }

    @Test
    void upperWallCollision() {
        ball.setY(5);
        ball.setDirectionY(-0.8);
        ball.move();

        collisionHandler.handleBallWallCollision(ball);

        assertEquals(0, ball.getY(), DELTA, "");
        assertEquals(8, ball.getDy(), DELTA, "");
    }

    @Test
    void leftWallCollision() {
        ball.setX(5);
        ball.setDirectionX(-0.8);
        ball.move();

        collisionHandler.handleBallWallCollision(ball);

        assertEquals(0, ball.getX(), DELTA, "");
        assertEquals(8, ball.getDx(), DELTA, "");
    }

    @Test
    void rightWallCollision() {

        ball.setX(Constants.SCREEN_WIDTH - 5);
        ball.setDirectionX(0.8);
        ball.move();

        collisionHandler.handleBallWallCollision(ball);

        assertEquals(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER, ball.getX(), DELTA, "");
        assertEquals(-8, ball.getDx(), DELTA, "");
    }

    @Test
    void underWallCollision() {

        ball.setY(Constants.SCREEN_HEIGHT - 5);

        ball.move();

        assertTrue(collisionHandler.handleBallWallCollision(ball));
    }
}
