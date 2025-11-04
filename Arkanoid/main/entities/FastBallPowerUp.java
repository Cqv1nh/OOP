package entities;

import java.util.ArrayList;
import managers.LevelState;
import util.Constants;

public class FastBallPowerUp extends PowerUp {
    private static final double SPEED_MULTIPLIER = 1.25;

    /**
     * Construtor 5 tham số.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param duration thời gian hiệu lực.
     */
    public FastBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT,
                "FAST_BALL", Constants.POWERUP_DURATION);
        this.initialDuration = duration;
    }

    /**
     * Áp dụng hiệu ứng.
     *
     * @param game
     */
    @Override
    public void applyEffect(LevelState game) {
        game.getActivePowerUpEffects().add(this);
        ArrayList<Ball> balls = game.getBalls();
        //Lặp qua tất cả Balls và tăng tốc độ
        for (Ball ball : balls) {
            if (ball != null) {
                ball.setSpeed(ball.getSpeed() * SPEED_MULTIPLIER);
            }
        }
    }

    /**
     * Xóa bỏ hiệu ứng.
     *
     * @param game
     */
    @Override
    public void removeEffect(LevelState game) {
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