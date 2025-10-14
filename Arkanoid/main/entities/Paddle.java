package entities;

import util.Constants;

public class Paddle extends MovableObject {
    private double speed = Constants.PADDLE_SPEED; // Speed la toc do, khong co huong
    private String currentPowerUp;
    /*Thêm code: Thêm object ball vào class Paddle:*/
    private Ball ball;

    /*Getter cho Ball:*/
    public Ball getBall() {
        return ball;
    }

    /*Setter cho Ball*/
    public void setBall(Ball ball) {
        this.ball = ball;
    }
    /*Hết thêm code.*/

    public Paddle(double x, double y, double width, double height ,
                  double dx, double dy, String currentPowerUp) {
        super(x, y, width, height, dx, dy);
        this.currentPowerUp = currentPowerUp;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    // Xem xet co dung k
    public void setCurrentPowerUp(String currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public String getCurrentPowerUp() {
        return currentPowerUp;
    }
    
    @Override
    public void move() { // DI chuyen den toa do X moi
        this.setX(this.getX() + this.getDx());
    }

    public void moveLeft() {
        setDx(-speed);
    }

    public void moveRight() {
        setDx(speed);
    }

    public void stopMoving() {
        setDx(0);
    }
    // Chuyen phan xu ly va cham voi man hinh vao game loop
    public boolean applyPowerUp() {
        return false;
    }

    @Override
    public void update(String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}