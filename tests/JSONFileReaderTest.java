import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JSONFileReaderTest {

    private String fileName = "./tests/randomOrderTest.json";

    @Test
    public void testCanCreateJSONFileReader() {

        JSONFileReader fileReader = new JSONFileReader(fileName);

    }

    @Test
    public void testOrderAddedIsReadProperly() throws Exception {

        JSONFileWriter fileWriter = new JSONFileWriter(fileName);

        Order testOrder = getOrder("4 Street", StreetDirection.EASTWEST, 4, 400, 12, 30, 27);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        fileWriter.allOrdersIntoJSONFile(orders);

        for (Order order : orders) {
            System.out.print(order.getAddress() + " - ");
            System.out.println(order.getSandwich());
        }

        JSONFileReader fileReader = new JSONFileReader(fileName);
        ArrayList<Order> readOrders = fileReader.readOrdersFromFile();
        Order readOrder = readOrders.get(0);
        assertOrderEquals(readOrder, "4 Street", StreetDirection.EASTWEST, 4, 400, 12, 30, 27, "Bagel, " +
                "Cheese, Lettuce, Tomato");

    }

    @Test
    public void testTwoOrdersAreReadProperly() throws Exception {

        JSONFileWriter fileWriter = new JSONFileWriter(fileName);

        Order testOrder1 = getOrder("J Street", StreetDirection.NORTHSOUTH, 10, 110, 5, 55, 7);
        Order testOrder2 = getOrder("1 Street", StreetDirection.EASTWEST, 1, 990, 17, 4, 59);

        ArrayList<Order> testOrders = new ArrayList<Order>();
        testOrders.add(testOrder1);
        testOrders.add(testOrder2);
        fileWriter.allOrdersIntoJSONFile(testOrders);

        JSONFileReader fileReader = new JSONFileReader(fileName);
        ArrayList<Order> orders = fileReader.readOrdersFromFile();

        Order order1 = orders.get(0);
        assertOrderEquals(order1, "J Street", StreetDirection.NORTHSOUTH, 10, 110, 5, 55, 7, "Bagel, Cheese, " +
                "Lettuce, Tomato");
        Order order2 = orders.get(1);
        assertOrderEquals(order2, "1 Street", StreetDirection.EASTWEST, 1, 990, 17, 4, 59, "Bagel, Cheese, Lettuce," +
                " Tomato");

    }


    private Order getOrder(String streetName, StreetDirection direction, int streetNum, int houseNum, int hour, int minute, int second) {
        Street testStreet = new Street(streetName, direction, streetNum);
        Address testAddress = new Address(houseNum, testStreet);
        Sandwich testSandwich = new sandwichBagel();
        testSandwich = new condimentCheese(testSandwich);
        testSandwich = new condimentLettuce(testSandwich);
        testSandwich = new condimentTomato(testSandwich);
        LocalTime testTime = LocalTime.of(hour, minute, second);
        return new Order(testAddress, testTime, testSandwich);
    }


    private void assertOrderEquals(Order order, String streetName, StreetDirection direction, int streetNum, int houseNum, int hour, int minute, int second, String sandwichDescription) {
        LocalTime time = order.getTime();
        assertEquals(hour, time.getHour());
        assertEquals(minute, time.getMinute());
        assertEquals(second, time.getSecond());

        Address address = order.getAddress();
        assertEquals(houseNum, address.getHouseNumber());

        Street street = address.getStreet();
        assertEquals(streetName, street.getName());
        assertEquals(direction, street.getDirection());
        assertEquals(streetNum, street.getNumber());

        Sandwich sandwich = order.getSandwich();
        assertEquals(sandwichDescription, sandwich.getDescription());
    }


}