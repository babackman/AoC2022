import java.util.List;

public class Day08 {

    public static void Run(List<String> input) {
        var treeGrid = ProcessInput(input);

        System.out.println("Day 07.1 visible trees: " + p1(treeGrid));
        System.out.println("Day 07.2 best scenic score: " + p2(treeGrid));
    }

    private static char[][] ProcessInput(List<String> input) {
        var treeGrid = new char[input.size()][input.get(0).length()];
        for (int i = 0, n = input.size(); i < n; i++)
            treeGrid[i] = input.get(i).toCharArray();
        return treeGrid;
    }

    private static int p1(char[][] treeGrid) {
        int visibleCount = 0;
        for (int row = 0, height = treeGrid.length; row < height; row++)
            for (int column = 0, width = treeGrid[row].length; column < width; column++) {
                boolean visible = isVisible(treeGrid, row, column, false);
                if (visible)
                    visibleCount++;
            }
        return visibleCount;
    }

    private static int p2(char[][] treeGrid) {
        int bestScore = 0;
        for (int row = 0, height = treeGrid.length; row < height; row++)
            for (int column = 0, width = treeGrid[row].length; column < width; column++) {
                int score = scenicScore(treeGrid, row, column);
                bestScore=Integer.max(bestScore,score);
            }
        return bestScore;
    }

    private static String formatIndices(int row, int column) {
        return "[" + row + "][" + column + "]";
    }

    private static boolean isVisible(char[][] treeGrid, int row, int column, boolean verbose) {
        // edges are easy
        if ((row == 0) || (row == treeGrid.length - 1) || (column == 0) || (column == treeGrid[row].length - 1))
            return true;
        char tree = treeGrid[row][column];
        String treeLabel = verbose
                ? "Tree" + formatIndices(row, column)
                : "";
        if (verbose)
            System.out.println(treeLabel + " is " + treeGrid[row][column]);

        // visible from left
        for (int c = column - 1; c >= 0; c--) {
            if (treeGrid[row][c] >= tree) {
                if (verbose)
                    System.out.println(treeLabel + " isn't visible from left, blocked by " + formatIndices(row, c));
                break;
            }
            if (c == 0) {
                if (verbose)
                    System.out.println(treeLabel + " is visible from left");
                return true;
            }
        }
        // visible from right
        for (int c = column + 1, width = treeGrid[row].length; c < width; c++) {
            if (treeGrid[row][c] >= tree) {
                if (verbose)
                    System.out.println(treeLabel + " isn't visible from right, blocked by " + formatIndices(row, c));
                break;
            }
            if (c == width - 1) {
                if (verbose)
                    System.out.println(treeLabel + " is visible from right");
                return true;
            }
        }
        // visible from top
        for (int r = row - 1; r >= 0; r--) {
            if (treeGrid[r][column] >= tree) {
                if (verbose)
                    System.out.println(treeLabel + " isn't visible from top, blocked by " + formatIndices(r, column));
                break;
            }
            if (r == 0) {
                if (verbose)
                    System.out.println(treeLabel + " is visible from top");
                return true;
            }
        }
        // visible from bottom
        for (int r = row + 1, height = treeGrid[row].length; r < height; r++) {
            if (treeGrid[r][column] >= tree) {
                if (verbose)
                    System.out.println(treeLabel + " isn't visible from bottom, blocked by " + formatIndices(r, column));
                break;
            }
            if (r == height - 1) {
                if (verbose)
                    System.out.println(treeLabel + " is visible from bottom");
                return true;
            }
        }

        return false;
    }
    private static int scenicScore(char[][] treeGrid, int row, int column)
    {
        // edges are easy
        if ((row == 0) || (row == treeGrid.length - 1) || (column == 0) || (column == treeGrid[row].length - 1))
            return 0;

        char tree = treeGrid[row][column];
        
        int leftScore = 0;
        for (int c = column - 1; c >= 0; c--) {
            leftScore++;
            if (treeGrid[row][c] >= tree) {
                break;
            }
        }
        
        int rightScore=0;
        for (int c = column + 1, width = treeGrid[row].length; c < width; c++) {
            rightScore++;
            if (treeGrid[row][c] >= tree) {
                break;
            }
        }

        int topScore=0;
        for (int r = row - 1; r >= 0; r--) {
            topScore++;
            if (treeGrid[r][column] >= tree) {
                break;
            }
        }

        int bottomScore=0;
        for (int r = row + 1, height = treeGrid[row].length; r < height; r++) {
            bottomScore++;
            if (treeGrid[r][column] >= tree) {
                break;
            }
        }
        return leftScore*rightScore*topScore*bottomScore;
    }
}
