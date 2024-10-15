import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderInformationPanel extends JPanel {

    private int panelWidth;

    // Orders list from which to get sandwiches
    private ArrayList<Order> orders;
    private ArrayList<Coordinate> routeCoordinates;
    private Coordinate nextCoordinate;
    DecimalFormat decimalFormat = new DecimalFormat(".00");

    /**
     * Will create the JPanel.
     * Use this constructor when adding to the JFrame
     *
     * @param panelWidth
     * @param panelHeight
     */
    OrderInformationPanel(int panelWidth, int panelHeight){
        this.panelWidth = panelWidth + 200;
        // JPanel configurations
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(0, panelHeight, this.panelWidth, panelHeight + 100);
        this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));

        orders = new ArrayList<>();
    }

    /**
     * Sets up the layout for the delivery information to set up on the panel
     * @param g - Graphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        DesignPanelLayout(g);

        updatePanel(g);
    }

    /**
     * updates the GUI display with the current order address, address coordinates, sandwich and cost
     * @param g
     */
    private void updatePanel(Graphics g){
        if(orders.size() <= 1){
            g.drawString("No Orders Remaining", 125, 35);
        }else {
            g.drawString(orders.get(0).getAddress().toString(), 125, 35);
            g.drawString(orders.get(0).getSandwich().getDescription(), 185, 65);
            g.drawString(nextCoordinate.toString(), 440, 35);
            g.drawString("$" + decimalFormat.format(orders.get(0).getSandwich().cost()), 740, 35);
            g.drawString(orders.get(0).getTime().toString(), 740, 65);

        }
    }

    /**
     * Layout that will keep track of next address including its coordinates as well as any remaining deliveries
     * Separate by 50
     * @param graphics
     */
    private void DesignPanelLayout(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        graphics.drawString("Current Order", (int) (this.panelWidth/2.5), 15);
        graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        graphics.drawString("Next Address:", 10, 35);
        graphics.drawString("Address Location:", 300, 35);
        graphics.drawString("Sandwich Description: ", 10, 65);
        graphics.drawString("Sandwich Cost: ", 620, 35 );
        graphics.drawString("Delivery Time: ", 620, 65 );

    }

    /**
     * update the remaining elements in orders displaying sandwiches
     * @param orders
     */
    public void updateUI(ArrayList<Order> orders, Coordinate coordinate) {
        this.orders = orders;
        this.nextCoordinate = coordinate;
        repaint();
    }

}
