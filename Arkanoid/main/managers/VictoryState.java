package managers;

import engine.KeyboardManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class VictoryState extends GameState {

    private KeyboardManager km;

    public VictoryState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
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

        if (km.getKey(KeyEvent.VK_R)) {
            manager.setState("menu");
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.decode("#FAB12F"));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 36));
        // Sửa lỗi chính tả "CONSTRAGLUTION" -> "CONGRATULATIONS!"
        drawCenteredString("CONGRATULATIONS!", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 0);

        drawCenteredString("PRESS R TO RETURN HOME", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 40);
    }
}
