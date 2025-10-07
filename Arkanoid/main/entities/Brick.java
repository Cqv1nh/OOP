package entities;

public abstract class Brick extends GameObject {
    private int hitPoints; //Số lần va chạm để gạch bị phá hủy.
    private String type; //Loại gạch.

    /*Trọng thêm code để xuất hiện powerup trong code.*/
    private boolean hasPowerUp; //Gạch có sinh power up không.
    private String powerUpType; //Loại powerup nếu có.

    private int score; //Điểm từng loại gạch.

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
    public boolean isPowerUp() {
        return hasPowerUp;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    //Sinh từng loại PowerUp theo tỷ lệ 3:3:3:1.
    private String getRandomPowerUpType() {
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
}
