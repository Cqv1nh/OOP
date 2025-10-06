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
    private Paddle paddle; // Thanh truot
    private Ball ball; // Ball 
    private ArrayList<Brick> brickList; // Danh sach gach
    private boolean ballLaunched = false; // Bong con dinh tren paddle hay da di chuyen 
    public GameWindow() {
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        ball = new Ball();
        paddle.setBall(ball);
        brickList = new ArrayList<>();

        // Dat cac vien gach cach nhau
        for (int i = 60; i <= 120; i= i + Constants.BRICK_HEIGHT + 5) {
            for(int j = 60; j <= 680; j = j + Constants.BRICK_WIDTH + 5) {
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
                g.fillOval((int) ball.getX(), 
                (int) ball.getY(), (int) ball.getRadius() * 2, 
                (int) ball.getRadius() * 2);
                
                for( Brick b : brickList) {
                    g.setColor(Color.GREEN);
                    g.fillRect((int) b.getX(), 
                    (int) b.getY(), 
                    (int) b.getWidth(), 
                    (int) b.getHeight());
                }

            }
        };

        // GAME LOOP 60 FPS
        Timer timer = new Timer(16, e -> {
            if (ballLaunched) {
                ball.move();
                double speed = ball.getSpeed();
            // va cham voi tuong
                if (ball.getX() <= 0 || ball.getX() + ball.getRadius() * 2 >= Constants.SCREEN_WIDTH) {
                    ball.setDirectionX(-ball.getDirectionX());
                    ball.setDx(speed * ball.getDirectionX());
                }
                if (ball.getY() <= 0) {
                    ball.setDirectionY(-ball.getDirectionY());
                    ball.setDy(speed * ball.getDirectionY()); // De qua bong roi xuong
                }

                
                // Va cham voi paddle
                if (ball.getY() + ball.getRadius() * 2 >= paddle.getY() 
                    && ball.getX() + ball.getRadius() * 2 >= paddle.getX() 
                    && ball.getX() <= paddle.getX() + paddle.getWidth()
                    && ball.getY() < paddle.getY() + paddle.getHeight()) {

                    // Tính vị trí tương đối va chạm so với tâm paddle
                    double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
                    double hitPosition = (ball.getX() + ball.getRadius()) - paddleCenter;

                    // Chuẩn hóa hitPosition thành [-1, 1]
                    double normalized = hitPosition / (paddle.getWidth() / 2.0);

                    // Giới hạn để không ra ngoài [-1,1]
                    if (normalized < -1) normalized = -1;
                    if (normalized > 1) normalized = 1;

                    // Góc bật lại: từ 150° (trái) → 30° (phải)
                    double bounceAngle = Math.toRadians(150 - 120 * (normalized + 1) / 2.0);
                    bounceAngle += Math.toRadians((Math.random() - 0.5) *10); // them ngau nhien + - 5 do
                    // Cập nhật hướng di chuyển mới
            
                    ball.setDirectionX(Math.cos(bounceAngle));
                    ball.setDirectionY(-Math.abs(Math.sin(bounceAngle)));

                    // Áp lại tốc độ (nếu Ball.java dùng dx, dy = speed * direction)
                    ball.setDx(speed * ball.getDirectionX());
                    ball.setDy(speed * ball.getDirectionY());
                }

                // Va cham voi brick
                ArrayList<Brick> bricksToRemove = new ArrayList<>();
                for (Brick b : brickList) {
                    if (ball.getX() + ball.getRadius() * 2 >= b.getX()
                        && ball.getX() <= b.getX() + b.getWidth()
                        && ball.getY() + ball.getRadius() * 2 >= b.getY()
                        && ball.getY() <= b.getY() + b.getHeight()) {

                        // Kiểm tra va chạm ngang hay dọc
                        double overlapLeft = (ball.getX() + ball.getRadius() * 2) - b.getX();
                        double overlapRight = (b.getX() + b.getWidth()) - ball.getX();
                        double overlapTop = (ball.getY() + ball.getRadius() * 2) - b.getY();
                        double overlapBottom = (b.getY() + b.getHeight()) - ball.getY();

                        double minOverlapX = Math.min(overlapLeft, overlapRight);
                        double minOverlapY = Math.min(overlapTop, overlapBottom);

                        // Bóng bật lại theo hướng ít chồng lấn hơn (thực tế hơn)
                        if (minOverlapX < minOverlapY) {
                            ball.setDirectionX(-ball.getDirectionX());
                            ball.setDx(speed * ball.getDirectionX());
                        } else {
                            ball.setDirectionY(-ball.getDirectionY());
                            ball.setDy(speed * ball.getDirectionY());
                        }

                        bricksToRemove.add(b);
                        break; // tránh xử lý trùng lặp nhiều viên
                    }
                }
                brickList.removeAll(bricksToRemove);

                if (ball.getY() > Constants.SCREEN_HEIGHT) {
                    ballLaunched = false;
                }
            } else {
                // Khi chua ban, ball nam tren paddle
                ball.setX(paddle.getX()+paddle.getWidth() / 2 - ball.getRadius());
                ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
            }

            gamePanel.repaint();
        });
        timer.start();

        // KEY CONTROL
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                // Di chuyen paddle
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    paddle.moveLeft();
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    paddle.moveRight();
                }
                // Ban bong khi nhan space
                if (key == KeyEvent.VK_SPACE && !ballLaunched) {
                    ballLaunched = true;
                }
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
