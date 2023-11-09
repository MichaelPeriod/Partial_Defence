package Renderables;

import DataPackets.RenderInfo;

import java.awt.*;

public abstract class Renderable {
    private boolean needsRendered = true;
    protected Point tilePos;
    protected RenderInfo renderInfo;

    public Renderable(Point _spritePos, Point _tilePos){
        tilePos = _tilePos;
        renderInfo = new RenderInfo(_spritePos, _tilePos);
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
}