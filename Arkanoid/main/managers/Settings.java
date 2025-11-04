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

    /**
     * Thiết lập cài đặt mặc định.
     */
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

    /**
     * Constructor không tham số.
     */
    public Settings() {
    }

    /**
     * Getter cho âm lượng tổng.
     *
     * @return âm lượng tổng.
     */
    public int getMasterVolume() {
        return masterVolume;
    }

    /**
     * Setter cho âm lượng tổng.
     *
     * @param masterVolume âm lượng tổng.
     */
    public void setMasterVolume(int masterVolume) {
        this.masterVolume = masterVolume;
    }

    /**
     * Getter cho âm thanh hiệu ứng.
     *
     * @return âm thanh hiệu ứng.
     */
    public int getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    /**
     * Setter cho âm thanh hiệu ứng.
     *
     * @param soundEffectsVolume âm thanh hiệu ứng.
     */
    public void setSoundEffectsVolume(int soundEffectsVolume) {
        this.soundEffectsVolume = soundEffectsVolume;
    }

    /**
     * Getter cho âm thanh background.
     *
     * @return âm thanh background.
     */
    public int getBackGroundMusic() {
        return backGroundMusic;
    }

    /**
     * Setter cho âm thanh bachground.
     *
     * @param backGroundMusic
     */
    public void setBackGroundMusic(int backGroundMusic) {
        this.backGroundMusic = backGroundMusic;
    }

    /**
     * Getter cho ngôn ngữ.
     *
     * @return ngôn ngữ.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter cho ngôn ngữ.
     *
     * @param language ngôn ngữ.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter cho phím di chuyển trái chính.
     *
     * @return phím di chuyển trái chính.
     */
    public int getMoveLeftPrimary() {
        return moveLeftPrimary;
    }

    /**
     * Setter cho phím di chuyển trái chính.
     *
     * @param moveLeftPrimary phím di chuyển trái chính.
     */
    public void setMoveLeftPrimary(int moveLeftPrimary) {
        this.moveLeftPrimary = moveLeftPrimary;
    }

    /**
     * Getter cho phím di chuyển trái phụ.
     *
     * @return phím di chuyển trái phụ.
     */
    public int getMoveLeftSecondary() {
        return moveLeftSecondary;
    }

    /**
     * Setter cho phím di chuyển trái phụ.
     *
     * @param moveLeftSecondary phím di chuyển trái phụ.
     */
    public void setMoveLeftSecondary(int moveLeftSecondary) {
        this.moveLeftSecondary = moveLeftSecondary;
    }

    /**
     * Getter cho phím di chuyển phải chính.
     *
     * @return phím di chuyển phải chính.
     */
    public int getMoveRightPrimary() {
        return moveRightPrimary;
    }

    /**
     * Setter cho phím di chuyển phải chính.
     *
     * @param moveRightPrimary phím di chuyển phải chính.
     */
    public void setMoveRightPrimary(int moveRightPrimary) {
        this.moveRightPrimary = moveRightPrimary;
    }

    /**
     * Getter cho phím di chuyển phải phụ.
     *
     * @return phím di chuyển phải phụ.
     */
    public int getMoveRightSecondary() {
        return moveRightSecondary;
    }

    /**
     * Setter cho phím di chuyển phải phụ.
     *
     * @param moveRightSecondary phím di chuyển phải phụ.
     */
    public void setMoveRightSecondary(int moveRightSecondary) {
        this.moveRightSecondary = moveRightSecondary;
    }
}

