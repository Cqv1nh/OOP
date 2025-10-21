package managers;

import engine.CollisionDetector;
import engine.KeybroadInputJPanel;
import engine.KeybroadManager;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import util.Constants;
import util.FileLevelLoader;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.awt.event.KeyEvent;

public class LevelState extends GameState {
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private boolean isLastLevel = false;
    private boolean levelWon = false;
    private int brickNum;
    private int levelId;
    private int score;
    private int lives;
    private boolean followPaddle = false;
    KeybroadManager km;
    // Level configuration
    private String levelFileName;
    private int levelNum;

    public LevelState(GameStateManager manager) {
        super(manager);  // Pass manager to parent
        km = manager.getKm();
    }

    // Initialize level with specific parameters
    public void initLevel(String levelFileName, int levelNum, int score, int lives) {
        this.levelFileName = levelFileName;
        this.levelNum = levelNum;
        this.score = score;
        this.lives = lives;

        paddle = new Paddle(
                Constants.INIT_PADDLE_X,
                Constants.INIT_PADDLE_Y,
                Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT,
                0, 0, "null"
        );

        ball = new Ball(
                Constants.INIT_BALL_X,
                Constants.INIT_BALL_Y,
                Constants.BALL_DIAMETER,
                Constants.BALL_DIAMETER,
                1, 1, 1,
                Constants.BALL_DIAMETER / 2.0
        );

        paddle.setSpeed(2);
        bricks = FileLevelLoader.loadLevelFromFile(levelFileName);
        brickNum = bricks.size();
        levelId = levelNum;
    }

    @Override
    public void enter() {
        // Called when entering this state
        System.out.println("Entering Level " + levelId);

    }

    @Override
    public void exit() {
        System.out.println("Exiting Level " + levelId);
    }


    @Override
    public void update() {
        if (checkWin()) {
            levelWon = true;
            if (isLastLevel) {
                // Game completed - go to victory screen
                manager.setState("victory");
            } else {
                // Load next level
                manager.loadNextLevel();
            }

        }

        if (km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("pause");
            return;
        }

        km.updatePaddle(paddle);

        if (!CollisionDetector.checkWallCollision(ball)) {
            lives--;
            if (!isAlive()) {
                // Game Over - transition to game over state
                manager.setState("gameover");
                return;
            }
            resetPaddle();
        }

        CollisionDetector.handlePaddleCollision(paddle, ball);

        // Handle brick collisions
        int pointScore;
        for (Brick brick : bricks) {
            pointScore = CollisionDetector.handleBrickCollision(brick, ball);
            if (pointScore > 0) {
                brickNum--;
                score += pointScore;
            }
        }

        paddle.move();

        if (followPaddle) {
            if (km.outFollow(ball)) {
                followPaddle = false;
            }
            followPaddle();
        }

        ball.move();
    }

    @Override
    public void render(Graphics2D g) {

    }

    // Your existing methods stay the same
    public boolean checkWin() {
        return brickNum == 0;
    }

    public void getKeybroadInput(KeybroadInputJPanel kij) {
        kij.update(paddle);
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public void followPaddle() {
        double paddleCentre = paddle.getX() + Constants.PADDLE_WIDTH / 2.0;
        ball.setX(paddleCentre);
    }

    public void resetPaddle() {
        paddle.setX(Constants.INIT_PADDLE_X);
        paddle.setY(Constants.INIT_PADDLE_Y);

        ball.setX(Constants.INIT_BALL_X);
        ball.setY(Constants.INIT_BALL_Y);

        ball.setDx(0);
        ball.setDy(0);

        followPaddle = true;
    }

    // All your getters and setters remain the same
    public Paddle getPaddle() { return paddle; }

    public Ball getBall() { return ball; }

    public List<Brick> getBricks() { return bricks; }

    public int getScore() { return score; }

    public int getLives() { return lives; }

    public int getLevelId() { return levelId; }

    public boolean isLastLevel() { return isLastLevel; }

    public void setLastLevel(boolean lastLevel) { this.isLastLevel = lastLevel; }

}