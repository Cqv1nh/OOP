package entities;

public class ExpandPaddlePowerUp extends PowerUp {
    //Constructor có tham số.
    public ExpandPaddlePowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, width, height, "EXPAND_PADDLE", duration); //Truyền type cố định rồi.
    }

    //Method.
    public void expandPaddle(Paddle paddle, int extra) {
        paddle.setWidth(paddle.getWidth() + extra);
        paddle.setHeight(paddle.getHeight()); //độ cao giữ nguyên.
    }

    @Override
    //Áp dụng effect.
    public void applyEffect(Paddle paddle) {
        expandPaddle(paddle, 2); // Sử dụng method ngay trên, powerup này khiến thanh dài thêm 2 đơn vị.
    }

    @Override
    //Xóa bỏ effect.
    public void removeEffect(Paddle paddle) {
        expandPaddle(paddle, -2);
    }

    @Override
    //Cập nhật vị trí đối tượng.
    public void update(String input) {
        setX(getX());
        setY(getY() + 1); //Tọa độ (0,0) ở góc trên bên trái -> xuống thì y tăng.
    }

    @Override
    //Vẽ đối tượng ra màn hình.
    public void render(){
        // Vẽ ký tự '*' tại vị trí (x, y) theo ANSI escape code
        System.out.print("\033[" + ((int)getY() + 1) + ";" + ((int)getX() + 1) + "H*");
    }
}