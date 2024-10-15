public class sandwichBagel extends Sandwich {

    /**
     * Sets the description of the concrete type on initiation
     */
    public sandwichBagel() {
        description = "Bagel";
    }

    /**
     * Sets & returns the cost of the concrete type with no condiments
     * @return the cost of the sandwich, presumably in dollars
     */
    @Override
    public double cost() {
        return 5.50;
    }

    /**
     * Returns the description of the concrete type with no condiments
     * @return the String description of the sandwich
     */
    @Override
    public String toString() {
        return "Bagel";
    }

    /**
     * Returns the current sandwich type with no additional condiments
     * @return null
     */
    @Override
    public Sandwich getSandwich() {
        return null;
    }
}
