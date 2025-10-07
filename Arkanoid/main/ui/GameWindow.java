import javax.swing.*;
import java.awt.event.*;
import entities.Ball;
import entities.Brick;
import entities.NormalBrick;
import entities.Paddle;
import util.Constants;
import util.GameState;

import java.util.ArrayList;

public class GameWindow extends JFrame {
    private Paddle paddle; // Thanh truot
    private Ball ball; // Ball 
    private ArrayList<Brick> brickList; // Danh sach gach
    private boolean ballLaunched = false; // Bong con dinh tren paddle hay da di chuyen
    private String gameState = GameState.GAMESTART;
    private GamePanel gamePanel;
    public GameWindow() {
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        ball = new Ball();
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        createBricks();
        
        // Đặt tiêu đề cho cửa sổ
        setTitle(Constants.SCREEN_TITLE);

        // Kích thước cửa sổ
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Thoát khi bấm nút X
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Không cho resize
        setResizable(false);

        gamePanel = new GamePanel(this);
        add(gamePanel);

        // GAME LOOP 60 FPS
        Timer timer = new Timer(16, e -> {
            // Neu chua vao game thi k chay Timer
            if (!gameState.equals(GameState.GAMEPLAYING)) {
                gamePanel.repaint();
                return;
            }

            if (ballLaunched) {
                ball.move();
                double speed = ball.getSpeed(); // Lay gia tri speed
            // va cham voi tuong
                if (ball.getX() <= 0) {
                    ball.setX(0);
                    ball.setDirectionX(Math.abs(ball.getDirectionX())); // Bat sang phai
                    ball.setDx(speed * ball.getDirectionX());
                }
                if (ball.getX() + ball.getRadius() * 2 >= Constants.SCREEN_WIDTH) {
                    ball.setX(Constants.SCREEN_WIDTH - ball.getRadius() * 2);
                    ball.setDirectionX(-Math.abs(ball.getDirectionX())); // Bat sang trai
                    ball.setDx(speed * ball.getDirectionX());
                }
                if (ball.getY() <= 0) {
                    ball.setY(0);
                    ball.setDirectionY(Math.abs(ball.getDirectionY())); // Bat xuong duoi
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
                if (brickList.isEmpty()) {
                    gameState = GameState.LEVELCLEAR;
                }

                if (ball.getY() > Constants.SCREEN_HEIGHT) {
                    // ballLaunched = false;
                    gameState = GameState.GAMEEND;
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
                // Trang thai vao game
                if (gameState.equals(GameState.GAMESTART)) {
                    if (key == KeyEvent.VK_SPACE) {
                        gameState = GameState.GAMEPLAYING; // Playing game
                        ballLaunched = false;
                    }
                    return;
                }
                if (gameState.equals(GameState.GAMEEND)) {
                    if (key == KeyEvent.VK_SPACE) {
                        restartGame();
                    }
                    return;
                }

                if (gameState.equals(GameState.LEVELCLEAR)) {
                    if (key == KeyEvent.VK_ENTER) {
                        nextLevel();
                    }
                    return;
                }
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

        // Hiển thị cửa sổ
        setVisible(true);
    }
    // Ham tao gach 
    private void createBricks() {
        brickList = new ArrayList<>();
        // Dat cac vien gach cach nhau
        for (int i = 60; i <= 120; i= i + Constants.BRICK_HEIGHT + 5) {
            for(int j = 60; j <= 680; j = j + Constants.BRICK_WIDTH + 5) {
                brickList.add(new NormalBrick(j, i, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
            }
        }
    }
    // Ham reset Game 
    private void restartGame() {
        createBricks();
        ballLaunched = false;
        gameState = GameState.GAMESTART;
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        ball = new Ball();
        paddle.setBall(ball);
    }
    private void nextLevel() {
        createBricks(); // Có thể tăng độ khó sau này
        gameState = GameState.GAMESTART;
        ballLaunched = false;
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        ball = new Ball();
        paddle.setBall(ball);
    }

    public String getGameState() {
        return gameState;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public ArrayList<Brick> getBricks() {
        return brickList;
    }
    public static void main(String[] args) {
        // Tạo cửa sổ trên luồng giao diện
        SwingUtilities.invokeLater(() -> new GameWindow());

    }
}
