package Model;

import Renderables.Tiles.Tile;
import DataPackets.RenderInfo;
import Renderables.Tiles.TileBuilder;

import java.awt.*;
import java.util.ArrayList;

/*
* A tilemap stores the collection of tiles */
public class Tilemap {
    //Store information about the tilemap
    private final Point mapSize;
    private Point mapOffset = new Point(0, 0);

    //Store all tiles in 2d-array
    private final Tile[][] tiles;

    //Constructors
    public Tilemap(Point _mapSize){
        mapSize = _mapSize;
        tiles = new Tile[mapSize.x][mapSize.y];
    }

    public Tilemap(Point _mapOffset, Point _mapSize){
        this(_mapSize);
        mapOffset = _mapOffset;
    }

    //Utility
    public void fill(TileBuilder t){
        for(int i = 0; i < mapSize.x; i++){
            for(int j = 0; j < mapSize.y; j++){
                Point pos = new Point(i, j);
                setTile(t, pos);
            }
        }
    }

    public void loadMap(TileBuilder[][] _newMap){
        for(int i = 0; i < mapSize.x; i++){
            for(int j = 0; j < mapSize.y; j++){
                //Ignore spots not on new map
                if(_newMap.length < i ||
                   _newMap[0].length < j) continue;

                setTile(_newMap[i][j], new Point(i, j));
            }
        }
    }

    //Handle tile updates
    public void setTileUpdate(Point pos, boolean updateStatus){
        //Make tile update
        getTile(pos).setNeedsRendered(updateStatus);
    }

    public ArrayList<RenderInfo> getTileUpdates(){
        //Search and find all tiles needing update and return it
        ArrayList<RenderInfo> toReturn = new ArrayList<>();

        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile != null && tile.getNeedsRendered()) {
                    toReturn.add(tile.getRenderInfo());
                    setTileUpdate(tile.getTilePos(), true);
                }
            }
        }

        return toReturn;
    }

    public void updateSprites(int updateNumber){
        //Pass update information for the tick
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile != null){
                    tile.updateSprites(updateNumber);
                }
            }
        }
    }

    //Tile getters and setters
    public Tile getTile(Point pos){
        if(pos.x >= mapSize.x || pos.x < 0 ||
                pos.y < 0 || pos.y >= mapSize.y)
            return null;
        return tiles[pos.x][pos.y];
    }

    public void setTile(TileBuilder t, Point pos){
        if(pos.x >= mapSize.x || pos.x < 0 ||
                pos.y < 0 || pos.y >= mapSize.y)
            return;
        tiles[pos.x][pos.y] = TileBuilder.build(t, pos);
    }
}
