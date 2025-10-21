package managers;

import engine.*;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import entities.PowerUp;
import ui.*;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelState2 extends GameState{
    private Paddle paddle; // Thanh truot

    private ArrayList<Brick> brickList; // Danh sach gach
    private ArrayList<PowerUp> powerUps; // Danh sach luu cac powerUp dang roi
    private ArrayList<PowerUp> activePowerUpsEffects; // Danh sach hieu ung dang co hieu luc
    private boolean extraLifeShieldActive = false; // Co trang thai "khien"

    private int lives = 3; // Khoi tao so mang la 3;
    private int score = 0;
    private int levelNum;
    private boolean ballLaunched = false; // Bong con dinh tren paddle hay da di chuyen
    private String gameState = util.GameState.GAMESTART; // Trang thai game
    private InputHandler inputHandler;  // Xu ly dau vao ban phim
    private BrickManager brickManager = new BrickManager(); // Quan ly gach cua level 1

    private Timer timer;

    private final EntityManager entityManager = new EntityManager();
    private final CollisionHandler collisionHandler = new CollisionHandler();
    private final PowerUpManager powerUpManager = new PowerUpManager();



    private boolean isLastLevel = false;
    private boolean levelWon = false;
    private String levelFileName;

    private ArrayList<Ball> balls;

    public LevelState2(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

    }

    public void initLevel(String levelFileName, int levelNum, int score, int lives) {
        this.levelFileName = levelFileName;
        this.levelNum = levelNum;
        this.score = score;
        this.lives = lives;



        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
                Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        paddle.setSpeed(5);

        Ball initialBall = new Ball();
        initialBall.setSpeed(Constants.BALL_SPEED);
        balls = new ArrayList<>();
        balls.add(initialBall);


        brickList = brickManager.createBricks(levelNum, Constants.SCREEN_WIDTH);

        System.out.println("Brick List size:");
        System.out.println(brickList.size());
        powerUps = new ArrayList<>();
        activePowerUpsEffects = new ArrayList<>();

        AssetManager.loadImages();
    }

    @Override
    public void enter() {
        timer = new Timer(16, e -> update());
        timer.start();
        System.out.println("Enter level(v2)");
    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        km.update();

        if (km.getKey(KeyEvent.VK_ESCAPE)) {
            km.clearAllKeys();
            manager.setState("pause");
        }

        if (km.getKey(KeyEvent.VK_C)) {
            km.clearAllKeys();
            manager.loadNextLevel2();
        }

        if(km.getKey(KeyEvent.VK_F)) {
            km.clearAllKeys();
            manager.setState("game over");
        }

        int panelWidth = Constants.SCREEN_WIDTH;
        int panelHeight = Constants.SCREEN_HEIGHT;


        km.updatePaddle(paddle);


        // 1. Cập nhật vị trí các thực thể (Bóng, Thanh trượt)
        entityManager.updateEntities(this, panelWidth);
        // 2. Xử lý tất cả các va chạm
        collisionHandler.handleCollisions(this, panelWidth, panelHeight);
        // 3. Cập nhật và quản lý Power-up
        powerUpManager.update(this, panelHeight);

        if (!isBallLaunched()) {
            if (km.outFollow(balls.getFirst())) {
                setBallLaunched(true);
            }
        }

        if (checkWin()) {
            levelWon = true;
            manager.loadNextLevel2();
            return;
        }

        if (!isAlive()) {
            manager.setState("game over");
            return;
        }
    }

    @Override
    public void render(Graphics2D g) {
        int currentLevel = levelNum;

        BufferedImage bgImage = AssetManager.levelBackgrounds.get(currentLevel);
        BufferedImage ballImage = AssetManager.ball.get(currentLevel);
        BufferedImage paddleImage = AssetManager.paddle.get(currentLevel);


        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, Constants.SCREEN_WIDTH,
                    Constants.SCREEN_HEIGHT, null);
        }

        // Vẽ Paddle
        g.drawImage(paddleImage, (int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight(), null);

        for (Ball ball : balls) {
            g.drawImage(ballImage,
                    (int) ball.getX(),
                    (int) ball.getY(),
                    (int) ball.getRadius() * 2,
                    (int) ball.getRadius() * 2, null);
        }

        // Vẽ Bricks
        for (Brick b : brickList) {
            BufferedImage brickImage = getBrickImage(b);
            if (brickImage != null) {
                g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
            }
        }

        // Vẽ Power-ups
        for (PowerUp p : powerUps) {
            BufferedImage powerUpImage = getPowerUpImage(p);
            if (powerUpImage != null) {
                g.drawImage(powerUpImage, (int) p.getX(), (int) p.getY(),
                        (int) p.getWidth(), (int) p.getHeight(), null);
            }
        }

        drawHUD(g);

        drawPowerUpTimers(g);
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lives: " + lives, 10, 25);
        g.drawString("Level: " + levelNum, 120, 25);
        // Căn lề phải cho điểm số
        String scoreText = "Score: " + score;
        FontMetrics fm = g.getFontMetrics();
        int scoreWidth = fm.stringWidth(scoreText);
        g.drawString(scoreText, Constants.SCREEN_WIDTH - scoreWidth - 10, 25);
    }

    private BufferedImage getBrickImage(Brick b) {
        int currentLevel = levelNum;

        if (b.getType() == BrickType.NORMAL) {
            return AssetManager.normalBrick.get(currentLevel);
        } else if (b.getType() == BrickType.STRONG) {
            switch (b.getHitPoints()) {
                case 3: return AssetManager.strongBrick.get(currentLevel);
                case 2: return AssetManager.strongBrick_1hit.get(currentLevel);
                case 1: return AssetManager.normalBrick.get(currentLevel);
                default: return null;
            }
        } else if (b.getType() == BrickType.EXPLOSIVE) {
            return AssetManager.explosiveBrick.get(currentLevel);
        } else if (b.getType() == BrickType.UNBREAKABLE) {
            return AssetManager.unbreakableBrick.get(currentLevel);
        }
        return null;
    }

    private BufferedImage getPowerUpImage(PowerUp p) {
        switch (p.getType()) {
            case "EXTRA_LIFE":
                return AssetManager.extraLife;
            // Thêm các case khác cho các power-up khác ở đây. Sửa:
            case "EXPAND_PADDLE":
                return AssetManager.expandPaddle;
            case "MULTI_BALL":
                return AssetManager.multiBall;
            case "FAST_BALL":
                return AssetManager.increaseSpeed;
            default:
                return null;
        }
    }

    private void drawPowerUpTimers(Graphics2D g) {
        ArrayList<PowerUp> activeEffects = activePowerUpsEffects;

        // Can hang so kich thuoc
        final int ICON_SIZE = 25; // Hinh vuong
        final int BAR_HEIGHT = 15; // Cao
        final int BAR_WIDTH = 100; // Rong
        final int PADDING = 8;

        int yPos = 25; // Vị trí Y ban đầu, ngay dưới HUD

        for (PowerUp p : activeEffects) {
            // Chỉ vẽ thanh thời gian cho Power-up có thời gian hiệu lực
            // Chinh la cac powerUp co initialDuration > 0
            if (p.getInitialDuration() > 0) {
                BufferedImage icon = getPowerUpImage(p);
                if (icon == null) {
                    continue;
                }

                // 1. Tính toán
                double timePercentage = p.getDuration() / p.getInitialDuration();
                // Ti le la 1
                // Đảm bảo không bị số âm
                timePercentage = Math.max(0, timePercentage);
                // Chieu rong nang luong chinh la ti le thoi gian
                int currentBarWidth = (int) (BAR_WIDTH * timePercentage);
                // Vị trí X, căn lề phải cho đẹp
                int xPos = Constants.SCREEN_WIDTH - BAR_WIDTH - ICON_SIZE - PADDING * 2;

                // 2. Vẽ Icon
                g.drawImage(icon, xPos, yPos, ICON_SIZE, ICON_SIZE, null);
                // 3. Vẽ thanh thời gian
                int barX = xPos + ICON_SIZE + PADDING;
                int barY = yPos + (ICON_SIZE - BAR_HEIGHT) / 2; // Căn giữa thanh với icon

                // Vẽ màu nền cho thanh (giống cái ống rỗng)
                g.setColor(new Color(60, 60, 60)); // Màu xám tối
                g.fillRect(barX, barY, BAR_WIDTH, BAR_HEIGHT);

                // Chọn màu cho "nước" bên trong
                if (p.getType().equals("EXPAND_PADDLE")) {
                    g.setColor(Color.CYAN);
                } else if (p.getType().equals("FAST_BALL")) {
                    g.setColor(Color.ORANGE);
                }
                // Vẽ phần "nước" còn lại, nó sẽ tự ngắn lại
                g.fillRect(barX, barY, currentBarWidth, BAR_HEIGHT);
                // Vẽ viền cho thanh để trông rõ hơn
                g.setColor(Color.DARK_GRAY);
                g.drawRect(barX, barY, BAR_WIDTH, BAR_HEIGHT);
                // Tăng vị trí Y cho Power-up tiếp theo (nếu có)
                yPos += ICON_SIZE + PADDING;
                // Cac powerUp se xep theo truc y
            }
        }
    }

    public void addBall(Ball newBall) {
        if (newBall != null) {
            this.balls.add(newBall);
        }
    }



    public boolean isAlive() {
        return lives > 0;
    }

    public boolean checkWin() {
        return this.getBricks().stream().allMatch(b -> b.getType() == BrickType.UNBREAKABLE);
    }

    public void expandPaddle(int amount) {
        this.paddle.setWidth(this.paddle.getWidth() + amount);
    }

    // Thu nhỏ thanh đỡ
    public void shrinkPaddle(int amount) {
        this.paddle.setWidth(this.paddle.getWidth() - amount);
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


    public void setLastLevel(boolean b) {
        isLastLevel = true;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
