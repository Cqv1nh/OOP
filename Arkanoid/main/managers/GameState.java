package managers;

import engine.KeybroadInputJPanel;
import engine.KeybroadManager;
import engine.MouseManager;

import java.awt.Graphics2D;
import java.io.IOException;

public abstract class GameState {
    protected GameStateManager manager;
    protected KeybroadManager km = new KeybroadManager();
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