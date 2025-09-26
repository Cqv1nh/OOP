package main.entities;

public class Paddle extends MovableObject {
    private double speed = 2.0; // Speed la toc do, khong co huong
    private String currentPowerUp;

    public Paddle(double x, double y, double width,
                  double height, double dx, double dy,
                  String currentPowerUp) {
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
        setX(getX()+getDx());
    }

    public void moveLeft() {
        setDx( -speed);
        move();
    }

    public void moveRight() {
        setDx(speed);
        move();
    }
    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        System.out.println("===");
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
    public boolean applyPowerUp() {
        return false;
    }
}