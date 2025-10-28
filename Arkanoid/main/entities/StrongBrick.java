package entities;
import util.BrickType;

public class StrongBrick extends Brick{
    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 3, BrickType.STRONG, 25);
    }

    @Override
    public void takeHit() {
        //setHitPoints(getHitPoints() - 1);
        if (!isFading()) {
            setHitPoints(getHitPoints() - 1);
        
            // KÍCH HOẠT HIỆU ỨNG MỜ (FADE) khi chết
            if (isDestroyed()) { 
                startFading();
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return getHitPoints() <= 0;
    }
}
