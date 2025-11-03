package managers;

import entities.Button;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import util.AssetManager;
import util.Constants;

public class MenuState extends GameState {
    private List<Button> buttons;

    /**
     * Khởi tạo màn hình menu, lấy trình quản lý phím/chuột, và tạo 5 nút bấm (New Game, Load Game, Settings, High Scores, Quit).
     *
     * @param manager
     */
    public MenuState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        BufferedImage buttonIdle = AssetManager.buttonNormal;
        BufferedImage buttonHover = AssetManager.buttonHover;

        buttons = new ArrayList<>();
        buttons.add(new Button(275, 170, 250, 50, "New Game", "New Game",
                buttonIdle, buttonHover));
        buttons.add(new Button(275, 240, 250, 50, "Load Game", "Load Game",
                buttonIdle, buttonHover)); // Nút này sẽ được xử lý
        buttons.add(new Button(275, 310, 250, 50, "Settings", "Settings",
                buttonIdle, buttonHover));
        buttons.add(new Button(275, 380,250, 50, "High Scores", "High Scores",
                buttonIdle, buttonHover));
        buttons.add(new Button(275, 450, 250, 50, "Quit", "Quit",
                buttonIdle, buttonHover));
    }

    /**
     * Method gọi loadLanguage() để tải và áp dụng file ngôn ngữ (dịch văn bản các nút).
     */
    @Override
    public void enter() {
        loadLanguage(manager.getLangCode());
    }

    /**
     * Method exit.
     */
    @Override
    public void exit() {
    }

    /**
     * Method cập nhật logic mỗi khung hình, kiểm tra chuột có liên quan gì 5 nút không.
     */
    @Override
    public void update() {
        // Check each button for mouse interaction
        for (Button button : buttons) {
            // Check if the mouse is hovering over this button
            button.setHoveringState(button.isHovering(mm.getMouseX(), mm.getMouseY()));
            // nut cap nhat trang thai chuot cho nut bam
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                // Thoa man ca 2 dieu kien la chuot nam trong nut va dc nhan
                // Check if left mouse button was clicked
                if (mm.isLeftJustPressed()) {
                    handleButtonClick(button.getFunction());
                    return; // Exit early after handling click
                }
            }
        }
    }

    /**
     * Vẽ ra màn hình.
     *
     * @param g
     */
    @Override
    public void render(Graphics2D g) {
        // background
        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH,
                Constants.SCREEN_HEIGHT, null);

        // Draw game title
        int logoWidth = 400; // Chiều rộng mong muốn
        int logoHeight = 100; // Chiều cao mong muốn
        int logoX = (Constants.SCREEN_WIDTH - logoWidth) / 2;
        int logoY = 50; // Cách đỉnh 50px
        g.drawImage(AssetManager.gameLogo, logoX, logoY, logoWidth, logoHeight, null);

        // Draw buttons
        for (Button button : buttons) {
            button.draw(g);
        }
    }

    /**
     * Xử lý logic khi một nút được click: dùng switch case để quyết định hành động.
     *
     * @param buttonText The text of the clicked button
     */
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "New Game":
                // Start a new game (resets progress and loads level 1)
                manager.startGame();
                break;

            case "Load Game":
                // Load a saved game (implement your save/load system)
                // Nút "Load Game" không nên tự động tải file nữa, 
                // mà chuyển sang một GameState mới là LoadGameState
                manager.setState("loadgame");
                break;
            
            case "High Scores":
                manager.setState("highscores"); // Chuyen sang state diem cao
                // Dc thuc hien trong GameStateManager
                break;

            case "Settings":
                // Switch to options/settings state
                // System.out.println("Options clicked (not implemented)");
                manager.setState("settings");
                break;

            case "Quit":
                // Exit the application
                System.out.println("Quitting game...");
                System.exit(0);
                break;

            default:
                System.err.println("Unknown button: " + buttonText);
                break;
        }
    }

    /**
     * Đọc file ngôn ngữ .properties (dựa trên mã langCode), lấy văn bản đã dịch, cập nhật lại ngôn ngữ.
     *
     * @param langCode
     */
    private void loadLanguage(String langCode) {
        Properties Props = manager.getLanguageProps();
        Props.clear();

        String fileName = "language/lang_" + langCode + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.err.println("Language file not found: " + fileName);
                return;
            }

            Props.load(new InputStreamReader(input, StandardCharsets.UTF_8));

            System.out.println(Props.getProperty("settings.save"));

            buttons.get(0).setText(Props.getProperty("menu.new_game", "New Game"));
            buttons.get(1).setText(Props.getProperty("menu.load_game", "Load Game"));
            buttons.get(2).setText(Props.getProperty("menu.settings", "Settings"));
            buttons.get(3).setText(Props.getProperty("menu.highScore", "High Scores"));
            buttons.get(4).setText(Props.getProperty("menu.quit", "Quit"));


            System.out.println("Language loaded: " + langCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}