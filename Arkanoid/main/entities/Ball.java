package entities;


import util.Constants;


public class Ball extends MovableObject {
    private double speed; //Tốc độ bóng.
    private double directionX; //Hướng đi của bóng theo chiều ngang.
    private double directionY; //Hướng đi của bóng theo chiều dọc.
    private double radius; //Bán kính của bóng.

    //Lưu vị trí ban đầu của bóng để reset khi bóng rơi.

    //Constructor mặc định
    // Thinh thay doi phu hop voi gameWindow
    public Ball() {
        super(Constants.INIT_BALL_X, Constants.INIT_BALL_Y,
                Constants.BALL_DIAMETER, Constants.BALL_DIAMETER, 0, 0); 
        this.speed = Constants.BALL_SPEED;
        this.directionX = -1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.radius = Constants.BALL_DIAMETER / 2;
    }

    //Constructor với speed, directionX, directionY
    public Ball(double speed, double directionX, double directionY, double radius) {
        super(0, 0, 1, 1, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.radius = radius;
    }

    //Getter, setter cho 5 thuộc tính: speed, directionX, directionY, symbol, radius.
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        this.setDx(speed * directionX); //Khi tốc độ thay đổi, cập nhật tốc độ di chuyển theo chiều x.
        this.setDy(speed * directionY); //Khi tốc độ thay đổi, cập nhật tốc độ di chuyển theo chiều y.
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
        this.setDx(speed * directionX); //Thay đổi directionX thì setDx cx thay đổi theo.
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
        this.setDy(speed * directionY);
    }

    //Getter và setter cho radius.
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    //sửa đổi phương thức move
    // Thinh thay doi phuong thuc move , day la ly do tai sao bong cu nhanh cham bat thuong
    @Override
    public void move() {
        this.setX(this.getX() + this.getDx());
        this.setY(this.getY() + this.getDy());
    }
}