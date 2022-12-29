import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Day15Test {
    @Test
    public void xcoordsAtSameYAsBeacon() {
        Day15.Sensor s = new Day15.Sensor(2,2,2,4);
        var xcoords = Day15.xCoordinatesInSensorRange(s, 4);
        assertEquals(1, xcoords.size());
        long xcoord=xcoords.get(0);
        assertEquals(2, xcoord);
    }
    @Test
    public void xcoords_Example_Offset0(){
        // sensor and beacon from problem description, y-offset=1
        var s = new Day15.Sensor(8,7, 2,10);
        var xcoords = Day15.xCoordinatesInSensorRange(s, 7);

        assertEquals(19,xcoords.size());
        assertTrue(xcoords.contains((long)-1));
        assertTrue(xcoords.contains((long)0));
        assertTrue(xcoords.contains((long)1));
        assertTrue(xcoords.contains((long)2));
        assertTrue(xcoords.contains((long)3));
        assertTrue(xcoords.contains((long)4));
        assertTrue(xcoords.contains((long)5));
        assertTrue(xcoords.contains((long)6));
        assertTrue(xcoords.contains((long)7));
        assertTrue(xcoords.contains((long)8));
        assertTrue(xcoords.contains((long)9));
        assertTrue(xcoords.contains((long)10));
        assertTrue(xcoords.contains((long)11));
        assertTrue(xcoords.contains((long)12));
        assertTrue(xcoords.contains((long)13));
        assertTrue(xcoords.contains((long)14));
        assertTrue(xcoords.contains((long)15));
        assertTrue(xcoords.contains((long)16));
        assertTrue(xcoords.contains((long)17));
    }
    @Test
    public void xcoords_Example_Offset1(){
        // sensor and beacon from problem description, y-offset=1
        var s = new Day15.Sensor(8,7, 2,10);
        var xcoords = Day15.xCoordinatesInSensorRange(s, 8);

        assertEquals(17,xcoords.size());
        assertTrue(xcoords.contains((long)0));
        assertTrue(xcoords.contains((long)1));
        assertTrue(xcoords.contains((long)2));
        assertTrue(xcoords.contains((long)3));
        assertTrue(xcoords.contains((long)4));
        assertTrue(xcoords.contains((long)5));
        assertTrue(xcoords.contains((long)6));
        assertTrue(xcoords.contains((long)7));
        assertTrue(xcoords.contains((long)8));
        assertTrue(xcoords.contains((long)9));
        assertTrue(xcoords.contains((long)10));
        assertTrue(xcoords.contains((long)11));
        assertTrue(xcoords.contains((long)12));
        assertTrue(xcoords.contains((long)13));
        assertTrue(xcoords.contains((long)14));
        assertTrue(xcoords.contains((long)15));
        assertTrue(xcoords.contains((long)16));
    }
}
