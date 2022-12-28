import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void Run(List<String> input) {
        var coordinates = readCoordinates(input);

        int surfaceArea = calculateSurfaceArea(coordinates, true);
        System.out.println("Day 18, pt1 - surface area is " + surfaceArea);

        surfaceArea = calculateSurfaceArea(coordinates, false);
        System.out.println("Day 18, pt2 - exterior surface area is " + surfaceArea);
    }

    private static List<Coordinate> readCoordinates(List<String> input) {
        var coordinates = new ArrayList<Coordinate>(input.size());
        for (var line : input) {
            var tokens = line.split(",");
            int x = Integer.parseInt(tokens[0]);
            int y = Integer.parseInt(tokens[1]);
            int z = Integer.parseInt(tokens[2]);
            coordinates.add(new Coordinate(x, y, z));
        }
        return coordinates;
    }

    public static int calculateSurfaceArea(List<Coordinate> coordinates, boolean includeVoids) {
        var model = new Model(coordinates, 1);
        // fill around droplet with Air
        floodFill(model, model.getMinCorner(), 'A');
        return calculateSurfaceArea(model, includeVoids);
    }

    private static void floodFill(Model model, Coordinate start, char fill) {
        var Q = new ArrayDeque<Coordinate>();
        Q.add(start);
        model.setElement(start, fill);

        while (!Q.isEmpty()) {
            var current = Q.removeFirst();
            Coordinate adjacent;
            if (current.getX() > model.getMinX()) {
                adjacent = current.getAdjacentCoordinate(Direction.Left);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
            if (current.getX() < model.getMaxX()) {
                adjacent = current.getAdjacentCoordinate(Direction.Right);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
            if (current.getY() > model.getMinY()) {
                adjacent = current.getAdjacentCoordinate(Direction.Up);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
            if (current.getY() < model.getMaxY()) {
                adjacent = current.getAdjacentCoordinate(Direction.Down);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
            if (current.getZ() > model.getMinZ()) {
                adjacent = current.getAdjacentCoordinate(Direction.Toward);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
            if (current.getZ() < model.getMaxZ()) {
                adjacent = current.getAdjacentCoordinate(Direction.Away);
                if (model.getElement(adjacent) == (char) 0) {
                    Q.add(adjacent);
                    model.setElement(adjacent, fill);
                }
            }
        }
    }

    private static int calculateSurfaceArea(Model model, boolean includeVoids) {
        int surfaceArea = 0;
        int minX = model.getMinX();
        int minY = model.getMinY();
        int minZ = model.getMinZ();
        int maxX = model.getMaxX();
        int maxY = model.getMaxY();
        int maxZ = model.getMaxZ();

        // counting Lava adjacent to Air, assuming most of the model space is Lava
        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int x = minX; x <= maxX; x++) {
                    var currentElement = model.getElement(x, y, z);
                    if (currentElement =='L')
                        continue;
                    if (!includeVoids && (currentElement != 'A'))
                        continue;
                    // Left
                    if ((x > 0) && (model.getElement(x - 1, y, z) == 'L'))
                        surfaceArea++;
                    // Right
                    if ((x < maxX) && (model.getElement(x + 1, y, z) == 'L'))
                        surfaceArea++;
                    // Up
                    if ((y > 0) && (model.getElement(x, y - 1, z) == 'L'))
                        surfaceArea++;
                    // Down
                    if ((y < maxY) && (model.getElement(x, y + 1, z) == 'L'))
                        surfaceArea++;
                    // Toward
                    if ((z > 0) && (model.getElement(x, y, z - 1) == 'L'))
                        surfaceArea++;
                    // Away
                    if ((z < maxZ) && (model.getElement(x, y, z + 1) == 'L'))
                        surfaceArea++;
                }
            }
        }
        return surfaceArea;
    }

    static class Model {
        char[][][] _elements;
        Coordinate _minCorner;
        Coordinate _maxCorner;

        public Model(List<Coordinate> coordinates, int padding) {
            int minX = Integer.MAX_VALUE,
                    minY = Integer.MAX_VALUE,
                    minZ = Integer.MAX_VALUE,
                    maxX = Integer.MIN_VALUE,
                    maxY = Integer.MIN_VALUE,
                    maxZ = Integer.MIN_VALUE;

            for (var c : coordinates) {
                minX = Math.min(minX, c.getX());
                minY = Math.min(minY, c.getY());
                minZ = Math.min(minZ, c.getZ());

                maxX = Math.max(maxX, c.getX());
                maxY = Math.max(maxY, c.getY());
                maxZ = Math.max(maxZ, c.getZ());
            }

            _minCorner = new Coordinate(minX - padding, minY - padding, minZ - padding);
            _maxCorner = new Coordinate(maxX + padding, maxY + padding, maxZ + padding);

            int width = _maxCorner.getX() - _minCorner.getX() + 1;
            int height = _maxCorner.getY() - _minCorner.getY() + 1;
            int depth = _maxCorner.getZ() - _minCorner.getZ() + 1;
            _elements = new char[width][height][depth];

            // fill in scanned Lava bits:
            for (var c : coordinates) {
                setElement(c, 'L');
            }

        };

        public Coordinate getMinCorner() {
            return _minCorner;
        }

        public int getMinX() {
            return _minCorner.getX();
        }

        public int getMinY() {
            return _minCorner.getY();
        }

        public int getMinZ() {
            return _minCorner.getZ();
        }

        public int getMaxX() {
            return _maxCorner.getX();
        }

        public int getMaxY() {
            return _maxCorner.getY();
        }

        public int getMaxZ() {
            return _maxCorner.getZ();
        }

        public char getElement(Coordinate where) {
            return getElement(where.getX(), where.getY(), where.getZ());
        }

        public char getElement(int x, int y, int z) {
            return _elements[x - _minCorner.getX()][y - _minCorner.getY()][z - _minCorner.getZ()];

        }

        public void setElement(int x, int y, int z, char c) {
            _elements[x - _minCorner.getX()][y - _minCorner.getY()][z - _minCorner.getZ()] = c;

        }

        public void setElement(Coordinate target, char c) {
            setElement(target.getX(), target.getY(), target.getZ(), c);
        }

    }

}
