package managers;

import engine.CollisionDetector;
import engine.KeybroadInputJPanel;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import util.Constants;
import util.FileLevelLoader;

import java.io.IOException;
import java.util.List;

public class LevelState {
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private boolean isLastLevel = false;
    private boolean levelWon = false;
    private int brickNum;
    private int levelId;
    private int score;
    private int lives;

    public LevelState(String levelFileName, int levelNum, int score, int lives) {
        paddle = new Paddle(
                Constants.INIT_PADDLE_X,
                Constants.INIT_PADDLE_Y,
                Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT,
                0, 0, "None"
        );

        this.score = score;
        this.lives = lives;

        ball = new Ball ( 5, 5 ,Constants.BALL_DIAMETER / 2.0);
        bricks = FileLevelLoader.loadLevelFromFile(levelFileName);

        brickNum = bricks.size();
        levelId = levelNum;
    }

    public boolean checkWin() {
        return brickNum == 0;
    }
    public void getKeybroadInput(KeybroadInputJPanel kij) throws IOException {
        kij.update(paddle);
    }

    public void resetPaddle() {

    }

    public void update(KeybroadInputJPanel kij) throws IOException {

        getKeybroadInput(kij);

        CollisionDetector.checkWallCollision(ball);
        CollisionDetector.handlePaddleCollision(paddle, ball);

        int pointScore;
        for (Brick brick : bricks) {
            pointScore = CollisionDetector.handleBrickCollision(brick, ball);
            if(pointScore > 0) {
                brickNum--;
                score += pointScore;
            }
        }

        paddle.move();
        ball.move();
    }

    public void hold(KeybroadInputJPanel kij) {
        while (true) {
            if (!kij.hold()) {
                break;
            }
        }
    }
    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public boolean isLastLevel() {
        return isLastLevel;
    }

    public void setLastLevel(boolean lastLevel) {
        this.isLastLevel = lastLevel;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getBrickNum() {
        return brickNum;
    }

    public void setBrickNum(int brickNum) {
        this.brickNum = brickNum;
    }

    public boolean isLevelWon() {
        return levelWon;
    }

    public void setLevelWon(boolean levelWon) {
        this.levelWon = levelWon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public static void main(String[] args) {
        LevelState levelState = new LevelState("main/resources/level2.txt", 1 ,0 ,0);
        List<Brick> test = levelState.getBricks();

        for (Brick brick : test) {
            System.out.println(String.format("%f + %f", brick.getX(), brick.getY()));
        }
    }
}
