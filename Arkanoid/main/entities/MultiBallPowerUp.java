package entities;

import java.util.ArrayList;
import managers.LevelState;
import util.Constants;

public class MultiBallPowerUp extends PowerUp {
    private static final double INSTANT_DURATION = 0.0;

    /**
     * Constructor 5 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width rộng.
     * @param height cao.
     * @param duration thời gian hiệu lực.
     */
    public MultiBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "MULTI_BALL", INSTANT_DURATION);
    }

    /**
     * Áp dụng hiệu ứng.
     *
     * @param game trạng thái game.
     */
    @Override
    public void applyEffect(LevelState game) {
        ArrayList<Ball> currentBalls = game.getBalls();

        // Tạo thêm 1 bóng.
        if (currentBalls.isEmpty()) {
            return;
        }

        // Lấy quả bóng gốc để sao chép thuộc tính.
        Ball originalBall = currentBalls.get(0);

        // Tạo ball mới gần ball hiện tại, hướng x ngược.
        Ball newBall = new Ball(
                originalBall.getSpeed(),
                -originalBall.getDirectionX(),
                originalBall.getDirectionY(),
                originalBall.getRadius()
        );

        // Đặt vị trí ban đầu gần quả bóng gốc
        newBall.setX(originalBall.getX() + 5);
        newBall.setY(originalBall.getY());

        // Thêm quả bóng mới vào game
        game.addBall(newBall);
    }

    /**
     * Xóa bỏ hiệu ứng.
     *
     * @param game trạng thái game.
     */
    @Override
    public void removeEffect(LevelState game) {
    }
}
