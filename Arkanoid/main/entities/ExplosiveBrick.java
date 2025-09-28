package entities;

import java.util.List;
import util.BrickType;

public class ExplosiveBrick extends Brick{

    public ExplosiveBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.EXPLOSIVE);
    }

    @Override
    public void takeHit() {
        setHitPoints(getHitPoints() - 1);
    }

    @Override
    public boolean isDestroyed() {
        // TODO Auto-generated method stub
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
                System.out.print("%");
            } 
        }
       
    }
    
    public void explode(List<Brick> Brick) {
        for(Brick other : Brick) {
            if (other != this) {
                double dentaX = Math.abs(other.getX() - this.getX());
                double dentaY = Math.abs(other.getY() - this.getY());

                if (dentaX <= 1 && dentaY <= 1) {
                    other.takeHit();
                }
                // if (explosiveBrick.isDestroyed()) {
                //     explosiveBrick.explode(allBricks);
                // }
            }      
        }
    }
}
