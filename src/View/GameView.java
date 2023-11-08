package View;

import DataPackets.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

public class GameView extends Client {
    private final Queue<RenderInfo> renderQueue = new LinkedList<>();
    private final Queue<InputInfo> inputQueue = new LinkedList<>();
    private final SpriteSheetReader spriteSheetReader;
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
                int alpha = pixels[i][j] >> 24;
                if(alpha == 0) continue;

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

    public Queue<InputInfo> getInputQueue() {
        return inputQueue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            InputInfo i = new InputInfo(InputInfo.InputType.Click,
                    true,
                    e.getPoint().toString());
            i.setMouseEvent(e);
            inputQueue.add(i);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }
}
