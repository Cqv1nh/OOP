package entities;
import util.Constants;
import ui.GameWindow;
import entities.Ball;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25; //Tốc độ bóng sẽ tăng 1.25 lần.

    //Construtor có tham số.
    public FastBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "FAST_BALL", duration); //Truyền type cố định rồi.
    }

    //Set tốc độ bóng trong thời gian Power Up.
    private void modifySpeedBallWhenPowerUp(Ball ball, double multiplier) {
        ball.setSpeed(ball.getSpeed() * multiplier);
    }

    //Áp dụng hiệu ứng lên thanh trong tgian PU. sửa
    @Override
    public void applyEffect(GameWindow game) {
        Ball ball = game.getBall();
        if (ball != null) {
            ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
        }
    }

    //Xóa bỏ hiệu ứng sau khi hết duration. sửa
    @Override
    public void removeEffect(GameWindow game) {
        Ball ball = game.getBall();
        if (ball != null) {
            ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER); //Chia cho 1.25.
        }
    }

    //Cập nhật vị trí đối tượng. sửa
    @Override
    public void update(String input) {
        setY(getY() + getFallSpeed()); //tăng y lên 1 do đi xuống, lấy fallspeed = 1.
    }

    //Vẽ đối tượng ra màn hình.
    @Override
    public void render() {
        // Vẽ ký tự '*' tại vị trí (x, y) theo ANSI escape code
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H*");
    }

}