import java.util.HashMap;
import java.util.List;

public class Day18 {
    public static void Run(List<String> input)
    {
        var dropletMap = processInput(input);

        // P1, total surface area
        int surfaceArea = 0;
        for (var droplet: dropletMap.values())
            surfaceArea += droplet.getUncoveredSideCount();
        System.out.println("Day 18 Pt 1, surface area= "+surfaceArea);
    }

    private static HashMap<Coordinate,DropletBit> processInput(List<String> input){
        var map = new HashMap<Coordinate,DropletBit>(input.size());
        for (var line : input){
            var tokens = line.split(",");
            int x=Integer.parseInt(tokens[0]);
            int y=Integer.parseInt(tokens[1]);
            int z=Integer.parseInt(tokens[2]);
            var coordinate = new Coordinate(x,y,z);
            var bit = new DropletBit();
            map.put(coordinate,bit);
            for (Side side: Side.values())
            {
                var adjacentBit = map.getOrDefault(coordinate.getAdjacentCoordinate(side), null);
                if (adjacentBit != null){
                    bit.SideIsCovered(side);
                    adjacentBit.SideIsCovered(side.opposite());
                }
            }
        }
        return map;
    }
    

    private enum  Side{
        Top(0), // +y
        Bottom(1), // -y
        Left(2), // -x
        Right(3), // -+x
        Front(4), // -z
        Back(5); // +z

        private int _value;
        private Side(int value){_value = value;}

        public int getValue(){return _value;}
        
        public Side opposite(){
            switch(this){
                case Top: return Bottom;
                case Bottom: return Top;
                case Left: return Right;
                case Right: return Left;
                case Front: return Back;
                case Back: return Front;
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

        private Coordinate getAdjacentCoordinate(Side side){
            switch (side){
                case Top:       return new Coordinate(_x,   _y-1,  _z);
                case Bottom:    return new Coordinate(_x,   _y-1,  _z);
                case Left:      return new Coordinate(_x-1, _y,    _z);
                case Right:     return new Coordinate(_x+1, _y,    _z);
                case Front:     return new Coordinate(_x,   _y,    _z-1);
                case Back:      return new Coordinate(_x,   _y,    _z+1);
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
            hash =hash*prime1 + _x;
            hash =hash*prime1 + _y;
            hash =hash*prime1 + _z;
            return hash;
        }
    }

    private static class DropletBit{
        private boolean[] _sideCovered;
        private int _uncoveredSides;
        public DropletBit() {
            _uncoveredSides = Side.values().length;
            _sideCovered=new boolean[_uncoveredSides];
            for (Side side: Side.values()){
             _sideCovered[side.getValue()] = false;
            };
        }

        public void SideIsCovered(Side covered)
        {
            int index = covered.getValue();
            if (!_sideCovered[index]){
                _sideCovered[index]=true;
                _uncoveredSides--;
            }
              
        }
        public int getUncoveredSideCount(){return _uncoveredSides;}
    }
}
