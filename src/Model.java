import java.awt.*;
import java.util.ArrayList;

public class Model {
    private final Point tileMapSize;
    private Tile[][] tiles;

    public Model(Point _tileMapSize){
        tileMapSize = _tileMapSize;
        tiles = new Tile[tileMapSize.x][tileMapSize.y];
    }

    public Tile GetTile(int x, int y){
        if(x >= tileMapSize.x || x < 0 || y < 0 || y >= tileMapSize.y)
            return null;
        return tiles[x][y];
    }

    public void SetTile(Tile t, int x, int y){
        if(x >= tileMapSize.x || x < 0 || y < 0 || y >= tileMapSize.y)
            return;
        tiles[x][y] = t;
    }

    public Tile[][] GetAllTiles(){
        return tiles;
    }

    public void GrassFill(){
        for(int i = 0; i < tileMapSize.x; i++){
            for(int j = 0; j < tileMapSize.y; j++){
                Point pos = new Point(i, j);
                tiles[i][j] = new T_Grass(pos);
            }
        }
    }

    public void Update(){

    }

    public ArrayList<RenderInfo> getRenderInfo(){
        ArrayList<RenderInfo> toReturn = new ArrayList<>();
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile.getNeedsRendered()) {
                    toReturn.add(tile.getRenderInfo());
                    tile.setNeedsRendered(false);
                }
            }
        }
        return toReturn;
    }
}
