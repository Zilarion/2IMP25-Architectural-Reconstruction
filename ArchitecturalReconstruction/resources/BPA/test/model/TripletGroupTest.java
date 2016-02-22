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
public class TripletGroupTest {

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
     * Test creating a group with a group && groupType == null
     */
    @Test
    public void createTripletGroupNull() {
        System.out.println("createGroupNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new TripletGroup(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test creating a group with triplet of size 2
     */
    @Test
    public void createTripletSize2Group() {
        System.out.println("createTripletSize2Group");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.EMPTY));
        try {
            instance = new TripletGroup(group);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test creating a triplet group with one cell == null
     */
    @Test
    public void createTripletOneNull() {
        System.out.println("createTripletOneNull");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(null);
        try {
            instance = new TripletGroup(group);
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
     * Test validating a triplet group which are all empty
     */
    @Test
    public void testValidTripletEmpty() {
        System.out.println("testValidTripletEmpty");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.EMPTY));
        group.add(new Cell(false, CellValue.EMPTY));
        try {
            instance = new TripletGroup(group);
            if (!instance.isValid()) {
                fail("Empty groups should always be valid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test validating a valid triplet group
     */
    @Test
    public void testValidTriplet() {
        System.out.println("testValidTriplet");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.ZERO));
        group.add(new Cell(false, CellValue.ONE));
        try {
            instance = new TripletGroup(group);
            if (!instance.isValid()) {
                fail("Groups should be valid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test validating an invalid triplet group
     */
    @Test
    public void testInvalidTriplet() {
        System.out.println("testInvalidTriplet");
        exception.expect(IllegalArgumentException.class);
        ArrayList<Cell> group = new ArrayList<Cell>();
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.ONE));
        group.add(new Cell(false, CellValue.ONE));
        try {
            instance = new TripletGroup(group);
            if (instance.isValid()) {
                fail("Groups should be invalid");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>       
}
