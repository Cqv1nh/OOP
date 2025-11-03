package managers;

import engine.KeyboardManager;
import engine.MouseManager;
import java.awt.Graphics2D;

public abstract class GameState {
    protected GameStateManager manager;
    protected KeyboardManager km = new KeyboardManager();
    protected MouseManager mm = new MouseManager();

    /**
     * Constructor.
     *
     * @param manager trình quản lý.
     */
    public GameState(GameStateManager manager) {
        this.manager = manager;
    }

    /**
     * Method trừu tượng được gọi khi bắt đầu trạng thái.
     */
    public abstract void enter();

    /**
     * Method trừu tượng được gọi khi thoát khỏi trạng thái.
     */
    public abstract void exit();

    /**
     * Method trừu tượng cập nhật vật thể.
     */
    public abstract void update();

    /**
     * Method trừu tượng vẽ vật thể ra màn hình.
     * @param g
     */
    public abstract void render(Graphics2D g);

}