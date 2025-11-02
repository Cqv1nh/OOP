package entities;

import util.Constants;

public class Ball extends MovableObject {
    private double speed; //Tốc độ bóng.
    private double directionX; //Hướng đi của bóng theo chiều ngang.
    private double directionY; //Hướng đi của bóng theo chiều dọc.
    private double radius; //Bán kính của bóng.

    /**
     * Constructor không có tham số.
     */
    public Ball() {
        super(Constants.INIT_BALL_X, Constants.INIT_BALL_Y,
                Constants.BALL_DIAMETER, Constants.BALL_DIAMETER, 0, 0); 
        this.speed = Constants.BALL_SPEED;
        this.directionX = -1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.radius = Constants.BALL_DIAMETER / 2;
    }

    /**
     * Constructor 4 tham số: speed, directionX, directionY.
     *
     * @param speed tốc độ.
     * @param directionX hướng đi theo chiều x.
     * @param directionY hướng đi theo chiều y.
     * @param radius bán kính.
     */
    public Ball(double speed, double directionX, double directionY, double radius) {
        super(0, 0, 1, 1, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.radius = radius;
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
     * Setter cho tốc độ.
     *
     * @param speed tốc độ.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        this.setDx(speed * directionX); //cập nhật tốc độ di chuyển theo chiều x.
        this.setDy(speed * directionY); //cập nhật tốc độ di chuyển theo chiều y.
    }

    /**
     * Getter cho hướng theo x.
     *
     * @return hướng theo x.
     */
    public double getDirectionX() {
        return directionX;
    }

    /**
     * Setter cho hướng theo x.
     *
     * @param directionX hướng theo x.
     */
    public void setDirectionX(double directionX) {
        this.directionX = directionX;
        this.setDx(speed * directionX); //Thay đổi directionX thì setDx cx thay đổi theo.
    }

    /**
     * Getter cho hướng theo y.
     *
     * @return hướng theo y.
     */
    public double getDirectionY() {
        return directionY;
    }

    /**
     * Setter cho hướng theo y.
     *
     * @param directionY hướng theo y.
     */
    public void setDirectionY(double directionY) {
        this.directionY = directionY;
        this.setDy(speed * directionY);
    }

    /**
     * Getter cho bán kính.
     *
     * @return bán kính.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Setter cho bán kính.
     *
     * @param radius bán kính.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Method được ghi đè move(), set lại speed ball theo x,y.
     */
    @Override
    public void move() {
        this.setX(this.getX() + this.getDx());
        this.setY(this.getY() + this.getDy());
    }
}