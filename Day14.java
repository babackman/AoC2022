import java.util.ArrayList;
import java.util.List;

public class Day14 {
   public static void Run(List<String> input)
   {
    var slice = ProcessInput(input);
    p1(slice);
   }
    
   private static CaveSlice ProcessInput(List<String> input)
   {
        var  rockPaths= new ArrayList<ArrayList<Point>>(input.size());
        int minX=500;
        int maxX=500;
        int maxY=0;
        for(var line: input){
            var pointStrings=line.split(" -> ");
            var path = new ArrayList<Point>(pointStrings.length);
            for (var pointString: pointStrings){
                var xyStrings = pointString.split(",");
                int x = Integer.parseInt(xyStrings[0]);
                minX = Math.min(x, minX);
                maxX = Math.max(x, maxX);
                int y = Integer.parseInt(xyStrings[1]);
                maxY = Math.max(y, maxY);
                path.add(new Point(x,y));
            }
            rockPaths.add(path);
        }

        var slice = new CaveSlice(minX, maxX, maxY);
        for(var path: rockPaths){
            for (int i=0; i<path.size()-1; i++){
                var point1=path.get(i);
                var point2=path.get(i+1);
                slice.AddRockLine(point1.X, point1.Y, point2.X, point2.Y);
            }
        }
        return slice;
   }
   private static void p1(CaveSlice slice){
        int sandUnits=0;
        DropResult result;
        do{
            result = slice.addSand(500);
            if (result == DropResult.Rest)
                sandUnits++;
        } while (result == DropResult.Rest);
        
        System.out.println("Day 14, p1: "+ sandUnits);
   }

   private static enum CellContent {
        Air, 
        Rock, 
        Sand, 
        Void
    };

    private static enum DropResult {
        Rest, // came to rest somewhere
        Void,  // fell into the void
        Blocked // starting position is not open
    };
    
    private static class CaveSlice{
        private int _minX;

        private CellContent[][] _contents;
        
        public CaveSlice(int minX, int maxX,int maxY){
            _minX=minX;
            
            int width = (maxX-minX+1);
            _contents = new CellContent[maxY+1][width];
            for (int row=0; row <= maxY; row++)
                for (int col=0; col < width; col++ )
                    _contents[row][col]=CellContent.Air;
        }
        public void AddRockLine(int x1, int y1, int x2, int y2)
        {
            if (x1 == x2)
            {
                // vertical line
                int start = Math.min(y1, y2);
                int end = Math.max(y1, y2);
                x1 -= _minX;
                for (int y=start; y<=end; y++)
                    _contents[y][x1] = CellContent.Rock;
            }
            else
            {
                // horizontal line
                x1 -= _minX;
                x2 -= _minX;
                int start = Math.min(x1, x2);
                int end = Math.max(x1, x2);
                for (int x=start; x <= end; x++)
                    _contents[y1][x] = CellContent.Rock;
            }
        }
        public DropResult addSand(int startX){
            var content = getContent(startX, 0);
            if (content == CellContent.Void)
                return DropResult.Void;
            else if (content != CellContent.Air)
                return DropResult.Blocked;
            
            int x=startX;
            int y=0; // always starts at y=0
            while(true)
            {
                // straight down?
                content = getContent(x,y+1);
                if (content==CellContent.Void)
                    return DropResult.Void;
                else if (content==CellContent.Air) {
                    y++;
                    continue;
                }
                // down and left?
                content = getContent(x-1, y+1);
                if (content==CellContent.Void)
                    return DropResult.Void;
                else if (content==CellContent.Air) {
                    x--;
                    y++;
                    continue;
                }
                // down and right?
                content = getContent(x+1, y+1);
                if (content==CellContent.Void)
                    return DropResult.Void;
                else if (content==CellContent.Air) {
                    x++;
                    y++;
                    continue;
                }

                break;
            }
            setContent(x,y, CellContent.Sand);
            return DropResult.Rest;
        }

        public CellContent getContent(int x, int y)
        {
            int col = x-_minX;
            if ((col < 0) || (col > _contents[0].length) || (y < 0) || (y >= _contents.length)){
                return CellContent.Void;
            }
            return _contents[y][col];
        }
        private void setContent(int x, int y, CellContent newContent)
        {
            int col = x-_minX;
            if (getContent(x, y) != CellContent.Void)
                _contents[y][x - _minX] = newContent;
        }
    }

    private static class Point{
        public int X;
        public int Y;
        public Point(int x, int y)
        {
            X=x;
            Y=y;
        }
    }
}
