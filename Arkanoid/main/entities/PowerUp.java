package entities;

import ui.GameWindow;

public abstract class PowerUp extends GameObject {
    private double duration; //Thời gian hiệu lực
    private String type; //Loại powerup
    private double fallSpeed = 3.0; //Tốc độ rơi vật phẩm đặc biệt là 3
    protected double initialDuration; // Thoi gian ban dau cua PowerUp , dung de tinh phan tram
    //Constructor có tham số.
    public PowerUp(double x, double y, double width, double height, String type, double duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    //Getter + setter cho Type:
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //Getter + setter cho Duration:
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getInitialDuration() {
        return initialDuration;
    }
    
    //Áp dụng hiệu ứng:
    // Thay doi tham so cua applyEffect 
    public abstract void applyEffect(GameWindow game);

    //Gỡ bỏ hiệu ứng:
    public abstract void removeEffect(GameWindow game);

    //Kiểm tra Hiệu ứng đã ra khỏi màn hỉnh (tụt xuống đày chưa)
    public boolean isOutOfBounds (int worldHeight) {
        if (this.getY() < worldHeight) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public abstract void update(String input); //Cập nhật vị trí theo phím bấm

    @Override
    public abstract void render(); //Vẽ đối tượng trên terminal

    //Chỉnh sửa: Getter cho tốc độ rơi của bóng.
    public double getFallSpeed() {
        return fallSpeed;
    }
}