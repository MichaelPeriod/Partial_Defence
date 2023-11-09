package Renderables.Tiles;

import java.awt.*;
import java.util.ArrayList;

public class AnimatedTile extends Tile {
    protected int updateFrequency = 1;
    private ArrayList<Point> sprites;
    private int currentSprite = 0;
    public AnimatedTile(Point _spritePos, Point _tilePos) {
        super(_spritePos, _tilePos);
    }
    public AnimatedTile(ArrayList<Point> _spritePoses, Point _tilePos) {
        this(_spritePoses.get(0), _tilePos);
    } //Both prev constructors make static

    public AnimatedTile(ArrayList<Point> _spritePoses, Point _tilePos, int _updateFrequency){
        this(_spritePoses, _tilePos); //Set to first tile
        updateFrequency = _updateFrequency;
        if(updateFrequency <= 0)
            updateFrequency = 1;
        sprites = _spritePoses;
        //Issue with finding other sprites
    }

    public void updateSprites(int updateNumber){
        if(updateNumber % updateFrequency == 0){
            System.out.println(sprites.get(currentSprite % sprites.size()).toString());
            setSprite(sprites.get(currentSprite % sprites.size()));
        }
    }
}
