
import java.time.LocalTime;
import java.util.*;

/**
 * Implements the strategy used to sort orders by time
 */
public class TimeSort extends CriteriaSort {

    /**
     * Creates a new instance of TimeSort
     */
    public TimeSort() {
        super.setOriginalOrders(new ArrayList<>());
        super.setRoute(new ArrayList<>());
    }

    /**
     * Returns the RouteMaker associated with TimeSort
     * @return the RouteMaker that TimeSort uses
     */
    @Override
    public RouteMaker getRouteMaker() {
        return new RouteMaker(true);
    }

    /**
     * Gets the next address to visit
     * @param truckLocation the truck's starting location
     * @param ordersCopy a list of the remaining orders
     * @return the next order the truck should visit
     */
    @Override
    public Order getNext(TruckLocation truckLocation, ArrayList<Order> ordersCopy) {
        Order next = ordersCopy.get(0);
        RouteMaker routeMaker = getRouteMaker();
        routeMaker.setStartLocation(truckLocation);
        routeMaker.calcRoute(next.getAddress());
        LocalTime minTime = next.getTime();
        //find the next order
        for (Order o : ordersCopy) {
            LocalTime time = o.getTime();
            if (time.isBefore(minTime) || time.equals(minTime)) {
                minTime = time;
                next = o;
            }
        }
        return next;

    }

    @Override
    public String toString() {
        return "Time Sort";
    }
}
