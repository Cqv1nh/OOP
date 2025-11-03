package managers;

import java.awt.event.KeyEvent;

public class Settings {
    private static final int DEFAULT_MASTER_VOLUME = 50;
    private static final int DEFAULT_SOUND_EFFECTS_VOLUME = 50;
    private static final int DEFAULT_BACKGROUND_MUSIC_VOLUME = 50;

    private static final int DEFAULT_PADDLE_LEFT_PRIMARY = KeyEvent.VK_A;
    private static final int DEFAULT_PADDLE_RIGHT_PRIMARY = KeyEvent.VK_D;
    private static final int DEFAULT_PADDLE_LEFT_SECONDARY = KeyEvent.VK_LEFT;
    private static final int DEFAULT_PADDLE_RIGHT_SECONDARY = KeyEvent.VK_RIGHT;

    private static final String DEFAULT_LANGUAGE = "en";

    private int masterVolume;
    private int soundEffectsVolume;
    private int backGroundMusic;

    private String language;

    private int moveLeftPrimary;
    private int moveLeftSecondary;
    private int moveRightPrimary;
    private int moveRightSecondary;

    public void setDefault() {
        masterVolume = DEFAULT_MASTER_VOLUME;
        soundEffectsVolume = DEFAULT_SOUND_EFFECTS_VOLUME;
        backGroundMusic = DEFAULT_BACKGROUND_MUSIC_VOLUME;

        language = DEFAULT_LANGUAGE;

        moveLeftPrimary = DEFAULT_PADDLE_LEFT_PRIMARY;
        moveRightPrimary = DEFAULT_PADDLE_RIGHT_PRIMARY;

        moveLeftSecondary = DEFAULT_PADDLE_LEFT_SECONDARY;
        moveRightSecondary = DEFAULT_PADDLE_RIGHT_SECONDARY;
    }

    public Settings() {
    }

    public int getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(int masterVolume) {
        this.masterVolume = masterVolume;
    }

    public int getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public void setSoundEffectsVolume(int soundEffectsVolume) {
        this.soundEffectsVolume = soundEffectsVolume;
    }

    public int getBackGroundMusic() {
        return backGroundMusic;
    }

    public void setBackGroundMusic(int backGroundMusic) {
        this.backGroundMusic = backGroundMusic;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMoveLeftPrimary() {
        return moveLeftPrimary;
    }

    public void setMoveLeftPrimary(int moveLeftPrimary) {
        this.moveLeftPrimary = moveLeftPrimary;
    }

    public int getMoveLeftSecondary() {
        return moveLeftSecondary;
    }

    public void setMoveLeftSecondary(int moveLeftSecondary) {
        this.moveLeftSecondary = moveLeftSecondary;
    }

    public int getMoveRightPrimary() {
        return moveRightPrimary;
    }

    public void setMoveRightPrimary(int moveRightPrimary) {
        this.moveRightPrimary = moveRightPrimary;
    }

    public int getMoveRightSecondary() {
        return moveRightSecondary;
    }

    public void setMoveRightSecondary(int moveRightSecondary) {
        this.moveRightSecondary = moveRightSecondary;
    }
}

