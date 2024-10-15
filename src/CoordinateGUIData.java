/**
 * Represents the data for the GUI and where neighborhood locations should
 * be relative to the intersections
 */
public class CoordinateGUIData {
    private final double width;
    private final double height;
    private final int padding;
    private final int ewCount;
    private final int nsCount;
    private final int hPB;
    private final int offsetEast;
    private final int offsetNorth;

    /**
     * Instantiates a new CoordinateGUIData with given data
     * @param width window width
     * @param height window height
     * @param padding distance from intersection to street
     * @param ewCount number of east-west streets
     * @param nsCount number of north-south streets
     * @param hPB houses per block
     * @param offsetEast distance to the east of streets for houses
     * @param offsetNorth distance to the west of streets for houses
     */
    public CoordinateGUIData(double width, double height, int padding, int ewCount, int nsCount, int hPB, int offsetEast, int offsetNorth) {
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.ewCount = ewCount;
        this.nsCount = nsCount;
        this.hPB = hPB;
        this.offsetEast = offsetEast;
        this.offsetNorth = offsetNorth;
    }

    public int getOffsetNorth() {
        return offsetNorth;
    }

    public int getOffsetEast() {
        return offsetEast;
    }

    public int gethPB() {
        return hPB;
    }

    public int getNsCount() {
        return nsCount;
    }

    public int getEwCount() {
        return ewCount;
    }

    public int getPadding() {
        return padding;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
