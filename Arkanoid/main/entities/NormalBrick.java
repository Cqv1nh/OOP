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
        for(int i = 0; i < getHeight(); i++) {
            System.out.print("\033["+((int)getY() + i + 1) + ";" + ((int)getX() + 1) + "H");
            for (int j = 0; j < getWidth(); j++) {
                System.out.print("#");
            } 
        }
        System.out.print("\033[0;0H");
    }  
}
