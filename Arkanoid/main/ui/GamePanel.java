package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Ball;
import entities.Brick;
import entities.Paddle;
import entities.PowerUp;
import util.GameState;
import util.AssetManager;
import util.BrickType;

// Class GamePanel dc dua ra rieng phu trach viec in cac vat the ben trong man hinh cua
// gameWindow
public class GamePanel extends JPanel {
    private GameWindow parent;
    // Constructor lay GameWindow lam thuoc tinh 
    public GamePanel(GameWindow parent) {
        this.parent = parent;
        setFocusable(true);
        setBackground(Color.BLACK); // Dat mau nen 1 lan o day
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String gameState = parent.getGameState(); // Lay trang thai tu gameWindown 
        // Chuyển đổi Graphics thành Graphics2D để có thể khử răng cưa
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (gameState) {
            case GameState.GAMESTART:
                drawGameStartState(g2d);
                break;
            case GameState.GAMEEND:
                drawGameOverState(g2d);
                break;
            case GameState.GAMEPLAYING:
                drawGamePlayingState(g2d);
                break;
            case GameState.LEVELCLEAR:
                drawLevelClearState(g2d);
                break;
            case GameState.GAMEWON:
                drawGameWonState(g2d);
                break;
        }

    }

    // --- CÁC PHƯƠNG THỨC VẼ CHO TỪNG TRẠNG THÁI ---
    // Ve man hinh gameStart
    private void drawGameStartState(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        drawCenteredString("Press SPACE to Start", getWidth(), getHeight(), g, 0, 0);
    }

    private void drawGamePlayingState(Graphics2D g) {
        // 1. Lấy level hiện tại và vẽ ảnh nền tương ứng
        // Chỉ lấy những ảnh cần dùng TRỰC TIẾP trong phương thức này
        int currentLevel = parent.getCurrentLevel();
        BufferedImage bgImage = AssetManager.levelBackgrounds.get(currentLevel);
        BufferedImage ballImage = AssetManager.ball.get(currentLevel);
        BufferedImage paddleImage = AssetManager.paddle.get(currentLevel);

        // Chỉ vẽ nếu ảnh nền tồn tại cho level đó
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        }
        // Nếu không có ảnh nền, nó sẽ tự động dùng màu nền đen đã được thiết lập trong hàm khởi tạo.
        
        // Vẽ Paddle
        Paddle paddle = parent.getPaddle();
        g.drawImage(paddleImage, (int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight(), null);

        // /cũ:Vẽ Ball
        /*Ball ball = parent.getBall();
        g.drawImage(AssetManager.ball, (int) ball.getX(), (int) ball.getY(), (int) ball.getRadius() * 2, (int) ball.getRadius() * 2, null); */
        
        // /mới
        ArrayList<Ball> balls = parent.getBalls(); // Lấy danh sách bóng
        for (Ball ball : balls) {
            g.drawImage(ballImage, 
                    (int) ball.getX(), 
                    (int) ball.getY(), 
                    (int) ball.getRadius() * 2, 
                    (int) ball.getRadius() * 2, null);
        }

        // Vẽ Bricks
        for (Brick b : parent.getBricks()) {
            BufferedImage brickImage = getBrickImage(b);
            if (brickImage != null) {
                g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
            }
        }

        // Vẽ Power-ups
        for (PowerUp p : parent.getPowerUps()) {
            BufferedImage powerUpImage = getPowerUpImage(p);
            if (powerUpImage != null) {
                g.drawImage(powerUpImage, (int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight(), null);
            }
        }

        // Vẽ HUD (Heads-Up Display)
        drawHUD(g);
        // Phuong thuc ve thanh nang luong thoi gian
        drawPowerUpTimers(g);
    }

    private void drawGameOverState(Graphics2D g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        drawCenteredString("GAME OVER", getWidth(), getHeight(), g, 0, -20);
        // Chu o ben tren
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        drawCenteredString("Press SPACE to Restart", getWidth(), getHeight(), g, 0, 40);
        // Chu o duoi
    }

    private void drawLevelClearState(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        drawCenteredString("LEVEL CLEARED!", getWidth(), getHeight(), g, 0, -20);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        drawCenteredString("Press ENTER for Next Level", getWidth(), getHeight(), g, 0, 40);
    }

    private void drawGameWonState(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        // Sửa lỗi chính tả "CONSTRAGLUTION" -> "CONGRATULATIONS!"
        drawCenteredString("CONGRATULATIONS!", getWidth(), getHeight(), g, 0, 0);
    }

    // Cac phuong thuc tien ich
    private void drawHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lives: " + parent.getLives(), 10, 25);
        g.drawString("Level: " + parent.getCurrentLevel(), 120, 25);
        // Căn lề phải cho điểm số
        String scoreText = "Score: " + parent.getScore();
        FontMetrics fm = g.getFontMetrics();
        int scoreWidth = fm.stringWidth(scoreText);
        g.drawString(scoreText, getWidth() - scoreWidth - 10, 25);
    }

    private void drawCenteredString(String text, int panelWidth, 
    int panelHeight, Graphics g, int xOffset, int yOffset) {
        FontMetrics fm = g.getFontMetrics();
        int x = (panelWidth - fm.stringWidth(text)) / 2;
        int y = (fm.getAscent() + (panelHeight - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(text, x + xOffset, y + yOffset);
    }
    private BufferedImage getBrickImage(Brick b) {
        // Lấy level hiện tại từ GameWindow (parent)
        int currentLevel = parent.getCurrentLevel();

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
    // Ve thanh nang luong
    private void drawPowerUpTimers(Graphics2D g) {
        ArrayList<PowerUp> activeEffects = parent.getActivePowerUpEffects();

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
                int xPos = getWidth() - BAR_WIDTH - ICON_SIZE - PADDING * 2;

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
}