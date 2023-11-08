package Tiles;

import DataPackets.RenderInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Tile {
    private boolean needsRendered = true;
    private Point tilePos;
    protected RenderInfo renderInfo;

    public Tile(Point _spritePos, Point _tilePos){
        tilePos = _tilePos;
        renderInfo = new RenderInfo(_spritePos, _tilePos);
    }

    public Tile(ArrayList<Point> _spritePoses, Point _tilePos){
        this(chooseRandomTile(_spritePoses), _tilePos);
    }

    public Point getTilePos() {
        return tilePos;
    }

    public void setNeedsRendered(boolean _needs){
        needsRendered = _needs;
    }

    public boolean getNeedsRendered(){
        return needsRendered;
    }

    public RenderInfo getRenderInfo() {
        return renderInfo;
    }

    private static Point chooseRandomTile(ArrayList<Point> tiles){
        return tiles.get(new Random().nextInt(0, tiles.size()));
    }
}
