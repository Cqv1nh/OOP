package engine;

import entities.Ball;
import entities.Paddle;
import java.util.ArrayList;
import managers.LevelState2;
import util.Constants;

public class EntityManager {
    /**
     * Constructor không có tham số.
     */
    public EntityManager() {
    }

    /**
     * Cập nhật thanh.
     *
     * @param paddle
     * @param panelWidth
     */
    public void updatePaddle(Paddle paddle, int panelWidth) {
        paddle.move();
        if (paddle.getX() < 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() > panelWidth) {
            paddle.setX(panelWidth - paddle.getWidth());
        }
    }

    /**
     * Cập nhật vật thể.
     *
     * @param levelState2
     * @param panelWidth
     */
    public void updateEntities(LevelState2 levelState2, int panelWidth) {
        updatePaddle(levelState2.getPaddle(), panelWidth);
        updateBall(levelState2);
    }

    /**
     * Cập nhật các quả bóng.
     *
     * @param game
     */
    public void updateBall(LevelState2 game) {
        ArrayList<Ball> balls = game.getBalls();
        Paddle paddle = game.getPaddle();
        balls.removeIf(ball -> ball.getY() > Constants.SCREEN_HEIGHT);

        if (balls.isEmpty()) {
            game.loseLives();
        }

        if (game.isBallLaunched()) {
            for (Ball ball : balls) {
                ball.move();
            }
        } else {
            Ball ball = balls.get(0);
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getRadius());
            ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
        }
    }
}
