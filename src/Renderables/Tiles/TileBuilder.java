package Renderables.Tiles;

import java.awt.*;

/*
* Builder class for tiles
* Pass into static function to produce any tile
* Resolves dependencies of requiring tile type before position known
* */
public enum TileBuilder {
    //Tile types
    Empty,
    Grass,
    Wall,
    Test;

    //Builder function once position is known
    public static Tile build(TileBuilder type, Point pos){
        switch (type){
            case Grass -> {
                return new T_Grass(pos);
            }
            case Wall -> {
                return new T_Wall(pos);
            }
            case Empty -> {
                return null;
            }
            default -> {
                return new T_Test(pos);
            }
        }
    }
}
