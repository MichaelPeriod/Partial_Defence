package Renderables;

import DataPackets.RenderInfo;

import java.awt.*;
import java.util.ArrayList;

/*
* Stores information on an object that can be rendered
* */
public abstract class Renderable {
    //Key variables
    private boolean needsRendered = true; //When true, will be included in model's call for needs rerendered
    protected RenderInfo renderInfo; //Stores the information needed to pass rendering information

    protected Point tilePos; //Stores the position
    private ArrayList<Point> sprites; //List of sprites
    private int currentSprite = 0; //Current sprite if within an animation
    private int updateFrequency = -1; //-1 is default for all non-animated sprites

    //Standard render constructor
    public Renderable(Point _spritePos, Point _tilePos){
        tilePos = _tilePos;
        renderInfo = new RenderInfo(_spritePos, _tilePos);
    }

    //Animated render constructor
    public Renderable(ArrayList<Point> _spritesPos, Point _tilePos, int _updateFrequency){
        this(_spritesPos.get(0), _tilePos);
        sprites = _spritesPos;
        updateFrequency = _updateFrequency;
    }

    public void updateSprites(int updateNumber){
        //Updates the sprite animation when the update number is on the frequency
        if(updateFrequency <= 0) return;
        if(updateNumber % updateFrequency == 0){
            currentSprite++;
            setSprite(sprites.get(currentSprite % sprites.size()));
        }
    }

    //Getters and setters
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
}