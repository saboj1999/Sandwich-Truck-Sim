import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodCoordinateCalculatorTest {

    @Test
    public void testCreateNewCalculator() {

        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);

        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);

    }

    @Test
    public void testTopLeftEWIsCorrect() {

        int padding = 5;
        int northOffset = 5;
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, padding, 10, 10, 10, 5, northOffset);

        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);
        Address a = new Address(100, new Street("10", StreetDirection.EASTWEST, 10));

        Coordinate coordinates = calculator.getLocationCoordinate(a);
        assertEquals(padding, coordinates.getX());
        assertEquals(900 - northOffset, coordinates.getY());


    }

    @Test
    public void testHouseInMiddleEWIsCorrect() {
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);
        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);

        Street testStreet = new Street("5", StreetDirection.EASTWEST, 5);
        Address testAddress = new Address(460, testStreet);
        Coordinate coordinates = calculator.getLocationCoordinate(testAddress);
        assertEquals(365, coordinates.getX());
        assertEquals(395, coordinates.getY());

    }

    @Test
    public void testTopLeftNSIsCorrect() {
        int padding = 5;
        int eastOffset = 5;
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, padding, 10, 10, 10, eastOffset, 5);
        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);
        Address a = new Address(100, new Street("A", StreetDirection.NORTHSOUTH, 1));

        Coordinate coordinates = calculator.getLocationCoordinate(a);
        assertEquals(eastOffset, coordinates.getX());
        assertEquals(padding, coordinates.getY());
    }


    @Test
    public void testHouseInMiddleNSIsCorrect() {
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);

        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);

        Street testStreet = new Street("C", StreetDirection.NORTHSOUTH, 3);
        Address testAddress = new Address(720, testStreet);
        Coordinate coordinates = calculator.getLocationCoordinate(testAddress);
        assertEquals(205, coordinates.getX());
        assertEquals(625, coordinates.getY());

    }

    @Test
    public void testStreetIntersection() {
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);

        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);
        Street testStreet1 = new Street("D", StreetDirection.NORTHSOUTH, 4);
        Street testStreet2 = new Street("3", StreetDirection.EASTWEST, 3);

        Coordinate coordinate = calculator.getLocationCoordinate(new StreetIntersection(testStreet1, testStreet2, Direction.NORTH));

        assertEquals(285, coordinate.getX());
        assertEquals(190, coordinate.getY());

    }

    @Test
    public void testStreetIntersection2() {
        CoordinateGUIData coordinateGUIData = new CoordinateGUIData(900, 900, 5, 10, 10, 10, 5, 5);

        NeighborhoodCoordinateCalculator calculator = new NeighborhoodCoordinateCalculator(coordinateGUIData);

        Street testStreet1 = new Street("5", StreetDirection.EASTWEST, 5);
        Street testStreet2 = new Street("A", StreetDirection.NORTHSOUTH, 1);

        Coordinate coordinate = calculator.getLocationCoordinate(new StreetIntersection(testStreet1, testStreet2, Direction.NORTH));

        assertEquals(-15, coordinate.getX());
        assertEquals(390, coordinate.getY());

    }





}