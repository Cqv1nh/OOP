import java.awt.event.*;
import entities.Paddle;
import util.GameState;

// Xu li dau vao ban phim 
public class InputHandler extends KeyAdapter {
    private GameWindow game; // Su dung GameWindow

    public InputHandler(GameWindow game) {
        this.game = game;
    }

    @Override 
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Paddle paddle = game.getPaddle();
        String state = game.getGameState();
        
        if (state.equals(GameState.GAMESTART) && key == KeyEvent.VK_SPACE) {
            game.setGameState(GameState.GAMEPLAYING);
            game.setBallLaunched(false);
        } else if (state.equals(GameState.GAMEEND) && key == KeyEvent.VK_SPACE) {
            game.restartGame();
        } else if (state.equals(GameState.LEVELCLEAR) && key == KeyEvent.VK_ENTER) {
            game.nextLevel();
        }

        if (!state.equals(GameState.GAMEPLAYING)) {
            return;
        }
         // Di chuyen paddle
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            paddle.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            paddle.moveRight();
        } else if (key == KeyEvent.VK_SPACE && !game.isBallLaunched()) {
            game.setBallLaunched(true);
        }
    }
}
