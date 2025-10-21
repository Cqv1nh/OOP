package entities;

import managers.LevelState2;
import util.Constants;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y, double width, double height, double duration) {
        // Sử dụng Constants cho kích thước và POWERUP_DURATION_MS cho duration
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXPAND_PADDLE", Constants.POWERUP_DURATION); 
        this.initialDuration = duration;
        // Gian gia tri ban dau bang gia tri thoi gian vua bat dau
    }



    // V2.
    @Override
    public void applyEffect(LevelState2 game) {
        // Lưu PowerUp này vào danh sách active để xử lý duration
        game.getActivePowerUpEffects().add(this);
        game.expandPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }

    @Override
    public void removeEffect(LevelState2 game) {
        game.shrinkPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }
}