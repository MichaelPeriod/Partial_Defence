package Model;

import Renderables.Tiles.TileBuilder;

import java.awt.*;
import java.util.Random;

/*
* Used to design map for creatures
* */
public class MazeGenerator {
    private final int centerReservedSpots = 4;
    private final int designatedPathways = 4;
    private TileBuilder[][] map;
    public MazeGenerator(Point _mazeSize){
        map = new TileBuilder[_mazeSize.x][_mazeSize.y];
    }

    public TileBuilder[][] generate(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(Math.random() > .5){
                    map[i][j] = TileBuilder.Wall;
                } else {
                    map[i][j] = TileBuilder.Empty;
                }
            }
        }
        return map;
    }

    public static void printTilemap(TileBuilder[][] _map){
        for(TileBuilder[] row : _map){
            for(TileBuilder tile : row){
                if(tile == TileBuilder.Wall){
                    System.out.print("XX");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
