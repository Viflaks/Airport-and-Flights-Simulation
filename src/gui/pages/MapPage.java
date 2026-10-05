package gui.pages;
import gui.MainFrame;
import gui.figures.AirportCheck;
import gui.figures.AirportSquare;
import gui.figures.FlightCircle;
import gui.listeners.BlinkingListener;
import gui.simulation.Simulation;
import model.Airport;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MapPage extends JPanel {

    public class Map extends JPanel{

        Map(){
            super(null);
            setBackground(Color.WHITE);

            addMouseListener(new BlinkingListener(this));

            airportSquares=new ArrayList<>();
            flightCircles=new ArrayList<>();
            selected=null;
            zoom=false;

            xLower=-180.0;
            xHigher=180.0;
            yLower=-90.0;
            yHigher=90.0;

            timerSelected=new Timer(200,e->{
                if (selected!=null){
                    selected.swapColor();
                }
            });
            timerRepaint=new Timer(100,e -> repaint());
            timerSelected.start();
            timerRepaint.start();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            updateSimInterface();
            for (AirportSquare airportSquare : airportSquares)
                if (airportSquare.isVisible())
                    airportSquare.draw(g);

            for (FlightCircle flightCircle : flightCircles)
                flightCircle.draw(g);

        }

        public void addAirport(AirportSquare airportSquare){
            airportSquares.add(airportSquare);
            repaint();
        }

        public void addFlightCircle(FlightCircle flightCircle){
            flightCircles.add(flightCircle);
        }

        public void clearFlightCircles(){
            flightCircles.clear();
        }

        public void tickFlightCircles(){
            Iterator<FlightCircle> flightIterator=flightCircles.iterator();
            while (flightIterator.hasNext()){
                FlightCircle flightCircle=flightIterator.next();
                if (!flightCircle.reachedFinish()){
                    flightCircle.tick();
                }
                else {
                    flightIterator.remove();
                }
            }
        }

        public void removeAirport(int index){
            airportSquares.remove(index);
            repaint();
        }

        public int convertToRealX(double xCord){
            return (int)Math.round((xCord-xLower)/getXRange()*getWidth());
        }

        public int convertToRealY(double yCord){
            return (int)Math.round((yHigher-yCord)/getYRange()*getHeight());
        }

        public int convertToGeoX(double xCord){
            return (int)(xLower+Math.round((xCord)/getWidth()*getXRange()));
        }

        public int convertToGeoY(double yCord){
            return (int)(yHigher-Math.round((yCord)/getHeight()*getYRange()));
        }


        public double getXRange(){
            return Math.abs(xLower)+Math.abs(xHigher);
        }

        public double getYRange(){
            return Math.abs(yLower)+Math.abs(yHigher);
        }

        public void setSelected(AirportSquare airportSquare){
            selected=airportSquare;
        }

        public void clearSelected(){
            selected=null;
        }

        public AirportSquare getSelected(){
            return selected;
        }

        public MapPage getMapPage(){
            return MapPage.this;
        }

        public ArrayList<AirportSquare> getAirportSquares(){
            return airportSquares;
        }

        public boolean isZoom(){
            return zoom;
        }

        public void setZoom(boolean zoom){
            this.zoom=zoom;
        }

        public void setxLower(double xLower) {
            this.xLower = xLower;
        }

        public void setxHigher(double xHigher) {
            this.xHigher = xHigher;
        }

        public void setyLower(double yLower) {
            this.yLower = yLower;
        }

        public void setyHigher(double yHigher) {
            this.yHigher = yHigher;
        }

        private double xLower;
        private double xHigher;
        private double yLower;
        private double yHigher;

        AirportSquare selected;
        ArrayList<AirportSquare> airportSquares;
        ArrayList<FlightCircle> flightCircles;
        Timer timerSelected;
        Timer timerRepaint;

        private boolean zoom;
    }

    public MapPage(MainFrame owner){
        super(new BorderLayout(10,10));
        setBackground(Color.GREEN);
        this.owner=owner;

        this.simulationTime=0;
        this.simulationStatus="Finished";

        airportChecks=new ArrayList<>();

        JPanel mapPanel=generateMapPanel();
        add(mapPanel,BorderLayout.CENTER);

        this.filterPanel=generateFilterPanel();
        add(filterPanel,BorderLayout.EAST);

        this.simulation=new Simulation(owner,this);
    }

    public JPanel generateMapPanel(){
        JPanel mapPanel=new JPanel();
        mapPanel.setLayout(new BorderLayout(10,20));
        mapPanel.setBackground(Color.GREEN);


        JLabel label=new JLabel("Map",SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        mapPanel.add(label,BorderLayout.NORTH);

        JPanel innerPanel=new JPanel();
        innerPanel.setLayout(new BorderLayout(10,20));
        innerPanel.setBackground(Color.LIGHT_GRAY);

        JPanel upperPanel=generateUpperPanel();
        innerPanel.add(upperPanel,BorderLayout.NORTH);

        this.map=new Map();
        this.map.setPreferredSize(new Dimension(1080,540));
        innerPanel.add(map,BorderLayout.CENTER);

        mapPanel.add(innerPanel,BorderLayout.CENTER);
        return mapPanel;
    }

    public JPanel generateUpperPanel(){
        JPanel upperPanel=new JPanel();
        upperPanel.setLayout(new BorderLayout(10,10));

        JPanel timerPanel=generateTimerPanel();
        upperPanel.add(timerPanel,BorderLayout.WEST);

        JPanel buttonPanel=generateButtonPanel();
        upperPanel.add(buttonPanel,BorderLayout.EAST);

        return upperPanel;
    }

    public JPanel generateTimerPanel(){
        JPanel timerPanel=new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Font font=new Font("Arial", Font.BOLD, 22);

        this.simulationTimeLabel =new JLabel("00:00",SwingConstants.LEFT);
        this.simulationTimeLabel.setFont(font);
        timerPanel.add(simulationTimeLabel);

        this.simulationStatusLabel=new JLabel("Finished",SwingConstants.LEFT);
        this.simulationStatusLabel.setFont(font);
        timerPanel.add(simulationStatusLabel);
        return timerPanel;
    }

    public JPanel generateButtonPanel(){
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton startButton=new JButton("Start");
        startButton.addActionListener(e->simulation.startSimulation());
        buttonPanel.add(startButton);

        JButton resetButton =new JButton("Reset");
        resetButton.addActionListener(e-> simulation.resetSimulation());
        buttonPanel.add(resetButton);

        JButton pauseButton=new JButton("Pause");
        pauseButton.addActionListener(e-> simulation.stopSimulation());
        buttonPanel.add(pauseButton);

        JButton resumeButton=new JButton("Resume");
        resumeButton.addActionListener(e->simulation.resumeSimulation());
        buttonPanel.add(resumeButton);

        JButton zoomInButton=new JButton("ZoomIn");
        zoomInButton.addActionListener(e-> map.setZoom(true));
        buttonPanel.add(zoomInButton);

        JButton zoomOutButton=new JButton("ZoomOut");
        zoomOutButton.addActionListener(e-> {
            map.setZoom(false);
            AirportSquare.resetAIRPORT_DIMENSION();
            FlightCircle.resetFLIGHT_DIMENSION();

            map.setyLower(-90);
            map.setyHigher(90);
            map.setxLower(-180);
            map.setxHigher(180);
        });
        buttonPanel.add(zoomOutButton);

        return buttonPanel;
    }

    public JPanel generateFilterPanel(){
        JPanel filterPanel=new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel,BoxLayout.Y_AXIS));

        JLabel filterLabel=new JLabel("AirportFilters");
        filterLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        filterPanel.add(filterLabel,SwingConstants.CENTER);
        filterPanel.add(Box.createVerticalStrut(20));
        filterPanel.setPreferredSize(new Dimension(300, 0));
        return filterPanel;
    }

    public void addAirport(Airport airport){
        AirportSquare airportSquare=new AirportSquare(map,airport);
        map.addAirport(airportSquare);

        AirportCheck airportCheck=new AirportCheck(map,airportSquare);
        airportChecks.add(airportCheck);
        filterPanel.add(airportCheck);
    }

    public void clearMap(){
        for (int i=airportChecks.size()-1;i>=0;i--)
            removeAirport(i);
    }

    public void removeAirport(int index){
        map.removeAirport(index);

        AirportCheck airportCheck=airportChecks.remove(index);
        filterPanel.remove(airportCheck);
    }

    public Map getMap(){
        return map;
    }

    public MainFrame getOwner(){
        return owner;
    }

    public void updateSimInterface(){
        String hours=String.format("%02d",simulationTime/60);
        String mins=String.format("%02d",simulationTime%60);
        simulationTimeLabel.setText(hours+":"+mins);
        simulationStatusLabel.setText(simulationStatus);
    }

    public void setSimulationTime(int simulationTime){
        this.simulationTime=simulationTime;
    }

    public void setSimulationStatus(String simulationStatus){
        this.simulationStatus=simulationStatus;
    }

    private JPanel filterPanel;
    private Map map;
    private MainFrame owner;
    private ArrayList<AirportCheck> airportChecks;

    private JLabel simulationTimeLabel;
    private int simulationTime;

    private JLabel simulationStatusLabel;
    private String simulationStatus;

    private Simulation simulation;
}
