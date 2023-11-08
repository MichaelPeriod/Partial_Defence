package Model;

import Tiles.Tile;
import DataPackets.RenderInfo;
import java.awt.*;
import java.util.ArrayList;

public class Model {
    private final Point tileMapSize;

    private final ArrayList<Tilemap> tileMaps = new ArrayList<>(); //Drawn 0, 1, 2, ...

    public Model(Point _tileMapSize){
        tileMapSize = _tileMapSize;
        tileMaps.add(new Tilemap(tileMapSize));
        tileMaps.add(new Tilemap(tileMapSize));
    }

    public void Update(){

    }

    public Tilemap getTilemap(int _tileMapIndex){
        return tileMaps.get(_tileMapIndex);
    }

    public ArrayList<RenderInfo> getTileUpdates(){
        ArrayList<RenderInfo> toUpdate = new ArrayList<>();
        for(int i = 0; i < tileMaps.size(); i++){
            for(RenderInfo r : getTilemap(i).getTileUpdates()){
                toUpdate.add(r);

                for(int j = i + 1; j < tileMaps.size(); j++){ //Render all tiles above
                    Tile t = getTilemap(j).GetTile(r.getTilePosition());
                    if(t != null)
                        t.setNeedsRendered(true);
                }
            }
        }
        return toUpdate;
    }
}
