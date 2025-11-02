package entities;

import util.BrickType;

public abstract class Brick extends GameObject {
    private int hitPoints; //Số lần va chạm để gạch bị phá hủy.
    private String type; //Loại gạch.
    private boolean hasPowerUp; //Gạch có sinh power up không.
    private String powerUpType; //Loại powerup nếu có.
    private int score; //Điểm từng loại gạch.
    private boolean isFading = false; //Trang thai dang mo dan
    private float alpha = 1.0f; //do trong suot
    private static final float FADE_SPEED = 0.05f; // Toc do lam mo khoang 1/3 giay

    /**
     * Constructor 7 tham số.
     *
     * @param x x.
     * @param y y.
     * @param width dài.
     * @param height rộng.
     * @param hitPoints số lần va chạm để phá hủy brick.
     * @param type loại gạch.
     * @param score điểm.
     */
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

    /**
     * Getter cho có powerup không?
     *
     * @return có powerup không.
     */
    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    /**
     * Getter cho loại powerup.
     *
     * @return loại powerup.
     */
    public String getPowerUpType() {
        return powerUpType;
    }

    /**
     * Method sinh powerup tỷ lệ 3:3:3:1.
     *
     * @return tên powerup được sinh ra.
     */
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

    /**
     * Getter cho điểm.
     *
     * @return điểm.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter cho điểm.
     *
     * @param score điểm.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter cho số hit để phá hủy gạch.
     *
     * @return số hit.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Getter cho loại gạch.
     *
     * @return loại gạch.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter cho số hit phá hủy gạch.
     *
     * @param hitPoints số hit.
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Setter cho loại gạch.
     *
     * @param type loại gạch.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method trừ 1 hit vào các brick cụ thể (sẽ được override).
     */
    public abstract void takeHit();

    /**
     * Method check brick phá hủy chưa.
     *
     * @return T or F.
     */
    public abstract boolean isDestroyed();

    /**
     * Method bắt đầu quá trình mờ dần.
     */
    public void startFading() {
        if (!isFading) {
            this.isFading = true;
        }
    }

    /**
     * Method xem có đang mờ hay không.
     *
     * @return T/F.
     */
    public boolean isFading() {
        return isFading;
    }

    /**
     * Method lấy độ trong suốt.
     *
     * @return độ trong suốt.
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Method update được ghi đè.
     */
    public void update() {
        updateFade();   // Gọi method làm mờ ngay trên.
    }

    /**
     * Cập nhật logic mờ (sẽ được gọi mỗi frame từ LevelState2).
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
     * Kiểm tra xem gạch đã sẵn sàng để bị xóa khỏi game chưa?
     *
     * @return T or F.
     */
    public boolean isReadyForRemoval() {
        // Gạch thường và gạch mạnh chỉ bị xóa KHI MỜ HẾT
        if (getType().equals(BrickType.NORMAL) || getType().equals(BrickType.STRONG)) {
            return isFading && alpha <= 0.0f;
        }
        
        // Gạch nổ (EXPLOSIVE) sẽ bị xóa ngay khi hitPoints <= 0
        if (isDestroyed() && !getType().equals(BrickType.UNBREAKABLE)) {
            return true;
        }
        
        return false;
    }
}
