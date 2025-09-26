package main.entities;

public class Paddle extends MovableObjext {
    private double speed; // Speed la toc do, khong co huong
    private String currentPowerUp;

    public Paddle(double x, double y, double width,
                  double height, double dx, double dy,
                  String currentPowerUp) {
        super(x, y, width, height, dx, dy);
        this.speed = Math.abs(dx);
        this.currentPowerUp = currentPowerUp;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    @Override




}