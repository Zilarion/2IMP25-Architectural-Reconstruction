package model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test cell methods
 *
 * @author: Ruud Andriessen, 0770663
 * @date: Sunday February 24
 */
public class CellTest {

    /**
     * The instance that is being tested
     */
    private Cell instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for the constructor">   
    /**
     * Test creating a cell with a value == null
     */
    @Test
    public void createCellNull() {
        System.out.println("createCellNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new Cell(false, null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for setValue"> 
    /**
     * Test changing a cell value to null
     */
    @Test
    public void setValueNull() {
        System.out.println("setValueNull");
        instance = new Cell(false, CellValue.ZERO);
        exception.expect(IllegalArgumentException.class);
        try {
            instance.setValue(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test changing a locked cell its value
     */
    @Test
    public void setValueLockedCell() {
        System.out.println("setValueLockedCell");
        instance = new Cell(true, CellValue.ZERO);
        exception.expect(IllegalStateException.class);
        try {
            instance.setValue(CellValue.ONE);
            fail("No exception thrown");
        } catch (IllegalStateException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test changing a cell to a new value
     */
    @Test
    public void setValueNormalCell() {
        System.out.println("setValueNormalCell");
        instance = new Cell(false, CellValue.ZERO);
        try {
            instance.setValue(CellValue.ONE);
        } catch (IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
        // Make sure the value changed
        if (instance.getValue() != CellValue.ONE) {
            fail("Value was not changed");
        }
    }

    /**
     * Test changing a cell to the empty value
     */
    @Test
    public void setValueEmptyNormalCell() {
        System.out.println("setValueNormalCell");
        instance = new Cell(false, CellValue.ZERO);
        try {
            instance.setValue(CellValue.EMPTY);
        } catch (IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
        // Make sure the value changed
        if (instance.getValue() != CellValue.EMPTY) {
            fail("Value was not changed");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for rotateValue">
    /**
     * Test rotating the value from ZERO to ONE
     */
    @Test
    public void testRotateValueZERO() {
        System.out.println("testRotateValueZERO");
        instance = new Cell(false, CellValue.ZERO);
        try {
            instance.rotateValue();
        } catch (IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
        // Make sure the value changed
        if (instance.getValue() != CellValue.ONE) {
            fail("Value should be ONE but was: "+instance.getValue());
        }
    }

    /**
     * Test rotating the value from EMPTY to ZERO
     */
    @Test
    public void testRotateValueEMPTY() {
        System.out.println("testRotateValueEMPTY");
        instance = new Cell(false, CellValue.EMPTY);
        try {
            instance.rotateValue();
        } catch (IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
        // Make sure the value changed
        if (instance.getValue() != CellValue.ZERO) {
            fail("Value should be ONE but was: "+instance.getValue());
        }
    }
    
    /**
     * Test rotating a locked cell
     */
    @Test
    public void testRotateValueLocked() {
        System.out.println("testRotateValueLocked");
        instance = new Cell(true, CellValue.EMPTY);
        exception.expect(IllegalStateException.class);
        try {
            instance.rotateValue();
            fail("No exception thrown");
        } catch (IllegalStateException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    //</editor-fold>
}
