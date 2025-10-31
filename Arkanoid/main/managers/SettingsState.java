package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.Button;
import entities.Slider;
import util.AssetManager;
import util.AudioManager;
import util.RenderUtil;

import java.awt.*;
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

public class SettingsState extends GameState {
    private ArrayList<Slider> sliders =  new ArrayList<>();
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

    public SettingsState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        BufferedImage track = AssetManager.trackImg;
        BufferedImage handle = AssetManager.handleImg;
        BufferedImage fill = AssetManager.fillImg;

        BufferedImage usUkFlag = AssetManager.UkFlag;
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

        masterVolume = new Slider(140, 100, 343, 4, 0, 100,
                50, track, handle, fill, "Master Volume");

        soundFx = new Slider(140, 150, 343, 4, 0, 100,
                50, track, handle, fill, "Sound Effect");

        backGroundMusic = new Slider(140, 200, 343, 4, 0, 100,
                50, track, handle, fill, "Background Music");

        returnButton = new Button(580, 20, 220, 50 , "Return", "Return",
                buttonIdle, buttonHover);
        saveButton = new Button(580, 90, 220, 50 , "Save", "Save",
                buttonIdle, buttonHover);
        returnDefault = new Button(580, 160, 220, 50 , "Set Default", "Set Default",
                buttonIdle, buttonHover);

        int startX = 60;
        int startY = 420;
        int flagWidth = 64;
        int flagHeight = 48;
        int spacing = 20;

        BufferedImage[] flags = {
                usUkFlag, vietnamFlag, germanyFlag, franceFlag,
                spainFlag, portugalFlag, netherlandsFlag,
                russiaFlag, romaniaFlag, italyFlag
        };

        String[] flagNames= {
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

    @Override
    public void enter() {
        loadSettingsFromFile();
    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        masterVolume.handleMouseEvent(mm);
        soundFx.handleMouseEvent(mm);
        backGroundMusic.handleMouseEvent(mm);

        AudioManager.setMasterVolume(masterVolume.getValue() / 100);
        AudioManager.setSoundFxVolume(soundFx.getValue() / 100);
        AudioManager.setBackgroundMusicVolume(backGroundMusic.getValue() / 100);

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
                System.out.println("Set Default Settings");
            }
        }

        for (Button flagButton : flagButtons) {
            if (flagButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    selectedFlagButton.setText(flagButton.getText());
                    selectedFlagButton.setButtonImage(flagButton.getButtonImage());
                    selectedFlagButton.setButtonWhenHover(flagButton.getButtonWhenHover());

                    String selectedLang = flagButton.getText();
                    settings.setLanguage(selectedLang);
                    loadLanguage(selectedLang);
                    saveSettingsToFile();

                    manager.setLangCode(selectedLang);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.decode("#C9C5B1"));
        g.fillRect(0, 0, 800, 600); // Adjust to your screen size

        g.setColor(Color.decode("#3D3D3D"));
        g.drawRect(25, 85, 510 ,140);

        g.setColor(Color.decode("#3D3D3D"));
        g.drawRect(25, 400, 570 ,140);

        g.setFont(new Font("Arial", Font.PLAIN, 10));

        g.setColor(Color.decode("#4A4A4A"));
        g.drawString(volumeSettings, 25, 75);
        g.drawString(languageSettings, 25, 395);

        String[] temp = currentSelectedLang.split(" ");

        for (int i = 0; i < temp.length; i++) {
            g.drawString(temp[temp.length - i - 1], selectedFlagButton.getX(), selectedFlagButton.getY() - (5 + i * 10));
        }

        masterVolume.render(g);
        soundFx.render(g);
        backGroundMusic.render(g);

        returnButton.draw(g, mm.getMouseX(), mm.getMouseY());
        saveButton.draw(g, mm.getMouseX(), mm.getMouseY());
        returnDefault.draw(g, mm.getMouseX(), mm.getMouseY());

        for (Button flagButton : flagButtons) {
            flagButton.drawFlag(g, mm.getMouseX(), mm.getMouseY());
        }

        selectedFlagButton.drawFlag(g, mm.getMouseX(), mm.getMouseY());

    }

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

            System.out.println("Language loaded: " + langCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSettingsToFile() {
        settings.setMasterVolume((int) masterVolume.getValue());
        settings.setSoundEffectsVolume((int) soundFx.getValue());
        settings.setBackGroundMusic((int) backGroundMusic.getValue());
        settings.setLanguage(currentLanguage);
        try {
            String json = settingsToJson(settings);
            Files.write(Paths.get(SETTINGS_FILE), json.getBytes());
            System.out.println("Settings saved to " + SETTINGS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    private String settingsToJson(Settings settings) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting() // Makes the JSON nicely formatted
                .create();
        return gson.toJson(settings);
    }

    private void loadSettingsFromFile() {
        Path path = Paths.get(SETTINGS_FILE);

        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                Gson gson = new Gson();
                settings = gson.fromJson(json, Settings.class);

                // Update sliders with loaded values
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
}
