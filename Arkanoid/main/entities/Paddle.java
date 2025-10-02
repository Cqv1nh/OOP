package entities;

import util.Constants;

public class Paddle extends MovableObject {
    private double speed = 1.0; // Speed la toc do, khong co huong
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

    public void setCurrentPowerUp(String currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public String getCurrentPowerUp() {
        return currentPowerUp;
    }
    
    @Override
    public void move() { // DI chuyen den toa do X moi
        double destX = this.getX() + this.getDx() * 20;
        if (destX < Constants.PADDLE_WIDTH / 2){
            this.setX(Constants.PADDLE_WIDTH / 2);
        } else if (destX > Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH / 2) {
            this.setX(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH / 2);
        }
        else {
            this.setX(destX);
        }
    }

    public void moveLeft() {
        setDx(-speed);
        move();
    }

    public void moveRight() {
        setDx(speed);
        move();
    }
    @Override
    public void update(String input) {
        // TODO Auto-generated method stub
        if ("a".equalsIgnoreCase(input)) {
            moveLeft();  // Sang trai
        }
        else if ("d".equalsIgnoreCase(input)) {
            moveRight(); // Sang phai
        } else {
            setDx(0);   // Dung yen dx = 0
        }
    }

    @Override
    public void render() {
        System.out.print("\033["+((int)getY()+1) + ";" + ((int)getX()+1) + "H");
        for(int i = 0; i < getWidth(); i++) {
            System.out.print("=");
        }
            
    }

    public boolean applyPowerUp() {
        return false;
    }
}