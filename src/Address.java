import java.util.Objects;

/**
 * Stores the data needed to represent an address
 */
public class Address extends NeighborhoodLocation {
    private final int houseNumber;
    private final Street street;

    /**
     * Creates a new instance of an Address with a house number and a street.
     *
     * @param number the house number
     * @param street the street the house is on
     */
    public Address(int number, Street street) {
        this.houseNumber = number;
        this.street = street;
    }

    /**
     * Gets the house number
     *
     * @return the house number of the address
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * Gets the street
     *
     * @return the street the house is on
     */
    public Street getStreet() {
        return street;
    }

    /**
     * Creates a string representing the address
     * @return A String representing the address
     */
    public String toString() {
        return houseNumber + "  " + street.getName();
    }

    /**
     * checks for equality
     * @param o another object
     * @return boolean for if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return houseNumber == address.houseNumber && Objects.equals(street, address.street);
    }

    /**
     * generates a hashcode
     * @return an int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, street);
    }

    @Override
    public boolean isAddress() {
        return true;
    }
}