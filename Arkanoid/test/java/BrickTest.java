import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.BrickType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BrickTest {
    private static final double DELTA = 0.0001;
    private Ball ball;
    @BeforeEach
    void setUp() {
        ball =  new Ball(10, 0.8, 0.8, 10);
    }

    @Test
    void normalBrickConstructorTest() {
        NormalBrick normalBrick = new NormalBrick(50, 50, 20, 10);

        assertEquals(50, normalBrick.getX(), DELTA);
        assertEquals(50, normalBrick.getY(), DELTA);
        assertEquals(20, normalBrick.getWidth(), DELTA);
        assertEquals(10, normalBrick.getHeight(), DELTA);
        assertEquals(BrickType.NORMAL, normalBrick.getType());
    }

    @Test
    void normalBrickTakeHit() {
        NormalBrick normalBrick = new NormalBrick(50, 50, 20, 10);

        normalBrick.takeHit();
        assertTrue(normalBrick.isDestroyed());
    }

    @Test
    void strongBrickConstructorTest() {
        StrongBrick strongBrick = new StrongBrick(50, 50, 20, 10);

        assertEquals(50, strongBrick.getX(), DELTA);
        assertEquals(50, strongBrick.getY(), DELTA);
        assertEquals(20, strongBrick.getWidth(), DELTA);
        assertEquals(10, strongBrick.getHeight(), DELTA);
        assertEquals(BrickType.STRONG, strongBrick.getType());
    }

    @Test
    void strongBrickTakeHit() {
        StrongBrick strongBrick = new StrongBrick(50, 50, 20, 10);

        strongBrick.takeHit();

        assertFalse(strongBrick.isDestroyed());

        strongBrick.takeHit();
        strongBrick.takeHit();

        assertTrue(strongBrick.isDestroyed());
    }

    @Test
    void unbreakableBrickConstructorTest() {
        UnbreakableBrick unbreakableBrick = new UnbreakableBrick(50, 50, 20, 10);

        assertEquals(50, unbreakableBrick.getX(), DELTA);
        assertEquals(50, unbreakableBrick.getY(), DELTA);
        assertEquals(20, unbreakableBrick.getWidth(), DELTA);
        assertEquals(10, unbreakableBrick.getHeight(), DELTA);
        assertEquals(BrickType.UNBREAKABLE, unbreakableBrick.getType());
    }

    @Test
    void unbreakableBrickTakeHit() {
        UnbreakableBrick unbreakableBrick = new UnbreakableBrick(50, 50, 20, 10);

        unbreakableBrick.takeHit();
        unbreakableBrick.takeHit();
        unbreakableBrick.takeHit();
        unbreakableBrick.takeHit();
        unbreakableBrick.takeHit();
        unbreakableBrick.takeHit();

        assertFalse(unbreakableBrick.isDestroyed());
    }

    @Test
    void explosiveBrickConstructorTest() {
        ExplosiveBrick explosiveBrick = new ExplosiveBrick(50, 50, 20, 10);

        assertEquals(50, explosiveBrick.getX(), DELTA);
        assertEquals(50, explosiveBrick.getY(), DELTA);
        assertEquals(20, explosiveBrick.getWidth(), DELTA);
        assertEquals(10, explosiveBrick.getHeight(), DELTA);
        assertEquals(BrickType.EXPLOSIVE, explosiveBrick.getType());
    }

    @Test
    void explosiveBrickTakeHit() {
        ExplosiveBrick explosiveBrick = new ExplosiveBrick(50, 50, 20, 10);
        NormalBrick normalBrick1 = new NormalBrick(50, 40, 20, 10);
        NormalBrick normalBrick2 = new NormalBrick(50, 60, 20, 10);
        StrongBrick strongBrick = new StrongBrick(30, 50, 20, 10);
        UnbreakableBrick unbreakableBrick = new UnbreakableBrick(70, 50, 20,10);

        List<Brick> brickList = new ArrayList<>();

        brickList.add(explosiveBrick);
        brickList.add(normalBrick1);
        brickList.add(normalBrick2);
        brickList.add(strongBrick);
        brickList.add(unbreakableBrick);

        explosiveBrick.startExplosion(brickList);

        assertTrue(normalBrick1.isDestroyed());
        assertTrue(normalBrick2.isDestroyed());
        assertFalse(strongBrick.isDestroyed());
        assertFalse(unbreakableBrick.isDestroyed());
    }
}
