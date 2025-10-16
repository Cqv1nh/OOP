package engine;

import ui.GameWindow;
import java.util.ArrayList;
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
        // /cũ
        //Ball ball = game.getBall();

        // /mới
        ArrayList<Ball> balls = game.getBalls();
        Paddle paddle = game.getPaddle();

        // /cũ
        /* Neu o trang thai phong
        if (game.isBallLaunched()) {
            ball.move();
        } else {
            // Giu bong tren thanh 
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getRadius());
            ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
        } */

        // /mới
        //XỬ LÝ BÓNG RƠI: Loại bỏ các quả bóng đã rơi
        //Chỉ loại bỏ nếu có nhiều hơn 1 quả bóng
        balls.removeIf(ball -> ball.getY() > game.getHeight() && balls.size() > 1);
    
        // KIỂM TRA MẤT MẠNG: Nếu quả bóng duy nhất (balls.get(0)) bị rơi
        if (balls.size() == 1 && balls.get(0).getY() > game.getHeight()) {
            game.loseLives();
            game.resetAfterLifeLost();
            return;
        }
    
        // Lặp qua tất cả các quả bóng hiện có để di chuyển
        for (Ball ball : balls) {
            // Neu o trang thai phong
            if (game.isBallLaunched()) {
                ball.move();
            } else if (ball == balls.get(0)) {
                // CHỈ quả bóng đầu tiên được giữ trên thanh Paddle khi chưa Launch
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getRadius());
                ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
            }
        }
    }
}
