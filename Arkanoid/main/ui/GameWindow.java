import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entities.Ball;
import entities.Brick;
import entities.NormalBrick;
import entities.Paddle;
import util.Constants;

import java.util.ArrayList;

public class GameWindow extends JFrame {

    public GameWindow() {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        Ball ball = new Ball(paddle.getX(), paddle.getY(), paddle.getWidth(), 1);
        
        ArrayList<Brick> brickList = new ArrayList<>();

        for (int i = 40; i <= 120; i= i + Constants.BRICK_HEIGHT) {
            for(int j = 40; j <= 680; j = j + Constants.BRICK_WIDTH ) {
                brickList.add(new NormalBrick(j, i, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
            }
        }
        
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

        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    paddle.moveLeft();
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    paddle.moveRight();
                }

                gamePanel.repaint();
            }
        });

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
