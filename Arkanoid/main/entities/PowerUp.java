package entities;

public abstract class PowerUp extends GameObject {
    private double duration; //Thời gian hiệu lực
    private String type; //Loại powerup

    /*Constructor không có tham số.
    public PowerUp() {
    } */

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

    //Áp dụng hiệu ứng:
    public void applyEffect(Paddle paddle) {

    }

    //Gỡ bỏ hiệu ứng:
    public void removeEffect(Paddle paddle) {

    }

    @Override
    public abstract void update(String input); //Cập nhật vị trí theo phím bấm

    @Override
    public abstract void render(); //Vẽ đối tượng trên terminal
}