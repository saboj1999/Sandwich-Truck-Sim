import java.util.Objects;

/**
 * Represents an (x, y) coordinate on the GUI
 */
public class Coordinate {

    private int x;
    private int y;


    /**
     * Instantiates a new coordinate at (x, y)
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a copy of another coordinate
     * @param coordinate the coordinate to copy
     */
    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(X: " + x + ", Y: " + y + ")";
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

