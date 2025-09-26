package main.entities;

public abstract class MovableObject extends GameObject {
    private double dx; // Toc do di chuyen theo truc x
    private double dy; // Toc do di chuyen theo truc y

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }
    public MovableObject(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }
    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }
    public void setDy(double dy) {
        this.dy = dy;
    }

    public abstract void move();
}