package entities;
import util.Constants;
import ui.GameWindow;
public class ExtraLifePowerUp extends PowerUp {

    public ExtraLifePowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXTRA_LIFE", duration);
    }

    //Áp dụng hiệu ứng thêm 1 mạng.
    @Override
    public void applyEffect(GameWindow game) {
        game.addLife();
    }

    //Xóa bỏ hiệu ứng.
    @Override
    public void removeEffect(Paddle paddle) {
    } //Hàm này k làm gì cả vì ng chơi mất mạng khi không còn bóng.

    //Cập nhật vị trí đối tượng.
    @Override
    public void update(String input) {
        setY(getY() + getFallSpeed()); //tăng y lên 1 do đi xuống, lấy fallSpeed = 1.
    }

    @Override
    public void render() {
        // Vẽ ký tự '*' tại vị trí (x, y) theo ANSI escape code
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H*");
    }
}

