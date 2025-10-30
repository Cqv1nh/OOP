package util;

import java.io.Serializable;
import java.util.Comparator;

// Lớp này sẽ lưu thông tin của một lần chơi.
public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 2L; // Khac voi GameStateData
    private int score; // diem so
    private long playTimeMillis;
    private int highestLevel;
    public ScoreEntry(int score, long playTimeMillis, int  highestLevel) {
        this.score = score;
        this.playTimeMillis = playTimeMillis;
        this.highestLevel = highestLevel;
    }

    // getter
    public int getScore() {
        return score;
    }

    public long getPlayTimeMillis() {
        return playTimeMillis;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    // Dùng để sắp xếp danh sách: điểm cao hơn là tốt hơn
    public static Comparator<ScoreEntry> ScoreComparator = new Comparator<ScoreEntry>() {
        public  int compare(ScoreEntry s1, ScoreEntry s2) {
            // toi uu 
            if (Integer.compare(s2.getScore(), s1.getScore()) != 0) {
                return Integer.compare(s2.getScore(), s1.getScore());
            } else {
                return Long.compare(s1.getPlayTimeMillis(), s2.getPlayTimeMillis());
            }
        }
    };
}