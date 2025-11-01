package managers;

import entities.Button;
import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class GameOverState extends GameState {
    private List<Button> buttons;

    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private final Font SUB_FONT = new Font("Arial", Font.PLAIN, 24);

    public GameOverState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        BufferedImage buttonIdle = AssetManager.buttonNormal;
        BufferedImage buttonHover = AssetManager.buttonHover;

        buttons = new ArrayList<>();
        // Keep function identifier "Return Menu" for handler compatibility
        buttons.add(new Button(275, 450, 250, 50, "RETURN", "Return Menu",
                buttonIdle, buttonHover));
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
            km.clearAllKeys();
            manager.setState("menu");
            return;
        }

        // Update buttons and check for mouse clicks
        for (Button button : buttons) {
            button.setHoveringState(button.isHovering(mm.getMouseX(), mm.getMouseY()));
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    handleButtonClick(button.getFunction());
                    return; // Exit early after state change
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Properties languageProps = manager.getLanguageProps();

        // Background
        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        // Title
        g.setColor(Color.RED);
        g.setFont(TITLE_FONT);
        drawCenteredString(languageProps.getProperty("gameover.message1", "GAME OVER"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 0);

        // Instruction
        g.setColor(Color.CYAN);
        g.setFont(SUB_FONT);
        drawCenteredString(languageProps.getProperty("gameover.message2", "Press R to return to menu"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 40);


        // Localize button text if language properties available
        if (!buttons.isEmpty()) {
            buttons.get(0).setText(languageProps.getProperty("gameover.return", "RETURN"));
        }

        buttons.getFirst().setText(languageProps.getProperty("gameover.return", "RETURN"));
        // Draw buttons
        for (Button button : buttons) {
            button.draw(g);
        }
    }

    /**
     * Handles button click actions
     * @param buttonFunction The function identifier of the clicked button
     */
    private void handleButtonClick(String buttonFunction) {
        switch (buttonFunction) {
            case "Return Menu":
                manager.setState("menu");
                break;
            default:
                System.err.println("Unknown button function: " + buttonFunction);
                break;
        }
    }
}