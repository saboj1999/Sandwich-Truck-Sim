// Java Program to create two checkbox
// one editable and other read only

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CustomOrder extends JFrame implements ItemListener {

    ArrayList<Order> orders = new ArrayList<>();
    public Boolean finished = false;
    // frame
    static JFrame f;

    // label
    static JLabel sandwichTitle, sandwichSelected, toppingTitle, toppingSelected,
            houseNumberTitle, houseNumberSelected, streetTitle, streetSelected,
            hourTitle, hourSelected, minuteTitle, minuteSelected, order;

    // combobox
    static JComboBox sandwichBox, toppingBox, houseNumberBox, streetBox, hourBox, minuteBox;

    public CustomOrder(){

        f = new JFrame("Custom Order");

        // create a object

        // array of string containing cities
        String s1[] = { "None", "Bagel", "Cheese Steak", "Hamburger"};
        String s2[] = { "No Toppings", "Bacon", "Cheese", "Egg", "Lettuce", "Mayo", "Sriracha", "Tomato"};
        String s3[] = { "None", "0", "999"};
        String s4[] = { "None", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String s5[] = { "None", "11", "5"};
        String s6[] = { "None", "0", "59"};


        // create checkbox
        sandwichBox = new JComboBox(s1);
        toppingBox = new JComboBox(s2);
        houseNumberBox = new JComboBox(s3);
        streetBox = new JComboBox(s4);
        hourBox = new JComboBox(s5);
        minuteBox = new JComboBox(s6);

        // set Kolakata and male as selected items
        // using setSelectedIndex
        sandwichBox.setSelectedIndex(0);
        toppingBox.setSelectedIndex(0);
        houseNumberBox.setSelectedIndex(0);
        streetBox.setSelectedIndex(0);
        hourBox.setSelectedIndex(0);
        minuteBox.setSelectedIndex(0);


        // add ItemListener
        sandwichBox.addItemListener(this);
        toppingBox.addItemListener(this);
        houseNumberBox.addItemListener(this);
        streetBox.addItemListener(this);
        hourBox.addItemListener(this);
        minuteBox.addItemListener(this);

        // set the checkbox as editable
        houseNumberBox.setEditable(true);
        hourBox.setEditable(true);
        minuteBox.setEditable(true);

        // create labels
        sandwichTitle = new JLabel("Select Sandwich");
        sandwichSelected = new JLabel("None selected");
        toppingTitle = new JLabel("Add Topping");
        toppingSelected = new JLabel("None selected");
        houseNumberTitle = new JLabel("Select House Number");
        houseNumberSelected = new JLabel("None Selected");
        streetTitle = new JLabel("Select Street");
        streetSelected = new JLabel("None selected");
        hourTitle = new JLabel("Hr: ");
        hourSelected = new JLabel("");
        minuteTitle = new JLabel("Min: ");
        minuteSelected = new JLabel("");
        order = new JLabel(" Currently no order placed!");


        // set color of text
        sandwichTitle.setForeground(Color.red);
        sandwichSelected.setForeground(Color.blue);
        toppingTitle.setForeground(Color.red);
        toppingSelected.setForeground(Color.blue);
        houseNumberTitle.setForeground(Color.red);
        houseNumberSelected.setForeground(Color.blue);
        streetTitle.setForeground(Color.red);
        streetSelected.setForeground(Color.blue);
        hourTitle.setForeground(Color.red);
        hourSelected.setForeground(Color.blue);
        minuteTitle.setForeground(Color.red);
        minuteSelected.setForeground(Color.blue);
        order.setForeground(Color.black);


        //buttons!

        JButton finish = new JButton("Finish");
        JButton another = new JButton("Another");

        // FINISH ORDERING
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                orders.add(collectOrder());

                setVisible(false);

                finished = true;

            }
        });

        // ANOTHER ORDER
        another.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                orders.add(collectOrder());

                sandwichBox.setSelectedIndex(0);
                toppingBox.setSelectedIndex(0);
                houseNumberBox.setSelectedIndex(0);
                streetBox.setSelectedIndex(0);
                hourBox.setSelectedIndex(0);
                minuteBox.setSelectedIndex(0);


            }
        });

        // create a new panel
        JPanel p = new JPanel();

        p.add(sandwichTitle);
        p.add(sandwichBox);
        p.add(sandwichSelected);

        p.add(toppingTitle);
        p.add(toppingBox);
        p.add(toppingSelected);

        p.add(houseNumberTitle);
        p.add(houseNumberBox);
        p.add(houseNumberSelected);

        p.add(streetTitle);
        p.add(streetBox);
        p.add(streetSelected);

        p.add(hourTitle);
        p.add(hourBox);
        p.add(hourSelected);

        p.add(minuteTitle);
        p.add(minuteBox);
        p.add(minuteSelected);

        p.add(order);


        // TODO properly add buttons
        p.add(finish);
        p.add(another);


        // set a layout for panel
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        sandwichBox.setMaximumSize(new Dimension(200,25));
        toppingBox.setMaximumSize(new Dimension(200,25));
        houseNumberBox.setMaximumSize(new Dimension(200,25));
        streetBox.setMaximumSize(new Dimension(200,25));
        hourBox.setMaximumSize(new Dimension(200,25));
        minuteBox.setMaximumSize(new Dimension(200,25));




        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(500, 450);

        f.show();

    }

    public void itemStateChanged(ItemEvent e)
    {
        // if the state combobox 1is changed
        if (e.getSource() == sandwichBox) {
            sandwichSelected.setText(sandwichBox.getSelectedItem() + " selected");
        }
        // if state of combobox 2 is changed
        else if(e.getSource() == toppingBox){
            toppingSelected.setText(toppingBox.getSelectedItem() + " selected");
        }
        else if(e.getSource() == houseNumberBox){
            houseNumberSelected.setText(houseNumberBox.getSelectedItem() + " selected");
        }
        else if(e.getSource() == streetBox){
            streetSelected.setText(streetBox.getSelectedItem() + " selected");

        }



        if(sandwichBox.getSelectedIndex() == 0) {
            order.setText("No sandwich selected!");
        }
        else if(streetBox.getSelectedIndex() == 0 || houseNumberBox.getSelectedIndex() == 0){
            order.setText("Where is the order going?");
        }
        else if(hourBox.getSelectedIndex() == 0 || minuteBox.getSelectedIndex() == 0){
            order.setText("What time should we get there?");
        }
        else{
            order.setText("A "+sandwichBox.getSelectedItem()+" with "+toppingBox.getSelectedItem()+" delivering to "
                    +houseNumberBox.getSelectedItem()+" "+streetBox.getSelectedItem()+" Street, at "
                    +hourBox.getSelectedItem()+":"+minuteBox.getSelectedItem());
        }




    }


    /**
     * @return current custom order
     * Returns the current custom order to be added to the list
     */
    public Order collectOrder(){

        StreetDirection direction;
        int streetNumber;
        Sandwich sandwich = null;
        // collect this order
        String temp = (String) streetBox.getSelectedItem();
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        List<String> numberList = Arrays.asList(numbers);

        if(numberList.contains(temp)){
            direction = StreetDirection.EASTWEST;
            assert temp != null;
            streetNumber = Integer.parseInt(temp);
        }
        else{
            direction = StreetDirection.NORTHSOUTH;
            assert temp != null;
            streetNumber = ((int)temp.charAt(0)) - 64;
        }

        Street street = new Street((String) streetBox.getSelectedItem() + " Street", direction, streetNumber);

        Address address = new Address(Integer.parseInt((String) houseNumberBox.getSelectedItem()), street);

        if(sandwichBox.getSelectedItem().equals("Cheese Steak")){
            sandwich = new sandwichCheeseSteak();
        }
        else if(sandwichBox.getSelectedItem().equals("Bagel")){
            sandwich = new sandwichBagel();
        }
        else if(sandwichBox.getSelectedItem().equals("Hamburger")){
            sandwich = new sandwichHamburger();
        }

        if(toppingBox.getSelectedItem().equals("Cheese")){
            sandwich = new condimentCheese(sandwich);
        }
        else if(toppingBox.getSelectedItem().equals("Lettuce")){
            sandwich = new condimentLettuce(sandwich);
        }
        else if(toppingBox.getSelectedItem().equals("Tomato")){
            sandwich = new condimentTomato(sandwich);
        }
        else if(toppingBox.getSelectedItem().equals("Bacon")){
            sandwich = new condimentBacon(sandwich);
        }
        else if(toppingBox.getSelectedItem().equals("Sriracha")){
            sandwich = new condimentSriracha(sandwich);
        }
        else if(toppingBox.getSelectedItem().equals("Mayo")){
            sandwich = new condimentMayo(sandwich);
        }

        LocalTime time = LocalTime.of(Integer.parseInt((String) hourBox.getSelectedItem()),
                Integer.parseInt((String) minuteBox.getSelectedItem()), 0);

        return new Order(address, time, sandwich);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
