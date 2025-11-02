package entities;
import util.BrickType;

public class UnbreakableBrick extends Brick {
    /**
     * Constructor 4 tham số (3 tham số còn lại cố định cho
     * gạch không thể phá vỡ).
     *
     * @param x x.
     * @param y y.
     * @param width dài.
     * @param height rộng.
     */
    public UnbreakableBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.UNBREAKABLE, 0);
    }

    /**
     * Method này không làm gì vì là gạch k bị phá vỡ.
     */
    @Override
    public void takeHit() {
    }

    /**
     * Method này trả F vì là gạch k bị phá vỡ.
     *
     * @return F.
     */
    @Override
    public boolean isDestroyed() {
        return false;
    }

}