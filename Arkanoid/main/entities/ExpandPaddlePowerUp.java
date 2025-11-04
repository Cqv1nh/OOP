package entities;

import managers.LevelState;
import util.Constants;

public class ExpandPaddlePowerUp extends PowerUp {
    /**
     * Constructor 5 tham số.
     *
     * @param x x.
     * @param y y.
     * @param width dài.
     * @param height rộng.
     * @param duration thời gian hiệu lực.
     */
    public ExpandPaddlePowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXPAND_PADDLE", Constants.POWERUP_DURATION); 
        this.initialDuration = duration;
    }

    /**
     * Áp dụng powerup.
     *
     * @param game trạng thái game hiện tại.
     */
    @Override
    public void applyEffect(LevelState game) {
        // Lưu PowerUp này vào danh sách active để xử lý duration
        game.getActivePowerUpEffects().add(this);
        game.expandPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }

    /**
     * Xóa bỏ hiệu ứng.
     *
     * @param game trạng thái game hiện tại.
     */
    @Override
    public void removeEffect(LevelState game) {
        game.shrinkPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }
}