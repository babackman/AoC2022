import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void Run(List<String> input) {
        var map = new Map(input);
        var shortestPath = FindShortestPath(map);
        
        System.out.println("Day 12 p1, shortest path is "+(shortestPath.size()-1)+" steps");
    }

    public static  List<MapPoint> FindShortestPath(Map map)
    {
        // based on https://en.wikipedia.org/w/index.php?title=Dijkstra%27s_algorithm&oldid=1127202995#Using_a_priority_queue
        
        var start = map.getStart();
        var destination = map.getDestination();
        start.setBestPredecssor(null, 0);

        var Q = new ArrayList<MapPoint>();
        Q.add(start);
        boolean foundPath=false;
        while (!Q.isEmpty() && !foundPath)
        {
            // get next point to evaluate
            var u = Q.remove(0);

            var neighbors = getLegalNextSteps(map, u);
            for (var v : neighbors)
            {
                // not bothering to check alternate distance like 
                // the wikipedia article because all edge lengths are 1 so
                // previous best plus new edge length can't be less than 
                // the previous best
                if ((v!= start) && (v.getBestPredecssor() == null))
                {
                    Q.add(v);
                    v.setBestPredecssor(u, u.getBestDistance()+1);
                    if (v == destination){
                        foundPath=true;
                        break;
                    }

                }
            }
        }
        if (!foundPath)
            return null;

        // build path starting with destination;
        var path = new  ArrayList<MapPoint>();
        var point = destination;
        do{
            path.add(0,point);
            point = point.getBestPredecssor();
        }while (point != null);
        
        return path;
    }

    public static List<MapPoint> getLegalNextSteps(Map map, MapPoint point) {
        var nextSteps = new ArrayList<MapPoint>();
        MapPoint next = map.getPoint(point.getX(), point.getY() - 1);
        if (IsLegalNextStep(point, next))
            nextSteps.add(next);

        next = map.getPoint(point.getX(), point.getY() + 1);
        if (IsLegalNextStep(point, next))
            nextSteps.add(next);

        next = map.getPoint(point.getX() - 1, point.getY());
        if (IsLegalNextStep(point, next))
            nextSteps.add(next);

        next = map.getPoint(point.getX() + 1, point.getY());
        if (IsLegalNextStep(point, next))
            nextSteps.add(next);

        return nextSteps;
    }

    public static boolean IsLegalNextStep(MapPoint current, MapPoint next)
    {
        if (next==null)
            return false;
        var elevationChange = next.getElevation() - current.getElevation();
        return (elevationChange <= 1);
    }

    private static class Map {
        int _width;
        int _height;
        MapPoint[][] _points;
        MapPoint _start;
        MapPoint _destination;

        public Map(List<String> mapData) {
            _height = mapData.size();
            _width = mapData.get(0).length();
            _points = new MapPoint[_height][_width];
            for (int y = 0; y < _height; y++) {
                var line = mapData.get(y);
                for(int x=0; x < _width; x++){
                    var elevation = line.charAt(x);
                    _points[y][x]=new MapPoint(x,y,elevation);
                    if (elevation == 'S'){
                        _start=_points[y][x];
                        _start.setElevation('a');
                    }
                    else if (elevation == 'E'){
                        _destination=_points[y][x];
                        _destination.setElevation('z');
                    }
                }
            }
        }

        public MapPoint getStart(){return _start;}
        public MapPoint getDestination(){return _destination;}

        public MapPoint getPoint(int x, int y)
        {
            if ((x < 0) || (x >= _width) || (y < 0) ||(y >= _height))
                return null;
            return _points[y][x];
        }
    }

    private static class MapPoint {
        int _x;
        int _y;
        char _elevation;
        MapPoint _bestPredecessor;
        int _bestDistance=Integer.MAX_VALUE;

        public MapPoint(int x, int y,char elevation) {
            _x = x;
            _y = y;
            _elevation = elevation;
        }

        public void setElevation(char elevation){
            _elevation = elevation;
        }

        public int getX(){return _x;}
        public int getY(){return _y;}
        public char getElevation(){return _elevation;}
        public MapPoint getBestPredecssor(){return _bestPredecessor;}
        public int getBestDistance(){return _bestDistance;}
        public void setBestPredecssor(MapPoint newBest, int newBestDistance){
            _bestPredecessor = newBest;
            _bestDistance = newBestDistance;
        }
    }
}
