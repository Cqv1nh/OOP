package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private void drawGameStartState(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        drawCenteredString("Press SPACE to Start", getWidth(), getHeight(), g, 0, 0);
    }

    private void drawGamePlayingState(Graphics2D g) {
        // Vẽ Paddle
        Paddle paddle = parent.getPaddle();
        g.drawImage(AssetManager.paddle, (int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), (int) paddle.getHeight(), null);

        // Vẽ Ball
        Ball ball = parent.getBall();
        g.drawImage(AssetManager.ball, (int) ball.getX(), (int) ball.getY(), (int) ball.getRadius() * 2, (int) ball.getRadius() * 2, null);
        
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
        if (b.getType() == BrickType.NORMAL) {
            return AssetManager.normalBrick;
        } else if (b.getType() == BrickType.STRONG) {
            switch (b.getHitPoints()) {
                case 3: return AssetManager.strongBrick;
                case 2: return AssetManager.strongBrick_1hit;
                case 1: return AssetManager.normalBrick;
                default: return null;
            }
        } else if (b.getType() == BrickType.EXPLOSIVE) {
            return AssetManager.explosiveBrick;
        } else if (b.getType() == BrickType.UNBREAKABLE) {
            return AssetManager.unbreakableBrick;
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
}