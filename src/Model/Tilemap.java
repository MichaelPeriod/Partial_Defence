package Model;

import Renderables.Tiles.T_Grass;
import Renderables.Tiles.Tile;
import DataPackets.RenderInfo;

import java.awt.*;
import java.util.ArrayList;

public class Tilemap {
    private final Point mapSize;
    private Point mapOffset = new Point(0, 0);
    private final Tile[][] tiles;

    public Tilemap(Point _mapSize){
        mapSize = _mapSize;
        tiles = new Tile[mapSize.x][mapSize.y];
    }

    public Tilemap(Point _mapOffset, Point _mapSize){
        this(_mapSize);
        mapOffset = _mapOffset;
    }

    public Tile GetTile(Point pos){
        if(pos.x >= mapSize.x || pos.x < 0 ||
                pos.y < 0 || pos.y >= mapSize.y)
            return null;
        return tiles[pos.x][pos.y];
    }

    public void SetTile(Tile t){
        if(t.getTilePos().x >= mapSize.x || t.getTilePos().x < 0 ||
                t.getTilePos().y < 0 || t.getTilePos().y >= mapSize.y)
            return;
        tiles[t.getTilePos().x][t.getTilePos().y] = t;
    }

    public void ClearTile(Point pos){
        if(pos.x >= mapSize.x || pos.x < 0 ||
                pos.y < 0 || pos.y >= mapSize.y)
            return;
        tiles[pos.x][pos.y] = null;
    }

    public Tile[][] GetAllTiles(){
        return tiles;
    }

    public void GrassFill(){
        for(int i = 0; i < mapSize.x; i++){
            for(int j = 0; j < mapSize.y; j++){
                Point pos = new Point(i, j);
                SetTile(new T_Grass(pos));
            }
        }
    }

    public void setTileUpdate(Point pos, boolean updateStatus){
        GetTile(pos).setNeedsRendered(updateStatus);
    }

    public ArrayList<RenderInfo> getTileUpdates(){
        ArrayList<RenderInfo> toReturn = new ArrayList<>();
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile != null && tile.getNeedsRendered()) {
                    toReturn.add(tile.getRenderInfo());
                    tile.setNeedsRendered(false);
                }
            }
        }
        return toReturn;
    }

    public void updateSprites(int updateNumber){
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile != null){
                    tile.updateSprites(updateNumber);
                }
            }
        }
    }
}
