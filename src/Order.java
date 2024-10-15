import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

/**
 * Stores all the data about an order
 */
public class Order implements TruckObserver {
    private Address address;
    private LocalTime time;
    private Coordinate truckLocation;
    private Sandwich sandwich;

    /**
     * Instantiates a new order at a given address and time
     * @param a the address of the order
     * @param t the time of the order
     */
    public Order(Address a, LocalTime t, Sandwich s) {
        this.address = a;
        this.time = t;
        this.sandwich = s;
    }

    public Address getAddress() {
        return this.address;
    }

    public LocalTime getTime() {return this.time;}

    @Override
    public void update(Coordinate location) {
        truckLocation = location;
    }
    
    public Sandwich getSandwich() {return this.sandwich;}

    public static Order getDefaultLocation() {
        Street homeStreet = new Street("F Street", StreetDirection.NORTHSOUTH, 6);
        Address homeAddress = new Address(500, homeStreet);
        LocalTime time = LocalTime.NOON;
        return new Order(homeAddress, time, null);
    }
}
