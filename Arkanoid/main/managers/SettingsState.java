package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.Button;
import entities.Slider;
import util.AssetManager;
import util.AudioManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SettingsState extends GameState {
    private ArrayList<Slider> sliders =  new ArrayList<>();
    Button returnButton;
    Button saveButton;
    Button returnDefault;

    Slider masterVolume;
    Slider soundFx;
    Slider backGroundMusic;

    Settings settings;
    private static final String SETTINGS_FILE = "settings.json";

    public SettingsState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        BufferedImage track = AssetManager.trackImg;
        BufferedImage handle = AssetManager.handleImg;
        BufferedImage fill = AssetManager.fillImg;

        masterVolume = new Slider(140, 100, 343, 4, 0, 100,
                50, track, handle, fill, "Master Volume");

        soundFx = new Slider(140, 150, 343, 4, 0, 100,
                50, track, handle, fill, "Sound Effect");

        backGroundMusic = new Slider(140, 200, 343, 4, 0, 100,
                50, track, handle, fill, "Background Music");

        returnButton = new Button(600, 20, 200, 50 , "Return");
        saveButton = new Button(600, 90, 200, 50 , "Save");
        returnDefault = new Button(600, 160, 200, 50 , "Set Default");

        settings= new Settings();
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
                System.out.println("Set Default Settings");
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.decode("#D9D9D9"));
        g.fillRect(0, 0, 800, 600); // Adjust to your screen size

        g.setColor(Color.decode("#3D3D3D"));
        g.drawRect(25, 85, 510 ,140);

        masterVolume.render(g);
        soundFx.render(g);
        backGroundMusic.render(g);

        returnButton.draw(g);
        saveButton.draw(g);
        returnDefault.draw(g);
    }

    private void saveSettingsToFile() {
        settings.setMasterVolume((int) masterVolume.getValue());
        settings.setSoundEffectsVolume((int) soundFx.getValue());
        settings.setBackGroundMusic((int) backGroundMusic.getValue());

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

                System.out.println("Settings loaded from " + SETTINGS_FILE);
            } catch (IOException e) {
                System.err.println("Error reading settings: " + e.getMessage());
            }
        } else {
            System.out.println("Settings file not found, using default values.");
        }
    }
}
