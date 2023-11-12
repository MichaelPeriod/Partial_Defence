package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
* Backbone of the view
* Stores much of the boilerplate code unrelated to the view itself
* */
public abstract class Client extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    //Tile dimensions
    public final int pixelSize = 4;
    public final int tileSize = 16;
    public final int tileSizeInPixels = pixelSize * tileSize;

    //Constructor to make window
    public Client(Point tileMapSize){
        //Set up window on initialization
        this.setPreferredSize(new Dimension(tileMapSize.x * tileSizeInPixels,
                                            tileMapSize.y * tileSizeInPixels));
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        //Set listeners
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    //Utility
    public Point screenPosToTilePos(Point pos){
        //Convert from screen to world pos
        return new Point(pos.x / tileSizeInPixels,
                         pos.y / tileSizeInPixels);
    }

    //Listener functions, by default set to do nothing
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}