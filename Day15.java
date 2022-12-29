import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.lang.String;

public class Day15 {
    public static void Run(List<String> input, long p1yCoordToCheck, long p2MaxCoord)
    {
        var sensors = readSensors(input);
        p1(sensors, p1yCoordToCheck);
        //p2(sensors, p2MaxCoord);
    }

    private static List<Sensor> readSensors(List<String>input){
        var sensors = new ArrayList<Sensor>(input.size());
        for(var line: input){
            var tokens = line.split(" ");
            // sensor x
            var token = tokens[2];
            token = token.substring(2,token.length()-1);
            var sensorX = Long.parseLong(token);
            // sensor y
            token = tokens[3];
            token = token.substring(2,token.length()-1);
            var sensorY = Long.parseLong(token);
            // beacon x
            token = tokens[8];
            token = token.substring(2,token.length()-1);
            var beaconX = Long.parseLong(token);
            // beacon y
            token = tokens[9];
            token = token.substring(2);
            var beaconY = Long.parseLong(token);

            sensors.add(new Sensor(sensorX, sensorY, beaconX, beaconY));
        }

        return sensors;
    }

    public static void p1(List<Sensor> sensors, long yCoordToCheck){
        HashSet<Long> allXCoordinates=new HashSet<Long>();
        for (Sensor s : sensors){
            var xcoords = xCoordinatesWithNoBeacon(s, yCoordToCheck);
            allXCoordinates.addAll(xcoords);
        }
        // weed out positions that actually have beacons on them:
        for (Sensor s : sensors){
            if (s.getBeaconY()==yCoordToCheck){
                allXCoordinates.remove(s.getBeaconX());
            }
        }
        System.out.println("Day 15, pt1.  Positions on row y="+yCoordToCheck+" with no beacon:"+allXCoordinates.size());
    }

    public static void p2(List<Sensor> sensors, long maxCoordinate){
        for(long y=0; y<= maxCoordinate; y++){

        }
    }

    public static List<Long> xCoordinatesWithNoBeacon(Sensor s, long ycoordinate)
    {
        var xCoordinates = new ArrayList<Long>();
        long xDistance = s._beaconDistance - Math.abs(ycoordinate-s.getY());
        if (xDistance < 0)
            return xCoordinates;// no points qualify
        xCoordinates.add(s.getX());
        for (long deltaX=1; deltaX <= xDistance; deltaX++)
        {
            xCoordinates.add(s.getX()-deltaX);
            xCoordinates.add(s.getX()+deltaX);
        }
        return xCoordinates;
    }

    public static class Sensor{
        long _x;
        long _y;
        long _beaconX;
        long _beaconY;
        long _beaconDistance;

        public Sensor(long sensorX, long sensorY, long beaconX, long beaconY){
            _x = sensorX;
            _y = sensorY;
            _beaconX = beaconX;
            _beaconY = beaconY;
            _beaconDistance = Math.abs(beaconX - sensorX) + Math.abs(beaconY - sensorY);
        }

        public long getX(){return _x;}
        public long getY(){return _y;}
        public long getBeaconX(){return _beaconX;}
        public long getBeaconY(){return _beaconY;}
        public long getBeaconDistance(){return _beaconDistance;}
    }
}
