package engine;


import util.LevelData;
import ui.GameWindow;
import util.BrickType;
import util.GameState;

public class GameStateManager {
    // THAY ĐỔI: Thêm tham số panelHeight
    public void checkGameStatus(GameWindow game, int panelHeight, int currentLevel) {
        // /cũ
        /*Thay bang gamePanel.getHeight
        if (game.getBall().getY() > panelHeight) {
            // giam mang di 1 lan
            game.loseLives();
            if (game.getLives() > 0) {
                game.resetAfterLifeLost();
            } else {
                game.setGameState(GameState.GAMEEND);
            }
            return; // De tranh kiem tra thang thua khi da thua
        } */

        // Kiểm tra điều kiện thắng
        // Thắng khi tất cả các gạch có thể phá đã bị phá
        if (game.getBricks().stream().allMatch(b -> b.getType() == BrickType.UNBREAKABLE)) {
            // Nếu là level cuối -> THẮNG GAME
            if (currentLevel >= LevelData.getTotalLevels()) {
                game.setGameState(GameState.GAMEWON);
            } else {
                game.setGameState(GameState.LEVELCLEAR);
            }
        }
    }
}
