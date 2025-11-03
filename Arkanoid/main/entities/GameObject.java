package entities;

public abstract class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Constructor 4 tham số.
     * @param x x
     * @param y y
     * @param width chiều rộng.
     * @param height chieu cao.
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter cho x.
     * 
     * @return x.
     */
    public double getX() {
        return x;
    }

    /**
     * Setter cho x.
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter cho y.
     *
     * @return y.
     */
    public double getY() {
        return y;
    }

    /**
     * Setter cho y.
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Getter cho độ rộng.
     *
     * @return độ rộng.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Setter cho độ rộng.
     *
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Getter cho độ cao.
     *
     * @return độ cao
     */
    public double getHeight() {
        return height;
    }

    /**
     * Setter cho độ cao.
     *
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Cập nhật hiệu ứng.
     */
    public void update() {
    }
} 