/**
 * Represents the location of the truck and the direction it faces
 */
public class TruckLocation {

    private final Address address;
    private final Direction dir;

    /**
     * Instantiates a new truck at a given address, facing a given direction
     * @param address the address the truck is at
     * @param dir the direction the truck is facing
     */
    public TruckLocation(Address address, Direction dir) {

        this.address = address;
        this.dir = dir;

    }

    public Address getAddress() {
        return address;
    }

    public Direction getDir() {
        return dir;
    }

    public String toString() {
        String result = address.toString();
        if (dir == Direction.NORTH) {
            return result + ", facing N";
        } else if (dir == Direction.SOUTH) {
            return result + ", facing S";
        } else if (dir == Direction.EAST) {
            return result + ", facing E";
        } else {
            return result + ", facing W";
        }
    }
}
