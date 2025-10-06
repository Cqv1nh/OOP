package entities;

import main.entities.Ball;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25; //Tốc độ bóng sẽ tăng 1.25 lần.

    //Construtor có tham số.
    public FastBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, width, height, "FAST_BALL", duration); //Truyền type cố định rồi.
    }

    //Set tốc độ bóng trong thời gian Power Up.
    private void modifySpeedBallWhenPowerUp(Ball ball, double multiplier) {
        ball.setSpeed(ball.getSpeed() * multiplier);
    }

    //Áp dụng hiệu ứng lên thanh trong tgian PU.
    @Override
    public void applyEffect(Paddle paddle) {
        Ball ball = paddle.getBall();
        if (ball != null) {
            modifySpeedBallWhenPowerUp(ball, SPEED_MULTIPLIER);
        }
    }

    //Xóa bỏ hiệu ứng sau khi hết duration.
    @Override
    public void removeEffect(Paddle paddle) {
        Ball ball = paddle.getBall();
        if (ball != null) {
            modifySpeedBallWhenPowerUp(ball, 1 / SPEED_MULTIPLIER); //Nhân với nghịch đảo của speed tăng.
        }
    }

    //Cập nhật vị trí đối tượng.
    @Override
    public void update(String input) {
        setX(getX());
        setY(getY() + 1); //tăng y lên 1 do đi xuống.
    }

    //Vẽ đối tượng ra màn hình.
    @Override
    public void render() {
        // Vẽ ký tự '*' tại vị trí (x, y) theo ANSI escape code
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H*");
    }

}