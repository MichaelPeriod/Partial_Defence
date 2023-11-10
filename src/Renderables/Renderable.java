package Renderables;

import DataPackets.RenderInfo;

import java.awt.*;
import java.util.ArrayList;

public abstract class Renderable {
    private boolean needsRendered = true;
    protected Point tilePos;
    protected RenderInfo renderInfo;
    private ArrayList<Point> sprites;
    private int currentSprite = 0;
    private int updateFrequency = -1; //-1 is default for all non-animated sprites

    public Renderable(Point _spritePos, Point _tilePos){
        tilePos = _tilePos;
        renderInfo = new RenderInfo(_spritePos, _tilePos);
    }

    public Renderable(ArrayList<Point> _spritesPos, Point _tilePos, int _updateFrequency){
        this(_spritesPos.get(0), _tilePos);
        sprites = _spritesPos;
        updateFrequency = _updateFrequency;
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

    public Point getTilePos() {
        return tilePos;
    }

    public void setSprite(Point _spritePos){
        getRenderInfo().setSpritePosition(_spritePos);
        setNeedsRendered(true);
    }

    public void updateSprites(int updateNumber){
        if(updateFrequency <= 0) return;
        if(updateNumber % updateFrequency == 0){
            currentSprite++;
            setSprite(sprites.get(currentSprite % sprites.size()));
        }
    }
}