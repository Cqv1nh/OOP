package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Button;
import entities.Slider;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import util.AssetManager;
import util.AudioManager;
import util.Constants;

public class SettingsState extends GameState {
    
    private ArrayList<Button> flagButtons = new ArrayList<>();
    private Button selectedFlagButton;
    private Properties languageProps = new Properties();

    private String currentLanguage = "en";

    private Button returnButton;
    private Button saveButton;
    private Button returnDefault;

    private Slider masterVolume;
    private Slider soundFx;
    private Slider backGroundMusic;

    private Settings settings;
    private static final String SETTINGS_FILE = "settings.json";

    private String volumeSettings = "";
    private String keybindSettings = "";
    private String languageSettings = "";
    private String currentSelectedLang = "";
    private String moveLeft = "";
    private String moveRight = "";

    private Button changeLeftPrimary;
    private Button changeLeftSecondary;
    private Button changeRightPrimary;
    private Button changeRightSecondary;
    private boolean waitingForKey = false;
    private String waitingForAction = "";

    String[] flagNames;

    Font defaultFont = new Font("Arial", Font.PLAIN, 13);

    /**
     * Thiết lập các cài đặt mặc định.
     *
     * @param manager
     */
    public SettingsState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        BufferedImage track = AssetManager.trackImg;
        BufferedImage handle = AssetManager.handleImg;
        BufferedImage fill = AssetManager.fillImg;

        BufferedImage ukFlag = AssetManager.UkFlag;
        BufferedImage vietnamFlag = AssetManager.vietnamFlag;
        BufferedImage germanyFlag = AssetManager.germanyFlag;
        BufferedImage franceFlag = AssetManager.franceFlag;
        BufferedImage spainFlag = AssetManager.spainFlag;
        BufferedImage portugalFlag = AssetManager.portugalFlag;
        BufferedImage netherlandsFlag = AssetManager.netherlandsFlag;
        BufferedImage russiaFlag = AssetManager.russiaFlag;
        BufferedImage romaniaFlag = AssetManager.romaniaFlag;
        BufferedImage italyFlag = AssetManager.italyFlag;

        BufferedImage buttonIdle = AssetManager.buttonNormal;
        BufferedImage buttonHover = AssetManager.buttonHover;

        masterVolume = new Slider(140, 50, 343, 4, 0, 100,
                50, track, handle, fill, "Master Volume");

        soundFx = new Slider(140, 100, 343, 4, 0, 100,
                50, track, handle, fill, "Sound Effect");

        backGroundMusic = new Slider(140, 150, 343, 4, 0, 100,
                50, track, handle, fill, "Background Music");

        returnButton = new Button(580, 20, 220, 50 , "Return", "Return",
                buttonIdle, buttonHover);
        saveButton = new Button(580, 90, 220, 50 , "Save", "Save",
                buttonIdle, buttonHover);
        returnDefault = new Button(580, 160, 220, 50 , "Set Default", "Set Default",
                buttonIdle, buttonHover);

        int keyBindY = 240;
        changeLeftPrimary = new Button(140, keyBindY, 150, 40, "Left P1",
                "LeftPrimary", buttonIdle, buttonHover);
        changeLeftSecondary = new Button(300, keyBindY, 150, 40, "Left P2",
                "LeftSecondary", buttonIdle, buttonHover);
        changeRightPrimary = new Button(140, keyBindY + 60, 150, 40, "Right P1",
                "RightPrimary", buttonIdle, buttonHover);
        changeRightSecondary = new Button(300, keyBindY + 60, 150, 40, "Right P2",
                "RightSecondary", buttonIdle, buttonHover);

        int startX = 60;
        int startY = 420;
        int flagWidth = 64;
        int flagHeight = 48;
        int spacing = 20;

        BufferedImage[] flags = {
                ukFlag, vietnamFlag, germanyFlag, franceFlag,
                spainFlag, portugalFlag, netherlandsFlag,
                russiaFlag, romaniaFlag, italyFlag
        };

        flagNames = new String[]{
                "en", "vn", "de", "fr",
                "es", "pt", "nl", "ru",
                "ro", "it"
        };

        for (int i = 0; i < flags.length; i++) {
            int x = startX + (i % 5) * (flagWidth + spacing);
            int y = startY + (i / 5) * (flagHeight + spacing);

            int width = flags[i].getWidth();
            int height = flags[i].getHeight();

            if (width > 2000) {
                width /= 48;
                height /= 48;
            } else {
                width /= 24;
                height /= 24;
            }

            Button flagButton = new Button(x, y, width, height, flagNames[i], "language",
                    flags[i], flags[i]);
            flagButtons.add(flagButton);
        }

        int selectedX = startX + (flagWidth + spacing) * 5;
        int selectedY = startY + flagHeight + spacing / 2;

        BufferedImage initSelected = flags[0];
        for (int i = 0; i < flagNames.length; i++){
            if (flagNames[i].equals(manager.getLangCode())) {
                initSelected = flags[i];
            }
        }

        selectedFlagButton = new Button(selectedX , selectedY, flags[0].getWidth() / 24,
                flags[0].getHeight() / 24, "en","language", initSelected, initSelected);

        settings = new Settings();
    }

    /**
     * Load các cài đặt.
     */
    @Override
    public void enter() {
        loadSettingsFromFile();

        Button flagButton = flagButtons.getFirst();
        for (int i = 0; i < flagNames.length; i++) {
            if (flagNames[i].equals(manager.getLangCode()));
            flagButton = flagButtons.get(i);
        }
        selectedFlagButton.setWidth(flagButton.getWidth());
        selectedFlagButton.setHeight(flagButton.getHeight());
    }

    /**
     * Thoát.
     */
    @Override
    public void exit() {
    }

    /**
     * Cập nhật cài đặt.
     */
    @Override
    public void update() {
        masterVolume.handleMouseEvent(mm);
        soundFx.handleMouseEvent(mm);
        backGroundMusic.handleMouseEvent(mm);

        AudioManager.setMasterVolume(masterVolume.getValue() / 100);
        AudioManager.setSoundFxVolume(soundFx.getValue() / 100);
        AudioManager.setBackgroundMusicVolume(backGroundMusic.getValue() / 100);

        if (waitingForKey && km.isAnyKeyPressed()) {
            int keyCode = km.getLastKeyPressed();
            switch (waitingForAction) {
                case "LeftPrimary":
                    settings.setMoveLeftPrimary(keyCode);
                    km.setMoveLeftPrimary(keyCode);
                    changeLeftPrimary.setText(KeyEvent.getKeyText(km.getMoveLeftPrimary()));
                    changeLeftPrimary.setHoveringState(false);
                    break;
                case "LeftSecondary":
                    settings.setMoveLeftSecondary(keyCode);
                    km.setMoveLeftSecondary(keyCode);
                    changeLeftSecondary.setText(KeyEvent.getKeyText(km.getMoveLeftSecondary()));
                    changeLeftSecondary.setHoveringState(false);
                    break;
                case "RightPrimary":
                    settings.setMoveRightPrimary(keyCode);
                    km.setMoveRightPrimary(keyCode);
                    changeRightPrimary.setText(KeyEvent.getKeyText(km.getMoveRightPrimary()));
                    changeRightPrimary.setHoveringState(false);
                    break;
                case "RightSecondary":
                    settings.setMoveRightSecondary(keyCode);
                    km.setMoveRightSecondary(keyCode);
                    changeRightSecondary.setText(KeyEvent.getKeyText(km.getMoveRightSecondary()));
                    changeRightSecondary.setHoveringState(false);
                    break;
            }
            waitingForKey = false;
            waitingForAction = "";
        }

        // Key binding button clicks
        if (changeLeftPrimary.isHovering(mm.getMouseX(), mm.getMouseY()) && mm.isLeftJustPressed()) {
            waitingForKey = true;
            waitingForAction = "LeftPrimary";
            resetHover();
            changeLeftPrimary.setHoveringState(true);
        }
        if (changeLeftSecondary.isHovering(mm.getMouseX(), mm.getMouseY()) && mm.isLeftJustPressed()) {
            waitingForKey = true;
            waitingForAction = "LeftSecondary";
            resetHover();
            changeLeftSecondary.setHoveringState(true);
        }
        if (changeRightPrimary.isHovering(mm.getMouseX(), mm.getMouseY()) && mm.isLeftJustPressed()) {
            waitingForKey = true;
            waitingForAction = "RightPrimary";
            resetHover();
            changeRightPrimary.setHoveringState(true);
        }
        if (changeRightSecondary.isHovering(mm.getMouseX(), mm.getMouseY()) && mm.isLeftJustPressed()) {
            waitingForKey = true;
            waitingForAction = "RightSecondary";
            resetHover();
            changeRightSecondary.setHoveringState(true);
        }

        if(returnButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                manager.setState("menu");
            }
        }

        if(saveButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                saveSettingsToFile();
                System.out.println("Save Settings");
            }
        }

        if(returnDefault.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                settings.setDefault();
                masterVolume.setValue(settings.getMasterVolume());
                soundFx.setValue(settings.getSoundEffectsVolume());
                backGroundMusic.setValue(settings.getBackGroundMusic());
                loadLanguage(settings.getLanguage());

                km.setMoveLeftPrimary(settings.getMoveLeftPrimary());
                km.setMoveLeftSecondary(settings.getMoveLeftSecondary());
                km.setMoveRightPrimary(settings.getMoveRightPrimary());
                km.setMoveRightSecondary(settings.getMoveRightSecondary());

                changeLeftPrimary.setText(KeyEvent.getKeyText(km.getMoveLeftPrimary()));
                changeLeftSecondary.setText(KeyEvent.getKeyText(km.getMoveLeftSecondary()));
                changeRightPrimary.setText(KeyEvent.getKeyText(km.getMoveRightPrimary()));
                changeRightSecondary.setText(KeyEvent.getKeyText(km.getMoveRightSecondary()));
                System.out.println("Set Default Settings");
            }
        }

        for (Button flagButton : flagButtons) {
            if (flagButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    selectedFlagButton.setText(flagButton.getText());
                    selectedFlagButton.setButtonNormal(flagButton.getButtonNormal());
                    selectedFlagButton.setButtonHover(flagButton.getButtonHover());
                    selectedFlagButton.setWidth(flagButton.getWidth());
                    selectedFlagButton.setHeight(flagButton.getHeight());

                    String selectedLang = flagButton.getText();
                    settings.setLanguage(selectedLang);
                    loadLanguage(selectedLang);
                    saveSettingsToFile();

                    manager.setLangCode(selectedLang);
                }
            }
        }
    }

    /**
     * Vẽ các đối tượng ra màn hình.
     *
     * @param g
     */
    @Override
    public void render(Graphics2D g) {
        Font font = defaultFont;

        g.drawImage(AssetManager.menuBackground, 0, 0,
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        g.setColor(Color.decode("#FAFFFF"));
        g.drawRect(25, 35, 510 ,140);
        g.drawRect(25, 220, 510, 140);
        g.drawRect(25, 400, 570 ,140);

        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.decode("#FAFFFF"));
        g.drawString(volumeSettings, 25, 25);
        g.drawString(languageSettings, 25, 395);
        g.drawString(keybindSettings, 25, 215);

        FontMetrics metrics = g.getFontMetrics(font);

        while (metrics.stringWidth(moveLeft) > 110 || metrics.stringWidth(moveRight) > 110) {
            font = new Font("Arial", Font.PLAIN, font.getSize() - 1);
            metrics = g.getFontMetrics(font);
        }
        g.setFont(font);
        g.drawString(moveLeft, 35, 265);
        g.drawString(moveRight, 35, 325);


        String[] temp = currentSelectedLang.split(" ");

        for (int i = 0; i < temp.length; i++) {
            g.drawString(temp[temp.length - i - 1], selectedFlagButton.getX(), selectedFlagButton.getY() - (5 + i * 12));
        }

        masterVolume.render(g);
        soundFx.render(g);
        backGroundMusic.render(g);

        drawKeyButton(g, changeLeftPrimary, settings.getMoveLeftPrimary());
        drawKeyButton(g, changeLeftSecondary, settings.getMoveLeftSecondary());
        drawKeyButton(g, changeRightPrimary, settings.getMoveRightPrimary());
        drawKeyButton(g, changeRightSecondary, settings.getMoveRightSecondary());

        returnButton.draw(g, mm.getMouseX(), mm.getMouseY());
        saveButton.draw(g, mm.getMouseX(), mm.getMouseY());
        returnDefault.draw(g, mm.getMouseX(), mm.getMouseY());

        for (Button flagButton : flagButtons) {
            flagButton.drawFlag(g, mm.getMouseX(), mm.getMouseY());
        }

        selectedFlagButton.drawFlag(g, mm.getMouseX(), mm.getMouseY());
    }

    /**
     * Vẽ nút bấm.
     *
     * @param g
     * @param button nút bấm.
     * @param keyCode
     */
    private void drawKeyButton(Graphics2D g, Button button, int keyCode) {
        button.draw(g, mm.getMouseX(), mm.getMouseY());
        //g.drawString(KeyEvent.getKeyText(keyCode), button.getX() + 10, button.getY() + 25);
    }

    /**
     * Tải ngôn ngữ lên.
     *
     * @param langCode mã ngôn ngữ.
     */
    private void loadLanguage(String langCode) {
        currentLanguage = langCode;
        languageProps.clear();

        String fileName = "language/lang_" + langCode + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.err.println("Language file not found: " + fileName);
                return;
            }

            languageProps.load(new InputStreamReader(input, StandardCharsets.UTF_8));

            returnButton.setText(languageProps.getProperty("settings.return", "Return"));
            saveButton.setText(languageProps.getProperty("settings.save", "Save"));
            returnDefault.setText(languageProps.getProperty("settings.default", "Set Default"));

            masterVolume.setName(languageProps.getProperty("settings.master", "Master Volume"));
            soundFx.setName(languageProps.getProperty("settings.fx", "Sound Effect"));
            backGroundMusic.setName(languageProps.getProperty("settings.background",
                    "Background Music"));

            volumeSettings = languageProps.getProperty("settings.volume", "VOLUME SETTINGS");
            keybindSettings = languageProps.getProperty("settings.keyBind", "KEY BINDING");
            languageSettings = languageProps.getProperty("settings.lang", "");
            currentSelectedLang = languageProps.getProperty("settings.currentLang", "SELECTED LANGUAGE");
            moveLeft = languageProps.getProperty("settings.moveLeft", "MOVE LEFT");
            moveRight = languageProps.getProperty("settings.moveRight", "MOVE RIGHT");

            System.out.println("Language loaded: " + langCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lưu lại cài đặt.
     */
    private void saveSettingsToFile() {
        settings.setMasterVolume((int) masterVolume.getValue());
        settings.setSoundEffectsVolume((int) soundFx.getValue());
        settings.setBackGroundMusic((int) backGroundMusic.getValue());
        settings.setLanguage(currentLanguage);

        settings.setMoveLeftPrimary(km.getMoveLeftPrimary());
        settings.setMoveLeftSecondary(km.getMoveLeftSecondary());
        settings.setMoveRightPrimary(km.getMoveRightPrimary());
        settings.setMoveRightSecondary(km.getMoveRightSecondary());

        try {
            String json = settingsToJson(settings);
            Files.write(Paths.get(SETTINGS_FILE), json.getBytes());
            System.out.println("Settings saved to " + SETTINGS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Lưu vào file json.
     *
     * @param settings
     * @return
     */
    private String settingsToJson(Settings settings) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting() // Makes the JSON nicely formatted
                .create();
        return gson.toJson(settings);
    }

    /**
     * Tải các cài đặt đã lưu.
     */
    private void loadSettingsFromFile() {
        Path path = Paths.get(SETTINGS_FILE);

        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                Gson gson = new Gson();
                settings = gson.fromJson(json, Settings.class);

                km.setMoveLeftPrimary(settings.getMoveLeftPrimary());
                km.setMoveLeftSecondary(settings.getMoveLeftSecondary());
                km.setMoveRightPrimary(settings.getMoveRightPrimary());
                km.setMoveRightSecondary(settings.getMoveRightSecondary());

                changeLeftPrimary.setText(KeyEvent.getKeyText(km.getMoveLeftPrimary()));
                changeLeftSecondary.setText(KeyEvent.getKeyText(km.getMoveLeftSecondary()));
                changeRightPrimary.setText(KeyEvent.getKeyText(km.getMoveRightPrimary()));
                changeRightSecondary.setText(KeyEvent.getKeyText(km.getMoveRightSecondary()));

                masterVolume.setValue(settings.getMasterVolume());
                soundFx.setValue(settings.getSoundEffectsVolume());
                backGroundMusic.setValue(settings.getBackGroundMusic());

                if (settings.getLanguage() != null) {
                    loadLanguage(settings.getLanguage());
                }

                System.out.println("Settings loaded from " + SETTINGS_FILE);
            } catch (IOException e) {
                System.err.println("Error reading settings: " + e.getMessage());
            }
        } else {
            System.out.println("Settings file not found, using default values.");
        }
    }

    /**
     * Tắt việc di chuột vào của tất cả nút liên quan.
     */
    private void resetHover() {
        changeLeftPrimary.setHoveringState(false);
        changeLeftSecondary.setHoveringState(false);
        changeRightPrimary.setHoveringState(false);
        changeRightSecondary.setHoveringState(false);
    }
}
