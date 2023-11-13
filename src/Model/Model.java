package Model;

import Renderables.Tiles.Tile;
import DataPackets.RenderInfo;
import Renderables.Tiles.TileBuilder;

import java.awt.*;
import java.util.ArrayList;

/*
* Model part of the MVC
* Used for keeping information of game state and positions
* */
public class Model {
    //Layer to keep track of the various levels of tiles
    public enum Layer {
        //Layer names and number
        Ground(0),
        Structures(1),
        Entities(2);

        //Stores a number to keep track of layer depth
        public final int tilemapNum;

        private Layer(int _tilemapNum) {
            tilemapNum = _tilemapNum;
        }

        public static Layer getLayer(int l) {
            //Used to get the layer by tilemap number
            for (Layer possibleLayer : Layer.values()) {
                if (possibleLayer.tilemapNum == l)
                    return possibleLayer;
            }
            return Structures; //If no layer found assume walls layer intended
        }

        public Layer getPreviousLayer() {
            //Get the layer before current unless at lowest layer
            if (tilemapNum == 0)
                return this;
            return getLayer(tilemapNum - 1);
        }
    }

    //Stores all tilemaps
    private final ArrayList<Tilemap> tileMaps = new ArrayList<>(); //Drawn 0, 1, 2, ...

    private final MazeGenerator mazeGenerator;

    public Model(Point _tileMapSize) {
        //Make a tilemap for each layer
        for(Layer l : Layer.values()){
            tileMaps.add(new Tilemap(_tileMapSize));
        }

        fill(Layer.Ground, TileBuilder.Grass);

        mazeGenerator = new MazeGenerator(_tileMapSize);
        generateNewMaze(Layer.Structures);
    }

    public void update() {
        //Update the game state once every tick
    }

    //Utilities
    public void fill(Layer l, TileBuilder t){
        getTilemap(l).fill(t);
    }

    public void loadMap(Layer l, TileBuilder[][] t){
        getTilemap(l).loadMap(t);
    }

    public void generateNewMaze(Layer l){
        loadMap(l, mazeGenerator.generate());
    }

    //Update any sprites based on tick number
    public void updateSprites(int updateNumber) {
        for (Layer l : Layer.values()) {
            getTilemap(l).updateSprites(updateNumber);
        }
    }

    //Make a sprite draw itself
    public void setTileUpdate(Layer layer, Point pos, boolean updateStatus) {
        getTilemap(layer).setTileUpdate(pos, updateStatus);
    }

    public void setTileUpdate(Layer layer, Point pos) {
        setTileUpdate(layer, pos, true);
    }

    //Retrieve all tile updates
    //Called by controller
    public ArrayList<RenderInfo> getTileUpdates() {
        ArrayList<RenderInfo> toUpdate = new ArrayList<>();

        //Make sure any tile updating will draw everything on the tile
        setRenderStacks();

        //Go through each result and return those in need of an update
        for (int i = 0; i < tileMaps.size(); i++) {
            for (RenderInfo r : getTilemap(Layer.getLayer(i)).getTileUpdates()) {
                toUpdate.add(r);
            }
        }

        return toUpdate; //Returns nothing unless a sprite has changed
    }

    private void setRenderStacks(){
        //Find each tile that needs an update and make all tiles on the same
        //position update as well to draw over former pixels
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

    //Tilemap getters and setters
    private Tilemap getTilemap(Layer layer) {
        return tileMaps.get(layer.tilemapNum);
    }

    public Tile getTile(Layer layer, Point pos) {
        return getTilemap(layer).getTile(pos);
    }

    public void setTile(Layer layer, TileBuilder t, Point pos) {
        getTilemap(layer).setTile(t, pos);
    }
}