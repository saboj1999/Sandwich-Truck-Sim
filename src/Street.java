import java.util.Objects;

/**
 * Stores the data for a street with its name, direction,
 * and an ordinal number representing its location
 */
public class Street {
    private String name;
    private StreetDirection direction;
    private int number;

    /**
     * Creates a new street
     *
     * @param name the displayed name of the street
     * @param dir the direction of street (East-West or North-South)
     * @param num the ordinal number of the street, corresponding to its location in order
     *            among the other streets
     */
    public Street(String name, StreetDirection dir, int num) {
        this.name = name;
        this.direction = dir;
        this.number = num;
    }

    /**
     * Gets the name of the street
     *
     * @return the name of the street
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the direction of the street
     *
     * @return the direction of the street
     */
    public StreetDirection getDirection() {
        return direction;
    }

    /**
     * Gets the ordinal number of the street
     *
     * @return the ordinal number of the street
     */
    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", direction=" + direction +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return number == street.number && Objects.equals(name, street.name) && direction == street.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, direction, number);
    }
}