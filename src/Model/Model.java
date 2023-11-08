package Model;

import Tiles.Tile;
import DataPackets.RenderInfo;
import java.awt.*;
import java.util.ArrayList;

public class Model {
    public enum Layer {
        Ground(0),
        Main(1);

        public final int tilemapNum;

        private Layer(int _tilemapNum){
            tilemapNum = _tilemapNum;
        }

        public static Layer getLayer(int l){
            for(Layer possibleLayer : Layer.values()){
                if(possibleLayer.tilemapNum == l)
                    return possibleLayer;
            }
            return Main;
        }

        public Layer getPreviousLayer(){
            if(tilemapNum == 0)
                return this;
            return getLayer(tilemapNum - 1);
        }
    }
    private final Point tileMapSize;

    private final ArrayList<Tilemap> tileMaps = new ArrayList<>(); //Drawn 0, 1, 2, ...

    public Model(Point _tileMapSize){
        tileMapSize = _tileMapSize;
        tileMaps.add(new Tilemap(tileMapSize));
        tileMaps.add(new Tilemap(tileMapSize));
    }

    public void Update(){

    }

    public Tilemap getTilemap(Layer layer){
        return tileMaps.get(layer.tilemapNum);
    }

    public Tile getTile(Layer layer, Point pos){
       return getTilemap(layer).GetTile(pos);
    }

    public void setTile(Layer layer, Tile t){
        getTilemap(layer).SetTile(t);
    }

    public void clearTile(Layer layer, Point pos){
        getTilemap(layer).ClearTile(pos);
        getTile(layer.getPreviousLayer(), pos).setNeedsRendered(true);
    }

    public ArrayList<RenderInfo> getTileUpdates(){
        ArrayList<RenderInfo> toUpdate = new ArrayList<>();
        for(int i = 0; i < tileMaps.size(); i++){
            for(RenderInfo r : getTilemap(Layer.getLayer(i)).getTileUpdates()){
                toUpdate.add(r);

                if(i > 0){
                    Tile under = getTile(Layer.getLayer(i - 1), r.getTilePosition());
                    if(under == null)
                        under.setNeedsRendered(true);
                }

                for(int j = i + 1; j < tileMaps.size(); j++){ //Render all tiles above
                    Tile t = getTile(Layer.getLayer(j), r.getTilePosition());
                    if(t != null)
                        t.setNeedsRendered(true);
                }
            }
        }
        return toUpdate;
    }
}
