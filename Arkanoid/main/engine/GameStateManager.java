package engine;

import ui.GameWindow;
import util.BrickType;
import util.Constants;
import util.GameState;

public class GameStateManager {
    public void checkGameStatus(GameWindow game) {
        // Thay bang gamePanel.getHeight
        if (game.getBall().getY() > Constants.SCREEN_HEIGHT) {
            // giam mang di 1 lan
            game.loseLives();
            if (game.getLives() > 0) {
                game.resetAfterLifeLost();
            } else {
                game.setGameState(GameState.GAMEEND);
            }
        }

        // Kiểm tra điều kiện thắng
        // Thắng khi tất cả các gạch có thể phá đã bị phá
        if (game.getBricks().stream().allMatch(b -> b.getType() == BrickType.UNBREAKABLE)) {
            game.setGameState(GameState.LEVELCLEAR);
        }
    }
}
