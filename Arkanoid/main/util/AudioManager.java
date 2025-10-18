package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    // Dùng Map để lưu trữ các Clip âm thanh, key là tên file (hoặc một định danh)
    private static Map<String, Clip> sounds = new HashMap<>();
    private static Clip backgroundMusicClip; // Clip riêng cho nhạc nền để dễ quản lý

    /**
     * Tải một tệp âm thanh WAV từ đường dẫn tài nguyên.
     *
     * @param path Đường dẫn đến tệp âm thanh trong thư mục resources (ví dụ: "/resources/sounds/my_sound.wav").
     * @param name Tên để tham chiếu âm thanh này trong Map.
     */
    public static void loadSound(String path, String name) {
        try (InputStream is = AudioManager.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Không thể tải âm thanh: " + path);
                return;
            }
            // Tạo AudioInputStream từ InputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
            
            // Lấy định dạng âm thanh
            AudioFormat format = audioInputStream.getFormat();

            // Lấy DataLine.Info cho Clip
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Mở Clip và tải dữ liệu từ AudioInputStream
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            sounds.put(name, clip);
            audioInputStream.close(); // Đóng input stream sau khi tải xong
            System.out.println("Load sound: " + name);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Định dạng tệp âm thanh không được hỗ trợ: " + path);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Lỗi IO khi tải âm thanh: " + path);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Thiết bị âm thanh không có sẵn: " + path);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi tải âm thanh: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Phát một âm thanh một lần.
     * @param name Tên của âm thanh đã tải.
     */
    public static void playSound(String name) {
        Clip clip = sounds.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Dừng nếu đang chạy để phát lại
            }
            clip.setFramePosition(0); // Đặt lại về đầu
            clip.start();
        } else {
            System.err.println("Âm thanh không tìm thấy: " + name);
        }
    }

    /**
     * Phát nhạc nền, lặp lại vô hạn.
     * @param name Tên của âm thanh nhạc nền đã tải.
     */
    public static void playBackgroundMusic(String name) {
        Clip clip = sounds.get(name);
        if (clip != null) {
            if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
                backgroundMusicClip.stop(); // Dừng nhạc nền cũ nếu có
            }
            backgroundMusicClip = clip;
            backgroundMusicClip.setFramePosition(0); // Đặt lại về đầu
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Lặp vô hạn
        } else {
            System.err.println("Nhạc nền không tìm thấy: " + name);
        }
    }

    /**
     * Dừng nhạc nền.
     */
    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    /**
     * Dừng tất cả các âm thanh đã tải (không bao gồm nhạc nền nếu nó đang lặp).
     */
    public static void stopAllSounds() {
        for (Clip clip : sounds.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    /**
     * Giải phóng tài nguyên của tất cả các Clip âm thanh. Nên gọi khi thoát game.
     */
    public static void closeAllSounds() {
        for (Clip clip : sounds.values()) {
            if (clip.isOpen()) {
                clip.close();
            }
        }
        if (backgroundMusicClip != null && backgroundMusicClip.isOpen()) {
            backgroundMusicClip.close();
        }
    }
}