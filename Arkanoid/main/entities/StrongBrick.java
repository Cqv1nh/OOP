package entities;
import util.BrickType;

public class StrongBrick extends Brick{
    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 3, BrickType.STRONG);
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

    @Override  // Su dung escape code ANSI  
    public void render() {
        for(int i = 0; i < getHeight(); i++) {
            // Di chuyen con tro den (y+i,x)
            System.out.println();
        }
    }  
}
