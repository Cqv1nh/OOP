package managers;

import engine.KeybroadManager;
import engine.MouseManager;
import entities.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameOverState extends GameState {
    private List<Button> buttons;
    private KeybroadManager km;
    private MouseManager mm;

    public GameOverState(GameStateManager manager) {
        super(manager);

        km = manager.getKm();
        mm = manager.getMm();

        buttons = new ArrayList<>();
        buttons.add(new Button(275, 240, 250, 50, "Return Menu"));
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

        km.update();

        // Check for 'R' key press to return to menu
        if (km.getKey(KeyEvent.VK_R)) {
            km.clearAllKeys();
            manager.setState("menu");
        }

        // Update buttons and check for mouse clicks
        for (Button button : buttons) {
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftPressed()) {
                    handleButtonClick(button.getText());
                    return; // Exit early after state change
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Clear background with red color
        g.setColor(Color.decode("#B22222"));
        g.fillRect(0, 0, 800, 600); // Adjust to your screen size

        // Draw "Game Over" text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String gameOverText = "GAME OVER";
        int textX = (800 - fm.stringWidth(gameOverText)) / 2;
        g.drawString(gameOverText, textX, 150);

        // Draw instruction text
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        fm = g.getFontMetrics();
        String instructionText = "Press R to return to menu";
        int instructionX = (800 - fm.stringWidth(instructionText)) / 2;
        g.drawString(instructionText, instructionX, 200);

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
            case "Return Menu":
                manager.setState("menu");
                break;
            default:
                System.err.println("Unknown button: " + buttonText);
                break;
        }
    }
}