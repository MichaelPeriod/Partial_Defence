package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import DataPackets.RenderInfo;

/*
* Reads sprite sheet and returns pixels in array
* */
public class SpriteSheetReader {
    //Sprite sheet file location
    //May transfer if multiple sprite sheets are introduced
    private final String spriteSheetPos = "src/res/SpriteSheet.png";
    private BufferedImage sheet; //Image of sheet
    private int[][] iSheet; //Integer 2d array translation of sheet


    private final int tileSize; //Size of a tile on a sprite

    //Constructor
    public SpriteSheetReader(int _tileSize) {
        tileSize = _tileSize;
        //Read file and convert to 2d int array
        try {
            sheet = ImageIO.read(new File(spriteSheetPos));
            iSheet = readImage(sheet);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Read and convert to 2d array on initialization of object
    private static int[][] readImage(BufferedImage image) {
        //Get image buffer
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();

        //Variables
        final int pixelLength = 4;
        int row = 0, col = 0;

        //To return
        int[][] result = new int[height][width];

        for (int pixel = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
            //Calculate value of pixel
            //Interpreted as each segment of 8 bytes is one value
            //Stored in order of alpha, red, green, blue
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red

            //Add to array
            result[row][col] = argb;

            //Move selector
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        //Return filled array
        return result;
    }

    //Get pixels from pre-calculated iSheet
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
}
