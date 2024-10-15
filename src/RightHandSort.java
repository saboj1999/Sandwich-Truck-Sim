import java.util.*;

/**
 * Implements the strategy used to sort orders by distance with only right turns
 */
public class RightHandSort extends CriteriaSort {


    /**
     * Creates a new instance of RightHandSort
     */
    public RightHandSort() {
        super.setOriginalOrders(new ArrayList<>());
        super.setRoute(new ArrayList<>());
    }

    /**
     * Returns the RouteMaker associated with RightHandSort
     * @return the RouteMaker that RightHandSort uses
     */
    @Override
    public RouteMaker getRouteMaker() {
        return new RouteMaker(false);
    }

    @Override
    public String toString() {
        return "Right Hand Sort";
    }
}
