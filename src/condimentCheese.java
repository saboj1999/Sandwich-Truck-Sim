public class condimentCheese extends SandwichDecorator {
    private Sandwich sandwich;

    /**
     * Initializes the sandwich object
     * @param sandwich the current sandwich object passed by the client
     */
    public condimentCheese(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    /**
     * Returns the true cost of the sandwich + condiment
     * @return the cost of the sandwich + that of the condiment
     */
    @Override
    public double cost() {
        return sandwich.cost() + 0.5;
    }

    /**
     * Returns the description of the condiment, to be used to display on the GUI
     * @return the formatted String for the GUI
     */
    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Cheese";
    }

    /**
     * Returns the name of the condiment, to be used to identify type of
     *  condiments available
     * @return the String name of the condiment formatted for the GUI
     */
    @Override
    public String getName() {
        return "Cheese";
    }

    /**
     * Returns the current sandwich Object
     * @return the wrapped sandwich object
     */
    public Sandwich getSandwich() {
        return sandwich;
    }
}
