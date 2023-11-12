package DataPackets;

import java.awt.*;

/*
* Used to send information of an object's position and visual
* */
public class RenderInfo {
    //The location of the sprite within the sprite sheet
    //(May need to expand to handle multiple sprite-sheets in the future)
        //Use enum of sheets written as SheetAlias(String location)
    private Point spritePosition;

    //In world specific position
    private Point tilePosition;
    private Point tileOffset;

    //Defines the information to transfer
    public RenderInfo(Point _sp, Point _tp){
        setSpritePosition(_sp);
        setTilePosition(_tp);
    }

    public RenderInfo(Point _sp, Point _tp, Point _to){
        this(_sp, _tp);
        setTileOffset(_to);
    }

    //Getters and setters
    public Point getSpritePosition() {
        return spritePosition;
    }

    public Point getTilePosition() {
        return tilePosition;
    }

    public Point getTileOffset() {
        return tileOffset;
    }

    public void setSpritePosition(Point spritePosition) {
        this.spritePosition = spritePosition;
    }

    public void setTilePosition(Point tilePosition) {
        this.tilePosition = tilePosition;
    }

    public void setTileOffset(Point tileOffset) {
        this.tileOffset = tileOffset;
    }
}
