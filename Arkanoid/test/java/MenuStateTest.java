import entities.Button;
import managers.*;
import engine.KeyboardManager;
import engine.MouseManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AssetManager;

public class MenuStateTest {
    private GameStateManager gsm;

    @BeforeEach
    void setUp() {
        KeyboardManager km = new KeyboardManager();
        MouseManager mm = new MouseManager();
        AssetManager.loadImages();
        gsm = new GameStateManager(km, mm);

        gsm.setState("menu");
    }

    @Test
    void testButtonsCreated() {

    }

}
