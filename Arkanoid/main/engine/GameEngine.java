package engine;

import entities.*;
import util.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;

    public GameEngine() {
        paddle = new Paddle(7,17,3,1,0,0,null);
        bricks = new ArrayList<>();
        bricks.add(new NormalBrick(6,6,1,1));
        bricks.add(new StrongBrick(6,7,1,1));
        bricks.add(new UnbreakableBrick(7,6,1,1));
        bricks.add(new ExplosiveBrick(7,7,1,1));
        score = 0;
        lives = 3;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getLives() {
        return lives;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
    public String getGameState() {
        return gameState;
    }

    public void startGame() {
        setGameState(GameState.GAMESTART);
    }

    public void updateGame() {
        setGameState(GameState.GAMEUPDATE);
    }

    public void handleInput(String input) {
        if ("s".equalsIgnoreCase(input)) {
            startGame();

        } else if ("q".equalsIgnoreCase(input)) {
            gameOver();
            return;
        }
        paddle.update(input);
    }

    public void checkColision() {
        // TODO: xử lý va chạm giữa Ball - Brick, Ball - Paddle, Paddle - PowerUp
    }
    public void gameOver() {
        setGameState(GameState.GAMEEND);
    }
}
