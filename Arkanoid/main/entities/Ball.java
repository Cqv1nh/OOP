package main.entities;

public class Ball extends MovableObject {
    private double speed;
    private double directionX;
    private double directionY;
    private char symbol = 'O'; //Quy ước terminal.

    //Constructor mặc định
    public Ball() {
        super(x, y, width, height, 0, 0);
        this.speed = 1.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.dx = speed * directionX;
        this.dy = speed * directionY;
    }

    //Constructor với speed, directionX, directionY
    public Ball(double speed, double directionX, double directionY) {
        super(x, y, width, height, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.dx = speed * directionX;
        this.dy = speed * directionY;
    }

    //Đặt bóng trên thanh:
    public Ball(double paddleX, double paddleY, double paddleWidth, int level) {
        super(paddleX + paddleWidth / 2, paddleY - 1, width, height, dx, dy);
        this.speed = level * 1.0;
        this.directionX = 1.0;
        this.directionY = -1.0;
        this.setDx(speed * directionX);
        this.setDy(speed * directionY);
    }

    //sửa đổi phương thức move
    @override
    public void move() {
        setX(getX() + getDx());
        setY(getY() + getDy());
    }

    //sửa đổi phương thức update cập nhật vị trí mới
    @override
    public void update() {
        move();
    }

    //sửa đổi phương thức render:
    @override
    public void render() {
    }

    //phương thức ktra bóng va cham vs đối tượng khác.
    public boolean checkCollision(GameObject other) {

    }

    //Phương thức khi chạm paddle, brick, gạch
    public void bounceOff(GameObject other) {

    }
}