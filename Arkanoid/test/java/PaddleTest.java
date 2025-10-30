import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.Test;
import util.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddleTest {
    private static final double DELTA = 0.0001;

    @Test
    void testContructor() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X, Constants.INIT_PADDLE_Y, Constants.POWERUP_WIDTH,
                Constants.PADDLE_HEIGHT,-1 ,0 , "NONE");

        assertEquals(Constants.INIT_PADDLE_X, paddle.getX(), DELTA);
        assertEquals(Constants.INIT_PADDLE_Y, paddle.getY(), DELTA);

        // Kiểm tra thuộc tính riêng của Paddle
        assertEquals(Constants.PADDLE_SPEED, paddle.getSpeed(), DELTA);
        assertEquals(-1, paddle.getDx(), DELTA);

    }

    @Test
    void testMoveLeft() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X, Constants.INIT_PADDLE_Y, Constants.POWERUP_WIDTH,
                Constants.PADDLE_HEIGHT,0 ,0 , "NONE");
        paddle.moveLeft();
        assertEquals(-Constants.PADDLE_SPEED, paddle.getDx(), DELTA);
    }

    @Test
    void testMoveRight() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X, Constants.INIT_PADDLE_Y, Constants.POWERUP_WIDTH,
                Constants.PADDLE_HEIGHT,0 ,0 , "NONE");
        paddle.moveRight();
        assertEquals(Constants.PADDLE_SPEED, paddle.getDx(), DELTA);
    }

    @Test
    void testStopMoving() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X, Constants.INIT_PADDLE_Y, Constants.POWERUP_WIDTH,
                Constants.PADDLE_HEIGHT,0 ,0 , "NONE");
        paddle.moveRight();
        paddle.stopMoving();
        assertEquals(0.0, paddle.getDx(), DELTA);
    }

    @Test
    void testMove() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X, Constants.INIT_PADDLE_Y, Constants.POWERUP_WIDTH,
                Constants.PADDLE_HEIGHT,0 ,0 , "NONE");
        // Set Dx dương để di chuyển sang phải
        paddle.setDx(5.0);
        double initialX = paddle.getX();

        paddle.move(); // Di chuyển


        assertEquals(initialX + 5.0, paddle.getX(), DELTA);
        assertEquals(Constants.INIT_PADDLE_Y, paddle.getY(), DELTA);
    }
}
