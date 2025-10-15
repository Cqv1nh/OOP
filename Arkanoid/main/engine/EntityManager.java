package engine;

import ui.GameWindow;
import entities.Ball;
import entities.Paddle;
// Thinh
// Lớp này chịu trách nhiệm cập nhật trạng thái 
//(chủ yếu là vị trí) của các thực thể di chuyển được Ball va paddle
public class EntityManager {
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
}
