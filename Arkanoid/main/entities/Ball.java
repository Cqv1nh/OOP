package entities;

import entities.MovableObject;
import util.Constants;
import entities.GameObject;

public class Ball extends MovableObject {
    private double speed; //Tốc độ bóng.
    private double directionX; //Hướng đi của bóng theo chiều ngang.
    private double directionY; //Hướng đi của bóng theo chiều dọc.
    private char symbol = 'O'; //Quy ước terminal.
    private double radius; //Bán kính của bóng.

    //Lưu vị trí ban đầu của bóng để reset khi bóng rơi.
    private double initialX;
    private double initialY;

    //Constructor mặc định
    // Thinh thay doi phu hop voi gameWindow
    public Ball() {
        super(Constants.INIT_BALL_X, Constants.INIT_BALL_Y, 
        Constants.BALL_DIAMETER, Constants.BALL_DIAMETER, 0, 0); //x=19 (giữa màn 40), y=17 (trên paddle), width=1, height=1.
        this.speed = 5.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.initialX = this.getX(); //Lưu vị trí ban đầu bằng tọa độ x.
        this.initialY = this.getY(); //Lưu vị trí ban đầu bằng tọa độ y.
        this.radius = Constants.BALL_DIAMETER / 2;
    }

    //Đặt bóng trên thanh:
    public Ball(double paddleX, double paddleY, double paddleWidth, int level) {
        super(paddleX + paddleWidth / 2.0, paddleY - 1.0, 1, 1, 0, 0);
        this.speed = level * 1.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.initialX = this.getX();
        this.initialY = this.getY();
    }

    //Constructor với speed, directionX, directionY
    public Ball(double speed, double directionX, double directionY, double radius) {
        super(19, 17, 1, 1, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
        this.initialX = this.getX();
        this.initialY = this.getY();
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

    public char getSymbol() {
        return symbol;
    }

    //Getter và setter cho radius.
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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
        if ((this.getY() + this.getHeight() < other.getY())
                || (this.getX() + this.getWidth() < other.getX())
                || (other.getY() + other.getHeight() < this.getY())
                || (other.getX() + other.getWidth() < this.getX())) {
            return false;
        } else {
            return true;
        }
    }

    //Phương thức khi chạm paddle, brick, tường.
    public void bounceOff(GameObject other) {
        //Chọn tường trái/phải.
        if (other.getX() == 0 || other.getX() == 39) {
            this.directionX = -this.directionX;
            this.setDx(speed * directionX);
        } else if (other.getY() == 0) {
            //Chạm tường trên.
            this.directionY = -this.directionY;
            this.setDy(speed * directionY);
        } else {
        /*Chạm vào brick hoặc paddle.
        Tình tọa độ tâm vật thể.*/
            double centreballX = this.getX() + this.getWidth() / 2.0;
            double centreballY = this.getY() + this.getHeight() / 2.0;
            double centreotherX = other.getX() + other.getWidth() / 2.0;
            double centreotherY = other.getY() + other.getHeight() / 2.0;

            //Khoảng cách giữa bóng và vật khác.
            double distanceX = centreballX - centreotherX;
            double distanceY = centreballY - centreotherY;

            //Kiểm tra và quyết định hướng bóng mới là ngang/dọc.
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                //Va chạm ngang
                this.directionX = -this.directionX;
                if (this.directionX == 0) {
                    if (distanceX >= 0) {
                        this.directionX = 1.0;
                    } else {
                        this.directionX = -1.0;
                    }
                }
                this.setDx(speed * directionX);
            } else if (Math.abs(distanceX) < Math.abs(distanceY)) {
                //Va chạm dọc
                this.directionY = -this.directionY;
                if (this.directionY == 0) {
                    if (distanceY >= 0) {
                        this.directionY = 1.0;
                    } else {
                        this.directionY = -1.0;
                    }
                }
                this.setDy(speed * directionY);
            } else {
                //Trường hợp chạm góc.
                this.directionX = -this.directionX;
                this.directionY = -this.directionY;
                this.setDx(speed * directionX);
                this.setDy(speed * directionY);
            }
        }
    }

    //Khi bóng rơi vực, reset lại vị trí ban đầu của bóng.
    public void resetToInitial() {
        this.setX(initialX); //Set vị trí tọa độ.
        this.setY(initialY);
        this.directionX = 1.0; //Set lại hướng bóng.
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
    }
}