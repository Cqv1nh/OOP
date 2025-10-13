package ui;

import entities.Ball;
import entities.Brick;
import entities.Paddle;
import managers.LevelState;
import util.Constants;

import java.awt.*;

public class GameRenderer {

    public GameRenderer() {

    }

    public void drawBackground(Graphics g) {
            g.setColor(Color.gray);
            g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    public void drawPaddle(Graphics g, Paddle paddle) {
        g.setColor(Color.CYAN);
        g.fillRect((int) paddle.getX(), (int) paddle.getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
    }

    public void drawBall(Graphics g, Ball ball) {
        g.setColor(Color.YELLOW);
        g.fillOval((int) ball.getX(), (int) ball.getY(),
                (int) ball.getWidth(), (int) ball.getHeight());
    }

    public void drawBrick(Graphics g, Brick brick) {
        if (brick.isDestroyed()) {
            return;
        }

        g.setColor(Color.GREEN);
        g.fillRect((int) brick.getX(), (int) brick.getY(),
                (int) brick.getWidth(), (int) brick.getHeight());
        g.setColor(Color.WHITE);
        g.drawRect((int) brick.getX(), (int) brick.getY(),
                (int) brick.getWidth(), (int) brick.getHeight());
    }

    public void drawScore (Graphics g, int lives, int score) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        Font hudFont = new Font("Consolas", Font.BOLD, 20);
        g2d.setFont(hudFont);
        g2d.setColor(Color.WHITE);


        String scoreText = String.format("SCORE: %04d", score); // Zero-pads the score
        String livesText = "LIVES: " + lives;


        g2d.drawString(scoreText, 10, 25);


        FontMetrics metrics = g2d.getFontMetrics(hudFont);
        int livesTextWidth = metrics.stringWidth(livesText);
        int x = Constants.SCREEN_WIDTH - livesTextWidth - 10;
        g2d.drawString(livesText, x, 25);
    }


    public void renderGame(Graphics g, LevelState levelState) {
        if(levelState.checkWin()) {
            if(levelState.isLastLevel()) {
                gameOver(g);
            }
            nextLevel(g, levelState.getLevelId());
            return;
        }

        drawBackground(g);
        drawPaddle(g, levelState.getPaddle());
        drawBall(g, levelState.getBall());

        drawScore(g, levelState.getLives(), levelState.getScore());
        for (Brick brick :  levelState.getBricks()) {
            drawBrick(g, brick);
        }
    }

    public void nextLevel(Graphics g, int previousLevel) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String message = String.format("Level %d passed, level %d is next.",
                previousLevel - 1, previousLevel);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        g.drawString(message, Constants.SCREEN_WIDTH / 2 - textWidth / 2,
                Constants.SCREEN_HEIGHT / 2);
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String message = String.format("Game Won");
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        g.drawString(message, Constants.SCREEN_WIDTH / 2 - textWidth / 2,
                Constants.SCREEN_HEIGHT / 2);
    }
}
