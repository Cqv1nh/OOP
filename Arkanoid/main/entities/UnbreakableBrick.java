package entities;
import util.BrickType;

public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.UNBREAKABLE);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void takeHit() {
        setHitPoints(getHitPoints());
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void update(String input) {

    }

    @Override
    public void render() {
         for (int i = 0; i < getHeight(); i++) {
            // Di chuyen con tro den (y+i,x)
            System.out.print("\033[" + ((int)getY() + i + 1) + ";" + ((int)getX()+1) + "H");
            for (int j = 0; j < getWidth(); j++) {
                System.out.print("^");
            }
        }
    }
}