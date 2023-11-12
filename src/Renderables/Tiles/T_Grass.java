package Renderables.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
* Specific tile
* Grass tile
* */
public class T_Grass extends Tile {
    //List of possible sprites
    private static final ArrayList<Point> spritePos = new ArrayList<>(Arrays.asList(
        new Point(1, 0),
        new Point(1, 1),
        new Point(1, 2),
        new Point(1, 2),
        new Point(1, 2),
        new Point(1, 2),
        new Point(1, 2)
    ));

    //Constructor for randomized tile
    public T_Grass(Point pos){
        super(spritePos, pos);
    }
}
