import javax.swing.*;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import util.Constants;
import util.GameState;

import java.util.ArrayList;
// Class thuc hien vong lap game
public class GameLoop {
    private GameWindow game;
    private GamePanel panel;
    private Timer timer;
    // Constructor
    public GameLoop(GameWindow game, GamePanel panel) {
        this.game = game;
        this.panel = panel;
    }

    public void start() {
        timer = new Timer(16, e -> update());
        // GAME LOOP 60 FPS
        timer.start();
    }

    private void update() {
        // Neu chua vao game thi k chay Timer
        if (!game.getGameState().equals(GameState.GAMEPLAYING)) {
            panel.repaint();
            return;
        }

        Ball ball = game.getBall();
        Paddle paddle = game.getPaddle();
        ArrayList<Brick> brickList = game.getBricks();

        double speed = ball.getSpeed(); // Lay gia tri speed
        if (game.isBallLaunched()) {
            ball.move();
            // va cham voi tuong
            if (ball.getX() <= 0) {
                ball.setX(0);
                ball.setDirectionX(Math.abs(ball.getDirectionX())); // Bat sang phai
                ball.setDx(speed * ball.getDirectionX());
            }
            if (ball.getX() + ball.getRadius() * 2 >= Constants.SCREEN_WIDTH) {
                ball.setX(Constants.SCREEN_WIDTH - ball.getRadius() * 2);
                ball.setDirectionX(-Math.abs(ball.getDirectionX())); // Bat sang trai
                ball.setDx(speed * ball.getDirectionX());
            }
            if (ball.getY() <= 0) {
                ball.setY(0);
                ball.setDirectionY(Math.abs(ball.getDirectionY())); // Bat xuong duoi
                ball.setDy(speed * ball.getDirectionY()); // De qua bong roi xuong
            }

            // Va cham voi paddle
            if (ball.getY() + ball.getRadius() * 2 >= paddle.getY() 
                && ball.getX() + ball.getRadius() * 2 >= paddle.getX() 
                && ball.getX() <= paddle.getX() + paddle.getWidth()
                && ball.getY() < paddle.getY() + paddle.getHeight()) {

                // Tính vị trí tương đối va chạm so với tâm paddle
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
                double hitPosition = (ball.getX() + ball.getRadius()) - paddleCenter;

                // Chuẩn hóa hitPosition thành [-1, 1]
                double normalized = hitPosition / (paddle.getWidth() / 2.0);

                // Giới hạn để không ra ngoài [-1,1]
                if (normalized < -1) normalized = -1;
                if (normalized > 1) normalized = 1;

                // Góc bật lại: từ 150° (trái) → 30° (phải)
                double bounceAngle = Math.toRadians(150 - 120 * (normalized + 1) / 2.0);
                bounceAngle += Math.toRadians((Math.random() - 0.5) *10); // them ngau nhien + - 5 do
                // Cập nhật hướng di chuyển mới
            
                ball.setDirectionX(Math.cos(bounceAngle));
                ball.setDirectionY(-Math.abs(Math.sin(bounceAngle)));

                // Áp lại tốc độ (nếu Ball.java dùng dx, dy = speed * direction)
                ball.setDx(speed * ball.getDirectionX());
                ball.setDy(speed * ball.getDirectionY());
            }

            // Va cham voi brick
            ArrayList<Brick> bricksToRemove = new ArrayList<>();
            for (Brick b : brickList) {
                if (ball.getX() + ball.getRadius() * 2 >= b.getX()
                    && ball.getX() <= b.getX() + b.getWidth()
                    && ball.getY() + ball.getRadius() * 2 >= b.getY()
                    && ball.getY() <= b.getY() + b.getHeight()) {

                    // Kiểm tra va chạm ngang hay dọc
                    double overlapLeft = (ball.getX() + ball.getRadius() * 2) - b.getX();
                    double overlapRight = (b.getX() + b.getWidth()) - ball.getX();
                    double overlapTop = (ball.getY() + ball.getRadius() * 2) - b.getY();
                    double overlapBottom = (b.getY() + b.getHeight()) - ball.getY();

                    double minOverlapX = Math.min(overlapLeft, overlapRight);
                    double minOverlapY = Math.min(overlapTop, overlapBottom);

                    // Bóng bật lại theo hướng ít chồng lấn hơn (thực tế hơn)
                    if (minOverlapX < minOverlapY) {
                        ball.setDirectionX(-ball.getDirectionX());
                        ball.setDx(speed * ball.getDirectionX());
                    } else {
                        ball.setDirectionY(-ball.getDirectionY());
                        ball.setDy(speed * ball.getDirectionY());
                    }
                    b.takeHit();
                    if (b.isDestroyed()) {
                        bricksToRemove.add(b);
                    }
                    break; // tránh xử lý trùng lặp nhiều viên
                }
            }
            brickList.removeAll(bricksToRemove);
            if (brickList.isEmpty()) {
                game.setGameState(GameState.LEVELCLEAR);
            }

            if (ball.getY() > Constants.SCREEN_HEIGHT) {
                // ballLaunched = false;
                game.setGameState(GameState.GAMEEND);
            }
        } else {
            ball.setX(paddle.getX()+paddle.getWidth() / 2 - ball.getRadius());
            ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
        }
        panel.repaint();
    }
}
