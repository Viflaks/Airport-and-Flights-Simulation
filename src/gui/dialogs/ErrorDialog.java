package gui.dialogs;

import gui.MainFrame;
import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {

    public static class ErrorPanel extends JPanel{

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Font font=new Font("Arial", Font.BOLD, 30);

            g.setColor(Color.RED);
            g.fillOval(0, 0, 50, 50);

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString("!", 22, 37);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString("Error Occurred",80,37);
        }
    }

    public ErrorDialog(String message){
        super(MainFrame.getInstance(),"Error");
        setLayout(null);
        JTextArea textArea=new JTextArea(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(200,100,400,200);
        add(scrollPane);
        ErrorPanel errorPanel=new ErrorPanel();
        add(errorPanel);
        errorPanel.setBounds(70,30,600,100);
        setSize(800,600);
        setVisible(true);
    }
}
