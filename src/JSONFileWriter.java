import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A class that writes a list of orders into a json file
 */
public class JSONFileWriter {

    private String fileName;

    /**
     * Makes a JSONFileWriter with the default path
     * Sandwich/src/randomOrders.json
     */
    public JSONFileWriter() {
        fileName = "./src/randomOrders.json";
    }

    /**
     * Makes a JSONFileWriter that writes to a specified file
     * @param filePath the path to write to
     */
    public JSONFileWriter(String filePath) {
        this.fileName = filePath;
    }

    /**
     * Turns a list of Orders into a JSON object
     * and writes it into filePath
     * @param orders the orders to turn into JSON
     */
    public void allOrdersIntoJSONFile(ArrayList<Order> orders) {
        JSONArray allOrders = new JSONArray();
        for (Order order : orders) {
            JSONObject jsonString = oneOrderIntoJSON(order);
            allOrders.add(jsonString);
        }

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(allOrders.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Turns one Order into a JSON object
     * @param order the Order to convert to JSON
     * @return a String representing the JSON for the Order
     */
    public JSONObject oneOrderIntoJSON(Order order) {

        JSONObject orderJSON = new JSONObject();

        JSONObject addressJSON = oneAddressIntoJSON(order.getAddress());
        orderJSON.put("address", addressJSON);

        JSONObject timeJSON = oneTimeIntoJSON(order.getTime());
        orderJSON.put("time", timeJSON);

        JSONObject sandwichJSON = oneSandwichIntoJSON(order.getSandwich());
        orderJSON.put("sandwich", sandwichJSON);

        return orderJSON;
    }


    /**
     * Turns one Address into a JSON object
     * @param address the Address to convert to JSON
     * @return the JSONObject representing the Address
     */
    private JSONObject oneAddressIntoJSON(Address address) {

        JSONObject addressJSON = new JSONObject();

        addressJSON.put("houseNumber", address.getHouseNumber());

        JSONObject streetJSON = oneStreetIntoJSON(address.getStreet());
        addressJSON.put("street", streetJSON);

        return addressJSON;

    }

    /**
     * Turns one Street into a JSONObject
     * @param street the Street to convert to JSON
     * @return the JSONObject representing the Street
     */
    private JSONObject oneStreetIntoJSON(Street street) {

        JSONObject streetJSON = new JSONObject();
        streetJSON.put("name", street.getName());
        streetJSON.put("direction", street.getDirection().toString());
        streetJSON.put("streetNum", street.getNumber());

        return streetJSON;

    }

    private JSONObject oneSandwichIntoJSON(Sandwich sandwich) {

        JSONObject sandwichJSON = new JSONObject();
        if (sandwich.getSandwich() != null) {
            sandwichJSON.put("sandwich", oneSandwichIntoJSON(sandwich.getSandwich()));
        }
        sandwichJSON.put("description", sandwich.getName());
        //sandwichJSON.put("cost", sandwich.cost());

        return sandwichJSON;
    }

    /**
     * Turns one LocalTime into a JSONObject
     * @param time the LocalTime to convert to JSON
     * @return the JSONObject representing the LocalTime
     */
    private JSONObject oneTimeIntoJSON(LocalTime time) {
        JSONObject timeJSON = new JSONObject();

        timeJSON.put("hour", time.getHour());
        timeJSON.put("minute", time.getMinute());
        timeJSON.put("second", time.getSecond());

        return timeJSON;

    }

}
