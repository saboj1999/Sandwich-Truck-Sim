import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void testCreateNewAddressReturnsNumberAndStreet() {

        Address a = new Address(500, new Street("A", StreetDirection.NORTHSOUTH, 1));

        assertEquals(500, a.getHouseNumber());
        assertEquals("A", a.getStreet().getName());
        assertEquals(StreetDirection.NORTHSOUTH, a.getStreet().getDirection());
        assertEquals(1, a.getStreet().getNumber());

    }


}