package Model;

import Renderables.Tiles.Tile;
import DataPackets.RenderInfo;
import java.awt.*;
import java.util.ArrayList;

public class Model {
    public enum Layer {
        Ground(0),
        Structures(1),
        Entities(2);

        public final int tilemapNum;

        private Layer(int _tilemapNum) {
            tilemapNum = _tilemapNum;
        }

        public static Layer getLayer(int l) {
            for (Layer possibleLayer : Layer.values()) {
                if (possibleLayer.tilemapNum == l)
                    return possibleLayer;
            }
            return Structures;
        }

        public Layer getPreviousLayer() {
            if (tilemapNum == 0)
                return this;
            return getLayer(tilemapNum - 1);
        }
    }

    private final Point tileMapSize;

    private final ArrayList<Tilemap> tileMaps = new ArrayList<>(); //Drawn 0, 1, 2, ...

    public Model(Point _tileMapSize) {
        tileMapSize = _tileMapSize;
        for(Layer l : Layer.values()){
            tileMaps.add(new Tilemap(tileMapSize));
        }
    }

    public void Update() {

    }

    public Tilemap getTilemap(Layer layer) {
        return tileMaps.get(layer.tilemapNum);
    }

    public Tile getTile(Layer layer, Point pos) {
        return getTilemap(layer).GetTile(pos);
    }

    public void setTile(Layer layer, Tile t) {
        getTilemap(layer).SetTile(t);
    }

    public void clearTile(Layer layer, Point pos) {
        getTilemap(layer).ClearTile(pos);
        getTile(layer.getPreviousLayer(), pos).setNeedsRendered(true);
    }

    public void setTileUpdate(Layer layer, Point pos, boolean updateStatus) {
        getTilemap(layer).setTileUpdate(pos, updateStatus);
    }

    public void setTileUpdate(Layer layer, Point pos) {
        setTileUpdate(layer, pos, true);
    }

    public ArrayList<RenderInfo> getTileUpdates() {
        ArrayList<RenderInfo> toUpdate = new ArrayList<>();
        setRenderStacks();

        for (int i = 0; i < tileMaps.size(); i++) {
            for (RenderInfo r : getTilemap(Layer.getLayer(i)).getTileUpdates()) {
                toUpdate.add(r);
            }
        }
        return toUpdate;
    }

    private void setRenderStacks(){
        for(Layer l : Layer.values()) {
            for (RenderInfo r : getTilemap(l).getTileUpdates()) {
                for (int j = 0; j < tileMaps.size(); j++) { //Render all tiles in stack
                    Tile t = getTile(Layer.getLayer(j), r.getTilePosition());
                    if (t != null)
                        t.setNeedsRendered(true);
                }
            }
        }
    }

    public void updateSprites(int updateNumber) {
        for (Layer l : Layer.values()) {
            getTilemap(l).updateSprites(updateNumber);
        }
    }
}