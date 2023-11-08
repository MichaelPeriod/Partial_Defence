package DataPackets;

import java.awt.*;

public class RenderInfo {
    private Point spritePosition;
    private Point tilePosition;

    public RenderInfo(Point _sp, Point _tp){
        spritePosition = _sp;
        tilePosition = _tp;
    }

    public Point getSpritePosition() {
        return spritePosition;
    }

    public Point getTilePosition() {
        return tilePosition;
    }
}
