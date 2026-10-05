package gui.dialogs;

import gui.MainFrame;

import javax.swing.*;

public class ExitDialog extends JDialog {
    public ExitDialog(MainFrame owner){
        super(owner,"Exit?");
        setLayout(null);
        setSize(400,300);
        label=new JLabel("User inactive, will close in 5 secs if user doesnt press the button");
        label.setBounds(100,100,200,50);
        add(label);

        button=new JButton("Continue");
        button.addActionListener(e->{
            owner.resetTime();
            dispose();
        });
        button.setBounds(100,200,200,50);
        add(button);

        setVisible(true);
    }

    public void updateLabel(int i){
        if (i==0) System.exit(0);
        label.setText("User inactive, will close in "+i+" secs if user doesnt press the button");
    }

    private JLabel label;
    private JButton button;
}
