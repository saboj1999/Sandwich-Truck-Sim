import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.Timer;

/**
 * Represents the truck on the GUI
 */
public class Truck extends JComponent implements ObserverSubject {
    private Coordinate nextAddressCoordinate;
    private final ArrayList<Order> addressesOrders;
    private ArrayList<NeighborhoodLocation> route;

    //Observers of truck
    private ArrayList<TruckObserver> observers;

    boolean up, down, left, right;
    Direction facingDirection;
    boolean atNextAddress;

    // CURRENT LOCATION AND NEXT DESTINATION
    Coordinate location;
    Coordinate destination;
    int NEXT_ROUTE;

    // TOOLS
    OrderSort sorter;
    CoordinateGUIData GUI;
    NeighborhoodCoordinateCalculator ncc;
    private final DeliveryInformationPanel deliveryInformationPanel;
    private final OrderInformationPanel sandwichPanel;

    Image upTruck, downTruck, rightTruck, leftTruck;
    int x;
    int y;
    int speed;

    final int TRUCK_CENTER_X = 700 / 2 - 15;
    final int TRUCK_CENTER_Y = 725 / 2 - 20;

    private OrderSortStrat oss;
    private GUI parent;
    /**
     * Instantiates a new truck
     */
    public Truck(OrderSortStrat oss, DeliveryInformationPanel informationPanel, OrderInformationPanel sandwichPanel) { // TODO: add parameter for route type for the buttons

        GUI = new CoordinateGUIData(700 * 0.9, 700 * 0.9, 15, 10, 10, 10, 5, -5);
        ncc = new NeighborhoodCoordinateCalculator(GUI);
        this.deliveryInformationPanel = informationPanel; // sets up reference to DeliveryInformationPanel
        this.sandwichPanel = sandwichPanel; // sets up reference to OrderInformationPanel
        addressesOrders = new ArrayList<>(100);
        observers = new ArrayList<>();
        this.parent = parent;

        this.right = true; // default right
        this.left = false;
        this.up = false;
        this.down = false;
        this.atNextAddress = false;
        this.x = TRUCK_CENTER_X;
        this.y = TRUCK_CENTER_Y - 15;
        this.speed = 1;
        this.oss = oss;
        //System.out.println("I am here: " + x + ", " + y);

        // setup AND scale images
        upTruck = setup("up");
        downTruck = setup("down");
        rightTruck = setup("right");
        leftTruck = setup("left");

        ArrayList<Order> toSort = new ArrayList<>();
        try {
            toSort = new JSONFileReader("./src/randomOrders.json").readOrdersFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Generate Route and Update Address Book
        sort(oss, toSort); // OSS is the OrderSortStrat

        // Instantiate Location and Destination
        location = new Coordinate(x, y);
        //destination = desiredRoute.get(0);
        destination = ncc.getLocationCoordinate(route.get(0), 15, 10);
        NEXT_ROUTE = 1;

        informationPanel.updateUI(addressesOrders); // Beginning address updates to panel
        sandwichPanel.updateUI(addressesOrders, nextAddressCoordinate);

    }

    /**
     * Converts an order to its coordinate
     *
     * @param order the order to convert
     * @return the coordinate at its location
     */
    private Coordinate convertOrderToTruckCoordinate(Order order) {
        Coordinate coordinate = ncc.getLocationCoordinate(order.getAddress());
        coordinate.setX(coordinate.getX() - 15 - GUI.getOffsetEast());
        coordinate.setY(coordinate.getY() - 10 + GUI.getOffsetNorth());
        return coordinate;
    }

    /**
     * @param orderSortStrat
     * @return the desired route
     * Instantiates sorter and sets strategy while saving info on the route and address coordinates.
     */
    private void sort(OrderSortStrat orderSortStrat, ArrayList<Order> toSort) {

        // try to initialize sorter
//        try {
//            sorter = new OrderSort("./src/randomOrders.json");
////            sorter = new OrderSort("sandwich/Sandwich/src/randomOrders.json");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        sorter = new OrderSort();

        // sort data into routes: time / distance / right-hand
        sorter.setStrategy(orderSortStrat);

        ArrayList<Order> sortedOrders = sorter.sortOrders(toSort);
        nextAddressCoordinate = convertOrderToTruckCoordinate(sortedOrders.get(0));
        for (Order o : sortedOrders) {
            addressesOrders.add(o);
            registerObserver(o);
        }
        //addressesOrders.addAll(sortedOrders);
        System.out.println(addressesOrders.size());

        this.route = sorter.getRoute();

    }

    public Image setup(String imageName) {

        Image image = null;

        // Try to set up and scale truck.png(s)
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imageName + "Truck.png")));
            if (Objects.equals(imageName, "up") || Objects.equals(imageName, "down")) {
                image = image.getScaledInstance(20, 30, Image.SCALE_SMOOTH);
            }
            if (Objects.equals(imageName, "right") || Objects.equals(imageName, "left")) {
                image = image.getScaledInstance(30, 20, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            System.out.println("Image cannot be found");
        }
        return image;
    }

    private void drawAddresses(ArrayList<Order> orders, Graphics2D g2) {

        ArrayList<Coordinate> addresses = new ArrayList<>();
        for (Order order : orders) {
            addresses.add(ncc.getLocationCoordinate(order.getAddress()));
        }

        g2.setColor(Color.red);

        for (int i = 0; i < addresses.size(); i++) {

            if (i == 0) {
                g2.setColor(Color.green);
                g2.fillRect(addresses.get(i).getX(), addresses.get(i).getY(), 8, 8);
                g2.setColor(Color.red);
            } else {
                g2.fillOval(addresses.get(i).getX(), addresses.get(i).getY(), 5, 5);
            }
        }
    }

    /**
     * Will draw Truck first at center of grid layout and then be continuously called to redraw as necessary
     *
     * @param graphics
     */
    public void draw(Graphics2D graphics) {
        if (atNextAddress) {
            try {
                if (addressesOrders.size() > 1) {
                    DeliveryNotification(graphics, "Delivering Sandwich!");
                } else if (addressesOrders.size() == 1) {
                    DeliveryNotification(graphics, "Restocking!");
                }
                removeObserver(addressesOrders.get(0));
                deliveryInformationPanel.updateUI(addressesOrders); // Updates panel containing delivery information
                sandwichPanel.updateUI(addressesOrders, nextAddressCoordinate);
                addressesOrders.remove(0);
                nextAddressCoordinate = convertOrderToTruckCoordinate(addressesOrders.get(0));
                atNextAddress = false;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        drawAddresses(addressesOrders, graphics);

        if (up) {
            graphics.drawImage(upTruck, x + 5, y, null);
        }
        if (down) {
            graphics.drawImage(downTruck, x + 5, y, null);
        }
        if (right) {
            graphics.drawImage(rightTruck, x, y, null);
        }
        if (left) {
            graphics.drawImage(leftTruck, x, y, null);
        }
    }

    /**
     * Creates pop-up notification displaying to the user that the truck has stopped for a delivery
     *
     * @param g
     */
    private void DeliveryNotification(Graphics g, String message) {
        stop();
        JDialog dialog = new JDialog(parent);
        JLabel l = new JLabel(message, SwingConstants.CENTER);

        Timer timer = new Timer(3000, e -> {
            drive();
            dialog.remove(l);
            dialog.setVisible(false);
        });
        timer.setRepeats(false);
        timer.start();

        dialog.add(l);
        dialog.setSize(150, 100);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    /**
     * stops truck
     */
    private void stop() {
        speed = 0;
    }

    /**
     * truck starts to go
     */
    private void drive(){
        speed = 1;
    }

    /**
     * Updates position of truck based on next order in queue
     */
    public void update() {

//        up = false;
//        down = false;
//        right = false;
//        left = false;

        if (location.getX() == destination.getX()) {

            if (location.getY() < destination.getY()) {

                y += speed;
                location.setY(y);
                setFacingDirection(Direction.SOUTH);
            }
            if (location.getY() > destination.getY()) {

                y -= speed;
                location.setY(y);
                setFacingDirection(Direction.NORTH);
            }

        } else {

            if (location.getX() > destination.getX()) {

                x -= speed;
                location.setX(x);
                setFacingDirection(Direction.WEST);
            }
            if (location.getX() < destination.getX()) {

                x += speed;
                location.setX(x);
                setFacingDirection(Direction.EAST);
            }
        }

        //inform the orders about the truck's new location
        notifyObservers(location);

        // DEBUG for Truck direction boolean values
        // System.out.println("Up: " + up + "\nDown: " + down + "\nRight: " + right + "\nLeft: " + left);

        if (location.equals(destination)) {
            destination = ncc.getLocationCoordinate(route.get(NEXT_ROUTE), 15, 10);
            if (NEXT_ROUTE < route.size() - 1)
                NEXT_ROUTE += 1;
            if (location.equals(nextAddressCoordinate)) {
                atNextAddress = true;
            }

            // DEBUG for TRUCK Location
//            System.out.println("I made it!");
//            System.out.println("I am here:  "+ location);
//            System.out.println("Going here: "+ destination);
        }
    }

    /**
     * Sets the truck direction
     * @param direction direction truck is facing
     */
    private void setFacingDirection(Direction direction) {
        facingDirection = direction;
        if (direction == Direction.NORTH) {
            up = true;
            down = false;
            right = false;
            left = false;
        } else if (direction == Direction.SOUTH) {
            up = false;
            down = true;
            right = false;
            left = false;
        } else if (direction == Direction.EAST) {
            up = false;
            down = false;
            right = true;
            left = false;
        } else if (direction == Direction.WEST) {
            up = false;
            down = false;
            right = false;
            left = true;
        }

    }

    @Override
    public void registerObserver(TruckObserver truckObserver) {
        observers.add(truckObserver);
    }

    @Override
    public void removeObserver(TruckObserver truckObserver) {
        observers.remove(truckObserver);
    }

    @Override
    public void notifyObservers(Coordinate location) {
        for (TruckObserver o : observers) {
            o.update(location);
        }
    }


}
