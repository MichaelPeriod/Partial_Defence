import DataPackets.*;
import Model.Model;
import Renderables.Tiles.TileBuilder;
import View.GameView;
import Model.MazeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Queue;

/*
* Controller of the MVC
* Application entry point
* */
public class Controller extends Thread {
    //Get final variables
    private final int tickRate = 60;
    private final Point tileMapSize = new Point(15, 9);

    //Store model and view
    private final Thread tickThread;
    private final GameView view;
    private final Model model;

    //Constructor
    public Controller(JFrame window){
        //Add a window to console
        view = new GameView(tileMapSize);
        window.add(view);

        //Display window
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Build Model
        model = new Model(tileMapSize);

        //Start as new thread
        tickThread = new Thread(this);
        tickThread.start(); //Calls run on new thread
    }

    @Override
    public void run() {
        //Function that runs every tick
        gameTick();
    }

    private void gameTick() { //Runs every tick
        //Declare variables
        final double tickInterval = 1000000000/(float)tickRate;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer;
        int ticksRan = 0;

        //Delay before running game to allow for completion of all tiles initializing
//        while (true){
//            currentTime = System.nanoTime();
//            if(currentTime - lastTime > 100)
//                break;
//        }

        //Loop for entire duration of game
        while(true){
            //Get time
            currentTime = System.nanoTime();

            //Figure out if time is past new frame time
            timer = currentTime - lastTime;
            delta += timer / tickInterval;
            lastTime = currentTime;
            if(delta <= 1) continue;

            //Run game functions
            handleInputs();
            model.update();
            updateScreen(ticksRan);

            //Draw frame and restart time till next frame
            delta--;
            ticksRan++;
        }
    }

    private void handleInputs(){
        //Get inputs from view
        Queue<InputInfo> inputs = view.getInputQueue();

        while(!inputs.isEmpty()){
            //Check each input
            InputInfo i = inputs.poll();

            //Send action to Model to act upon the input
            switch (i.input){
                case Click -> {
                    //Point screenPos = i.mouseEvent.getPoint();
                    //Point tilePos = view.screenPosToTilePos(screenPos);

                    if(i.mouseEvent.getButton() == MouseEvent.BUTTON1){
                        model.fill(Model.Layer.Structures, TileBuilder.Empty);
                        model.generateNewMaze(Model.Layer.Structures);
                    }
                }
            }
        }
    }

    private void updateScreen(int totalTicks){
        //Prompt sprites to see if update is wanted
        model.updateSprites(totalTicks);

        //Transfer all render information to the view
        for(RenderInfo renderInfo : model.getTileUpdates()){
            view.addToRenderQueue(renderInfo);
        }

        //Draw the updates
        view.repaint();
    }
}
