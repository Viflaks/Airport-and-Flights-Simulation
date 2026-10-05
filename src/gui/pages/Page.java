package gui.pages;

import gui.listeners.LoadFunctionListener;
import gui.MainFrame;
import gui.listeners.SaveFunctionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class Page extends JPanel {

    public Page(MainFrame owner, Color color, String title, String addButtonText,String removeButtonText, AbstractTableModel tableModel) {
        super(new BorderLayout(10, 30));
        this.owner = owner;
        this.setBackground(color);

        //Add Title
        JLabel titleLabel=new JLabel(title,SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel,BorderLayout.NORTH);

        //Add Table
        this.tableModel=tableModel;
        this.table=new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane,BorderLayout.CENTER);

        //Add BottomPanel
        JPanel bottomPanel=generateBottomPanel(addButtonText,removeButtonText);
        add(bottomPanel,BorderLayout.SOUTH);
    }

    public JPanel generateBottomPanel(String addButtonText,String removeButtonText){
        JPanel bottomPanel=new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));

        JPanel pathPanel=generatePathPanel();

        JPanel buttonPanel=generateButtonPanel(addButtonText,removeButtonText);

        bottomPanel.add(pathPanel);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(buttonPanel);

        return bottomPanel;
    }

    public JPanel generatePathPanel(){
        JPanel pathPanel=new JPanel();
        pathPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,20));

        //Add Label and TextField for PathToFile
        JLabel pathLabel=new JLabel("Path to File:");
        pathPanel.add(pathLabel);
        this.pathTextField=new JTextField(40);
        pathPanel.add(pathTextField);

        return pathPanel;
    }

    public JPanel generateButtonPanel(String addButtonText,String removeButtonText){
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

        JButton loadButton = generateButton("Load From File");
        JButton saveButton = generateButton("Save Into File");
        this.addButton=generateButton(addButtonText);
        this.removeButton=generateButton(removeButtonText);

        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        //loadButton Function
        loadButton.addActionListener(new LoadFunctionListener(this));

        //saveButton Function
        saveButton.addActionListener(new SaveFunctionListener(this));

        return buttonPanel;
    }

    public JButton generateButton(String buttonText){
        JButton button=new JButton(buttonText);
        button.setPreferredSize(new Dimension(300,30));
        return button;
    }

    public String getPathTextFieldInfo(){
        return pathTextField.getText();
    }

    public MainFrame getOwner(){
        return owner;
    }

    protected JTextField pathTextField;
    protected AbstractTableModel tableModel;
    protected JTable table;

    protected JButton addButton;
    protected JButton removeButton;

    private MainFrame owner;
}