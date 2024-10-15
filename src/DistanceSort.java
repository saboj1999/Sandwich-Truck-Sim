import java.util.*;

/**
 * Implements the strategy used to sort orders by distance
 */
public class DistanceSort extends CriteriaSort {

    /**
     * Creates a new instance of DistanceSort
     */
    public DistanceSort() {
        super.setOriginalOrders(new ArrayList<>());
        super.setRoute(new ArrayList<>());
    }

    /**
     * Returns the RouteMaker associated with DistanceSort
     * @return the RouteMaker that DistanceSort uses
     */
    @Override
    public RouteMaker getRouteMaker() {
        return new RouteMaker(true);
    }

    @Override
    public String toString() {
        return "Distance Sort";
    }
}
