package managers;


import engine.KeybroadManager;
import engine.MouseManager;
import entities.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuState extends GameState {
    private List<Button> buttons;
    private KeybroadManager km;
    private MouseManager mm;

    public MenuState(GameStateManager manager) {
        super(manager);

        km = manager.getKm();
        mm = manager.getMm();

        buttons = new ArrayList<>();
        buttons.add(new Button(275, 170, 250, 50, "New Game"));
        buttons.add(new Button(275, 240, 250, 50, "Load Game"));
        buttons.add(new Button(275, 310, 250, 50, "Options"));
        buttons.add(new Button(275, 380, 250, 50, "Quit"));
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        km.update();

        // Check each button for mouse interaction
        for (Button button : buttons) {
            // Check if the mouse is hovering over this button
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                // Check if left mouse button was clicked
                if (mm.isLeftPressed()) {
                    handleButtonClick(button.getText());
                    return; // Exit early after handling click
                }   
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Clear background
        g.setColor(Color.decode("#2C3E50"));
        g.fillRect(0, 0, 800, 600); // Adjust to your screen size

        // Draw game title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 56));
        FontMetrics fm = g.getFontMetrics();
        String titleText = "ARKANOID";
        int titleX = (800 - fm.stringWidth(titleText)) / 2;
        g.drawString(titleText, titleX, 100);

        // Draw buttons
        for (Button button : buttons) {
            button.draw(g);
        }

        // Draw version or credits at bottom
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("v1.0", 10, 580);
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
                System.out.println("Load Game clicked (not implemented)");
                // manager.loadGame();
                break;

            case "Options":
                // Switch to options/settings state
                System.out.println("Options clicked (not implemented)");
                // manager.setState("options");
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