import javax.swing.*;

import entities.Ball;
import entities.Brick;
import entities.NormalBrick;
import entities.Paddle;
import util.Constants;

import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    public GameWindow() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        Ball ball = new Ball(paddle.getX(), paddle.getY(), paddle.getWidth(), 1);
        
        ArrayList<Brick> brickList = new ArrayList<>();
        brickList.add(new NormalBrick(40, 40,Constants.BRICK_WIDTH,Constants.BRICK_HEIGHT));
        // Đặt tiêu đề cho cửa sổ
        setTitle(Constants.SCREEN_TITLE);

        // Kích thước cửa sổ
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Thoát khi bấm nút X
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Không cho resize
        setResizable(false);

        // Tạo panel chính (màn hình game)
        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Vẽ nền màu đen
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.WHITE);
                g.fillRect(
                    (int) paddle.getX(), 
                    (int) paddle.getY(), 
                    (int) paddle.getWidth(), 
                    (int) paddle.getHeight());

                g.setColor(Color.RED);
                g.fillOval(Constants.INIT_BALL_X, Constants.INIT_BALL_Y, Constants.BALL_DIAMETER, Constants.BALL_DIAMETER);
                
                for( Brick b : brickList) {
                    g.setColor(Color.GREEN);
                    g.fillRect((int) b.getX(), 
                    (int) b.getY(), 
                    (int) b.getWidth(), 
                    (int) b.getHeight());
                }

            }
        };

        // Thêm panel vào cửa sổ
        add(gamePanel);

        // Hiển thị cửa sổ
        setVisible(true);
    }

    public static void main(String[] args) {
        // Tạo cửa sổ trên luồng giao diện
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
