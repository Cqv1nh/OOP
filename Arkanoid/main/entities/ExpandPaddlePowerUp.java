package entities;

import util.Constants;
import ui.GameWindow;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y, double width, double height, double duration) {
        // Sử dụng Constants cho kích thước và POWERUP_DURATION_MS cho duration
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXPAND_PADDLE", Constants.POWERUP_DURATION); 
        this.initialDuration = duration;
        // Gian gia tri ban dau bang gia tri thoi gian vua bat dau
    }

    @Override
    // Áp dụng effect.
    public void applyEffect(GameWindow game) {
        // Lưu PowerUp này vào danh sách active để xử lý duration
        game.getActivePowerUpEffects().add(this); 
        game.expandPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }

    @Override
    // Xóa bỏ effect.
    public void removeEffect(GameWindow game) {
        game.shrinkPaddle(Constants.PADDLE_EXPAND_AMOUNT);
    }

    @Override
    // Cập nhật vị trí đối tượng.
    public void update(String input) {
        setY(getY() + getFallSpeed());
    }

    @Override
    // Vẽ đối tượng ra màn hình.
    public void render(){
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H#");
    }
}