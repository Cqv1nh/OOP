package entities;
import util.BrickType;

public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.UNBREAKABLE, 0);
    }

    @Override
    public void takeHit() {
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

}