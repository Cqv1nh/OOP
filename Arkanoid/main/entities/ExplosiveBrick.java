package entities;

import java.util.ArrayList;
import java.util.List;
import util.BrickType;
import util.Constants;

public class ExplosiveBrick extends Brick{
    private static final double MAX_DIM = Math.max(Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
    private static final double BLAST_RADIUS = MAX_DIM + 6; //Bán kính vụ nổ
    private boolean isExploding = false;
    private int currentFrame = 0; 
    private int frameDelay = 3; // Mỗi frame animation kéo dài 3 frame game (60fps/3 = 20 FPS)
    private int frameCounter = 0;
    private static final int TOTAL_FRAMES = 10; // Tổng số khung hình nổ (10 ảnh PNG)

    /**
     * Getter kiểm tra xem có đang nổ không?
     *
     * @return T or F.
     */
    public boolean isExploding() { 
        return isExploding; 
    }

    /**
     * Getter lấy ra khung hình hiện tại.
     *
     * @return khung hình.
     */
    public int getCurrentFrame() { 
        return currentFrame; 
    }

    /**
     * Getter lấy tổng số frame.
     *
     * @return tổng frame.
     */
    public static int getTotalFrames() { 
        return TOTAL_FRAMES; 
    }

    /**
     * Constructor 4 tham số (3 tham số còn lại cố định cho gạch nổ).
     *
     * @param x x.
     * @param y y.
     * @param width dài.
     * @param height rộng.
     */
    public ExplosiveBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, BrickType.EXPLOSIVE, 50);
    }

    /**
     * Thực hiện -1hit cho gạch nổ.
     */
    @Override
    public void takeHit() {
        setHitPoints(getHitPoints() - 1);
    }

    /**
     * Kiểm tra gạch bị phá vỡ chưa.
     *
     * @return T or F.
     */
    @Override
    public boolean isDestroyed() {
        return getHitPoints() <= 0;
    }

    /**
     * Method kích hoạt vụ nổ.
     *
     * @param brickList List các gạch.
     * @return brickList List các gạch bị ảnh hưởng do gạch nổ.
     */
    public List<Brick> startExplosion(List<Brick> brickList) {
        if (isExploding) return new ArrayList<>(); 
        isExploding = true; // Bật cờ animation
        currentFrame = 0;   // Bắt đầu từ frame 0
        frameCounter = 0;

        List<Brick> destroyedByBlast = new ArrayList<>();

        // Lay tam
        double centerX = this.getX() + this.getWidth() / 2;
        double centerY = this.getY() + this.getHeight() / 2;

        for(Brick b : brickList) {
            // Neu la chinh no thi k xet , tuy nhien van xet cac gach no khac
            if (b == this) {
                continue;
            }
            double otherCenterX = b.getX() + b.getWidth() / 2;
            double otherCenterY = b.getY() + b.getHeight() / 2;
            // Tinh khoang cach tu 2 tam
            double distance = Math.sqrt((otherCenterX - centerX) * (otherCenterX - centerX) 
            + (otherCenterY - centerY) * (otherCenterY - centerY));

            // Neu gach nam trong pham vi vu no thi cho no
            if (distance < BLAST_RADIUS && !(b instanceof UnbreakableBrick)) {
                int oldHitPoints = b.getHitPoints(); // Lấy HP cũ
                b.takeHit(); 
            
                if (b.isDestroyed() && oldHitPoints > 0) { // Vừa bị phá hủy bởi vụ nổ này
                
                    destroyedByBlast.add(b); // Thêm gạch bị hủy vào danh sách
                
                    if (b instanceof ExplosiveBrick) {
                        // TRƯỜNG HỢP NỔ DÂY CHUYỀN
                        ExplosiveBrick nextExplosiveBrick = (ExplosiveBrick) b;
                        // Gọi đệ quy và gom danh sách các gạch bị hủy tiếp theo
                        destroyedByBlast.addAll(nextExplosiveBrick.startExplosion(brickList));
                    
                    } else if (b.getType().equals(BrickType.NORMAL) || b.getType().equals(BrickType.STRONG)) {
                        // Gạch thường/mạnh bị nổ ké: Kích hoạt animation mờ
                        // Hoặc BỎ QUA FADING để xóa ngay và hiển thị animation nổ tạm thời (nếu bạn chọn cách này)
                        b.startFading(); // Vẫn giữ Fading cho gạch thường bị nổ ké
                    }
                }
            }
        }

        return destroyedByBlast;
    }

    /**
     * Method cập nhật đối tượng.
     */
    @Override
    public void update() {
        super.update(); // Gọi logic mờ cũ (nếu có)

        if (isExploding && currentFrame < TOTAL_FRAMES) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                currentFrame++; // Chuyển sang frame tiếp theo
                frameCounter = 0;
            }
        }
    }

    /**
     * Method xóa gạch nổ khỏi màn hình khi xong animation.
     *
     * @return T or F.
     */
    @Override
    public boolean isReadyForRemoval() {
        // Gạch nổ sẽ bị xóa sau khi: hitPoints <= 0 VÀ animation đã chạy hết
        return getHitPoints() <= 0 && isExploding && currentFrame >= TOTAL_FRAMES;
    }
}








