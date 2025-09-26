package renderer;

import entities.Paddle;
import utils.Constants;

import java.util.Arrays;


public class RendererConsole {
    private final int TerminalWidth = Constants.SCREEN_WIDTH / 20;
    private final int TerminalHeight = Constants.SCREEN_HEIGHT / 10;
    char[][] grid;

    public RendererConsole() {
        this.grid = new char[TerminalHeight][TerminalWidth];
    }

    private void CharacterClear() {
        for (int i = 0; i < TerminalHeight; i++) {
            Arrays.fill(grid[0], 0, TerminalWidth, '#');
        }
    }

    private void DrawBorder() {
        Arrays.fill(grid[0], 0, TerminalWidth, '#');
        Arrays.fill(grid[TerminalHeight - 1], 0, TerminalWidth, '#');

        for (int i = 0; i < TerminalHeight - 1; i++) {
            grid[i][0] = '#';
            grid[i][TerminalWidth - 1] = '#';
        }
    }

    private void DrawPaddle(Paddle paddle) {
        int TerminalX = (int) paddle.getX() / 10;
        int TerminalY = TerminalHeight - 5;

        int TerminalWidth = (int) paddle.getWidth() / 10;

        int endX = Math.min(TerminalX + TerminalWidth, TerminalWidth);
        int startX = Math.max(0, TerminalX);

        Arrays.fill(grid[TerminalY], startX, endX, '═');
    }

    private void DrawBall() {

    }

    private void DrawBrick() {

    }

    private void display() {
        CharacterClear();
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < TerminalHeight; i++){
            s.append(grid[i]);
            s.append('\n');
        }

        System.out.print(s);
    }

    private void clear(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

}