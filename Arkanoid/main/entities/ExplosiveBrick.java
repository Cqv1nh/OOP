package entities;

import java.util.List;
import util.BrickType;
import util.Constants;

public class ExplosiveBrick extends Brick{
    // Ban kinh vu no, gach nao trong pham vi se bi takeHis
    private static final double BLAST_RADIUS = Constants.BRICK_WIDTH * 1.5;

    public ExplosiveBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.EXPLOSIVE, 50);
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
                System.out.print("*");
            } 
        }
    }
    
    // Ham kich hoat vu no
    public void explode(List<Brick> Brick) {
        // Lay tamm
        double centerX = this.getX() + this.getWidth() / 2;
        double centerY = this.getY() + this.getHeight() / 2;

        for(Brick b : Brick) {
            // Neu la chinh no thi k xet , tuy nhien van xet cac gach no khac
            if (b == this) {
                continue;
            }
            double otherCenterX = b.getX() + b.getWidth() / 2;
            double otherCenterY = b.getY() + b.getHeight() / 2;
            // Tinh khoang cach tu 2 tan
            double distance = Math.sqrt((otherCenterX - centerX) * (otherCenterX - centerX) 
            + (otherCenterY - centerY) * (otherCenterY - centerY));

            // Neu gach nam trong pham vi vu no thi cho no
            if (distance < BLAST_RADIUS) {
                // neu la gach k bi pha vo thi k xet
                if (!(b instanceof UnbreakableBrick)) {
                    b.takeHit();
                }
            }
        }
    }
}
