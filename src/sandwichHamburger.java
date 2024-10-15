public class sandwichHamburger extends Sandwich{

    /**
     * Sets the description of the concrete type on initiation
     */
    public sandwichHamburger() {
        description = "Hamburger";
    }

    /**
     * Sets & returns the cost of the concrete type with no condiments
     * @return the cost of the sandwich, presumably in dollars
     */
    @Override
    public double cost() {
        return 4.00;
    }

    /**
     * Returns the description of the concrete type with no condiments
     * @return the String of the description
     */
    @Override
    public String toString() {
        return "Hamburger";
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
