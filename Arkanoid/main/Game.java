
import engine.KeyboardManager;
import engine.MouseManager;
import managers.GameStateManager;
import util.AssetManager;
import util.AudioManager;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Game extends JPanel implements Runnable {
    private GameStateManager gameStateManager;
    private Thread thread;
    private boolean isRunning;
    private KeyboardManager km = new KeyboardManager();
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
                mm.update();
                km.update();
                gameStateManager.update();

                repaint();
                delta--;
            }

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Xóa nền cũ

        // Kiểm tra gameStateManager có tồn tại không
        if (gameStateManager != null) {
            Graphics2D g2d = (Graphics2D) g; // Ép kiểu để có nhiều chức năng vẽ hơn

            // Bật khử răng cưa (làm đồ họa mượt hơn) - Tùy chọn
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Gọi phương thức render của GameState hiện tại
            gameStateManager.render(g2d);
        } else {
            // Vẽ thông báo lỗi nếu gameStateManager chưa được khởi tạo
            g.setColor(Color.RED);
            g.drawString("ERROR: gameStateManager is null!", 100, 100);
        }
    }

    // // Helper method to get current state name
    // private String getCurrentStateName() {
    //     if (gameStateManager.getCurrentState() == null) {
    //         return "unknown";
    //     }

    //     // Check instance type to determine state name
    //     if (gameStateManager.getCurrentState() instanceof LevelState2 ) {
    //         return "level";
    //     }
    //     // You can add more checks for other states if needed
    //     // For now, we'll use a simple approach

    //     return "menu"; // Default fallback
    // }

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
            Thread.currentThread().interrupt(); // Re-interrupt thread
        }
    }

    // Getters for accessing game state manager
    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AssetManager.loadImages(); // được gọi chỉ một lần duy nhất khi game khởi động
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
                    AudioManager.closeAllSounds(); // <-- THÊM DÒNG NÀY VÀO ĐÂY
                }
            });
        });
    }
}