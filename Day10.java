import java.util.ArrayList;
import java.util.List;

public class Day10 {
    private static final char LitPixel =  '█';
    private static final char UnlitPixel = ' ';

    public static void Run(List<String> input) {
        var registerHistory = BuildRegisterHistory(input);

        System.out.println("Day 10 p1: " + p1(registerHistory));
        p2(registerHistory);
    }

    private static int p1(List<Integer> registerHistory) {
        return getSignalStrength(20, registerHistory)
                + getSignalStrength(60, registerHistory)
                + getSignalStrength(100, registerHistory)
                + getSignalStrength(140, registerHistory)
                + getSignalStrength(180, registerHistory)
                + getSignalStrength(220, registerHistory);

    }

    private static void p2(List<Integer> registerHistory)
    {
        var display = new ArrayList<String>();
        int screenWidth=40;
        var currentRow  = new char[screenWidth];
        for (int cycle = 1, n = registerHistory.size(); cycle < n; cycle++){
            int screenColumn = (cycle-1)%screenWidth;
            int deltaX = registerHistory.get(cycle) - (screenColumn);
            if ((deltaX >=-1) && (deltaX <= 1))
                currentRow[screenColumn] = LitPixel;
            else
                currentRow[screenColumn] = UnlitPixel;
            if (screenColumn == screenWidth-1)
                display.add(new String(currentRow));
        }
        
        for(var line: display)
            System.out.println(line);
    }

    private static ArrayList<Integer> BuildRegisterHistory(List<String> input) {
        var history = new ArrayList<Integer>(input.size());

        // one fake cycle to align with problem's one-based descriptions
        history.add(0);

        int X = 1;
        for (var line : input) {
            if (line.charAt(0) == 'n') {
                // noop.
                history.add(X);
            } else {
                // addx V
                var V = Integer.parseInt(line.substring(5));
                // two cycles of same value, then increment
                history.add(X);
                history.add(X);
                X += V;
            }
        }
        return history;
    }

    // get the signal strength for a one-based cycle number in the one-based X
    // register history
    private static int getSignalStrength(int cycleNumber, List<Integer> registerHistory) {
        return cycleNumber * registerHistory.get(cycleNumber);
    }

}
