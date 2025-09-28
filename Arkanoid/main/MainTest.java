import entities.NormalBrick;
import entities.Paddle;

public class MainTest {
    public static void main(String[] args) {
        NormalBrick b = new NormalBrick(1, 1, 2, 2);
        Paddle p = new Paddle(4, 4, 5, 1,0, 0, null);

        // In Brick
        b.render();

        // In Paddle
        p.render();

        // Kết thúc chương trình
        System.out.println();
    }
}
