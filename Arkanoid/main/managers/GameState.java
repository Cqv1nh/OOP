package managers;


import engine.KeyboardManager;
import engine.MouseManager;

import java.awt.Graphics2D;


public abstract class GameState {
    protected GameStateManager manager;
    protected KeyboardManager km = new KeyboardManager();
    protected MouseManager mm = new MouseManager();

    public GameState(GameStateManager manager) {
        this.manager = manager;
    }

    // Lifecycle methods
    public abstract void enter();
    public abstract void exit();

    // Game loop methods
    public abstract void update();
    public abstract void render(Graphics2D g);

}