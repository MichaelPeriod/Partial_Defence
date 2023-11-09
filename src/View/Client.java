package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Client extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    public final int pixelSize = 4;
    public final int tileSize = 16;
    public final int tileSizeInPixels = pixelSize * tileSize;
    public final Point tileMapSize;
    public final Point screenSize;
    private final boolean isRunning = true;

    public Client(Point _tileMapSize){
        tileMapSize = _tileMapSize;
        screenSize = new Point(tileMapSize.x * tileSizeInPixels,
                               tileMapSize.y * tileSizeInPixels);
        //Set up window on initialization
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    @Override
    public void paint(Graphics g) {
        //Draw window and create 2d drawing plane
        super.paint(g);
    }

    public Point screenPosToTilePos(Point pos){
        return new Point(pos.x / tileSizeInPixels,
                         pos.y / tileSizeInPixels);
    }

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