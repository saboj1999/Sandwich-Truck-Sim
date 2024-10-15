import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the grid for the city
 */
public class CityGridSetup extends JPanel {

    // Constants
    final int BLOCK_WIDTH = 50;
    final int BLOCK_HEIGHT = 50;
    final int FPS = 60;

    private int xPosition = 10;
    private int yPosition = 10;

    private int windowWidth;
    private final int windowHeight;

    GenerateRandoms generateRandoms = new GenerateRandoms();
    ArrayList<Order> orderForJson = new ArrayList<>();

    /**
     * Instantiates a new CityGridSetup with a given width and height
     * @param windowWidth the width of the window
     * @param windowHeight the height of the window
     */
    public CityGridSetup(int windowWidth, int windowHeight, ArrayList<Order> orders){

        // Default white was hurting my eyes, remove later
        setBackground(Color.GRAY);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        //fixme setOrderForJSON();

//        orderForJson = generateRandoms.generateRandomOrderList();
        orderForJson = orders;
        JSONFileWriter json = new JSONFileWriter("./src/randomOrders.json");
        json.allOrdersIntoJSONFile(orderForJson);



    }

    /**
     * Names the streets on the GUI grid 10x10 grid layout
     *
     * @param graphics
     */
    private void nameStreets(Graphics2D graphics) {
        graphics.setColor(Color.yellow);
        int numXStreets = 0;
        int numYStreets = 0;

        ArrayList<String> possibleNorthSouthStreets = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            possibleNorthSouthStreets.add(i + " Street");
        }

        ArrayList<String> possibleEastWestStreets = new ArrayList<>();
        for (int i = 65; i <= 74; i++) {
            possibleEastWestStreets.add((char) i + " Street");
        }

        for (String possibleNorthSouthStreet : possibleNorthSouthStreets) {
            graphics.setFont(new Font("Dialog", Font.BOLD, 10));

            int y_coordinate = windowHeight / yPosition * numYStreets + 7;
            int x_coordinate = windowWidth / xPosition;
            // Add street names to left side of the window
            graphics.drawString(possibleNorthSouthStreet, x_coordinate + 10, y_coordinate);
            // Add street name to center of the window
            graphics.drawString(possibleNorthSouthStreet, (int) (x_coordinate * 4.5), y_coordinate);
            // Add street name to right side of the window
            graphics.drawString(possibleNorthSouthStreet, x_coordinate * 8 + 10, y_coordinate);
            numYStreets++;
        }

        for (String possibleEastWestStreet : possibleEastWestStreets) {
            graphics.setFont(new Font("Dialog", Font.BOLD, 10));

            alignNorthSouthStreetText(graphics, false);

            int x_coordinate = windowWidth / xPosition * numXStreets;
            int y_coordinate = windowHeight / yPosition;
            // Add Street names to the top side of window
            graphics.drawString(possibleEastWestStreet, x_coordinate, (int) (y_coordinate * .25));
            // Add Street names to center of the window
            graphics.drawString(possibleEastWestStreet, x_coordinate, (int) (y_coordinate * 4.25));
            // Add street names to the bottom of the window
            graphics.drawString(possibleEastWestStreet, x_coordinate, (int) (y_coordinate * 8.25));
            numXStreets++;

            // RESET FONT TO BE HORIZONTAL!
            graphics.setFont(new Font("Dialog", Font.BOLD, 10));
        }
    }


    /**
     * Changes the orientation of the NorthSouth street names to be displayed vertically on the GUI.
     *
     * @param graphics2D
     */
    private void alignNorthSouthStreetText(Graphics2D graphics2D, Boolean drawingTallyMarks) {
        Font font = new Font(null);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(90), 0, 0);
        Font rotateText;
        if (drawingTallyMarks)
            font = new Font("Dialog", Font.PLAIN, 5);
        rotateText = font.deriveFont(affineTransform);
        graphics2D.setFont(rotateText);
    }

    /**
     * 20 tick marks on each square (block) representing addresses.
     * Will be divided by 4 so 5 tick marks on each side of the square
     */
    private void drawAddressTickMarks(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.white);
        // Aligns the tally marks evenly across the square
        int houseDistance = 13;
        for (int i = 0; i < 5; i++) {
            Font font = new Font("Dialog", Font.PLAIN, 5);
            graphics.setFont(font);
            // for East to West
            graphics.drawString("l", x + houseDistance, y + 15);
            graphics.drawString("l", x + houseDistance, y + 60);

            // For north to south
            alignNorthSouthStreetText(graphics, true);

            graphics.drawString("l", x + 10, y + houseDistance);
            graphics.drawString("l", x + 55, y + houseDistance);

            graphics.setFont(new Font(null));
            houseDistance += 10;
        }
        // Reset Color for squares
        graphics.setColor(Color.black);
    }


    /**
     * Creates the 10x10 grid block layout for the sandwich truck simulation
     *
     * @param graphics2D
     */
    private void createGridLayout(Graphics2D graphics2D) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = windowWidth / xPosition * i;
                int y = windowHeight / yPosition * j;
                graphics2D.fillRect((windowWidth / xPosition * i) + 10, (windowHeight / yPosition * j) + 10, BLOCK_WIDTH, BLOCK_HEIGHT);
                drawAddressTickMarks(graphics2D, x, y);
            }
        }
    }


    /**
     * @param g2
     * Draws or "sets up" the City Grid for the GUI
     */
    public void setup(Graphics2D g2){

        g2.setColor(Color.black);

        createGridLayout(g2);

        nameStreets(g2);

    }



}
