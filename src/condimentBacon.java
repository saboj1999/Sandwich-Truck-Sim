public class condimentBacon extends SandwichDecorator {

    Sandwich sandwich;

    /**
     * Initializes the sandwich object
     * @param sandwich the current sandwich object passed by the client
     */
    public condimentBacon(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    /**
     * Returns the true cost of the sandwich + condiment
     * @return the cost of the sandwich + that of the condiment
     */
    @Override
    public double cost() {
        return sandwich.cost() + 1.50;
    }

    /**
     * Returns the sandwich object
     * @return the wrapped sandwich object
     */
    @Override
    public Sandwich getSandwich() {
        return sandwich;
    }

    /**
     * Returns the name of the condiment, to be used to identify type of
     *  condiments available
     * @return the String name of the sandwich
     */
    @Override
    public String getName() {
        return "Bacon";
    }

    /**
     * Returns the description of the condiment, to be used to display on the GUI
     * @return the String name of the condiment formatted for the GUI
     */
    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Bacon";
    }
}
