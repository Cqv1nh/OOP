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
import java.util.ArrayList;
// Class GamePanel dc dua ra rieng phu trach viec in cac vat the ben trong man hinh cua
// gameWindow
public class GamePanel extends JPanel {
    private GameWindow parent;
    // Constructor lay GameWindow lam thuoc tinh 
    public GamePanel(GameWindow parent) {
        this.parent = parent;
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String gameState = parent.getGameState(); // Lay trang thai tu gameWindown 
        Paddle paddle = parent.getPaddle();
        Ball ball = parent.getBall();
        ArrayList<Brick> brickList = parent.getBricks();
        ArrayList<PowerUp> powerUps = parent.getPowerUps();  
        // Vẽ nền màu đen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameState.equals(GameState.GAMESTART)) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Press SPACE to Start", 200, 300);

        } else if (gameState.equals(GameState.GAMEPLAYING)) {
            // g.setColor(Color.WHITE);
            // g.fillRect(
            //     (int) paddle.getX(), 
            //     (int) paddle.getY(), 
            //     (int) paddle.getWidth(), 
            //     (int) paddle.getHeight());
            // Paddle
            g.drawImage(AssetManager.paddle, 
            (int) paddle.getX(), (int) paddle.getY(), (int) paddle.getWidth(), 
            (int) paddle.getHeight(), null);
            // Ball 
            // g.setColor(Color.RED);
            // g.fillOval((int) ball.getX(), 
            // (int) ball.getY(), (int) ball.getRadius() * 2, 
            // (int) ball.getRadius() * 2);
            g.drawImage(AssetManager.ball, 
            (int) ball.getX(), (int) ball.getY(), (int) ball.getRadius() * 2, 
            (int) ball.getRadius() * 2, null);
                
            // for( Brick b : brickList) {
            //     if (b.getType() == BrickType.NORMAL) {
            //         g.setColor(Color.GREEN);
            //     } else if (b.getType() == BrickType.STRONG) {
            //         if (b.getHitPoints() == 3) {
            //             g.setColor(Color.BLUE);
            //         } else if (b.getHitPoints() == 2) {
            //              // mau xanh nhat hon
            //             g.setColor(new Color(100, 100, 255));
            //         } else if (b.getHitPoints() == 1) {
            //             // Giong voi mau normal brick
            //             g.setColor(Color.GREEN);
            //         }
            //     } else if (b.getType() == BrickType.EXPLOSIVE) {
            //         g.setColor(Color.YELLOW);
            //     } else if (b.getType() == BrickType.UNBREAKABLE) {
            //         g.setColor(Color.GRAY);
            //     }

            //     g.fillRect((int) b.getX(), 
            //     (int) b.getY(), 
            //     (int) b.getWidth(), 
            //     (int) b.getHeight());
            // }
            for (Brick b : brickList) {
                BufferedImage brickImage = getBrickImage(b);
                if (brickImage != null) {
                    g.drawImage(brickImage, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
                }
            }

            // Ve cac powerUps
            // for (PowerUp p : powerUps) {
            //     if (p.getType().equals("EXTRA_LIFE")) {
            //         g.setColor(Color.PINK);
            //         // Ve thanh hinh chu nhat
            //         g.fillRect((int) p.getX(), (int) p.getY(), 
            //         (int) p.getWidth(), (int) p.getHeight());
            //         g.setColor(Color.BLACK);
            //         g.drawString("L", (int) p.getX() + 5, (int) p.getY() + 15);
            //     }
            //     
            // }
                /* else if cho cac loai power up khac, sửa code:
                else if (p.getType().equals("EXPAND_PADDLE")) {
                    g.setColor(Color.BLUE); //Chọn màu sắc
                    //Vẽ hình chữ nhật.
                    g.fillRect((int) p.getX(), (int) p.getY(),
                    (int) p.getWidth(), (int) p.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("E", (int) p.getX() + 5, (int) p.getY() + 15);
                }

                else if (p.getType().equals("MULTI_BALL")) {
                    g.setColor(Color.GREEN); //Chọn màu sắc
                    //Vẽ hình chữ nhật.
                    g.fillRect((int) p.getX(), (int) p.getY(),
                    (int) p.getWidth(), (int) p.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("+1", (int) p.getX() + 5, (int) p.getY() + 15);
                }

                else if (p.getType().equals("FAST_BALL")) {
                    g.setColor(Color.YELLOW); //Chọn màu sắc
                    //Vẽ hình chữ nhật.
                    g.fillRect((int) p.getX(), (int) p.getY(),
                    (int) p.getWidth(), (int) p.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("FL", (int) p.getX() + 5, (int) p.getY() + 15);
                }  */
            
            for (PowerUp p : powerUps) {
                BufferedImage powerUpImage = getPowerUpImage(p);
                if (powerUpImage != null) {
                    g.drawImage(powerUpImage, (int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight(), null);
                }
            }

            // Ve dong chua hien thi so mang
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Lives: " + parent.getLives(), 10, 20);
            // Ve dong hien thi so diem Score
            g.drawString("Scores: " + parent.getScore(), 90, 20);

        } else if (gameState.equals(GameState.GAMEEND)) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", 290, 280);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press SPACE to Restart", 275, 340);
        } else if (gameState.equals(GameState.LEVELCLEAR)) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("LEVEL CLEARED!", 233, 280);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press ENTER for Next Level", 238, 340);
        }
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