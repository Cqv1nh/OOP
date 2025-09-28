import entities.NormalBrick;
import entities.Paddle;

public class MainTest {
    public static void main(String[] args) {
        NormalBrick b = new NormalBrick(1, 2, 2, 2);
        Paddle p = new Paddle(4, 4, 5, 1, 0, 0, null);
        System.out.print("\033[2J");
        // In Brick
        b.render();

        // In Paddle
        p.render();
         System.out.print("\033[0;0H");
        // Kết thúc chương trình
        System.out.println();
    }
}
