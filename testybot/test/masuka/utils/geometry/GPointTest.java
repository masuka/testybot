/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package masuka.utils.geometry;

import masuka.robocode.utils.geometry.Gpoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tgergiya
 */
public class GPointTest {

    public GPointTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

     @Test
    public void testConstructor_double_double() {
        System.out.println("testConstructor_double_double");
        double x1 = -Math.sqrt(99);
        double y1 = Math.sqrt(37);
        Gpoint point = new Gpoint(x1, y1);

        assertEquals(point.getX(), x1, 0);
        assertEquals(point.getY(), y1, 0);
    }

    /**
     * Test of getDistance method, of class Gpoint.
     */
    @Test
    public void testGetDistance_GLine() {
        System.out.println("testGetDistance_GLine");
    }

    /**
     * Test of getDistance method, of class Gpoint.
     */
    @Test
    public void testGetDistance_GPoint() {
        System.out.println("testGetDistance_GPoint");
        double x1 = Math.sqrt(11);
        double y1 = Math.sqrt(7);
        double x2 = -y1;
        double y2 = x1;
        double expRes = 6;
        Gpoint p1 = new Gpoint(x1, y1);
        Gpoint p2 = new Gpoint(x2, y2);

        double res1 = p1.getDistance(p2);
        double res2 = p2.getDistance(p1);
        double res3 = p1.getDistance(p1);

        assertEquals(expRes, res1, 0);
        assertEquals(res1, res2, 0);
        assertEquals(res3, 0, 0);
    }

    /**
     * Test of getDistance method, of class Gpoint.
     */
    @Test
    public void testGetDistance_double_double() {
        System.out.println("testGetDistance_double_double");
        double x1 = Math.sqrt(12);
        double y1 = Math.sqrt(20);
        double x2 = -y1;
        double y2 = x1;
        double expRes = 8;
        Gpoint point = new Gpoint(x1, y1);

        double res1 = point.getDistance(x2, y2);
        double res2 = point.getDistance(x1, y1);

        assertEquals(expRes, res1, 0);
        assertEquals(res2, 0, 0);
    }

    /**
     * Test of getProection method, of class Gpoint.
     */
    @Test
    public void testGetProection() {
        System.out.println("testGetProection");
    }

    /**
     * Test of setXY method, of class Gpoint.
     */
    @Test
    public void testSetXY_GPoint() {
        System.out.println("setXY_GPoint");
        double expX = Math.sqrt(10 + 9*Math.random());
        double expY = Math.sqrt(20 + 9*Math.random());
        Gpoint p1 = new Gpoint(expX, expY);
        Gpoint p2 = new Gpoint(0, 0);
        p2.setXY(p1);
        double resX = p2.getX();
        double resY = p2.getY();
        assertEquals(expX, resX, 0);
        assertEquals(expY, resY, 0);
    }

    /**
     * Test of setXY method, of class Gpoint.
     */
    @Test
    public void testSetXY_double_double() {
        System.out.println("setXY_double_double");
        double expX = Math.sqrt(10 + 9*Math.random());
        double expY = Math.sqrt(20 + 9*Math.random());
        Gpoint point = new Gpoint(0, 0);
        point.setXY(expX, expY);
        double resX = point.getX();
        double resY = point.getY();
        assertEquals(expX, resX, 0);
        assertEquals(expY, resY, 0);
    }

    @Test
    public void testSetGetXY() {
        System.out.println("setGetXY");
        Gpoint point = new Gpoint(0, 0);
        double expX = Math.sqrt(10 + 9*Math.random());
        double expY = Math.sqrt(20 + 9*Math.random());
        point.setX(expX);
        point.setY(expY);
        double resX = point.getX();
        double resY = point.getY();
        assertEquals(expX, resX, 0);
        assertEquals(expY, resY, 0);
    }

}