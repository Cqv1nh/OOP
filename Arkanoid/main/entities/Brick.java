package entities;

import util.BrickType;

public abstract class Brick extends GameObject {
    private int hitPoints; //Số lần va chạm để gạch bị phá hủy.
    private String type; //Loại gạch.

    /*Trọng thêm code để xuất hiện powerup trong code.*/
    private boolean hasPowerUp; //Gạch có sinh power up không.
    private String powerUpType; //Loại powerup nếu có.

    private int score; //Điểm từng loại gạch.

    private boolean isFading = false; //Trang thai dang mo dan
    private float alpha = 1.0f; //do trong suot

    private static final float FADE_SPEED = 0.05f; // Toc do lam mo khoang 1/3 giay

    //Phần này sửa constructor này thêm 2 thuộc tính vừa thêm.
    public Brick(double x, double y, double width, double height, int hitPoints, String type, int score) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.score = score;

        //Sinh tỷ lệ 25% có powerup.
        if (Math.random() < 0.25) {
            this.hasPowerUp = true;
            this.powerUpType = getRandomPowerUpType();
        } else {
            this.hasPowerUp = false;
            this.powerUpType = null;
        }
    }

    //Getter cho 2 attribute mới.
    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    //Sinh từng loại PowerUp theo tỷ lệ 3:3:3:1.
    public String getRandomPowerUpType() {
        double rand = Math.random();
        if (rand <= 0.3) {
            return "EXPAND_PADDLE";
        } else if (rand <= 0.6) {
            return "FAST_BALL";
        } else if (rand <= 0.9) {
            return "MULTI_BALL";
        } else {
            return "EXTRA_LIFE";
        }
    }

    //getter và setter cho điểm.
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*Hết thêm code.*/

    //Getter và setter.
    public int getHitPoints() {
        return hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public abstract void takeHit();
    public abstract boolean isDestroyed();


    //Them code:
    /**
     * Bắt đầu quá trình mờ dần
     */
    public void startFading() {
        if (!isFading) {
            this.isFading = true;
        }
    }

    /**
     * Trả về trạng thái có đang mờ hay không
     */
    public boolean isFading() {
        return isFading;
    }

    /**
     * Lấy độ trong suốt hiện tại
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Cập nhật logic mờ (sẽ được gọi mỗi frame từ LevelState2)
     */
    public void updateFade() {
        if (isFading) {
            alpha -= FADE_SPEED; // Giảm độ trong suốt
            if (alpha < 0.0f) {
                alpha = 0.0f;
            }
        }
    }

    /**
     * Kiểm tra xem gạch đã sẵn sàng để bị xóa khỏi game chưa
     */
    public boolean isReadyForRemoval() {
        // Gạch thường (NORMAL) và gạch mạnh (STRONG) chỉ bị xóa KHI MỜ HẾT
        if (getType().equals(BrickType.NORMAL) || getType().equals(BrickType.STRONG)) {
            return isFading && alpha <= 0.0f;
        }
        
        // Gạch nổ (EXPLOSIVE) hoặc các loại gạch vỡ khác (không mờ)
        // sẽ bị xóa ngay khi hitPoints <= 0
        if (isDestroyed() && !getType().equals(BrickType.UNBREAKABLE)) {
            return true;
        }
        
        return false;
    }
}
