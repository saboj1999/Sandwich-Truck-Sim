import javax.naming.NamingEnumeration;
import java.util.ArrayList;

/**
 *  NeighborhoodCoordinateCalculator is a helper class for calculating the
 *  pixel coordinates of a house based on a window size, neighborhood parameters,
 *  and spacing constants.
 */
public class NeighborhoodCoordinateCalculator {
    //window size in pixels
    private final double displayWidth;
    private final double displayHeight;
    //distance from the street to the first house on each block
    private final int streetPadding;
    //number of streets running in each direction
    private final int eastWestStreetCount;
    private final int northSouthStreetCount;
    //number of houses per block
    private final int housesPerBlock;
    //distance that houses are offset from the street
    private final int houseOffsetEast;
    private final int houseOffsetNorth;

    /**
     * Creates a new NeighborhoodCoordinateCalculator of a given size,
     * with a given neighborhood size and appropriate paddings
     * @param coordinateGUIData a CoordinateGUIData representing how the neighborhood is represented
     */

    public NeighborhoodCoordinateCalculator(CoordinateGUIData coordinateGUIData) {
        this.displayWidth = coordinateGUIData.getWidth();
        this.displayHeight = coordinateGUIData.getHeight();
        this.streetPadding = coordinateGUIData.getPadding();
        this.eastWestStreetCount = coordinateGUIData.getEwCount();
        this.northSouthStreetCount = coordinateGUIData.getNsCount();
        this.housesPerBlock = coordinateGUIData.gethPB();
        this.houseOffsetEast = coordinateGUIData.getOffsetEast();
        this.houseOffsetNorth = coordinateGUIData.getOffsetNorth();
    }

    /**
     * Calculates the coordinates corresponding to a given location
     *
     * @param location the location to calculate the coordinates of
     * @return a coordinate representing the GUI coordinates
     */
    public Coordinate getLocationCoordinate(NeighborhoodLocation location, int offsetX, int offsetY) {
        if (location.isAddress()) {
            Coordinate coord = getLocationCoordinate((Address) location);
            coord.setX(coord.getX() - offsetX - this.houseOffsetEast);
            coord.setY(coord.getY() - offsetY + this.houseOffsetNorth);
            return coord;
        } else {
            return getLocationCoordinate((StreetIntersection) location);
        }

    }

    /**
     * Calculates the coordinates corresponding to a given address
     *
     * @param address the address to calculate the coordinates of
     * @return a coordinate representing the GUI coordinates
     */
    public Coordinate getLocationCoordinate(Address address) {
        int houseNumber = address.getHouseNumber();
        Street street = address.getStreet();
        StreetDirection direction = street.getDirection();
        int streetNum = street.getNumber();
        int x, y;
        if (direction == StreetDirection.EASTWEST) {
            int blocksFromTop = streetNum - 1;
            y = (int) (blocksFromTop / (float)(eastWestStreetCount - 1) * displayHeight - houseOffsetNorth);
            int blocksFromLeft = houseNumber / 100 - 1;
            int eastOfStreet = (houseNumber % 100) / 10;
            int startX = (int) (blocksFromLeft / (float)(northSouthStreetCount - 1) * displayWidth + streetPadding);
            int blockSize = (int) (displayWidth / (float)(northSouthStreetCount - 1) - (streetPadding * 2));
            int houseDistance = blockSize / (housesPerBlock - 1);
            int offsetX = houseDistance * eastOfStreet;
            x = startX + offsetX;
        } else {
            int blocksFromLeft = streetNum - 1;
            x = (int) (blocksFromLeft / (float) (northSouthStreetCount - 1) * displayWidth + houseOffsetEast);
            int blocksFromTop = houseNumber / 100 - 1;
            int southOfStreet = (houseNumber % 100) / 10;
            int startY = (int) (blocksFromTop / (float) (eastWestStreetCount - 1) * displayHeight + streetPadding);
            int blockSize = (int) (displayHeight / (float) (eastWestStreetCount - 1) - (streetPadding * 2));
            int houseDistance = blockSize / (housesPerBlock - 1);
            int offsetY = houseDistance * southOfStreet;
            y = startY + offsetY;
        }
        return new Coordinate(x, y);
    }
    /**
     * Calculates the coordinates corresponding to a given intersection
     *
     * @param streetIntersection the intersection to calculate the coordinates of
     * @return a coordinate representing the GUI coordinates
     */
    public Coordinate getLocationCoordinate(StreetIntersection streetIntersection) {
        Street street1 = streetIntersection.getEwStreet();
        Street street2 = streetIntersection.getNsStreet();

        if (street1.getDirection() == StreetDirection.EASTWEST) {
            Street temp = street1;
            street1 = street2;
            street2 = temp;
        }

        //street1 is NS, street2 is EW
        int blocksFromLeft = street1.getNumber() - 1;
        int x = (int) ((blocksFromLeft / (float) (northSouthStreetCount - 1) * displayWidth)) - 15;
        int blocksFromTop = street2.getNumber() - 1;
        int y = (int) ((blocksFromTop / (float)(eastWestStreetCount - 1) * displayHeight)) - 10;

        return new Coordinate(x, y);


    }


    //method to convert arrayList<NeighborhoodLocation> to ArrayList<Coordinate> with truckOffset
    public ArrayList<Coordinate> convertRouteToTruckCoordinates(ArrayList<NeighborhoodLocation> locations, int offsetX, int offsetY) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (NeighborhoodLocation location : locations) {
            if (!location.isAddress()) {
                coordinates.add(getLocationCoordinate((StreetIntersection) location));
            } else {
                Coordinate coord = getLocationCoordinate((Address) location);
                coord.setX(coord.getX() - offsetX - this.houseOffsetEast);
                coord.setY(coord.getY() - offsetY + this.houseOffsetNorth);
                coordinates.add(coord);
            }
        }
        return coordinates;
    }
}