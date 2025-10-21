package entities;

import managers.LevelState2;
import util.Constants;
import ui.GameWindow;

import java.util.ArrayList;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25; //Tốc độ bóng sẽ tăng 1.25 lần.

    //Construtor có tham số.
    public FastBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT,
                "FAST_BALL", Constants.POWERUP_DURATION); //Truyền type cố định rồi.
        // Gian gia tri ban dau bang gia tri thoi gian vua bat dau
        this.initialDuration = duration;
        // Chi dung cho cac class co thoi gian
    }

    //Áp dụng hiệu ứng lên thanh trong tgian PU. sửa
    @Override
    public void applyEffect(GameWindow game) {
        game.getActivePowerUpEffects().add(this);
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
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "HF");
    }


    //V2.
    @Override
    public void applyEffect(LevelState2 game) {
        game.getActivePowerUpEffects().add(this);
        ArrayList<Ball> balls = game.getBalls();
        //Lặp qua tất cả Balls và tăng tốc độ
        for (Ball ball : balls) {
            if (ball != null) {
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }
    }

    @Override
    public void removeEffect(LevelState2 game) {
        ArrayList<Ball> balls = game.getBalls();
        // Lặp qua tất cả Balls và giảm tốc độ về ban đầu
        for (Ball ball : balls) {
            if (ball != null) {
                // Chia tốc độ hiện tại cho hằng số nhân
                ball.setSpeed(ball.getSpeed() / SPEED_MULTIPLIER);
            }
        }
    }
}