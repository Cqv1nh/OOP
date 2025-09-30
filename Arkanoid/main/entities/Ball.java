package main.entities;

public class Ball extends MovableObject {
    private double speed; //Tốc độ bóng.
    private double directionX; //Hướng đi của bóng.
    private double directionY;
    private char symbol = 'O'; //Quy ước terminal.

    //Constructor mặc định
    public Ball() {
        super(19, 17, 1, 1, 0, 0); //x=19 (giữa màn 40), y=17 (trên paddle), width=1, height=1.
        this.speed = 1.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
    }

    //Constructor với speed, directionX, directionY
    public Ball(double speed, double directionX, double directionY) {
        super(19, 17, 1, 1, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
    }

    //Đặt bóng trên thanh:
    public Ball(double paddleX, double paddleY, double paddleWidth, int level) {
        super(paddleX + paddleWidth / 2, paddleY - 1, 1, 1, 0, 0);
        this.speed = level * 1.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
    }

    //Getter, setter cho 4 thuộc tính: speed, directionX, directionY, symbol
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public char getSymbol() {
        return symbol;
    }

    //sửa đổi phương thức move
    @Override
    public void move() {
        setX(getX() + getDx());
        setY(getY() + getDy());
    }

    //sửa đổi phương thức update cập nhật vị trí mới
    @Override
    public void update(String input) {
        move();
    }

    //sửa đổi phương thức render:
    @Override
    public void render() {
    }

    //phương thức ktra bóng va cham vs đối tượng khác.
    public boolean checkCollision(GameObject other) {
        if ((this.getY() + this.getHeight() < other.getY()) || (this.getX() + this.getWidth() < other.getX()) || (other.getY() + other.getHeight() < this.getY()) || (other.getX() + other.getWidth() < this.getX())) {
            return false;
        } else {
            return true;
        }
    }

    //Phương thức khi chạm paddle, brick, gạch.
    public void bounceOff(GameObject other) {
        //Tình tọa độ tâm vật thể.
        centreballX = this.getX() + this.getWidth() / 2;
        centreballY = this.getY() + this.getHeight() / 2;
        centreotherX = other.getX() + other.getWidth() / 2;
        centreotherY = other.getY() + other.getHeight() / 2;

        //Khoảng cách giữa bóng và vật khác.
        distanceX = centreballX - centreotherX;
        distanceY = centreballY - centreotherY;

        //Khoảng cách để 2 vật tiếp xúc nhau.
        distanceminX = 
    }
}