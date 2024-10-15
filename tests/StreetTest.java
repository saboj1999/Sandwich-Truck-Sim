import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {

    @Test
    public void testCreateStreetAndGetValuesBack() {

        Street s = new Street("A", StreetDirection.NORTHSOUTH, 1);

        assertEquals("A", s.getName());
        assertEquals(StreetDirection.NORTHSOUTH, s.getDirection());
        assertEquals(1, s.getNumber());

    }

}