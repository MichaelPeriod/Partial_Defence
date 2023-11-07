import java.awt.*;

public abstract class Tile {
    private boolean needsRendered = true;
    private Point tilePos;
    protected RenderInfo renderInfo;

    public Tile(Point _spritePos, Point _tilePos){
        tilePos = _tilePos;
        renderInfo = new RenderInfo(_spritePos, _tilePos);
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
}
