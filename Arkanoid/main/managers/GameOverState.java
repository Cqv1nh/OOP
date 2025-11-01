package managers;

import entities.Button;
import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;


import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class GameOverState extends GameState {
    private Button backButtons;
    private final Font FONT_TITLE = new Font("Arial", Font.BOLD, 36);
    private final Font FONT_SUBTITLE = new Font("Arial", Font.BOLD, 24);

    public GameOverState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        backButtons = new Button(275, 450, 250, 50, "Return Menu");
    }

    @Override
    public void enter() {
        km.clearAllKeys();
    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {

        // Check for 'R' key press to return to menu
        if (km.isKeyJustPressed(KeyEvent.VK_R)) {
            manager.setState("menu");
            return;
        }

        // Update buttons and check for mouse clicks
        backButtons.setHoveringState(backButtons.isHovering(mm.getMouseX(), mm.getMouseY()));
        if (backButtons.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                handleButtonClick(backButtons.getText());
                return; // Exit early after state change
            }
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        // Clear background with red color
        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        // Draw "Game Over" text
        g.setColor(Color.RED);
        g.setFont(FONT_TITLE);
        drawCenteredString("GAME OVER", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -20);

        g.setColor(Color.CYAN);
        g.setFont(FONT_SUBTITLE);
        drawCenteredString("PRESS R TO RETURN HOME", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 30);
        // Draw instruction text

        // Draw buttons
        backButtons.draw(g);
    }

    /**
     * Handles button click actions
     * @param buttonText The text of the clicked button
     */
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Return Menu":
                manager.setState("menu");
                break;
            default:
                System.err.println("Unknown button: " + buttonText);
                break;
        }
    }
}