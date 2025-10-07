package entities;

import entities.Ball;
import java.util.List;

public class MultiBallPowerUp extends PowerUp {
    public MultiBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, width, height, "MULTI_BALL", duration);
    }

    //Áp dụng hiệu ứng.
    @Override
    public void applyEffect(Paddle paddle) {
        //Tạo ball mới dựa trên Ball hiện tại của Paddle.
        Ball currentBall = paddle.getBall();

        //Tạo ball mới gần ball hiện tại, hướng ngẫu nhiên.
        Ball addBall = new Ball(
                currentBall.getSpeed(),
                -currentBall.getDirectionX(), //Lấy hướng x ngược với Ball cũ.
                currentBall.getDirectionY(),
                currentBall.getRadius()
        );

        //Đặt vị trí ban đầu cho Ball mới.
        addBall.setX(currentBall.getX());
        addBall.setY(currentBall.getY());

        /* **CHÚ Ý**: Cần thêm ball này vào GameManager
        Thêm: gameManager.addBall(newBall);
        Hoặc: balls.add(newBall); */

    }

    //Xóa bỏ hiệu ứng.
    @Override
    public void removeEffect(Paddle paddle) {
    } //Hàm này sẽ không làm gì vì bóng sẽ tồn tại đến khi rơi vực.

    //Cập nhật vị trí đối tượng.
    @Override
    public void update(String input) {
        setX(getX());
        setY(getY() + getFallSpeed()); //tăng y lên 1 do đi xuống, lấy fallspeed = 1.
    }

    //Vẽ đối tượng ra màn hình.
    @Override
    public void render() {
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H*");
    }
}
