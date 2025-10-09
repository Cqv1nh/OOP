import javax.swing.*;
import java.awt.*;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import util.GameState;
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

        // Vẽ nền màu đen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameState.equals(GameState.GAMESTART)) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Press SPACE to Start", 200, 300);

        } else if (gameState.equals(GameState.GAMEPLAYING)) {
            g.setColor(Color.WHITE);
            g.fillRect(
                (int) paddle.getX(), 
                (int) paddle.getY(), 
                (int) paddle.getWidth(), 
                (int) paddle.getHeight());
            // Ball 
            g.setColor(Color.RED);
            g.fillOval((int) ball.getX(), 
            (int) ball.getY(), (int) ball.getRadius() * 2, 
            (int) ball.getRadius() * 2);
                
            for( Brick b : brickList) {
                if (b.getType() == BrickType.NORMAL) {
                    g.setColor(Color.GREEN);
                }

                if (b.getType() == BrickType.STRONG) {
                    g.setColor(Color.BLUE);
                }
                g.fillRect((int) b.getX(), 
                (int) b.getY(), 
                (int) b.getWidth(), 
                (int) b.getHeight());
            }
            // Ve dong chua hien thi so mang
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Lives: " + parent.getLives(), 10, 20);

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
}