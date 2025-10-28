package entities;

import util.BrickType;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.NORMAL, 10);
    }

    @Override
    public void takeHit() {
        //setHitPoints(getHitPoints() - 1);
        if (!isFading()) { 
            setHitPoints(getHitPoints() - 1);

            // KÍCH HOẠT HIỆU ỨNG MỜ (FADE)
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
