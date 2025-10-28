package managers;

import java.awt.event.KeyEvent;

public class Settings {
    private static final int DEFAULT_MASTER_VOLUME = 50;
    private static final int DEFAULT_SOUND_EFFECTS_VOLUME = 50;
    private static final int DEFAULT_BACKGROUND_MUSIC_VOLUME = 50;

    private static final int DEFAULT_PADDLE_LEFT = KeyEvent.VK_A;
    private static final int DEFAULT_PADDLE_RIGHT = KeyEvent.VK_D;

    private static final String DEFAULT_LANGUAGE = "english";

    private int masterVolume;
    private int soundEffectsVolume;
    private int backGroundMusic;

    private int paddleLeft;
    private int paddleRight;


    public void setDefault() {
        masterVolume = DEFAULT_MASTER_VOLUME;
        soundEffectsVolume = DEFAULT_SOUND_EFFECTS_VOLUME;
        backGroundMusic = DEFAULT_BACKGROUND_MUSIC_VOLUME;

        paddleLeft =  DEFAULT_PADDLE_LEFT;
        paddleRight = DEFAULT_PADDLE_RIGHT;
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

    public int getPaddleLeft() {
        return paddleLeft;
    }

    public void setPaddleLeft(int paddleLeft) {
        this.paddleLeft = paddleLeft;
    }

    public int getPaddleRight() {
        return paddleRight;
    }

    public void setPaddleRight(int paddleRight) {
        this.paddleRight = paddleRight;
    }
}

