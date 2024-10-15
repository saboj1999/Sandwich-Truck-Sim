import java.util.ArrayList;

/**
 * A class that generalizes an order sort by a certain criteria
 */
public abstract class CriteriaSort implements OrderSortStrat {

    private ArrayList<Order> originalOrders;
    private ArrayList<Order> sortedOrders;
    private ArrayList<NeighborhoodLocation> route;
    private int routeDistance;
    private TruckLocation lastTruckLocation;

    /**
     * Gets the list of orders that the truck has to visit sorted by time
     * @return an ArrayList of Orders sorted by criteria
     */
    @Override
    public ArrayList<Order> getSortedOrders() {
        this.sortedOrders = new ArrayList<>();
        sortOrders();
        addHomeLocationToRoute();
        return this.sortedOrders;
    }

    /**
     * Gets the route the truck follows to deliver all orders
     * @return an ArrayList of Neighborhood locations the truck passes through
     */
    public ArrayList<NeighborhoodLocation> getRoute() {
        return route;
    }

    /**
     * Sets the list of original orders to sort
     * @param orders an ArrayList of orders to sort
     */
    @Override
    public void setOriginalOrders(ArrayList<Order> orders) {
        this.originalOrders = orders;
    }

    /**
     * Returns the total distance of the route
     * @return the total distance of the route
     */
    @Override
    public int getDistance() {
        return routeDistance;
    }

    /**
     * Sorts the orders and creates the route
     */
    private void sortOrders() {
        int distance = 0;
        TruckLocation truckLocation = new TruckLocation(new Address(500, new Street("F Street", StreetDirection.NORTHSOUTH, 6)), Direction.NORTH);
        RouteMaker routeMaker = getRouteMaker();
        ArrayList<Order> ordersCopy = new ArrayList<>(originalOrders);
        while (ordersCopy.size() > 0) {
            routeMaker.setStartLocation(truckLocation);
            Order next = getNext(truckLocation, ordersCopy);
            routeMaker.calcRoute(next.getAddress());
            route.addAll(routeMaker.getRoute());
            sortedOrders.add(next);
            distance += routeMaker.getDistance();
            Direction nextDirection = routeMaker.getEndDirection();
            truckLocation = new TruckLocation(next.getAddress(), nextDirection);
            lastTruckLocation = truckLocation;
            ordersCopy.remove(next);
        }
        routeDistance = distance;
    }

    private void addHomeLocationToRoute() {
        RouteMaker routeMaker = getRouteMaker();
        routeMaker.setStartLocation(lastTruckLocation);
        routeMaker.calcRoute(Order.getDefaultLocation().getAddress());
        route.addAll(routeMaker.getRoute());
        sortedOrders.add(Order.getDefaultLocation());
        routeDistance += routeMaker.getDistance();
    }

    /**
     * Gets the RouteMaker for the concrete class
     * @return the RouteMaker used by the concrete class
     */
    public abstract RouteMaker getRouteMaker();

    /**
     * Sets the route to the given route
     * @param route an ArrayList to set the route to
     */
    public void setRoute(ArrayList<NeighborhoodLocation> route) {
        this.route = route;
    }

    /**
     * Gets the next address to visit by DistanceSort
     * @param truckLocation the starting location of the truck
     * @param ordersCopy the remaining orders
     * @return the next order the truck should visit
     */
    public Order getNext(TruckLocation truckLocation, ArrayList<Order> ordersCopy) {
        Order next = ordersCopy.get(0);
        RouteMaker routeMaker = getRouteMaker();
        routeMaker.setStartLocation(truckLocation);
        routeMaker.calcRoute(next.getAddress());
        int minDist = routeMaker.getDistance();
        //find the next order
        for (Order o : ordersCopy) {
            routeMaker.calcRoute(o.getAddress());
            int dist = routeMaker.getDistance();
            if (dist <= minDist) {
                minDist = dist;
                next = o;
            }
        }
        return next;
    }

    public abstract String toString();
}
