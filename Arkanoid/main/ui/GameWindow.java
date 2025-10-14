package ui;
import javax.swing.*;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import entities.PowerUp;
import util.AssetManager;
import util.Constants;
import util.GameState;

import java.util.ArrayList;

public class GameWindow extends JFrame {
    private Paddle paddle; // Thanh truot
    private Ball ball; // Ball 
    private ArrayList<Brick> brickList; // Danh sach gach
    private ArrayList<PowerUp> powerUps; // Danh sach luu cac powerUp dang roi
    private ArrayList<PowerUp> activePowerUpsEffects; // Danh sach hieu ung dang co hieu luc
    private boolean extraLifeShieldActive = false; // Co trang thai "khien"

    private int lives = 3; // Khoi tao so mang la 3;
    private int score = 0;
    private boolean ballLaunched = false; // Bong con dinh tren paddle hay da di chuyen
    private String gameState = GameState.GAMESTART; // Trang thai game
    private GamePanel gamePanel; // Ve cac vat the
    private GameLoop gameLoop; // Vong lap game
    private InputHandler inputHandler;  // Xu ly dau vao ban phim
    private BrickManager brickManager; // Quan ly gach cua level 1

    public GameWindow() {
        AssetManager.loadImages();
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);

        ball = new Ball();
        // Xem xet xoa di
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        
        // Brick manager
        brickManager = new BrickManager();
        brickList = brickManager.createBricks();
        // PowerUp khoi tao
        powerUps = new ArrayList<>();
        activePowerUpsEffects = new ArrayList<>();

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

    //SỬA CODE:
    // Mở rộng thanh đỡ
    public void expandPaddle(int amount) {
        this.paddle.setWidth(this.paddle.getWidth() + amount);
    }

    // Thu nhỏ thanh đỡ
    public void shrinkPaddle(int amount) {
        this.paddle.setWidth(this.paddle.getWidth() - amount);
    }
    
    // SỬA LỖI: Thêm method này để xử lý MultiBallPowerUp
    // Thêm một quả bóng phụ (tạm thời chỉ in ra console, logic thực tế cần được bổ sung)
    public void addExtraBall() {
        // Cần bổ sung logic tạo và quản lý bóng mới (Ball) tại đây.
        // Hiện tại, tạm thời để trống hoặc in thông báo.
        System.out.println("Extra ball added (Logic for new Ball creation needs implementation)");
    }
    
    // Ham reset Game choi lai tu dau
    public void restartGame() {
        // dat lai gia tri lives = 3;
        this.lives = 3;
        this.score = 0;
        this.extraLifeShieldActive = false; // Reset trang thai powerup
        powerUps.clear(); // Xoa het cac hieu ung dang roi
        activePowerUpsEffects.clear(); // Xoa het cac hieu ung co hieu luc

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
        // Dat lai so mang bang 3 cho level moi
        this.lives = 3;
        brickList = brickManager.createBricks(); // Có thể tăng độ khó sau này
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
        Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        ball = new Ball();
        ball.setSpeed(Constants.BALL_SPEED);
        paddle.setBall(ball);
        gameState = GameState.GAMESTART;
        ballLaunched = false;
    }

    // Sau khi bi mat 1 mang ( bong roi xuong vuc)
    // Reset lai vi tri bong va paddle nhu moi vao game
    public void resetAfterLifeLost() {
        setBallLaunched(false);
        // Dat lai vi tri paddle ve giua man hinh
        paddle.setX(Constants.INIT_PADDLE_X);
        paddle.setY(Constants.INIT_PADDLE_Y);
        
        // Dat qua bong tren thanh paddle
        ball.setX(Constants.INIT_BALL_X);
        ball.setY(Constants.INIT_BALL_Y);
        // Van su dung lai cac paddle va ball cu nen k can dung ham new
        
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

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps; 
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

    public int getLives() {
        return lives;
    }
    // Thay ham setLives bang loseLives de goi tu dong tru mang
    public void loseLives() {
        this.lives --;
    }

    public boolean isExtraLifeShieldActive() {
        return extraLifeShieldActive;
    }

    public void setExtraLifeShieldActive(boolean active) {
        this.extraLifeShieldActive = active;
    }

    public ArrayList<PowerUp> getActivePowerUpEffects() {
        return activePowerUpsEffects;
    }

    public void addScore(int newScore) {
        this.score += newScore;
    }

    public int getScore() {
        return score;
    }

    public void addLife() {
        this.lives++;
    }
    
    public static void main(String[] args) {
        // Tạo cửa sổ trên luồng giao diện
        SwingUtilities.invokeLater(() -> new GameWindow());

    }
}
