import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A class that reads JSON files into a list of Order objects
 */
public class JSONFileReader {

    private final String fileName;

    /**
     * Creates a new JSONFileReader for the specified file
     *
     * @param fileName the path to the file to read
     */
    public JSONFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads a file and returns all orders stored as JSON
     * in that file
     * @return an ArrayList of Orders that are in the file
     */
    public ArrayList<Order> readOrdersFromFile() throws Exception {
        ArrayList<Order> orders = new ArrayList<>();

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(fileName));

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject orderJSON = (JSONObject) jsonArray.get(i);
            Order order = processOneJSONOrder(orderJSON);
            orders.add(order);
        }

        return orders;

    }

    /**
     * Process the JSON of a single order
     * @param orderJSON JSON for the order to process
     * @return the Order object represented by the JSON
     */
    private Order processOneJSONOrder(JSONObject orderJSON) {

        JSONObject addressJSON = (JSONObject) orderJSON.get("address");
        Address address = processAddressJSON(addressJSON);

        JSONObject timeJSON = (JSONObject) orderJSON.get("time");
        LocalTime time = processTimeJSON(timeJSON);

        JSONObject sandwichJSON = (JSONObject) orderJSON.get("sandwich");
        Sandwich sandwich = processSandwichJSON(sandwichJSON);

        return new Order(address, time, sandwich);
    }

    /**
     * Process the JSON of a single time
     * @param timeJSON JSON for the time to process
     * @return the LocalTime object represented by the JSON
     */
    private LocalTime processTimeJSON(JSONObject timeJSON) {

        int hour = ((Long) timeJSON.get("hour")).intValue();
        int minute = ((Long) timeJSON.get("minute")).intValue();
        int second = ((Long) timeJSON.get("second")).intValue();

        return LocalTime.of(hour, minute, second);

    }

    /**
     * Process the JSON of a single address
     * @param addressJSON JSON for the address to process
     * @return the Address object represented by the JSON
     */
    private Address processAddressJSON(JSONObject addressJSON) {

        int houseNumber = ((Long) addressJSON.get("houseNumber")).intValue();

        JSONObject streetJSON = (JSONObject) addressJSON.get("street");
        Street street = processStreetJSON(streetJSON);

        return new Address(houseNumber, street);


    }

    /**
     * Process the JSON of a single sandwich
     * @param sandwichJSON
     * @return Sandwich Type
     */
    private Sandwich processSandwichJSON(JSONObject sandwichJSON) {

        String description = (String) sandwichJSON.get("description");

        if (description.equals("Cheese")) {
            return new condimentCheese(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Bagel")) {
            return new sandwichBagel();
        } else if (description.equals("Hamburger")) {
            return new sandwichHamburger();
        } else if (description.equals("Tomato")) {
            return new condimentTomato(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Lettuce")) {
            return new condimentLettuce(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Cheese Steak")) {
            return new sandwichCheeseSteak();
        } else if (description.equals("Bacon")) {
            return new condimentBacon(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Egg")) {
            return new condimentEgg(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Mayo")) {
            return new condimentMayo(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        } else if (description.equals("Sriracha")) {
            return new condimentSriracha(processSandwichJSON((JSONObject) sandwichJSON.get("sandwich")));
        }

        System.out.println("Returning null for " + description);
        return null;
    }

    /**
     * Process the JSON of a single street
     * @param streetJSON JSON for the street to process
     * @return the Street object represented by the JSON
     */
    private Street processStreetJSON(JSONObject streetJSON) {

        String streetName = (String) streetJSON.get("name");
        String directionString = (String) streetJSON.get("direction");
        StreetDirection direction;
        if (directionString.equals("EASTWEST")) {
            direction = StreetDirection.EASTWEST;
        } else {
            direction = StreetDirection.NORTHSOUTH;
        }
        int streetNum = ((Long) streetJSON.get("streetNum")).intValue();

        return new Street(streetName, direction, streetNum);

    }
}
