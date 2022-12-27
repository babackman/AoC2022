import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void Run(List<String> input)
    {
        var coordinates = readCoordinates(input);
        var bounds  = getBoundingVolume(coordinates);
        
        // inflate the bounds by 1 all around to allow air to get to all edges:
        var minCorner = new Coordinate(bounds[0].getX()-1,
            bounds[0].getY()-1, 
            bounds[0].getZ()-1);
        var maxCorner = new Coordinate(bounds[1].getX()+1,
            bounds[1].getY()+1, 
            bounds[1].getZ()+1);
        
        int width = maxCorner.getX()-minCorner.getX()+1;
        int height = maxCorner.getY()-minCorner.getY()+1;
        int depth = maxCorner.getZ()-minCorner.getZ()+1;
        var modelSpace = new char[width][height][depth];
        
        // fill in scanned Lava bits:
        for (var c : coordinates){
            setModelElement(modelSpace,minCorner,c,'L');
        }
        
        // fill around droplet with Air
        floodFill(modelSpace, minCorner, maxCorner, minCorner, 'A');

        int surfaceArea = calculateSurfaceArea(modelSpace);
        System.out.println("Day 18, pt1 - surface area is " + surfaceArea);
    }

    private static List<Coordinate> readCoordinates(List<String> input){
        var coordinates = new ArrayList<Coordinate>(input.size());
        for (var line : input){
            var tokens = line.split(",");
            int x=Integer.parseInt(tokens[0]);
            int y=Integer.parseInt(tokens[1]);
            int z=Integer.parseInt(tokens[2]);
            coordinates.add( new Coordinate(x,y,z));
        }
        return coordinates;
    }

    private static Coordinate[] getBoundingVolume(List<Coordinate>coordinates) {
        int minX=Integer.MAX_VALUE,
            minY=Integer.MAX_VALUE,
            minZ=Integer.MAX_VALUE,
            maxX=Integer.MIN_VALUE,
            maxY=Integer.MIN_VALUE,
            maxZ=Integer.MIN_VALUE;
        
        for(var c : coordinates) {
            minX= Math.min(minX,c.getX());
            minY= Math.min(minY,c.getY());
            minZ= Math.min(minZ,c.getZ());

            maxX= Math.max(maxX,c.getX());
            maxY= Math.max(maxY,c.getY());
            maxZ= Math.max(maxZ,c.getZ());
        }

        return new Coordinate[]{
            new Coordinate(minX,minY,minZ),
            new Coordinate(maxX, maxY, maxZ)
        };

    }
    
    private static char getModelElement(char[][][] modelSpace, Coordinate minCorner, Coordinate where)
    {
        return modelSpace[where.getX()-minCorner.getX()]
            [where.getY()-minCorner.getY()]
            [where.getZ()-minCorner.getZ()];
    }

    private static void setModelElement(char[][][] modelSpace, Coordinate minCorner, Coordinate target, char c)
    {
        modelSpace[target.getX()-minCorner.getX()]
            [target.getY()-minCorner.getY()]
            [target.getZ()-minCorner.getZ()] = c;
    }

    private static void floodFill(char[][][]modelSpace, Coordinate minCorner, Coordinate maxCorner, Coordinate start, char fill)
    {
        var Q = new ArrayDeque<Coordinate>();
        Q.add(start);
        setModelElement(modelSpace, minCorner, start, fill);

        while (!Q.isEmpty())
        {
            var current = Q.removeFirst();
            Coordinate adjacent;
            if(current.getX() > minCorner.getX())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Left);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
            if(current.getX() < maxCorner.getX())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Right);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
            if(current.getY() > minCorner.getY())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Up);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
            if(current.getY() < maxCorner.getY())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Down);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
            if(current.getZ() > minCorner.getZ())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Toward);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
            if(current.getZ() < maxCorner.getZ())
            {
                adjacent = current.getAdjacentCoordinate(Direction.Away);
                if ( getModelElement(modelSpace, minCorner, adjacent) == (char)0){
                    Q.add(adjacent);
                    setModelElement(modelSpace, minCorner, adjacent, fill);
                }
            }
        }
    }

    private static int calculateSurfaceArea(char[][][]modelSpace)  {
        int surfaceArea=0;
        int maxX = modelSpace.length-1;
        int maxY = modelSpace[0].length-1;
        int maxZ = modelSpace[0][0].length-1;

        // counting Lava adjacent to Air rather assuming most of the model space is Lava
        for (int x=0; x <= maxX; x++){
            for (int y=0; y <= maxY; y++){
                for (int z=0; z <= maxZ; z++)
                {
                    if (modelSpace[x][y][z] != 'A')
                        continue;
                    // Left
                    if ((x > 0) && (modelSpace[x-1][y][z]=='L'))
                        surfaceArea++;
                    // Right
                    if ((x < maxX) && (modelSpace[x+1][y][z]=='L'))
                        surfaceArea++;
                    // Up
                    if ((y > 0) && (modelSpace[x][y-1][z]=='L'))
                        surfaceArea++;
                    // Down
                    if ((y < maxY) && (modelSpace[x][y+1][z]=='L'))
                        surfaceArea++;
                    // Toward
                    if ((z > 0) && (modelSpace[x][y][z-1]=='L'))
                        surfaceArea++;
                    // Away
                    if ((z < maxZ) && (modelSpace[x][y][z+1]=='L'))
                        surfaceArea++;
                }
            }
        }
        return surfaceArea;
    }

    private enum  Direction{
        Up(0), // -y
        Down(1), // +y
        Left(2), // -x
        Right(3), // -+x
        Toward(4), // -z
        Away(5); // +z

        private int _value;
        private Direction(int value){_value = value;}

        public int getValue(){return _value;}
        
        public Direction opposite(){
            switch(this){
                case Up: return Down;
                case Down: return Up;
                case Left: return Right;
                case Right: return Left;
                case Toward: return Away;
                case Away: return Toward;
                default: return this;
            }
        }
    }

    private static class Coordinate{
        int _x;
        int _y;
        int _z;
        public Coordinate(int x, int y, int z) {
            _x=x;
            _y=y;
            _z=z;
        }

        public int getX(){return _x;}
        public int getY(){return _y;}
        public int getZ(){return _z;}

        @Override
        public String toString(){
            return String.format("[%d,%d,%d]",_x,_y,_z);
        }

        private Coordinate getAdjacentCoordinate(Direction dir){
            switch (dir){
                case Up:       return new Coordinate(_x,   _y-1,  _z);
                case Down:    return new Coordinate(_x,   _y+1,  _z);
                case Left:      return new Coordinate(_x-1, _y,    _z);
                case Right:     return new Coordinate(_x+1, _y,    _z);
                case Toward:     return new Coordinate(_x,   _y,    _z-1);
                case Away:      return new Coordinate(_x,   _y,    _z+1);
                default: return this;
            }
        }
    
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coordinate))
                return false;
            var otherCoord = (Coordinate)obj;
            return ((_x==otherCoord._x) && (_y==otherCoord._y) && (_z==otherCoord._z));
        }

        @Override
        public int hashCode() {
            final int prime1=17;
            final int prime2 = 486187739 ;
            int hash=prime1;
            hash =hash*prime2 + _x;
            hash =hash*prime2 + _y;
            hash =hash*prime2 + _z;
            return hash;
        }
    }   
}