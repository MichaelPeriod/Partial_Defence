import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        //Create window and set properties
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Partial Defence");

        new Controller(window);
    }
}
