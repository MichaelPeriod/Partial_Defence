package Renderables.Tiles;

import DataPackets.RenderInfo;
import Renderables.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Tile extends Renderable {
    public Tile(Point _spritePos, Point _tilePos){
        super(_spritePos, _tilePos);
    }

    public Tile(ArrayList<Point> _spritePoses, Point _tilePos){
        this(chooseRandomSprite(_spritePoses), _tilePos);
    }

    public Tile(ArrayList<Point> _spritesPos, Point _tilePos, int _updateFrequency){
        super(_spritesPos, _tilePos, _updateFrequency);
    }

    private static Point chooseRandomSprite(ArrayList<Point> tiles){
        return tiles.get(new Random().nextInt(0, tiles.size()));
    }
}
