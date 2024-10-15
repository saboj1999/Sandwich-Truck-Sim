
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates the actual grid for the city
 */
public class SandwichCityGrid extends JPanel implements Runnable {
    // Constants
    final int FPS = 60; // maybe we could make this value into a slider so the user can control the simulation speed

    CityGridSetup cityGrid; // TODO possibly make public
    private final Truck truck;
    public Thread thread;

    /**
     * Initialize instance variables. Gets the height and width to set up the 10x10 grid with.
     * Due to the Width being 1000, each block (rectangle) is 100 apart
     *
     * @param windowWidth
     * @param windowHeight
     */
    SandwichCityGrid(int windowWidth, int windowHeight, OrderSortStrat oss, DeliveryInformationPanel informationPanelReference, OrderInformationPanel orderInformationPanel, ArrayList<Order> orders) {
        this.setLayout(null);
        this.setBounds(0, 0, windowWidth, windowHeight);

        setBackground(Color.GRAY);

        // Instantiate CityGrid
        this.cityGrid = new CityGridSetup(windowWidth, windowHeight, orders);

        // Instantiate Truck
        this.truck = new Truck(oss, informationPanelReference, orderInformationPanel);

        // Start Runnable
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Adds the components to the GUI
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D g2 = (Graphics2D) graphics;

        cityGrid.setup(g2);

        g2.dispose();
    }

    /**
     * Runnable method
     * Will call the update() method to update x and y coordinates and the repaint() method to
     * be able to paint the truck going to its next delivery address coordinates
     */
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (thread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // UPDATE : Update Location of Objects on Screen
                update();

                // DRAW : Re-Draw Objects after each Update
                repaint();

                delta--;
            }
        }
    }

    /**
     * This method will update the x and y coordinates for the truck to take on. For demonstration purposes
     * simply moves to bottom right corner of window for now
     */
    private void update() {

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        truck.update();
    }

    /**
     * @param g Paints components at 60 frames per second
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        truck.draw(g2);

        g2.dispose();
    }

}

