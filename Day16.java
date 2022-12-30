import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 {
    public static void Run(List<String> input){
        var valves = readValves(input);
        p1(valves);
    }

    private static void p1(Map<String,Valve> valves){
        // start at AA, find most pressure released in 30 minutes
        var startingPath = new Path(30);
        startingPath.addVisit(valves.get("AA"), 0);
        var bestPath = greatestPressureRelease(startingPath);
        System.out.println("Day 16 pt 1, greatest pressure release: " + bestPath.getPressureReleased());
        System.out.print("Path found: ");
        for (var v : bestPath.getVisitedValves())
            System.out.print(v.getName()+",");

    }

    private static Path greatestPressureRelease(Path startingPath)
    {
        var bestPath = startingPath;
        if (startingPath.getRemainingTime() > 1)
        {
            var current = startingPath.getLastVisted();
            var candidates = current.getNeighbors();

            startingPath.decrementRemainingTime(); // to move to next candidate
            for(var next: candidates)
            {
                var candidatePath = new Path(startingPath);
                int releasedPressure = 0;
                if (!candidatePath.alreadyVisited(next) && next.getFlowRate() > 0 )
                {
                    candidatePath.decrementRemainingTime();// spend minute opening
                    releasedPressure = next.getFlowRate() * candidatePath.getRemainingTime();
                }
                candidatePath.addVisit(next,releasedPressure);

                var bestNewPath = greatestPressureRelease(candidatePath);
                if (bestNewPath.getPressureReleased() > bestPath.getPressureReleased()){
                    bestPath = bestNewPath;
                }
            }
        }
        return bestPath;
    }
    private static Map<String, Valve> readValves(List<String> input){
        var valves = new HashMap<String, Valve>(input.size());
        for (var line: input)
        {
            var tokens = line.split(",? ");
            String valveName=tokens[1];
            Valve newValve = valves.get(valveName);
            if (newValve == null)
            {
                newValve=new Valve(valveName);
                valves.put(valveName,newValve);
            }
            var rateString = tokens[4].substring("rate=".length(),tokens[4].length()-1);
            int flowRate = Integer.parseInt(rateString);
            newValve.setFlowRate(flowRate);

            for (int i=9; i < tokens.length; i++)
            {
                var neighbor = valves.get(tokens[i]);
                if (neighbor == null)
                {
                    neighbor = new Valve(tokens[i]);
                    valves.put(tokens[i], neighbor);
                }
                newValve.addNeighbor(neighbor);
            }
        }
        return valves;
    }

    public static class Valve{
        String _name;
        int _flowRate;
        ArrayList<Valve> _neighbors;

        public Valve(String name)
        {
            _name=name;
            _neighbors = new ArrayList<Valve>();
        }
        public String getName(){return _name;}
        public int getFlowRate(){return _flowRate;}
        public void setFlowRate(int rate){_flowRate=rate;}

        public void addNeighbor(Valve valve){
            _neighbors.add(valve);
        }
        public Collection<Valve> getNeighbors(){return Collections.unmodifiableCollection(_neighbors);}
    }

    public static class Path{
        ArrayList<Valve> _visitedValves;
        int _pressureReleased;
        int _remainingTime;

        public Path(int remainingTime)
        {
            _remainingTime=remainingTime;
            _visitedValves=new ArrayList<Valve>();
        }
        public Path( Path other){
            _remainingTime = other.getRemainingTime();
            _pressureReleased = other.getPressureReleased();
            _visitedValves=new ArrayList<Valve>(other._visitedValves);
        }

        public int getRemainingTime(){return _remainingTime;}
        public int getPressureReleased(){return _pressureReleased;}
        public boolean alreadyVisited(Valve valve){return _visitedValves.contains(valve);}
        public Valve getLastVisted(){return _visitedValves.get(_visitedValves.size()-1);}
        public Collection<Valve> getVisitedValves(){return Collections.unmodifiableCollection(_visitedValves);}

        public void decrementRemainingTime(){_remainingTime--;}
        public void addVisit(Valve valve,int released){
            _visitedValves.add(valve);
            _pressureReleased += released;}
    }
}
