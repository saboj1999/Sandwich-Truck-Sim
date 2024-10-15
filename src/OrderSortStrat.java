import java.util.ArrayList;

/**
 * An Interface for the various strategies used to sort the orders
 */
public interface OrderSortStrat {
    ArrayList<Order> getSortedOrders();
    ArrayList<NeighborhoodLocation> getRoute();
    void setOriginalOrders(ArrayList<Order> orders);
    int getDistance();
    String toString();
}
