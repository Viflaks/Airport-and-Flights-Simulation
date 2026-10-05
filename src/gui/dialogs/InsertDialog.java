package gui.dialogs;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class InsertDialog extends JDialog{

    public InsertDialog(MainFrame owner, String title, String[] labels){
        super(owner,title);
        setSize(700,500);
        setLayout(new BorderLayout(10,10));

        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        this.textBoxes=new JTextField[4];
        for(int i=0;i<4;i++){
            JPanel innerPanel=generatePanel(labels[i],i);
            innerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(innerPanel);
            panel.add(Box.createVerticalStrut(20));
        }
        add(panel,BorderLayout.CENTER);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        update=new JButton(title);
        update.setPreferredSize(new Dimension(300,30));
        update.setAlignmentX(0);
        buttonPanel.add(update);

        add(buttonPanel,BorderLayout.SOUTH);
        setVisible(true);
    }

    public JPanel generatePanel(String labelText,int index){
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,30,10));

        JLabel label=new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25));
        panel.add(label);

        textBoxes[index]=new JTextField(20);
        panel.add(textBoxes[index]);
        return panel;
    }

    public String getTextField(int i) {
        return textBoxes[i].getText();
    }

    protected JButton update;
    private JTextField[] textBoxes;
}
