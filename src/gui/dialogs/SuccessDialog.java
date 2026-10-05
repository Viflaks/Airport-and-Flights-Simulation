package gui.dialogs;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class SuccessDialog extends JDialog {
    public static class SuccessPanel extends JPanel{

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d=(Graphics2D) g;
            Path2D checkmark=new Path2D.Double();
            checkmark.moveTo(13, 30);
            checkmark.lineTo(23, 42);
            checkmark.lineTo(43, 18);

            g.setColor(Color.GREEN);
            g.fillOval(0, 0, 50, 50);

            g.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(4.0f));
            g2d.draw(checkmark);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Successful Operation",80,37);
        }
    }

    public SuccessDialog(){
        super(MainFrame.getInstance(),"Success");
        setLayout(null);
        SuccessPanel successPanel=new SuccessPanel();
        successPanel.setBounds(30,50,400,200);
        add(successPanel);
        setSize(500,300);
        setVisible(true);
    }
}
