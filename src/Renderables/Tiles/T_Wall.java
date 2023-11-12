package Renderables.Tiles;

import java.awt.Point;

/*
 * Specific tile
 * Wall tile
 * */
public class T_Wall extends Tile {
    //Store sprite location in sprite sheet
    private static final Point spritePos = new Point(0, 0);

    //Constructor for normal tile
    public T_Wall(Point pos){
        super(spritePos, pos);
    }
}
