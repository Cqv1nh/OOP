package main.entities;

public abstract class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Constructor .
     * @param x toa do x
     * @param y toa do y
     * @param width chieu rong 
     * @param height chieu ngang
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get toa do x .
     * 
     * @return toa do x
     */
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public abstract void update(); // Cap nhat vi tri theo phim bam
    public abstract void render(); // Ve doi tuong trong man hinh terminal
} 