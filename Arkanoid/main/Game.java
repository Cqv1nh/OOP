import engine.KeybroadInputJPanel;
import managers.LevelState;
import ui.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Game extends JPanel implements Runnable {
    private LevelState levelState;
    private GameRenderer gameRenderer;
    private Thread thread;
    private boolean isRunning;
    private KeybroadInputJPanel kij;
    private int numLevel = 5;
    private int currentLevel = 1;
    private int totalScore = 0;
    private int lives = 3;

    // Game loop timing
    private final int FPS = 60;
    private final double UPDATE_INTERVAL = 1000000000.0 / FPS;

    public Game() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        setDoubleBuffered(true);

        levelState = new LevelState("main/resources/level1.txt", currentLevel, totalScore, lives); // Fixed: Use the no-arg constructor
        gameRenderer = new GameRenderer();

        kij = new KeybroadInputJPanel();
        addKeyListener(kij);

        System.out.println("Game initialized successfully");
        System.out.println("Bricks loaded: " + levelState.getBricks().size());
    }

    public boolean nextLevel() {
        currentLevel++;
        if (currentLevel > numLevel) {
            System.out.println("WON");
            return false;
        }

        levelState = new LevelState(String.format("main/resources/level%d.txt", currentLevel),
                currentLevel, totalScore, lives);


        //

        return true;
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
                try {
                    levelState.update(kij);
                } catch (IOException e) {
                    e.printStackTrace();
                    isRunning = false;
                }

                repaint();
                delta--;
            }

            // Small sleep to prevent CPU overuse
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (levelState.checkWin()) {
                repaint();
                totalScore = levelState.
                if(!nextLevel()){
                    this.stopGame();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Debug: Check if renderer and gameState are not null
        if (gameRenderer == null) {
            System.out.println("ERROR: gameRenderer is null!");
            return;
        }
        if (levelState == null) {
            System.out.println("ERROR: gameState is null!");
            return;
        }

        gameRenderer.renderGame(g, levelState);
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

    public LevelState getLevelState() {
        return levelState;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
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