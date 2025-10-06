package entities;

import main.entities.Ball;
import java.util.List;

public class MultiBallPowerUp extends PowerUp {
    public MultiBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, width, height, "MULTI_BALL", duration);
    }

    //Áp dụng hiệu ứng.
    @Override
    public void applyEffect(Paddle paddle) {

    }

    //Xóa bỏ hiệu ứng.
    @Override
    public void removeEffect(Paddle paddle) {
    } //Hàm này sẽ không làm gì vì bóng sẽ tồn tại đến khi rơi vực.

    //Cập nhật vị trí đối tượng.
    @Override
    public void update(String input) {
        setX(getX());
        setY(getY() + 1); //tăng y lên 1 do đi xuống.
    }

    //Vẽ đối tượng ra màn hình.
    @Override
    public void render() {
        System.out.println("MB tại (" + getX() + ", " + getY() + ")");
    }
}
