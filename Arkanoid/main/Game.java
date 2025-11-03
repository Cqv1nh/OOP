import engine.KeyboardManager;
import engine.MouseManager;
import javax.swing.*;
import java.awt.*;
import managers.GameStateManager;
import util.AssetManager;
import util.AudioManager;
import static java.lang.Thread.sleep;

// Game.java implements Runnable.
public class Game extends JPanel implements Runnable {
    private GameStateManager gameStateManager;
    // Một Thread mới (thread = new Thread(this);) được tạo, lấy chính đối tượng Game (là một Runnable) làm nhiệm vụ.
    private Thread thread;
    private boolean isRunning;
    private KeyboardManager km = new KeyboardManager();
    private MouseManager mm = new MouseManager();
    // Game loop timing
    private final int FPS = 60;
    private final double UPDATE_INTERVAL = 1000000000.0 / FPS;

    /**
     * Constructor không có tham số để setup.
     */
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

    /**
     * Thread Game (Luồng 1 - Logic).
     */
    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        // isRunning = true khi startGame
        // isRunning = false khi stopGame
        while (isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / UPDATE_INTERVAL;
            lastTime = currentTime;

            if (delta >= 1) {
                // Cập nhật input (km.update(), mm.update()).
                mm.update();
                km.update();
                // Cập nhật logic game (gameStateManager.update())
                gameStateManager.update();
                // Yêu cầu vẽ lại (repaint())
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

    /**
     * Event Dispatch Thread (EDT) (Luồng 2 - Giao diện).
     * Đây là luồng mặc định của Java Swing, chịu trách nhiệm xử lý tất cả các tương tác giao diện.
     * Khi luồng game (Luồng 1) gọi repaint(), nó không vẽ ngay lập tức. Nó chỉ yêu cầu EDT rằng lúc nào rảnh thì vẽ lại.
     * EDT, khi đến lượt, sẽ gọi phương thức paintComponent(Graphics g) của Game.java
     * Luồng này sau đó thực thi gameStateManager.render(g2d) để vẽ mọi thứ lên màn hình.
     *
     * @param g the <code>Graphics</code> object to protect
     */
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

    /**
     * Method bắt đầu game.
     */
    public void startGame() {
        if (thread == null) {
            // Tao 1 luong moi
            thread = new Thread(this);
            isRunning = true;
            // Khi game.startGame() được gọi, thread.start() được thực thi.
            thread.start();
            // thread.start() tự động gọi phương thức run() của lớp Game.
            // cả hai luồng hoạt động độc lập và song song. Hệ điều hành sẽ liên tục chuyển đổi CPU giữa hai luồng (và các luồng khác), 
            // tạo cảm giác chúng đang chạy cùng một lúc.
        }
    }

    /**
     * Method dừng game.
     */
    public void stopGame() {
        isRunning = false;
        try {
            if (thread != null) {
                // Lệnh này bảo luồng hiện tại (thường là luồng chính hoặc luồng 
                // giao diện) phải chờ cho đến khi luồng game (thread) 
                // tắt hẳn (tức là đã thoát ra khỏi phương thức run()).
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Re-interrupt thread
            // Nếu luồng hiện tại bị gián đoạn, dòng này sẽ đặt lại 
            // "cờ gián đoạn" để code khác (nếu có) sau đó có 
            // thể biết rằng nó đã bị gián đoạn.
        }
    }

    /**
     * Getter truy cập gamestatemanager.
     *
     * @return gameStateManager.
     */
    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    /**
     * Method main để tạo ra 1 JFrame (cửa sổ).
     *
     * @param args
     */
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

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    game.stopGame();
                    AudioManager.closeAllSounds(); 
                }
            });
        });
    }
}