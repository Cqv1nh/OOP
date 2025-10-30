import entities.Ball;
import org.junit.jupiter.api.Test;
import util.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallTest {
    private static final double DELTA = 0.0001;

    @Test
    void testDefaultConstructor() {
        Ball ball = new Ball();

        // 1. Kiểm tra vị trí ban đầu
        assertEquals(Constants.INIT_BALL_X, ball.getX(), DELTA);
        assertEquals(Constants.INIT_BALL_Y, ball.getY(), DELTA);

        // 2. Kiểm tra tốc độ và hướng
        assertEquals(Constants.BALL_SPEED, ball.getSpeed(), DELTA);
        assertEquals(-1.0, ball.getDirectionX(), DELTA);
        assertEquals(-1.0, ball.getDirectionY(), DELTA);

        // 3. Kiểm tra vận tốc di chuyển (dx, dy)
        assertEquals(Constants.BALL_SPEED * -1.0, ball.getDx(), DELTA);
        assertEquals(Constants.BALL_SPEED * -1.0, ball.getDy(), DELTA);

        // 4. Kiểm tra bán kính
        assertEquals(Constants.BALL_DIAMETER / 2, ball.getRadius(), DELTA);
    }

    @Test
    void testNormalConstructor() {
        double testSpeed = 10.0;
        double testDirX = 0.5;
        double testDirY = 0.8;
        double testRadius = 5.0;

        Ball ball = new Ball(testSpeed, testDirX, testDirY, testRadius);

        assertEquals(testSpeed, ball.getSpeed(), DELTA);
        assertEquals(testDirX, ball.getDirectionX(), DELTA);
        assertEquals(testDirY, ball.getDirectionY(), DELTA);

        // 2. Kiểm tra vận tốc di chuyển (dx, dy)
        assertEquals(testSpeed * testDirX, ball.getDx(), DELTA);
        assertEquals(testSpeed * testDirY, ball.getDy(), DELTA);

        // 3. Kiểm tra bán kính
        assertEquals(testRadius, ball.getRadius(), DELTA);
    }

    @Test
    void testMove() {
        Ball ball = new Ball(5.0, 1.0, -1.0, 1.0);

        ball.setX(10.0);
        ball.setY(20.0);

        ball.move();

        assertEquals(15.0, ball.getX(), DELTA);
        assertEquals(15.0, ball.getY(), DELTA);
    }
}
