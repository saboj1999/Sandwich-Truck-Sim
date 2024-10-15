import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DistanceSortTest {

    private String fileName = "./tests/distanceSortTestOrders.json";

    @Test
    public void testDistanceSort() throws Exception {
        testSort(new DistanceSort());
    }

    @Test
    public void testRightHandSort() {
        testSort(new RightHandSort());
    }

    @Test
    public void testTimeSort() {
        testSort(new TimeSort());
    }


    private void testSort(OrderSortStrat strategy) {
        ArrayList<Order> toSort = new ArrayList<>();
        try {
            toSort = new JSONFileReader(fileName).readOrdersFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        OrderSort sorter = new OrderSort();
        CoordinateGUIData testGUI = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);
        NeighborhoodCoordinateCalculator ncc = new NeighborhoodCoordinateCalculator(testGUI);
        sorter.setStrategy(strategy);
        ArrayList<Order> sortedOrders = sorter.sortOrders(toSort);
        //ArrayList<Coordinate> route = sorter.getCoordinateRoute(testGUI);
        ArrayList<NeighborhoodLocation> routeLocs = sorter.getRoute();
        ArrayList<Coordinate> route = ncc.convertRouteToTruckCoordinates(routeLocs, 15, 10);


        for (Coordinate c : route) {
            System.out.println(c.toString());
        }

        for (Order o : sortedOrders) {
            System.out.println(o.getAddress());
        }
    }


}