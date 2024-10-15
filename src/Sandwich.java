public abstract class Sandwich {
    String description;

    public String getDescription() {
        return description;
    }

    public abstract double cost();

    @Override
    public String toString() {
        return "Sandwich{" +
                "description='" + description + '\'' +
                '}';
    }

    public abstract Sandwich getSandwich();

    public String getName() {
        return description;
    }
}
