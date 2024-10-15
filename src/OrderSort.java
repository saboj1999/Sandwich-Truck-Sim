import java.util.ArrayList;

/**
 * The context from which the order sorting strategies are used
 */
public class OrderSort {

    OrderSortStrat strategy;
    ArrayList<Order> orders;

    /**
     * Instantiates a new OrderSort with the orders in filename
     * @param filename the file from which to read the orders
     * @throws Exception IOException if the file is not found
     */
    public OrderSort(String filename) throws Exception {
        orders =  new JSONFileReader(filename).readOrdersFromFile();
    }

    public OrderSort() {

    }

    /**
     * Changes the strategy used to sort the orders
     * @param strategy the new strategy to be used
     */
    public void setStrategy(OrderSortStrat strategy) {
        this.strategy = strategy;
        this.strategy.setOriginalOrders(this.orders);
    }

    /**
     * Gets a list of orders sorted based on the strategy
     * @return a list of Orders that the truck will go to based on strategy
     */
    public ArrayList<Order> getSortedOrders() {
        return strategy.getSortedOrders();
    }

    public ArrayList<Order> sortOrders(ArrayList<Order> orders) {
        strategy.setOriginalOrders(orders);
        return strategy.getSortedOrders();
    }

    /**
     * Gets the route as a list of neighborhood locations
     * @return a list of neighborhood locations that the truck will go to based on strategy
     */
    public ArrayList<NeighborhoodLocation> getRoute() {
        return strategy.getRoute();
    }

    /**
     * Gets the distance traveled by the route
     * @return an int of the distance the truck will travel
     */
    public int getDistance() {
        return strategy.getDistance();
    }

}
