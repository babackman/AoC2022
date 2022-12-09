import java.util.HashSet;
import java.util.List;
import java.lang.Math;

public class Day09 {
    public static void Run(List<String> input) {
        p1(input);
    }

    private static void p1(List<String> input) {
        var head = new Position();
        var tail = new Position();
        var tailPositions = new HashSet<Position>();
        for (var line : input) {
            int deltaX = 0;
            int deltaY = 0;
            char direction = line.charAt(0);
            switch (direction) {
                case 'U':
                    deltaY = 1;
                    break;
                case 'D':
                    deltaY = -1;
                    break;
                case 'R':
                    deltaX = 1;
                    break;
                case 'L':
                    deltaX = -1;
                    break;
            }
            int steps = Integer.parseInt(line.substring(2));
            for (int i = 0; i < steps; i++) {
                head.X += deltaX;
                head.Y += deltaY;
                UpdateTail(head, tail);
                tailPositions.add(tail);
            }
        }
        System.out.println("09.1 - positions visited by tail:" + tailPositions.size());
    }

    private static void UpdateTail(Position head, Position tail) {
        int deltaX = head.X - tail.X;
        int deltaY = head.Y - tail.Y;
        if ((Math.abs(deltaX) < 2) && (Math.abs(deltaY) < 2)){
            //close enough
            return;
        }
        // different rows, move vertically one step in direction of head
        tail.X += Math.signum(deltaX);
        // different columns, move horizontally one step in direction of head
        tail.Y += Math.signum(deltaY);
    }

    private static class Position {
        public int X = 0;
        public int Y = 0;

        @Override
        public boolean equals(Object obj) {
            return (obj.X==x) && (oby.Y==Y)
        }

        @Override
        public int hashCode() {
            return (X<<16) ^ Y;
        }
    }
}
