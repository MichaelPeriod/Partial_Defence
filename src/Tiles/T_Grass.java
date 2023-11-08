package Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class T_Grass extends Tile {
    private static final ArrayList<Point> spritePos = new ArrayList<>(Arrays.asList(
        new Point(1, 0),
        new Point(1, 1),
        new Point(1, 2),
        new Point(1, 3),
        new Point(1, 4)
    ));
    public T_Grass(Point pos){
        super(spritePos, pos);
    }
}
