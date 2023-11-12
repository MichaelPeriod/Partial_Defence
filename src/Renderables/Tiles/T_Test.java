package Renderables.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Specific tile
 * Test tile
 * Currently acting as animated tile test
 * */
public class T_Test extends Tile {
    //List of tiles in order
    private static final ArrayList<Point> spritePos = new ArrayList<>(Arrays.asList(
            new Point(2, 1),
            new Point(2, 0)
    ));
    //Numbers of ticks between frame updates
    private static final int frameRate = 20;
    //Constructor for animated tile
    public T_Test(Point pos) {
        super(spritePos, pos, frameRate);
    }
}
