import javax.swing.*;
import entities.Ball;
import entities.Brick;
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
    private GameLoop gameLoop;
    private InputHandler inputHandler;
    private BrickManager brickManager;

    public GameWindow() {
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        ball = new Ball();
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        
        // Brick manager
        brickManager = new BrickManager();
        brickList = brickManager.createBricks();

        // Đặt tiêu đề cho cửa sổ
        setTitle(Constants.SCREEN_TITLE);

        // Kích thước cửa sổ
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Thoát khi bấm nút X
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Không cho resize
        setResizable(false);

        // Setup UI components
        gamePanel = new GamePanel(this);
        add(gamePanel);

        // Input + Loop
        inputHandler = new InputHandler(this);
        gamePanel.addKeyListener(inputHandler);
        gamePanel.setFocusable(true);
        // GAME LOOP 60 FPS
        gameLoop = new GameLoop(this, gamePanel);
        gameLoop.start();
        
        // Hiển thị cửa sổ
        setVisible(true);
    }
    
    // Ham reset Game 
    public void restartGame() {
        brickList = brickManager.createBricks();
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        ball = new Ball();
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        ballLaunched = false;
        gameState = GameState.GAMESTART;
    }

    public void nextLevel() {
        brickList = brickManager.createBricks(); // Có thể tăng độ khó sau này
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        ball = new Ball();
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        gameState = GameState.GAMESTART;
        ballLaunched = false;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
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

    public boolean isBallLaunched() {
        return ballLaunched;
    }

    public void setBallLaunched(boolean ballLaunched) {
        this.ballLaunched = ballLaunched;
    }
    public static void main(String[] args) {
        // Tạo cửa sổ trên luồng giao diện
        SwingUtilities.invokeLater(() -> new GameWindow());

    }
}
