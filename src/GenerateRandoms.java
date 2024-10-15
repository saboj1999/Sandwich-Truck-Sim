import java.time.LocalTime;
import java.util.*;

/**
 * A class that generates random values for data types
 */
public class GenerateRandoms {
    private final Random random;

    /**
     * Instantiates a new GenerateRandoms()
     */
    public GenerateRandoms() {
        random = new Random();
    }

    private LocalTime generateRandomTime() {

        int randomHour = random.nextInt(6) + 11;
        int randomMinute = random.nextInt(60);
        int randomSecond = random.nextInt(60);

        return LocalTime.of(randomHour, randomMinute, randomSecond);


    }


    /**
     * Generates a random address
     * @return an Address at random location
     */
    private Address generateRandomAddress() {

        int randomNumber = random.nextInt(90);
        int randomHouseNumber = (randomNumber + 10) * 10;

        return new Address(randomHouseNumber, this.generateRandomStreet());
    }

    /**
     * Generates a random Street
     * @return a random Street
     */
    private Street generateRandomStreet() {

        int generateRandomDirection = random.nextInt(2);

        if (generateRandomDirection == 0) {
            StreetDirection dir = StreetDirection.EASTWEST;
            int streetNameForEW = random.nextInt(10) + 1;
            return new Street(streetNameForEW + " Street", dir, streetNameForEW);

        } else {
            StreetDirection dir = StreetDirection.NORTHSOUTH;
            int streetNameForNS = random.nextInt(10) + 65;
            return new Street((char) streetNameForNS + " Street", dir, streetNameForNS - 64);
        }
    }


    /**
     * Generates a random sandwich
     * @return a random Sandwich
     */
    private Sandwich generateRandomSandwich() {

        Sandwich sandwich;

        ArrayList<String> concreteTypes = new ArrayList<>(
                Arrays.asList("Bagel", "Hamburger", "Cheese Steak")
        );

        ArrayList<String> availCondiments = new ArrayList<>(
                Arrays.asList("Cheese", "Lettuce", "Tomato", "Bacon",
                        "Egg", "Mayo", "Sriracha")
        );

        int randConcreteType = random.nextInt(concreteTypes.size());
        int numCondiments = random.nextInt(3);

        // Get a random concrete type
        if (Objects.equals(concreteTypes.get(randConcreteType), "Bagel"))
            sandwich = new sandwichBagel();

        else if(Objects.equals(concreteTypes.get(randConcreteType), "Hamburger"))
            sandwich = new sandwichHamburger();

        else
            sandwich = new sandwichCheeseSteak();

        // keeps track of randCondiments to avoid duplicates of condiments in a sandwich
        HashSet<Integer> tracker = new HashSet<>();

        for (int j = 0; j < numCondiments; j++) {
            int randCondiment = random.nextInt(availCondiments.size());

            while (tracker.add(randCondiment)) {

                if (Objects.equals(sandwich.getName(), "Cheese Steak")) {
                    if(Objects.equals(availCondiments.get(randCondiment), "Lettuce"))
                        sandwich = new condimentLettuce(sandwich);
                    if (Objects.equals(availCondiments.get(randCondiment), "Cheese"))
                        sandwich = new condimentCheese(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Mayo"))
                        sandwich = new condimentMayo(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Sriracha"))
                        sandwich = new condimentSriracha(sandwich);
                }

                if (Objects.equals(sandwich.getName(), "Bagel")) {
                    if (Objects.equals(availCondiments.get(randCondiment), "Cheese"))
                        sandwich = new condimentCheese(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Bacon"))
                        sandwich = new condimentBacon(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Sriracha"))
                        sandwich = new condimentSriracha(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Egg"))
                        sandwich = new condimentEgg(sandwich);
                }

                if (Objects.equals(sandwich.getName(), "Hamburger")) {
                    if (Objects.equals(availCondiments.get(randCondiment), "Cheese"))
                        sandwich = new condimentCheese(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Lettuce"))
                        sandwich = new condimentLettuce(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Tomato"))
                        sandwich = new condimentTomato(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Bacon"))
                        sandwich = new condimentBacon(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Sriracha"))
                        sandwich = new condimentSriracha(sandwich);
                    if(Objects.equals(availCondiments.get(randCondiment), "Mayo"))
                        sandwich = new condimentMayo(sandwich);
                }
            }
        }

        return sandwich;
    }

    /**
     * creates a list of random orders
     *
     * @return an ArrayList of random Orders
     */
    public ArrayList<Order> generateRandomOrderList() {
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            LocalTime randomTime = generateRandomTime();
            Sandwich randomSandwich = generateRandomSandwich();
            Address randomAddress = generateRandomAddress();
            Order nextOrder = new Order(randomAddress, randomTime, randomSandwich);
            orders.add(nextOrder);
        }

        return orders;
    }

}
