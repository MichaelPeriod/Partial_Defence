package DataPackets;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputInfo {
    public enum InputType {
        Click,
        ButtonPressed
    }

    public InputType input;
    public boolean inputHeld;
    public String info;
    public long inputTime;

    public KeyEvent keyEvent;
    public MouseEvent mouseEvent;

    public InputInfo(InputType _input, boolean _inputHeld, String _info){
        input = _input;
        inputHeld = _inputHeld;
        info = _info;

        inputTime = System.currentTimeMillis();
    }

    public void setMouseEvent(MouseEvent e){
        mouseEvent = e;
    }

    public void setKeyEvent(KeyEvent e){
        keyEvent = e;
    }
}
