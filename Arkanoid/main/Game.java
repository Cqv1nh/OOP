
import engine.KeybroadInputJPanel;
import engine.KeybroadManager;
import engine.MouseManager;
import managers.GameStateManager;
import managers.LevelState;
import managers.LevelState2;
import ui.GameRenderer;
import ui.InputHandler;


import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Game extends JPanel implements Runnable {
    private GameStateManager gameStateManager;
    private GameRenderer gameRenderer;
    private Thread thread;
    private boolean isRunning;
    private KeybroadInputJPanel kij;
    private KeybroadManager km = new KeybroadManager();
    private MouseManager mm = new MouseManager();


    // Game loop timing
    private final int FPS = 60;
    private final double UPDATE_INTERVAL = 1000000000.0 / FPS;

    public Game() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        setDoubleBuffered(true);

        this.addKeyListener(km);
        this.addMouseListener(mm);
        this.addMouseMotionListener(mm);

        // Initialize the game state manager
        gameStateManager = new GameStateManager(km, mm);
        gameRenderer = new GameRenderer();

        // Start at menu state
        gameStateManager.setState("menu");

        System.out.println("Game initialized successfully");
    }

    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / UPDATE_INTERVAL;
            lastTime = currentTime;

            if (delta >= 1) {
                //mm.update();
                //km.update();
                gameStateManager.update();

                repaint();
                delta--;
            }

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if renderer and manager are not null
        if (gameRenderer == null) {
            System.out.println("ERROR: gameRenderer is null!");
            return;
        }
        if (gameStateManager == null) {
            System.out.println("ERROR: gameStateManager is null!");
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        // Render based on current state
        String currentStateName = getCurrentStateName();

        switch (currentStateName) {
            case "level":
            case "menu":
            case "pause":
            case "game over":
            case "settings":
            case "victory":
                gameStateManager.render(g2d);
                break;

            default:
                // Fallback
                g2d.setColor(Color.WHITE);
                g2d.drawString("Unknown State", 100, 100);
                break;
        }
    }

    // Helper method to get current state name
    private String getCurrentStateName() {
        if (gameStateManager.getCurrentState() == null) {
            return "unknown";
        }

        // Check instance type to determine state name
        if (gameStateManager.getCurrentState() instanceof LevelState2 ) {
            return "level";
        }
        // You can add more checks for other states if needed
        // For now, we'll use a simple approach

        return "menu"; // Default fallback
    }

    public void startGame() {
        if (thread == null) {
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }
    }

    public void stopGame() {
        isRunning = false;
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Getters for accessing game state manager
    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arkanoid Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            Game game = new Game();
            frame.add(game);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);

            game.requestFocusInWindow(); // Ensure the panel has focus for key input
            game.startGame();

            // Optional: Add window closing listener to properly stop the game
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    game.stopGame();
                }
            });
        });
    }
}