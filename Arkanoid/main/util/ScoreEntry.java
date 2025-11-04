package util;

import java.io.Serializable;
import java.util.Comparator;

// Lớp này sẽ lưu thông tin của một lần chơi.
public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 2L; // Khac voi GameStateData
    private int score; // diem so
    private long playTimeMillis;
    private int highestLevel;

    /**
     * Constructor 3 tham số.
     * 
     * @param score điểm số.
     * @param playTimeMillis thời gian chơi.
     * @param highestLevel level cao nhất.
     */
    public ScoreEntry(int score, long playTimeMillis, int highestLevel) {
        this.score = score;
        this.playTimeMillis = playTimeMillis;
        this.highestLevel = highestLevel;
    }

    /**
     * Getter cho điểm số.
     * 
     * @return điểm số.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter cho thời gian chơi.
     * 
     * @return thời gian chơi.
     */
    public long getPlayTimeMillis() {
        return playTimeMillis;
    }

    /**
     * Getter cho level cao nhất.
     * 
     * @return level cao nhất.
     */
    public int getHighestLevel() {
        return highestLevel;
    }

    /**
     * Dùng để sắp xếp danh sách: điểm cao hơn là tốt hơn.
     */
    public static Comparator<ScoreEntry> ScoreComparator = new Comparator<ScoreEntry>() {
        public  int compare(ScoreEntry s1, ScoreEntry s2) {
            if (Integer.compare(s2.getScore(), s1.getScore()) != 0) {
                return Integer.compare(s2.getScore(), s1.getScore());
            } else {
                return Long.compare(s1.getPlayTimeMillis(), s2.getPlayTimeMillis());
            }
        }
    };
}