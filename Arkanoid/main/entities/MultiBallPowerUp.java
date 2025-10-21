package entities;

import managers.LevelState2;
import util.Constants;
import ui.GameWindow;

import java.util.ArrayList;

public class MultiBallPowerUp extends PowerUp {
    private static final double INSTANT_DURATION = 0.0;

    public MultiBallPowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "MULTI_BALL", INSTANT_DURATION);
    }

    //Áp dụng hiệu ứng.
    @Override
    public void applyEffect(GameWindow game) {

    }


    //Cập nhật vị trí đối tượng.
    @Override
    public void update(String input) {
        setY(getY() + getFallSpeed()); //tăng y lên 1 do đi xuống, lấy fallspeed = 1.
    }

    //Vẽ đối tượng ra màn hình.
    @Override
    public void render() {
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "HM");
    }

    //Xóa bỏ hiệu ứng.
    @Override
    public void removeEffect(GameWindow game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEffect'");
    } //Hàm này sẽ không làm gì vì bóng sẽ tồn tại đến khi rơi vực.


    @Override
    public void applyEffect(LevelState2 game) {
        ArrayList<Ball> currentBalls = game.getBalls();

        // Tạo thêm 1 bóng.
        if (currentBalls.isEmpty()) {
            return;
        }

        // Lấy quả bóng gốc để sao chép thuộc tính
        Ball originalBall = currentBalls.get(0);

        //Tạo ball mới gần ball hiện tại, hướng x ngược.
        Ball newBall = new Ball(
                originalBall.getSpeed(),
                -originalBall.getDirectionX(), // Hướng X ngược
                originalBall.getDirectionY(),
                originalBall.getRadius()
        );

        // Đặt vị trí ban đầu gần quả bóng gốc
        // Đặt vị trí lệch một chút (ví dụ: +5 đơn vị X) để tránh va chạm ngay lập tức
        newBall.setX(originalBall.getX() + 5);
        newBall.setY(originalBall.getY());

        // Thêm quả bóng mới vào game
        game.addBall(newBall); // Sử dụng phương thức addBall() mới trong GameWindow
    }

    @Override
    public void removeEffect(LevelState2 game) {

    }
}
