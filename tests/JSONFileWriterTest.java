import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JSONFileWriterTest {

    private String fileName = "./tests/randomOrderTest.json";

    @Test
    public void testOneJSONLineWorks() {

        JSONFileWriter mkFile = new JSONFileWriter(fileName);
        Street testStreet = new Street("A Street", StreetDirection.NORTHSOUTH, 1);
        Address testAddress = new Address(560, testStreet);

        Sandwich testSandwich = new sandwichBagel();
        testSandwich = new condimentCheese(testSandwich);
        testSandwich = new condimentLettuce(testSandwich);
        testSandwich = new condimentTomato(testSandwich);

        Sandwich anotherTestSandwich = new sandwichCheeseSteak();
        anotherTestSandwich = new condimentCheese(anotherTestSandwich);
        anotherTestSandwich = new condimentLettuce(anotherTestSandwich);
        anotherTestSandwich = new condimentTomato(anotherTestSandwich);

        Order testOrder = new Order(testAddress, LocalTime.of(9, 53, 26), testSandwich);
        Order anotherTestOrder = new Order(testAddress, LocalTime.of(9, 53, 26), anotherTestSandwich);

        assertEquals("{\"address\":{\"street\":{\"name\":\"A Street\",\"streetNum\":1,\"direction\":\"NORTHSOUTH\"},\"houseNumber\":560},\"sandwich\":{\"description\":\"Tomato\",\"sandwich\":{\"description\":\"Lettuce\",\"sandwich\":{\"description\":\"Cheese\",\"sandwich\":{\"description\":\"Bagel\"}}}},\"time\":{\"hour\":9,\"minute\":53,\"second\":26}}", mkFile.oneOrderIntoJSON(testOrder).toString());
        assertEquals("{\"address\":{\"street\":{\"name\":\"A Street\",\"streetNum\":1,\"direction\":\"NORTHSOUTH\"},\"houseNumber\":560},\"sandwich\":{\"description\":\"Tomato\",\"sandwich\":{\"description\":\"Lettuce\",\"sandwich\":{\"description\":\"Cheese\",\"sandwich\":{\"description\":\"Cheese Steak\"}}}},\"time\":{\"hour\":9,\"minute\":53,\"second\":26}}", mkFile.oneOrderIntoJSON(anotherTestOrder).toString());
    }


    @Test
    public void testAllOrdersIntoJSON() {
        JSONFileWriter mkFile = new JSONFileWriter(fileName);
        GenerateRandoms gr = new GenerateRandoms();
        ArrayList<Order> orders = gr.generateRandomOrderList();
        mkFile.allOrdersIntoJSONFile(orders);
    }


}