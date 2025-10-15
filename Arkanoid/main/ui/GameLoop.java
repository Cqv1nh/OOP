package ui;


import engine.CollisionHandler;
import engine.EntityManager;
import engine.GameStateManager;
import engine.PowerUpManager;
import util.GameState;

import javax.swing.Timer;

// Class thuc hien vong lap game
public class GameLoop {
    private GameWindow game;
    private GamePanel panel;
    private Timer timer;

    // Các lớp quản lý chuyên biệt
    private final EntityManager entityManager;
    private final CollisionHandler collisionHandler;
    private final PowerUpManager powerUpManager;
    private final GameStateManager gameStateManager;
    
    // Constructor
    public GameLoop(GameWindow game, GamePanel panel) {
        this.game = game;
        this.panel = panel;

        // Khởi tạo các trình quản lý
        this.entityManager = new EntityManager();   
        this.collisionHandler = new CollisionHandler();
        this.powerUpManager = new PowerUpManager();
        this.gameStateManager = new GameStateManager();
    }

    public void start() {
        timer = new Timer(16, e -> update());
        // GAME LOOP 60 FPS
        timer.start();
    }

    private void update() {
        // Neu chua vao game thi k chay Timer
        if (!game.getGameState().equals(GameState.GAMEPLAYING)) {
            panel.repaint();
            return;
        }

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        // 1. Cập nhật vị trí các thực thể (Bóng, Thanh trượt)
        entityManager.updateEntities(game, panelWidth);
        // 2. Xử lý tất cả các va chạm
        collisionHandler.handleCollisions(game, panelWidth);
        // 3. Cập nhật và quản lý Power-up
        powerUpManager.update(game, panelHeight);
        // 4. Kiểm tra trạng thái thắng/thua của game
        // THAY ĐỔI: Truyền thêm level hiện tại vào
        gameStateManager.checkGameStatus(game, panelHeight, game.getCurrentLevel());
        // 5. Vẽ lại màn hình
        panel.repaint();
    }
}
