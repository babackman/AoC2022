import java.util.HashSet;
import java.util.List;
import java.lang.Math;

public class Day09 {
    public static void Run(List<String> input) {
        p1(input);
        p2(input);
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
                UpdateFollower(head, tail);
                tailPositions.add(tail);
            }
        }
        System.out.println("09.1 - positions visited by tail:" + tailPositions.size());
    }
    private static void p2(List<String> input) {
        var knots =new  Position[10];
        for(int i=0; i<knots.length; i++)
            knots[i]=new Position();
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
            for (int step = 0; step < steps; step++) {
                knots[0].X += deltaX;
                knots[0].Y += deltaY;
                for (int knot=1; knot<knots.length; knot++){
                    UpdateFollower(knots[knot-1], knots[knot]);
                }
                tailPositions.add(knots[9]);
            }
        }
        System.out.println("09.2 - positions visited by tail:" + tailPositions.size());
    }

    private static void UpdateFollower(Position leader, Position follower) {
        int deltaX = leader.X - follower.X;
        int deltaY = leader.Y - follower.Y;
        if ((Math.abs(deltaX) < 2) && (Math.abs(deltaY) < 2)){
            //close enough, no move necessary
            return;
        }
        // different rows, move vertically one step in direction of head
        follower.X += Math.signum(deltaX);
        // different columns, move horizontally one step in direction of head
        follower.Y += Math.signum(deltaY);
    }

    private static class Position {
        public int X = 0;
        public int Y = 0;

        @Override
        public boolean equals(Object obj) {
            var otherposition = (Position)obj;
            return (otherposition.X==X) && (otherposition.Y==Y);
        }

        @Override
        public int hashCode() {
            return (X<<16) ^ Y;
        }
    }
}
