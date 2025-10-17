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
        // 1. XÓA TẤT CẢ bóng rơi ra ngoài (KHÔNG PHÂN BIỆT CHÍNH/PHỤ)
        balls.removeIf(ball -> ball.getY() > game.getHeight());

        // 2. KIỂM TRA MẤT MẠNG: Nếu danh sách bóng trở nên rỗng sau khi xóa
        if (balls.isEmpty()) {
            game.loseLives();
            if (game.getLives() <= 0) {
                // MẠNG HẾT -> KẾT THÚC GAME
                game.setGameState(util.GameState.GAMEEND); // Chuyển trạng thái GAME OVER
                return; // Dừng cập nhật
            }
            game.resetAfterLifeLost();
            return;
        }
    
        // Lặp qua tất cả các quả bóng hiện có để di chuyển
        for (Ball ball : balls) {
            if (game.isBallLaunched()) {
                ball.move();
            } else if (ball == balls.get(0)) {
                // Giữ QUẢ BÓNG ĐẦU TIÊN trên Paddle khi chưa Launch
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getRadius());
                ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
            }
        }
    }
}
