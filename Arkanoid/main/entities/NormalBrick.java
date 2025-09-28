package entities;

import util.BrickType;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.NORMAL);
    }

    @Override
    public void takeHit() {
        setHitPoints(getHitPoints() - 1);
    }

    @Override
    public boolean isDestroyed() {
        return getHitPoints() <= 0;
    }

    @Override
    public void update(String input) {
        
    }

    @Override
    public void render() {
        for (int i = 0; i < getHeight(); i++) {
            // Di chuyển con trỏ đến đúng vị trí hàng
            System.out.print("\033[" + ((int)getY() + i + 1) + ";" + ((int)getX() + 1) + "H");
            
            // In một hàng brick
            for (int j = 0; j < getWidth(); j++) {
                System.out.print("#");
            }
        }
    }

}
