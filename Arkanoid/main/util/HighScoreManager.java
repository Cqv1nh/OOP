package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections; // them s

// Lớp này sẽ xử lý việc đọc và ghi danh sách điểm cao vào một tệp riêng.
public class HighScoreManager {
    private static final String HIGHSCORE_FILE = "highscores.dat";
    private static final int MAX_SCORES_TO_KEEP = 5; // Chỉ lưu Top 5

    // Tải danh sách điểm cao
    @SuppressWarnings("unchecked") // Bỏ qua cảnh báo khi ép kiểu (cast)
    public static ArrayList<ScoreEntry> loadHighScores() {
        ArrayList<ScoreEntry> scores = new ArrayList<>();
        File saveFile = new File(HIGHSCORE_FILE);

        if (saveFile.exists()) {
            // lay noi dung tu file dung inputstream 
            try (FileInputStream fis = new FileInputStream(saveFile); 
                ObjectInputStream ois = new ObjectInputStream(fis)) {

                scores = (ArrayList<ScoreEntry>) ois.readObject();
                System.out.println("High scores loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading high scores:" + e.getMessage());
                // Có thể file bị hỏng, tạo mới danh sach score
                scores = new ArrayList<>();
            }
        }
        return scores;
    }

    // Lưu danh sách điểm cao
    public static void saveHighScores(ArrayList<ScoreEntry> scores) {
        try (FileOutputStream fos = new FileOutputStream(HIGHSCORE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(scores);
                System.out.println("High scores saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving high scores:" + e.getMessage());
        }
    }

    // Thêm một điểm mới, sắp xếp, cắt bớt, và lưu
    /**
     * Chỉ cần nhận điểm và level, thời gian sẽ tự lấy
     */
    public static void addScore(ScoreEntry newScore) {
        if(newScore.getScore() <= 0) {
            return; // Không lưu điểm 0
        }

        // Tai danh sach diem so cu
        ArrayList<ScoreEntry> scores = loadHighScores();
        scores.add(newScore);   
        Collections.sort(scores, ScoreEntry.ScoreComparator);
        // Giu lai top 5
        while (scores.size() > MAX_SCORES_TO_KEEP) {
            scores.remove(scores.get(scores.size() - 1)); // Xoa di thanh phan co diem thap nhat
        }
        saveHighScores(scores); // Luu lai vao file 
    }
}
