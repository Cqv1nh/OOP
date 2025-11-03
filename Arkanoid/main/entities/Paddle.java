package entities;

import util.Constants;

public class Paddle extends MovableObject {
    private double speed = Constants.PADDLE_SPEED;
    private String currentPowerUp;
    private Ball ball;

    /**
     * Getter cho Ball.
     *
     * @return bóng.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Setter cho Ball.
     *
     * @param ball
     */
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    /**
     * Constructor 7 tham số.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param dx
     * @param dy
     * @param currentPowerUp powerup đang áp dụng.
     */
    public Paddle(double x, double y, double width, double height ,
                  double dx, double dy, String currentPowerUp) {
        super(x, y, width, height, dx, dy);
        this.currentPowerUp = currentPowerUp;
    }

    /**
     * Setter cho tốc độ.
     *
     * @param speed tốc độ.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter cho tốc độ.
     *
     * @return tốc độ.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter cho powerup hiện tại.
     *
     * @param currentPowerUp PowerUp hiện tại.
     */
    public void setCurrentPowerUp(String currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    /**
     * Getter cho powerup hiện tại.
     *
     * @return powerup.
     */
    public String getCurrentPowerUp() {
        return currentPowerUp;
    }

    /**
     * Method di chuyển.
     */
    @Override
    public void move() { // DI chuyen den toa do X moi
        this.setX(this.getX() + this.getDx());
    }

    /**
     * Method di chuyển sang trái.
     */
    public void moveLeft() {
        setDx(-speed);
    }

    /**
     * Method di chuyển sang phải.
     */
    public void moveRight() {
        setDx(speed);
    }

    /**
     * Method dừng di chuyển.
     */
    public void stopMoving() {
        setDx(0);
    }

    /**
     * Áp dụng PowerUp
     *
     * @return false.
     */
    public boolean applyPowerUp() {
        return false;
    }

}