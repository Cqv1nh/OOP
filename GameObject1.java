public abstract class GameObject1 {
    private double x;
    private double y;
    private int width;
    private int height;

    /**
     * Constructor .
     * @param x toa do x
     * @param y toa do y
     * @param width chieu rong 
     * @param height chieu ngang
     */
    public GameObject1(double x, double y, int width, int height) {
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

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void update(); // Cap nhat vi tri theo phim bam
    public abstract void render(); // Ve doi tuong trong man hinh terminal
    
} 