package DataPackets;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
* Used for sending information of an input from one place to another
* */

public class InputInfo {
    //Input types (includes mouse and keyboard)
    public enum InputType {
        Click,
        ButtonPressed
    }

    //Store general information about an input
    public InputType input;
    public boolean inputHeld;
    public String info;
    public long inputTime;

    //Keeps the specific event that happens
    public KeyEvent keyEvent;
    public MouseEvent mouseEvent;

    //Builds the basic requirements for an input
    public InputInfo(InputType _input, boolean _inputHeld, String _info){
        input = _input;
        inputHeld = _inputHeld;
        info = _info;

        inputTime = System.currentTimeMillis();
    }

    //Sets the event specifically if it exists
    public void setMouseEvent(MouseEvent e){
        mouseEvent = e;
    }

    public void setKeyEvent(KeyEvent e){
        keyEvent = e;
    }
}
