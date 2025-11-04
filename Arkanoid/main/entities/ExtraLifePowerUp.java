package entities;

import managers.LevelState;
import util.Constants;

public class ExtraLifePowerUp extends PowerUp {
    private static final double INSTANT_DURATION = 0.0;

    /**
     * Constructor 5 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width chiều rộng.
     * @param height chiều cao.
     * @param duration thời gian hiệu lực.
     */
    public ExtraLifePowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXTRA_LIFE", INSTANT_DURATION);
    }

    /**
     * Áp dụng hiệu ứng thêm 1 mạng.
     *
     * @param game trạng thái game hiện tại.
     */
    @Override
    public void applyEffect(LevelState game) {
        game.addLife();
    }

    /**
     * Xóa bỏ hiệu ứng (không áp dụng cho powerup này).
     *
     * @param game trạng thái game hiện tại.
     */
    @Override
    public void removeEffect(LevelState game) {
    }
}

