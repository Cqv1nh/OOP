package engine;

import managers.LevelState2;
import ui.GameWindow;
import java.util.ArrayList;
import entities.Ball;
import entities.Paddle;
import util.Constants;

// Thinh
// Lớp này chịu trách nhiệm cập nhật trạng thái 
//(chủ yếu là vị trí) của các thực thể di chuyển được Ball va paddle
public class EntityManager {

    public EntityManager() {
    }

    public void updateEntities(GameWindow game, int panelWidth) {
        updatePaddle(game.getPaddle(), panelWidth);
        updateBall(game);
    }

    public void updatePaddle(Paddle paddle, int panelWidth) {
        // Xu ly logic Paddle dau tien
        // Giu paddle trong man hinh
        paddle.move();
        if (paddle.getX() < 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() > panelWidth) {
            paddle.setX(panelWidth - paddle.getWidth());
        }
    }

    public void updateBall(GameWindow game) {
        Ball ball = game.getBall();
        Paddle paddle = game.getPaddle();
        // Neu o trang thai phong
        if (game.isBallLaunched()) {
            ball.move();
        } else {
            // Giu bong tren thanh
            ball.setX(paddle.getX()+paddle.getWidth() / 2 - ball.getRadius());
            ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
        }
    }

    //V2.
    public void updateEntities(LevelState2 levelState2, int panelWidth) {
        updatePaddle(levelState2.getPaddle(), panelWidth);
        updateBall(levelState2);
    }

    //V2.
    public void updateBall(LevelState2 game) {
        ArrayList<Ball> balls = game.getBalls();
        Paddle paddle = game.getPaddle();


        // Neu o trang thai phong
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
