import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class GameView extends Client {
    private Queue<RenderInfo> renderQueue = new LinkedList<>();
    private SpriteSheetReader spriteSheetReader;
    public GameView(Point _tileMapSize) {
        super(_tileMapSize);
        spriteSheetReader = new SpriteSheetReader(tileSize);
    }

    @Override
    public void paint(Graphics g) {
        //Draw window and create 2d drawing plane
        Graphics2D g2d = (Graphics2D) g;

        while(!renderQueue.isEmpty()){
            RenderInfo r = renderQueue.poll();
            Point startPos = new Point(
                     r.getTilePosition().x * tileSizeInPixels,
                     r.getTilePosition().y * tileSizeInPixels);
            draw(g2d, startPos, spriteSheetReader.retrievePixels(r));
        }
    }

    private void draw(Graphics2D g, Point startPos, int[][] pixels){
        for(int i = 0; i < tileSize; i++){
            for(int j = 0; j < tileSize; j++){
                Color color = new Color(pixels[i][j]);
                g.setColor(color);
                g.fillRect(startPos.x + i * pixelSize,
                           startPos.y + j * pixelSize,
                              pixelSize, pixelSize);
            }
        }
    }

    public void addToQueue(RenderInfo _render){
        renderQueue.add(_render);
    }
}
