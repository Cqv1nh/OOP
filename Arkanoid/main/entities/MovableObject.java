package entities;

public abstract class MovableObject extends GameObject {
    private double dx; // Tốc độ di chuyển theo trục x.
    private double dy; // Tốc độ di chuyển theo trục y.

    /**
     * Constructor 4 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width rộng.
     * @param height cao.
     */
    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }

    /**
     * Constructor 6 tham số (thêm tốc độ di chuyển).
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width rộng.
     * @param height cao.
     * @param dx tốc độ di chuyển theo x.
     * @param dy tốc độ di chuyển theo y.
     */
    public MovableObject(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Getter cho tốc độ di chuyển theo x.
     *
     * @return tốc độ theo x.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Setter cho tốc độ di chuyển theo x.
     *
     * @param dx
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Getter cho tốc độ di chuyển theo y.
     *
     * @return tốc độ theo y.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Setter cho tốc độ di chuyển theo y.
     *
     * @param dy
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Method trừu tượng cho di chuyển.
     */
    public abstract void move();
}