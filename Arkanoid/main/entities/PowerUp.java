package main.entities;

public PowerUp extends GameObject {
    private double duration; //Thời gian hiệu lực
    private String type; //Loại powerup

    //Constructor không có tham số.
    public PowerUp() {
    }

    //Constructor có tham số.
    public PowerUp(String type, double duration) {
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
    public int getDuration() {
        return duration;
    }

    public void setDuration() {
        this.duration = duration;
    }

    
}