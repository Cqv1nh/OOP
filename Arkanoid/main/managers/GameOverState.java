package managers;

import entities.Button;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static util.RenderUtil.drawCenteredString;
import util.AssetManager;
import util.Constants;

public class GameOverState extends GameState {
    private List<Button> buttons;
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 36);
    private final Font SUB_FONT = new Font("Arial", Font.PLAIN, 24);

    /**
     * Constructor khởi tạo màn hình gameover + tạo Return.
     *
     * @param manager
     */
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

    /**
     * Ghi đè: Reset phím ngay sau khi vào màn hình GameOver.
     */
    @Override
    public void enter() {
        km.clearAllKeys();
    }

    /**
     * Ghi đè: Thoát.
     */
    @Override
    public void exit() {
    }

    /**
     * Ghi đè: Cập nhật logic mỗi khung hình.
     */
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
                    return;
                }
            }
        }
    }

    /**
     * Vẽ mọi thứ lên màn hình.
     *
     * @param g
     */
    @Override
    public void render(Graphics2D g) {
        Properties languageProps = manager.getLanguageProps();
        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
        g.setColor(Color.decode("#E30B5D"));
        g.setFont(TITLE_FONT);
        drawCenteredString(languageProps.getProperty("gameover.message1", "GAME OVER"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, -80);
        g.setColor(Color.decode("#66CCFF"));
        g.setFont(SUB_FONT);
        drawCenteredString(languageProps.getProperty("gameover.message2", "Press R to return to menu"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 50);
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
     * Xử lý chức năng của nút (chuyển về menu).
     *
     * @param buttonFunction The function identifier of the clicked button.
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