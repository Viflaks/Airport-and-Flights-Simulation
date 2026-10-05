package model;

import gui.tables.AirportTable;

public class Flight{
    public static class Template{
        public Template(String from, String to, String departure, int duration){
            this.from =from;
            this.to =to;
            this.departure=departure;
            this.duration =duration;
        }


        public Template(Flight flight){
            this.from=flight.from.getCode();
            this.to=flight.to.getCode();
            this.departure=flight.departure;
            this.duration=flight.duration;
        }

        public String getFrom() {
            return from;
        }

        public String getDeparture() {
            return departure;
        }
        public String getTo() {
            return to;
        }

        public int getDuration() {
            return duration;
        }

        private String from;
        private String to;
        private String departure;
        private int duration;
    }
    public static class Record{

        public String[] getParameters(){
            return new String[]{from,to,departure,duration};
        }
        private String from;
        private String to;
        private String departure;
        private String duration;
    }

    public Flight(Airport from, Airport to, String departure, int duration){
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.duration=duration;
        this.departureInt=formDeparture();
    }

    public Flight(Template t, AirportTable airportTable){
        this.from =airportTable.getAirport(t.from);
        this.to =airportTable.getAirport(t.to);
        this.departure =t.departure;
        this.duration=t.duration;
        this.departureInt=formDeparture();
    }

    public int formDeparture(){
        int hours;
        int mins;
        if (departure.length()==4){
            hours= Integer.parseInt(departure.substring(0,1));
            mins=Integer.parseInt(departure.substring(2,4));
        }
        else{
            hours= Integer.parseInt(departure.substring(0,2));
            mins=Integer.parseInt(departure.substring(3,5));
        }
        return 60*hours+mins;
    }

    public int getDuration() {
        return duration;
    }

    public String getDeparture() {
        return departure;
    }

    public int getDepartureInt(){
        return departureInt;
    }

    public Airport getTo() {
        return to;
    }

    public Airport getFrom() {
        return from;
    }

    private Airport from;
    private Airport to;
    private String departure;
    private int duration;

    private int departureInt;
}
