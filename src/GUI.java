import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * GUI JFrame class
 */
public class GUI extends JFrame {

    // Constants
    final int WIDTH = 700;
    final int HEIGHT = 700;
    final String TITLE = "Sandwich Shop";

    DeliveryInformationPanel deliveryInformationPanel = new DeliveryInformationPanel(WIDTH, HEIGHT);
    OrderInformationPanel orderInformationPanel = new OrderInformationPanel(WIDTH, HEIGHT);
    OrderSortStrat orderSortStrat;
    SandwichCityGrid sandwichCityGrid;
    CustomOrder customOrder;
    ArrayList<Order> orders;


    /**
     * Initializes the GUI
     */
    public GUI() {
        selectOrderMethod();
        selectSortingMethod();
        setBackground(Color.black);
        setTitle(TITLE);
        setLayout(null);
        setBounds(0, 0, WIDTH + 200, HEIGHT + 110);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.sandwichCityGrid = new SandwichCityGrid(WIDTH, HEIGHT, orderSortStrat, deliveryInformationPanel, orderInformationPanel, orders);

        add(sandwichCityGrid);
        deliveryInformationPanel.displaySortingMethod(orderSortStrat);
        //fixme sandwichCityGrid.setOrders();
        add(deliveryInformationPanel);
        add(orderInformationPanel);

        setVisible(true);
    }


    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Determines which sorting method to use for Truck Deliveries or exits the application if user exited program
     */
    private void selectSortingMethod() {
        //Custom button text
        int sortingMethod = jDialogSelection();

        if (sortingMethod == 0)
            orderSortStrat = new TimeSort();
        else if (sortingMethod == 1)
            orderSortStrat = new DistanceSort();
        else if (sortingMethod == 2)
            orderSortStrat = new RightHandSort();
        else {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        }
    }

    /**
     * Determines whether Custom orders or Random orders, or a
     * combinations of both will be used when creating sandwich orders.
     */
    private void selectOrderMethod() {
        //Custom button text
        int orderMethod = jOrderSelection();

        if (orderMethod == 0){
            // Fully random mode
            GenerateRandoms generateRandoms = new GenerateRandoms();
            orders = generateRandoms.generateRandomOrderList();
        }
        else if (orderMethod == 1){

            createOrderList();

        }
        else {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        }
    }

    public void createOrderList(){

        customOrder = new CustomOrder();

        while(!customOrder.finished){
            orders = customOrder.getOrders();
        }
    }

    /**
     * gets user response of type of routing strategy to use
     *
     * @return an int that represents the selection made
     */
    private int jDialogSelection() {
        Object[] options =
                {"Time Sort", // returns 0 when selected on JDialog
                        "Distance Sort", // returns 1 when selected on JDialog
                        "Right Hand Sort"}; // returns 2 when selected on JDialog


        return JOptionPane.showOptionDialog(this,
                "Select Routing Strategy",
                "Routing Strategy",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
    }

    /**
     * gets user response of type of order method to use
     *
     * @return an int that represents the order made
     */
    private int jOrderSelection() {
        Object[] options =
                {"Random Orders", // returns 0 when selected on JOrder
                        "Custom Orders"}; // returns 1 when selected on JOrder


        return JOptionPane.showOptionDialog(this,
                "Random or Custom Orders?",
                "Make Orders",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
    }


}
