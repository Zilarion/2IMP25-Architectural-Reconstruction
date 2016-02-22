/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import model.Cell;
import model.CellValue;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ruud Andriessen, 0770663
 */
public class CompoundCommandTest {

    /**
     * The instance that is being tested
     */
    private CompoundCommand instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Tests for the constructor">
    /**
     * Test the constructor of the CompoundCommand
     */
    @Test
    public void testConstructList() {
        System.out.println("testConstructList");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            if (instance == null || !instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the Add method">
    /**
     * Test adding a null action to an CompoundCommand
     */
    @Test
    public void testNullAdd() {
        System.out.println("testNullAdd");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            instance.add(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test adding a valid action to the CompoundCommand
     */
    @Test
    public void testValidActionAdd() {
        System.out.println("testValidActionAdd");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            instance.add(new Command(
                    CommandType.REPLACEVALUE,
                    new Cell(false, CellValue.EMPTY),
                    CellValue.ONE));
            if (instance == null || instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the Clear method">
    /**
     * Test clear an CompoundCommand
     */
    @Test
    public void testClear() {
        System.out.println("testClear");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            instance.add(new Command(
                    CommandType.REPLACEVALUE,
                    new Cell(false, CellValue.EMPTY),
                    CellValue.ONE));
            instance.clear();
            if (instance == null || !instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the execute methods">
    /**
     * Test executing a single action in the CompoundCommand
     */
    @Test
    public void testSingleExecute() {
        System.out.println("testSingleExecute");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c = new Cell(false, CellValue.EMPTY);
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c, CellValue.ONE));
            instance.execute();
            if (instance == null || instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            } else if (c.getValue() != CellValue.ONE) {
                fail("Action not correctly executed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test executing several actions in the CompoundCommand
     */
    @Test
    public void testSeveralExecute() {
        System.out.println("testSeveralExecute");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c1 = new Cell(false, CellValue.EMPTY);
            Cell c2 = new Cell(false, CellValue.ONE);
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c1, CellValue.ONE));
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c2, CellValue.EMPTY));
            instance.execute();
            if (instance == null || instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            } else if (c1.getValue() != CellValue.ONE
                    || c2.getValue() != CellValue.EMPTY) {
                fail("Action not correctly executed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the undo method">
    /**
     * Test executing a single undo in the CompoundCommand
     */
    @Test
    public void testSingleUndo() {
        System.out.println("testSingleUndo");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c = new Cell(false, CellValue.EMPTY);
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c, CellValue.ONE));
            instance.execute();
            instance.undo();
            if (instance == null || instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            } else if (c.getValue() != CellValue.EMPTY) {
                fail("Action not correctly undone");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test undoing several actions in the CompoundCommand
     */
    @Test
    public void testSeveralUndo() {
        System.out.println("testSeveralUndo");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c1 = new Cell(false, CellValue.EMPTY);
            Cell c2 = new Cell(false, CellValue.ONE);
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c1, CellValue.ONE));
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c2, CellValue.EMPTY));
            instance.execute();
            instance.undo();
            if (instance == null || instance.isEmpty()) {
                fail("ActionList not correctly constructed");
            } else if (c1.getValue() != CellValue.EMPTY
                    || c2.getValue() != CellValue.ONE) {
                fail("Action not correctly executed");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the get method">
    /**
     * Test getting a negative number
     */
    @Test
    public void testNegativeGet() {
        System.out.println("testNegativeGet");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            instance.get(-1);
            fail("No exception thrown");
        } catch (IndexOutOfBoundsException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test getting an index out of bound
     */
    @Test
    public void testOutOfBoundsGet() {
        System.out.println("testOutOfBoundsGet");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c = new Cell(false, CellValue.EMPTY);
            instance.add(new Command(CommandType.REPLACEVALUE,
                    c, CellValue.ONE));
            instance.get(1);
            fail("No exception thrown");
        } catch (IndexOutOfBoundsException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test getting a valid index
     */
    @Test
    public void testValidGet() {
        System.out.println("testValidGet");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new CompoundCommand();
            Cell c = new Cell(false, CellValue.EMPTY);
            Command a = new Command(CommandType.REPLACEVALUE,
                    c, CellValue.ONE);
            instance.add(a);
            if (!instance.get(0).equals(a)) {
                fail("Not correctly get");

            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
