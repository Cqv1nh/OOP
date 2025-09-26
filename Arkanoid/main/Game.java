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

                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            render.CharacterClear();
        }
    }
}