import entities.Paddle;
import renderer.RendererConsole;
import utils.Constants;



public class Game {
    public static void main(String[] args) {
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X,
                Constants.INIT_PADDLE_Y, Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT,0);

        RendererConsole render = new RendererConsole();

        while (true) {
            render.clearConsole();

            render.DrawBorder();
            render.DrawPaddle(paddle);

            render.display();

            try {
                // Pause the program for 2000 milliseconds (2 seconds)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // This block is executed if another thread interrupts this one while it's sleeping.
                // For simple games, you can often just print the stack trace.
                e.printStackTrace();
            }

            render.CharacterClear();
        }
    }
}