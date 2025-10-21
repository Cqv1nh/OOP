package util;

import java.util.HashMap;
import java.util.Map;

public class LevelData {
    // Sử dụng Map để lưu trữ các màn chơi, dễ dàng truy cập bằng số level
    private static final Map<Integer, int[][]> levels = new HashMap<>();

    // Sử dụng khối static để khởi tạo dữ liệu ngay khi lớp được tải
    static {
        // 0: Rỗng, 1: Normal, 2: Strong, 3: Explosive, 4: Unbreakable
        // LEVEL 1: Giới thiệu cơ bản
        levels.put(1, new int[][]{
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 2, 2, 2, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            // {1, 0, 0}
        });

        // LEVEL 2: Thêm gạch Unbreakable và Strong
        levels.put(2, new int[][]{
            {1, 2, 1, 2, 1, 4, 1, 2, 1, 2, 1},
            {1, 2, 1, 2, 1, 4, 1, 2, 1, 2, 1},
            {0, 1, 2, 1, 0, 4, 0, 1, 2, 1, 0},
            {0, 0, 1, 1, 1, 4, 1, 1, 1, 0, 0}
            // {1, 0, 0}
        });

        // LEVEL 3: Giới thiệu gạch nổ (Explosive)
        levels.put(3, new int[][]{
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {1, 1, 2, 1, 1, 3, 1, 1, 2, 1, 1},
            {1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1},
            {0, 1, 1, 2, 1, 1, 1, 2, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0}
            // {1, 0, 0}
        });

        // LEVEL 4: Bức tường thép
        levels.put(4, new int[][]{
            {2, 2, 2, 4, 4, 4, 4, 4, 2, 2, 2},
            {2, 3, 2, 3, 1, 1, 1, 3, 2, 3, 2},
            {2, 2, 2, 3, 1, 2, 1, 3, 2, 2, 2},
            {0, 0, 0, 3, 1, 1, 1, 3, 0, 0, 0},
            {0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0}
            // {1, 0, 0}
        });

        // LEVEL 5: Màn cuối thử thách
        levels.put(5, new int[][]{
            {4, 1, 2, 3, 4, 3, 4, 3, 2, 1, 4},
            {4, 1, 2, 1, 4, 1, 4, 1, 2, 1, 4},
            {4, 1, 2, 1, 4, 2, 4, 1, 2, 1, 4},
            {4, 1, 2, 3, 4, 3, 4, 3, 2, 1, 4},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}
            // {1, 0 ,0}
        });
    }
    // Lấy layout (mảng 2D) cho một level cụ thể.
    public static int[][] getLayoutForLevel(int levelNumber) {
        return levels.get(levelNumber);
    }
    // Lấy tổng số level hiện có.
    public static int getTotalLevels() {
        return levels.size();
    }
}
