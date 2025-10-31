package managers;


import engine.KeyboardManager;
import engine.MouseManager;
import entities.Button;
import util.AssetManager;
import util.Constants;

// Cần Point
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuState extends GameState {
    private List<Button> buttons;
    private KeyboardManager km;
    private MouseManager mm;

    public MenuState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        buttons = new ArrayList<>();
        buttons.add(new Button(275, 170, 250, 50, "New Game"));
        buttons.add(new Button(275, 240, 250, 50, "Load Game")); // Nút này sẽ được xử lý
        buttons.add(new Button(275, 310, 250, 50, "High Scores")); // Them nut
        buttons.add(new Button(275, 380, 250, 50, "Settings"));
        buttons.add(new Button(275, 450, 250, 50, "Quit"));
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        // mm.update(); // Cập nhật chuột để check click

        // Check each button for mouse interaction
        for (Button button : buttons) {
            // Cap nhat trang thai nut bam
            button.setHoveringState(button.isHovering(mm.getMouseX(), mm.getMouseY()));
            // Check if the mouse is hovering over this button
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                // Thoa man ca 2 dieu kien la chuot nam trong nut va dc nhan 
                // Check if left mouse button was clicked
                if (mm.isLeftJustPressed()) {
                    handleButtonClick(button.getText());
                    return; // Exit early after handling click
                }   
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        
        // background
        g.drawImage(AssetManager.menuBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
        
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
     * Handles button click actions
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
}