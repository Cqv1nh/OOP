package util;

import entities.NormalBrick;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileLevelLoader {

    public FileLevelLoader() {

    }

    /**
     * Phuong thuc de lay vi tri cac khoi gach tu file co san.
     * @param filePath duong dan file.
     * @return danh sach cac khoi gach.
     */
    public static List<NormalBrick> loadLevelFromFile(String filePath) {
        List<NormalBrick> bricks = new ArrayList<>();

        try (InputStream is = new FileInputStream(filePath);) {
            if (is == null) {
                throw new IOException("Cannot find resource: " + filePath);
            }

            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char brickChar = line.charAt(col);
                    int health = 0;

                    switch (brickChar) {
                        case 'B':
                            health = 1; // Normal brick
                            break;
                        case 'H':
                            health = 2; // Hard brick
                            break;
                        case 'E':
                            health = -1; // Steel brick
                            break;
                    }

                    if (health != 0) {
                        float x = col;// * Constants.BRICK_WIDTH;
                        float y = row;//* Constants.BRICK_HEIGHT + Constants.LEVEL_TOP_OFFSET;
                        bricks.add(new NormalBrick(y, x, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error loading level file: " + filePath);
            e.printStackTrace();
        }

        return bricks;
    }

    public static void main(String[] args) {
        List<NormalBrick> bricks = loadLevelFromFile("src/resources/level1.txt");

        for (NormalBrick brick : bricks) {
            System.out.println((int) brick.getX() + " " + (int) brick.getY());
        }
    }
}
