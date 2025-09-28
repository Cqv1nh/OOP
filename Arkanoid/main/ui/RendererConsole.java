package ui;

import entities.Paddle;
import util.Constants;

import java.util.Arrays;


public class RendererConsole {
    private final int TerminalWidth = Constants.SCREEN_WIDTH / 20;
    private final int TerminalHeight = Constants.SCREEN_HEIGHT / 20;
    char[][] grid;

    public RendererConsole() {
        this.grid = new char[TerminalHeight][TerminalWidth];
    }

    public void CharacterClear() {
        for (int i = 0; i < TerminalHeight; i++) {
            Arrays.fill(grid[i], 0, TerminalWidth, ' ');
        }
    }

    public void DrawBorder() {
        Arrays.fill(grid[0], 0, TerminalWidth, '#');
        Arrays.fill(grid[TerminalHeight - 1], 0, TerminalWidth, '#');

        for (int i = 0; i < TerminalHeight; i++) {
            grid[i][0] = '#';
            grid[i][TerminalWidth - 1] = '#';
        }
    }

    public void DrawPaddle(Paddle paddle) {
        int TerminalX = (int) paddle.getX() / 20;
        int TerminalY = this.TerminalHeight - 5;
        int TerminalWidth = (int) paddle.getWidth() / 20;

        for (int i = 0; i < TerminalWidth; i++) {
            int currentX = TerminalX + i;

            if (currentX > 0 && currentX < TerminalWidth) {
                grid[TerminalY][currentX] = '═';
            }
        }
    }

    public void DrawBall() {

    }

    public void DrawBrick() {

    }

    public void display() {

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < TerminalHeight; i++){
            s.append(grid[i]);
            s.append('\n');
        }

        System.out.print(s);
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {

                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

}