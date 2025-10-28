package managers;

import engine.*;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import entities.PowerUp;
import ui.BrickManager; // Đảm bảo import đúng
import util.*;

// Cần Point
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet; // Cần HashSet

public class LevelState2 extends GameState{
    private Paddle paddle; // Thanh truot

    private ArrayList<Brick> brickList; // Danh sach gach
    private ArrayList<PowerUp> powerUps; // Danh sach luu cac powerUp dang roi
    private ArrayList<PowerUp> activePowerUpsEffects; // Danh sach hieu ung dang co hieu luc
    

    private int lives = 3; // Khoi tao so mang la 3;
    private int score = 0;
    private int levelNum;
    private boolean ballLaunched = false; // Bong con dinh tren paddle hay da di chuyen
    // private String gameState = util.GameState.GAMESTART; // Trang thai game
    private BrickManager brickManager; // Chỉ khai báo, không khởi tạo ở đây
    
    private final EntityManager entityManager = new EntityManager();
    private final CollisionHandler collisionHandler = new CollisionHandler();
    private final PowerUpManager powerUpManager = new PowerUpManager();

    private boolean isLastLevel = false;
    // private boolean levelWon = false;
    // private String levelFileName;

    private ArrayList<Ball> balls;
    private int countdownTimer = -1; // -1: không hoạt động, >0: đang đếm (tính theo frame)
    private static final int COUNTDOWN_FPS = 60; // FPS giả định cho đếm ngược

    public LevelState2(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

    }

    public void initLevel(int levelNum, int score, int lives, HashSet<Point> remainingBrickIndices) {
        this.levelNum = levelNum;
        this.score = score;
        this.lives = lives;
        paddle = new Paddle(Constants.INIT_PADDLE_X,Constants.INIT_PADDLE_Y,
                Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,0,0,null);
        paddle.setSpeed(Constants.PADDLE_SPEED);

        Ball initialBall = new Ball();
        initialBall.setSpeed(Constants.BALL_SPEED);
        balls = new ArrayList<>();
        balls.add(initialBall);
        powerUps = new ArrayList<>();
        activePowerUpsEffects = new ArrayList<>();

        brickManager = new BrickManager();
        brickList = brickManager.createBricks(levelNum, Constants.SCREEN_WIDTH, remainingBrickIndices);
        System.out.println("Level " + levelNum + " Initialized. Score: " + this.score + ", Lives: " + this.lives);
        
        // Đảm bảo rằng AssetManager.loadImages() được gọi một lần ở đâu đó khi 
        // game bắt đầu (ví dụ: trong hàm tạo Game hoặc GameFrame nếu bạn tạo nó, 
        // hoặc thậm chí trong main trước khi tạo cửa sổ). 
        // (Kiểm tra lại xem bạn đã có lệnh gọi này ở đâu đó chưa).
        // Reset trạng thái phóng bóng khi bắt đầu level mới
        ballLaunched = false;
        countdownTimer = -1; // Đảm bảo countdown không kích hoạt khi init level bình thường
    }

    // === THÊM HÀM KÍCH HOẠT ĐẾM NGƯỢC ===
    public void startLoadCountdown() {
        this.countdownTimer = 3 * COUNTDOWN_FPS;
        this.ballLaunched = false;
        System.out.println("Starting countdown after load...");
    }

    @Override
    public void enter() {
        System.out.println("Entering Level " + levelNum);

        // Kiểm tra xem có phải vào từ load game không
        if (manager.isLoadingGame()) {
            startLoadCountdown();          // Bắt đầu đếm ngược
            manager.setLoadingGameFlag(false); // Reset cờ ngay lập tức
            // KHÔNG reset vị trí bóng/paddle
        } else {
            // Nếu là game mới hoặc qua màn:
            resetBallAndPaddle();          // Reset vị trí
        }
        // Luôn dọn dẹp power-up cũ và reset trạng thái phóng bóng
        clearEffectsAndPowerups();
        ballLaunched = false;

    }

    @Override
    public void exit() {
        System.out.println("Exiting Level " + levelNum);
        countdownTimer = -1;
        // Nên dừng cả âm thanh nếu có âm thanh riêng cho level
    }

    @Override
    public void update() {
        // km.update(); // đã được gọi trong Game.run(), không cần gọi lại ở đây

        if (countdownTimer > 0) {
            countdownTimer --;
            if (countdownTimer == 0) {
                System.out.println("Countdown finished!");
                ballLaunched = false;
                countdownTimer = -1;
            }
            return;
        }

        // Xử lý input chuyển state (ESC, C, F) - Dùng isKeyJustPressed
        if (km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("pause");
            return; // Thoát sớm sau khi đổi state
        }

        if (km.isKeyJustPressed(KeyEvent.VK_C)) {
            // km.clearAllKeys();
            manager.loadNextLevel();
            return;
        }

        if(km.isKeyJustPressed(KeyEvent.VK_F)) {
            manager.setState("game over");
            return;
        }

        int panelWidth = Constants.SCREEN_WIDTH;
        int panelHeight = Constants.SCREEN_HEIGHT;


        km.updatePaddle(paddle);


        // 1. Cập nhật vị trí các thực thể (Bóng, Thanh trượt)
        entityManager.updateEntities(this, panelWidth);

        // === THÊM BƯỚC 1.5: CẬP NHẬT TRẠNG THÁI MỜ CỦA GẠCH ===
        for (Brick b : brickList) {
            b.update(); // Gọi hàm updateFade() chúng ta đã tạo
        }
    
        // 2. Xử lý tất cả các va chạm
        collisionHandler.handleCollisions(this, panelWidth, panelHeight);

        // 3. Cập nhật và quản lý Power-up
        powerUpManager.update(this, panelHeight);

        if (!isBallLaunched() && countdownTimer < 0 ) {
            if (km.outFollow(balls.getFirst())) {
                setBallLaunched(true);
            }
        }

        if (checkWin()) {
            System.out.println("Level " + levelNum + " Won!");
            manager.loadNextLevel(); // Sửa thành tên mới
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

        // === VẼ ĐẾM NGƯỢC HOẶC GAME ===
        if (countdownTimer > 0) {
            // Tính số giây còn lại (1-3)
            int seconds = (countdownTimer / COUNTDOWN_FPS) + 1;
            if (seconds > 3) {
                seconds = 3; // Giới hạn là 3
            }
            // Vẽ số đếm ngược
            g.setColor(new Color(255, 255, 255, 200)); // Màu trắng hơi mờ
            g.setFont(new Font("Arial", Font.BOLD, 120)); // Font rất lớn
            RenderUtil.drawCenteredString(String.valueOf(seconds),
                    Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 0);

            if (paddleImage != null) {
                g.drawImage(paddleImage, (int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight(), null);
            }

            if (ballImage != null && !balls.isEmpty()) {
                Ball firstBall = balls.getFirst();
                // Cập nhật lại vị trí bóng trên paddle (quan trọng nếu paddle di chuyển lúc countdown)
                firstBall.setX(paddle.getX() + paddle.getWidth() / 2 - firstBall.getRadius());
                firstBall.setY(paddle.getY() - firstBall.getRadius() * 2 - 1);
                g.drawImage(ballImage,
                    (int) firstBall.getX(),
                    (int) firstBall.getY(),
                    (int) firstBall.getRadius() * 2,
                    (int) firstBall.getRadius() * 2, null);
            }
            drawHUD(g); // Vẫn vẽ HUD
        
        } else {
            // Vẽ game bình thường khi không đếm ngược
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
                if (b instanceof entities.ExplosiveBrick) {
                    entities.ExplosiveBrick eb = (entities.ExplosiveBrick) b;
        
                    if (eb.isExploding() && eb.getCurrentFrame() < eb.getTotalFrames()) {
                        int frameIndex = eb.getCurrentFrame();
                        BufferedImage frameImg = AssetManager.explosionFrames.get(frameIndex);
            
                        if (frameImg != null) {
                            // Kích thước nổ to hơn 50% so với gạch
                            final int PADDING = (int)(b.getWidth() * 0.5); 

                            final double SCALE_FACTOR = 1.5; // Kích thước gấp rưỡi (150%)
                            int newWidth = (int)(b.getWidth() * SCALE_FACTOR);
                            int newHeight = (int)(b.getHeight() * SCALE_FACTOR);
            
                            // Tính toán PADDING: Lượng chênh lệch kích thước (1.5 - 1.0 = 0.5 lần)
                            // Lượng dịch chuyển để căn giữa: (Kích thước mới - Kích thước cũ) / 2
                            int xOffset = (newWidth - (int)b.getWidth()) / 2;
                            int yOffset = (newHeight - (int)b.getHeight()) / 2;
                
                            g.drawImage(
                                frameImg, 
                                (int)b.getX() - xOffset,      // X: Dịch về bên trái
                                (int)b.getY() - yOffset,      // Y: Dịch lên trên
                                newWidth,                     // Chiều rộng mới
                                newHeight,                    // Chiều cao mới
                            null

                                /*frameImg, 
                                (int)b.getX() - PADDING/2,    // X: Dịch về bên trái để căn giữa
                                (int)b.getY() - PADDING/2,    // Y: Dịch lên trên để căn giữa
                                (int)b.getWidth() + PADDING,  // Chiều rộng mới
                                (int)b.getHeight() + PADDING, // Chiều cao mới
                                null */
                            );
                            // SAU KHI VẼ NỔ, CHUYỂN SANG GẠCH KẾ TIẾP
                            continue; 
                        }
                    }
                }

                BufferedImage brickImage = getBrickImage(b);

                if (brickImage != null) {
                    // cu : g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);

                    // LOGIC VẼ MỜ 
                    if (b.isFading()) {
                        // 1. Lấy độ mờ (alpha) từ viên gạch
                        float alpha = b.getAlpha(); 
                        // 2. Lưu lại thiết lập vẽ cũ
                        Composite oldComposite = g.getComposite(); 
                        
                        // 3. Thiết lập chế độ vẽ mờ
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        
                        // 4. Vẽ viên gạch với độ mờ
                        g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
                        
                        // 5. Khôi phục thiết lập vẽ cũ (RẤT QUAN TRỌNG)
                        g.setComposite(oldComposite); 
                    } else {
                        // Vẽ bình thường (nếu không mờ)
                        g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
                    }
                    // KẾT THÚC
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

    // Thêm phương thức reset vị trí bóng và paddle
    private void resetBallAndPaddle() {
        paddle.setX(Constants.INIT_PADDLE_X);
        paddle.setY(Constants.INIT_PADDLE_Y);
        paddle.setWidth(Constants.PADDLE_WIDTH); // Reset cả chiều rộng paddle
        paddle.stopMoving();

        balls.clear(); // Xóa hết bóng cũ
        Ball newBall = new Ball(); // Tạo bóng mới với vị trí mặc định
        // Đặt lại tốc độ (quan trọng nếu có power-up FastBall từ màn trước)
        newBall.setSpeed(Constants.BALL_SPEED);
        balls.add(newBall);
        ballLaunched = false; // Bóng dính vào paddle
    }

    private void clearEffectsAndPowerups() {
        if (activePowerUpsEffects != null) {
            Iterator<PowerUp> iterator = activePowerUpsEffects.iterator();
            while (iterator.hasNext()) {
                PowerUp p = iterator.next();
                p.removeEffect(this);
                iterator.remove();
            }
        }
        if (powerUps != null) {
            powerUps.clear();
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
        // Đảm bảo paddle không bị thu nhỏ quá mức
         double newWidth = this.paddle.getWidth() - amount;
         this.paddle.setWidth(Math.max(newWidth, Constants.PADDLE_WIDTH / 2.0)); // Giới hạn kích thước tối thiểu
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
        if (lives > 0) { // Chỉ trừ mạng nếu còn > 0
               this.lives--;
               if (this.lives > 0) {
                    // Nếu còn mạng, reset vị trí
                    resetBallAndPaddle();
               }
          }
         System.out.println("Lost a life! Lives remaining: " + this.lives);
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
