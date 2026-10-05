package gui;
import java.awt.event.*;

public class MainAdapter extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
