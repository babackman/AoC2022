import org.junit.Test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class Day18Test {
    @Test
    public void Area_SingleCube(){
        var coords = Arrays.asList(new Coordinate[]{C(1,1,1)});
        int expectedArea = 6;
        int area = Day18.calculateSurfaceArea(coords, true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_Rod211(){
        var coords = Arrays.asList(new Coordinate[]{C(1,1,1),C(2,1,1)});
        int expectedArea = (2*4) // edges
                        + (2*1); // ends
        int area = Day18.calculateSurfaceArea(coords, true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_Plate212PlusBump(){
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,1),

            C(1,2,2), C(2,2,2),
            C(1,2,1), C(2,2,1)
        });

        int expectedArea = 4 // top
            + 4 // bottom
            + (4*2) // plate edges
            + 4; // bump edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_Cup323Up(){
        // upward facing "cup" w/ 3x3 base, 2 high
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2),                  C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2), C(2,2,2), C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),
        });
        int expectedArea = 9 // top
            + 9 // bottom
            + (4*2*3) // outer edges
            + 4; // inner edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_cube3_xtunnel(){
        //3x3 cube with tunnel parallel to x axis
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            //C(1,2,2), C(2,2,2), C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });

        int expectedArea = 9 * 4 // 4 unbroken faces
            + (8 * 2) // 2 faces with holes
            + 4 * 3; // inner edges of tunnel
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_hollowCube3(){
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2),/*C(2,2,2),*/ C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 unbroken faces
            + 6; // interior void
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_cup333up(){
        // 3x3x3 cup open at top
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2),/*C(2,1,2),*/ C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2),/*C(2,2,2),*/ C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }
    @Test
    public void Area_cup333down(){
        // 3x3x3 cup open at bottom
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2),/*C(2,2,2),*/ C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2),/*C(2,3,2),*/ C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_cup333left(){
        // 3x3x3 cup open at left
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            /*C(1,2,2),C(2,2,2),*/              C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }
    @Test
    public void Area_cup333right(){
        // 3x3x3 cup open at left
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2),/*C(2,2,2),              C(3,2,2),*/
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test
    public void Area_cup333front(){
        // 3x3x3 cup open at front
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3), C(2,2,3), C(3,2,3),
            C(1,2,2),/*C(2,2,2),*/              C(3,2,2),
            C(1,2,1), /*C(2,2,1),*/ C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords, true);
        assertEquals(expectedArea, area);
    }
    @Test
    public void Area_cup333back(){
        // 3x3x3 cup open at back
        var coords = Arrays.asList(new Coordinate[]{
            C(1,1,3), C(2,1,3), C(3,1,3),
            C(1,1,2), C(2,1,2), C(3,1,2),
            C(1,1,1), C(2,1,1), C(3,1,1),

            C(1,2,3),/*C(2,2,3),*/      C(3,2,3),
            C(1,2,2),/*C(2,2,2),*/      C(3,2,2),
            C(1,2,1), C(2,2,1), C(3,2,1),

            C(1,3,3), C(2,3,3), C(3,3,3),
            C(1,3,2), C(2,3,2), C(3,3,2),
            C(1,3,1), C(2,3,1), C(3,3,1),
        });
        int expectedArea = 9 * 6 // 6 faces
                        + (4 * 2 ); // inside edges
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);
    }

    @Test 
    public void Area_18test_txt(){
        // example from problem description, including surface of interior void
        var coords = Arrays.asList(new Coordinate[]{
            /*1,1,6 */   /*2,1,6*/        /*3,1,6 */  
            /*1,1,5*/   C(2,1,5),  /*3,1,5 */  // 5 exposed sides
            /*1,1,4 */   /*2,1,4*/        /*3,1,4 */  
            /*1,1,3 */   /*2,1,3*/        /*3,1,3 */
            /*1,1,2 */   C(2,1,2), /*3,1,2 */ // 5 exposed sides
            /*1,1,1 */   /*2,1,1*/        /*3,1,1 */

            /*1,2,6*/   C(2,2,6),  /*3,2,6 */  // 5 exposed sides
            C(1,2,5), /*2,2,5*/  C(3,2,5),  //5+6+5=16 exposed sides
            /*1,2,4 */   C(2,2,4), /*3,2,4 */  // 4 exposed sides
            /*1,2,3 */   C(2,2,3), /*3,2,3 */  // 4 exposed sides
            C(1,2,2), C(2,2,2),C(3,2,2), // 5+0+5=10 exposed sides
            /*1,2,1 */   C(2,2,1),  /*3,2,1 */ // 5 exposed sides

            /*1,3,6 */   /*2,3,6*/        /*3,3,6 */  
            /*1,3,5*/    C(2,3,5), /*3,3,5 */  // 5 exposed sides
            /*1,3,4 */   /*2,3,4*/        /*3,3,4 */  
            /*1,3,3 */   /*2,3,3*/        /*3,3,3 */
            /*1,3,2 */   C(2,3,2), /*3,3,2 */ // 5 exposed sides
            /*1,3,1 */   /*2,3,1*/        /*3,3,1 */
        });
        int expectedArea = 5+5+5+16+4+4+10+5+5+5;
        int area = Day18.calculateSurfaceArea(coords,true);
        assertEquals(expectedArea, area);

    }

    private Coordinate C(int x, int y, int z){
        return new Coordinate(x,y,z);
    }
}
