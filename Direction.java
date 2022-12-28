
public enum Direction {
    Up(0), // -y
    Down(1), // +y
    Left(2), // -x
    Right(3), // -+x
    Toward(4), // -z
    Away(5); // +z

    private int _value;

    private Direction(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }

    public Direction opposite() {
        switch (this) {
            case Up:
                return Down;
            case Down:
                return Up;
            case Left:
                return Right;
            case Right:
                return Left;
            case Toward:
                return Away;
            case Away:
                return Toward;
            default:
                return this;
        }
    }
}