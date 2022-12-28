
public class Coordinate {
    int _x;
    int _y;
    int _z;

    public Coordinate(int x, int y, int z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getZ() {
        return _z;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d,%d]", _x, _y, _z);
    }

    Coordinate getAdjacentCoordinate(Direction dir) {
        switch (dir) {
            case Up:
                return new Coordinate(_x, _y - 1, _z);
            case Down:
                return new Coordinate(_x, _y + 1, _z);
            case Left:
                return new Coordinate(_x - 1, _y, _z);
            case Right:
                return new Coordinate(_x + 1, _y, _z);
            case Toward:
                return new Coordinate(_x, _y, _z - 1);
            case Away:
                return new Coordinate(_x, _y, _z + 1);
            default:
                return this;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate))
            return false;
        var otherCoord = (Coordinate) obj;
        return ((_x == otherCoord._x) && (_y == otherCoord._y) && (_z == otherCoord._z));
    }

    @Override
    public int hashCode() {
        final int prime1 = 17;
        final int prime2 = 486187739;
        int hash = prime1;
        hash = hash * prime2 + _x;
        hash = hash * prime2 + _y;
        hash = hash * prime2 + _z;
        return hash;
    }
}