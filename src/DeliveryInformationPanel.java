import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class DeliveryInformationPanel extends JPanel {

    // Address and Delivery Route list
    private ArrayList<Order> orders;
    private OrderSortStrat orderSortStrat;

    /**
     * Will create the JPanel.
     * Use this constructor when adding to the JFrame
     *
     * @param panelWidth
     * @param panelHeight
     */
    DeliveryInformationPanel(int panelWidth, int panelHeight) {
        // JPanel configurations
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(panelWidth, 0, panelWidth + 200, panelHeight);
        this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        //initializing variables
        orders = new ArrayList<>();
    }

    /**
     * Sets up the layout for the delivery information to set up on the panel
     *
     * @param g - Graphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        DesignPanelLayout(g);

        if (orders != null)
            updatePanel(g);
    }

    /**
     * updates the next addresses
     * Each string positioned +20 y from its corresponding information description
     * @param g
     */
    private void updatePanel(Graphics g) {
        if(orders.size() <= 1){
            g.drawString("No Orders Remaining", 10, 120);
        }
        remainingAddresses(g);

    }

    /**
     * Displays the remaining addresses the sandwich truck has to deliver to
     * @param g
     */
    private void remainingAddresses(Graphics g) {
        int offset = 120;
        for (int i = 1; i < orders.size(); i++) {
            g.drawString(orders.get(i).getAddress().toString(), 10, offset);
            offset += 35;
        }
    }

    /**
     * Layout that will keep track of next address including its coordinates as well as any remaining deliveries
     * Separate by 50
     * @param graphics
     */
    private void DesignPanelLayout(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 13));
        graphics.drawString("Routing Information:", 10, 20);
        graphics.setFont(new Font(Font.DIALOG_INPUT, Font.ITALIC, 13));
        graphics.drawString("Sorting Method:", 10, 40);
        graphics.drawString(orderSortStrat.toString(), 10, 60);
        graphics.drawString(String.format("Total Route Distance: %.1f", orderSortStrat.getDistance() / 100.0), 10, 80);
        graphics.drawString("Deliveries Remaining:", 10, 100);
        graphics.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 13));

    }

    /**
     * update the remaining elements in address and routes list to display on the panel
     * @param orders
     */
    public void updateUI(ArrayList<Order> orders) {
        this.orders = orders;
        repaint();
    }

    public void displaySortingMethod(OrderSortStrat strat){
        this.orderSortStrat = strat;
    }
}
