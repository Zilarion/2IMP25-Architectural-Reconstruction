package model;

import commands.Command;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ruud Andriessen, 0770663
 */
public class LineGroupTest {

    /**
     * The instance that is being tested
     */
    private Group instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for the constructor">
    /**
     * Test creating a group with a type == null
     */
    @Test
    public void createLineGroupNull() {
        System.out.println("createLineGroupNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LineGroup(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test creating a group line with one cell null
     */
    @Test
    public void createGroupEmptyGroup() {
        System.out.println("createGroupEmptyGroup");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LineGroup(new ArrayList<Cell>());
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for isValid">    
    /**
     * Test validating a line group which are all empty
     */
    @Test
    public void testValidLineEmpty() {
        System.out.println("testValidLineEmpty");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.EMPTY));
        try {
            instance = new LineGroup(group);
            if (!instance.isValid()) {
                fail("Empty groups should always be valid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test validating a valid line group
     */
    @Test
    public void testValidLine() {
        System.out.println("testValidLine");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.ZERO));
        try {
            instance = new LineGroup(group);
            if (!instance.isValid()) {
                fail("Groups should be valid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test validating an invalid line group
     */
    @Test
    public void testInvalidLine() {
        System.out.println("testInvalidLine");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.ONE));
        try {
            instance = new LineGroup(group);
            if (instance.isValid()) {
                fail("Groups should be invalid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
