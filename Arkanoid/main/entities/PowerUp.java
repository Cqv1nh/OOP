package entities;

import managers.LevelState;

public abstract class PowerUp extends GameObject {
    private double duration; // Thời gian hiệu lực.
    private String type; // Loại powerup.
    private double fallSpeed = 3.0; // Tốc độ rơi vật phẩm là 3.
    protected double initialDuration; // Thời gian ban đầu của powerup.

    /**
     * Constructor 6 tham số.
     *
     * @param x x.
     * @param y y.
     * @param width rộng.
     * @param height cao.
     * @param type loại.
     * @param duration thời gian hiệu lực.
     */
    public PowerUp(double x, double y, double width, double height, String type, double duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    /**
     * Getter cho loại Powerup.
     *
     * @return loại powerup.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter cho loại powerup.
     *
     * @param type loại.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter cho thời gian hiệu lực.
     *
     * @return tgian hiệu lực.
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Setter cho tgian hiệu lực.
     *
     * @param duration tgian.
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Getter cho tgian ban đầu của PowerUp.
     *
     * @return tgian ban đầu.
     */
    public double getInitialDuration() {
        return initialDuration;
    }

    /**
     * Kiểm tra hiệu ứng đã ra khỏi màn hỉnh chưa.
     *
     * @param worldHeight giới hạn màn hình.
     * @return T or F.
     */
    public boolean isOutOfBounds (int worldHeight) {
        if (this.getY() < worldHeight) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Getter cho tốc độ rơi của bóng.
     *
     * @return tốc độ rơi.
     */
    public double getFallSpeed() {
        return fallSpeed;
    }

    /**
     * Áp dụng powerup (sẽ được lớp con Override).
     *
     * @param game
     */
    public abstract void applyEffect(LevelState game);

    /**
     * Loại bỏ powerup (sẽ được lớp con Override).
     *
     * @param game
     */
    public abstract void removeEffect(LevelState game);
}