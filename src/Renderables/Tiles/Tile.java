package Renderables.Tiles;

import DataPackets.RenderInfo;
import Renderables.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Tile class
 * Supports standard, random, and animated tile
 * */
public abstract class Tile extends Renderable {
    //Standard constructor
    public Tile(Point _spritePos, Point _tilePos){
        super(_spritePos, _tilePos);
    }

    //Random tile constructor
    public Tile(ArrayList<Point> _spritePoses, Point _tilePos){
        this(chooseRandomSprite(_spritePoses), _tilePos);
    }

    //Animated tile constructor
    public Tile(ArrayList<Point> _spritesPos, Point _tilePos, int _updateFrequency){
        super(_spritesPos, _tilePos, _updateFrequency);
    }

    private static Point chooseRandomSprite(ArrayList<Point> tiles){
        //Chooses a random sprite from a list
        return tiles.get(new Random().nextInt(0, tiles.size()));
    }
}
