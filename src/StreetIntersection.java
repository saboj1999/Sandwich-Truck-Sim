import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores data about the intersection of two streets
 * and which way the truck is facing at the intersection
 */
public class StreetIntersection extends NeighborhoodLocation {

    private final Street ewStreet;
    private final Street nsStreet;
    private final Direction direction;


    /**
     * Instantiates a new StreetIntersection at the two given streets
     * @param ewStreet the east-west street of the intersection
     * @param nsStreet the north-south street of the intersection
     * @param direction the direction the truck is facing at the intersection
     */
    public StreetIntersection(Street ewStreet, Street nsStreet, Direction direction) {
        this.ewStreet = ewStreet;
        this.nsStreet = nsStreet;
        this.direction = direction;
    }

    public Street getEwStreet() {
        return ewStreet;
    }

    public Street getNsStreet() {
        return nsStreet;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Static method to get a list of all the intersections in the city grid
     * @return an ArrayList of StreetIntersections representing every intersection in the grid
     */
    public static ArrayList<StreetIntersection> getAllIntersections() {
        ArrayList<Street> ewStreets = new ArrayList<>();
        ArrayList<Street> nsStreets = new ArrayList<>();
        ArrayList<Direction> directions = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ewStreets.add(new Street(i + " Street", StreetDirection.EASTWEST, i));
        }

        for (int i = 65; i <= 74; i++) {
            nsStreets.add(new Street((char) i + " Street", StreetDirection.NORTHSOUTH, i - 64));
        }
        directions.add(Direction.NORTH);
        directions.add(Direction.EAST);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);

        ArrayList<StreetIntersection> intersections = new ArrayList<>();

        for (Street s1 : ewStreets) {
            for (Street s2 : nsStreets) {
                for (Direction d : directions) {
                    intersections.add(new StreetIntersection(s1, s2, d));
                }
            }
        }

        return intersections;

    }

    @Override
    public String toString() {
        return "StreetIntersection{" +
                "ewStreet=" + ewStreet.toString() +
                ", nsStreet=" + nsStreet.toString() +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetIntersection that = (StreetIntersection) o;
        return Objects.equals(ewStreet, that.ewStreet) && Objects.equals(nsStreet, that.nsStreet) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ewStreet, nsStreet, direction);
    }

    @Override
    public boolean isAddress() {
        return false;
    }
}

