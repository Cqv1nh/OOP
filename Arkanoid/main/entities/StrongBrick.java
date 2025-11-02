package entities;
import util.BrickType;

public class StrongBrick extends Brick{
    /**
     * Constructor 4 tham số (3 tham số còn lại cố định cho gạch mạnh).
     *
     * @param x x.
     * @param y y.
     * @param width dài.
     * @param height rộng.
     */
    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 3, BrickType.STRONG, 25);
    }

    /**
     * Ghi đè method trừ 1 hit cho gạch.
     */
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

    /**
     * Kiểm tra xem gạch đã bị phá hay chưa.
     *
     * @return T or F.
     */
    @Override
    public boolean isDestroyed() {
        return getHitPoints() <= 0;
    }
}
