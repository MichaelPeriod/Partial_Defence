package Renderables.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class T_Test extends Tile {
    private static final ArrayList<Point> spritePos = new ArrayList<>(Arrays.asList(
            new Point(2, 1),
            new Point(2, 0)
    ));
    private static final int frameRate = 20;
    public T_Test(Point pos) {
        super(spritePos, pos, frameRate);
    }
}
