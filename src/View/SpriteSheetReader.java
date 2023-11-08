package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import DataPackets.RenderInfo;

public class SpriteSheetReader {
    private final String spriteSheetPos = "src/res/SpriteSheet.png";
    private final int tileSize;
    private BufferedImage sheet;
    private int[][] iSheet;
    private boolean isReady = false;

    public SpriteSheetReader(int _tileSize) {
        tileSize = _tileSize;
        try {
            sheet = ImageIO.read(new File(spriteSheetPos));
            iSheet = readImage(sheet);
            isReady = true;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int[][] retrievePixels(RenderInfo r){
        int[][] toReturn = new int[tileSize][tileSize];
        Point pos = r.getSpritePosition();
        Point tileOffset = new Point(pos.x * tileSize, pos.y * tileSize);
        for(int i = 0; i < tileSize; i++) {
            for (int j = 0; j < tileSize; j++) {
                toReturn[j][i] = iSheet[tileOffset.x + i][tileOffset.y + j];
            }
        }
        return toReturn;
    }

    private static int[][] readImage(BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();

        int[][] result = new int[height][width];
        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        return result;
    }
}
