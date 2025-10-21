package entities;

import managers.LevelState2;
import util.Constants;

import java.util.ArrayList;

public class MultiBallPowerUp extends PowerUp {
    private static final double INSTANT_DURATION = 0.0;

    public MultiBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "MULTI_BALL", INSTANT_DURATION);
    }

    @Override
    public void applyEffect(LevelState2 game) {
        ArrayList<Ball> currentBalls = game.getBalls();

        // Tạo thêm 1 bóng.
        if (currentBalls.isEmpty()) {
            return;
        }

        // Lấy quả bóng gốc để sao chép thuộc tính
        Ball originalBall = currentBalls.get(0);

        //Tạo ball mới gần ball hiện tại, hướng x ngược.
        Ball newBall = new Ball(
                originalBall.getSpeed(),
                -originalBall.getDirectionX(), // Hướng X ngược
                originalBall.getDirectionY(),
                originalBall.getRadius()
        );

        // Đặt vị trí ban đầu gần quả bóng gốc
        // Đặt vị trí lệch một chút (ví dụ: +5 đơn vị X) để tránh va chạm ngay lập tức
        newBall.setX(originalBall.getX() + 5);
        newBall.setY(originalBall.getY());

        // Thêm quả bóng mới vào game
        game.addBall(newBall); // Sử dụng phương thức addBall() mới trong GameWindow
    }

    @Override
    public void removeEffect(LevelState2 game) {

    }
}
