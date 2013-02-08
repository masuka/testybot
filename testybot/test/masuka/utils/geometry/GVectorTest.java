/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package masuka.utils.geometry;

import masuka.robocode.utils.geometry.Gvector;
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
public class GVectorTest {

    public GVectorTest() {
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

    /**
     * Test of getLength method, of class Gvector.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        Gvector instance = null;
        double expResult = 0.0;
        double result = instance.getLength();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeading method, of class Gvector.
     */
    @Test
    public void testGetHeading() {
        System.out.println("getHeading");
        Gvector instance = null;
        double expResult = 0.0;
        double result = instance.getHeading();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class Gvector.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        double vx1 = Math.sqrt(7);
        double vy1 = -Math.sqrt(18);
        Gvector vec1 = new Gvector(vx1, vy1);
        vec1.normalize();
        double vx2 = vec1.getVx();
        double vy2 = vec1.getVy();

        Gvector vec2 = new Gvector(0, 0);
        vec2.normalize();

        assertEquals(vx1/5, vx2, 0.0);
        assertEquals(vy1/5, vy2, 0.0);
        assertEquals(vec2.getVx(), 0, 0);
        assertEquals(vec2.getVy(), 0, 0);
    }

    /**
     * Test of multiply method, of class Gvector.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        double m = 0.0;
        Gvector instance = null;
        instance.multiply(m);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Gvector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Gvector v = null;
        Gvector instance = null;
        instance.add(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLenght method, of class Gvector.
     */
    @Test
    public void testSetLenght() {
        System.out.println("setLenght");
        double l = 0.0;
        Gvector instance = null;
        instance.setLenght(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVx method, of class Gvector.
     */
    @Test
    public void testSetGetVxVy() {
        System.out.println("setVx");
        double expVx = -Math.sqrt(47);
        double expVy = Math.sqrt(99);
        Gvector vector = new Gvector(0, 0);
        vector.setVx(expVx);
        vector.setVy(expVy);
        double resVx = vector.getVx();
        double resVy = vector.getVy();

        assertEquals(expVx, resVx, 0.0);
        assertEquals(expVy, resVy, 0.0);
    }

}