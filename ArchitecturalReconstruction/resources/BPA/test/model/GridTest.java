package model;

import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test grid methods
 *
 * @author: Ruud Andriessen, 0770663
 * @date: Sunday February 24
 */
public class GridTest {

    /**
     * The instance that is being tested
     */
    private Grid instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for loadPuzzle"> 
    /**
     * Test loading a puzzle with null
     */
    @Test
    public void loadPuzzleWithNull() {
        System.out.println("loadPuzzleWithNull");
        instance = new Grid();
        exception.expect(IllegalArgumentException.class);
        try {
            instance.loadPuzzle(null);
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertFalse(e.getMessage() == null);
        }
    }

    /**
     * Test loading a puzzle with a wrong invariant
     */
    @Test
    public void loadInvalidPuzzle() {
        System.out.println("loadInvalidPuzzle");
        instance = new Grid();
        exception.expect(IllegalArgumentException.class);
        try {
            instance.loadPuzzle(new Scanner(
                    ". . 1 0 \n"
                    + ". 1 . . \n"
                    + ". . 0 0 \n"));
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertFalse(e.getMessage() == null);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for getCell">   
    /**
     * Test getting a cell with negative coordinates
     */
    @Test
    public void GetCellNegativeOutOfBounds() {
        System.out.println("GetCellNegativeOutOfBounds");
        instance = new Grid();
        exception.expect(IllegalArgumentException.class);
        try {
            instance.getCell(-1, -1);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test getting a cell with one dimension out of bounds
     */
    @Test
    public void GetCellOneDimensionOfBounds() {
        System.out.println("GetCellOneDimensionOfBounds");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . 1 0 \n"
                + ". 1 . . \n"
                + ". . 0 1 \n"
                + ". 1 . 0* \n"));
        exception.expect(IllegalArgumentException.class);
        try {
            instance.getCell(4, 0);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test getting a cell with both dimensions out of bounds
     */
    @Test
    public void GetCellTwoDimensionsOfBounds() {
        System.out.println("GetCellTwoDimensionsOfBounds");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . 1 0 . . \n"
                + ". 1 . . 1 1* \n"
                + ". . 0 1 1 1\n"
                + ". 1 . 0* 0 0*\n"));
        exception.expect(IllegalArgumentException.class);
        try {
            instance.getCell(6, 4);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test getting a valid cell
     */
    @Test
    public void GetCellValid() {
        System.out.println("GetCellValid");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . 1 0 . . \n"
                + ". 1 . . 1 1* \n"
                + ". . 0 1 1 1\n"
                + ". 1 . 0* 0 0*\n"));
        try {
            Cell result = instance.getCell(3, 2);
            assertNotNull("The result may not be null", result);
        } catch (IllegalArgumentException e) {
            fail("No assertion should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for hasGroupViolation">
    /**
     * Try checking null for a group violation
     */
    @Test
    public void hasGroupViolationNull() {
        System.out.println("hasGroupViolationNull");
        instance = new Grid();
        exception.expect(IllegalArgumentException.class);
        try {
            instance.hasGroupViolation(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Try checking a new cell for a group violation
     */
    @Test
    public void hasGroupViolationNewCell() {
        System.out.println("hasGroupViolationNewCell");
        instance = new Grid();
        try {
            instance.loadPuzzle(new Scanner(
                    "0  .  .  1* \n"
                    + ".  1* .  0  \n"
                    + "0  0  .  .  \n"
                    + "1* 0  .  .  \n"));
            if (instance.hasGroupViolation(new Cell(false, CellValue.EMPTY))) {
                fail("Cell should not have violated a rule");
            }
        } catch (Exception e) {
            fail("No assertion should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for isCompleted">
    /**
     * Try checking a new cell for a group violation
     */
    @Test
    public void TestIsCompletedOnCompletedPuzzle() {
        System.out.println("TestIsCompletedOnCompletedPuzzle");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                "0  1*  0  1* \n"
                + "1  1* 0  0  \n"
                + "0  0  1  1  \n"
                + "1* 0  1  0  \n"));
        if (!instance.isCompleted()) {
            fail("Puzzle should have been completed");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for setCell">
    /**
     * Test setting a cell with negative coordinates
     */
    @Test
    public void setCellNegativeOutOfBounds() {
        System.out.println("setCellNegativeOutOfBounds");
        instance = new Grid();
        exception.expect(IllegalArgumentException.class);
        try {
            instance.setCell(-1, -1, CellValue.EMPTY);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test setting a cell with one dimension out of bounds
     */
    @Test
    public void setCellOneDimensionOfBounds() {
        System.out.println("setCellOneDimensionOfBounds");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . 1 0 \n"
                + ". 1 . . \n"
                + ". . 0 1 \n"
                + ". 1 . 0* \n"));
        exception.expect(IllegalArgumentException.class);
        try {
            instance.setCell(4, 0, CellValue.EMPTY);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test setting a cell with both dimensions out of bounds
     */
    @Test
    public void setCellTwoDimensionsOfBounds() {
        System.out.println("GetCellTwoDimensionsOfBounds");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . 1 0 . . \n"
                + ". 1 . . 1 1* \n"
                + ". . 0 1 1 1\n"
                + ". 1 . 0* 0 0*\n"));
        exception.expect(IllegalArgumentException.class);
        try {
            instance.setCell(6, 4, CellValue.EMPTY);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test setting a valid cell
     */
    @Test
    public void setCellValid() {
        System.out.println("GetCellValid");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . \n"
                + ". 1*\n"));
        try {
            instance.setCell(1, 1, CellValue.EMPTY);
            if (instance.getCell(1, 1).getValue() != CellValue.EMPTY) {
                fail("Cell value not correctly changed");
            }
        } catch (IllegalArgumentException e) {
            fail("No assertion should have been caught but caught: \n" + e);
        }
    }
    
     /**
     * Test setting a valid cell to null
     */
    @Test
    public void setCellNull() {
        System.out.println("GetCellValid");
        instance = new Grid();
        instance.loadPuzzle(new Scanner(
                ". . \n"
                + ". 1*\n"));
        try {
            instance.setCell(1, 1, null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Test cases for undo">   
    /** Test undoing while the there are no past actions */
    @Test
    public void emptyUndo() {
        System.out.println("emptyUndo");
        instance = new Grid();        
        exception.expect(IllegalStateException.class);
        try {
            instance.undo();
            fail("No exception thrown");
        } catch ( IllegalStateException e ) {   
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());    
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Test cases for redo">   
    /** Test undoing while the there are no past actions */
    @Test
    public void emptyRedo() {
        System.out.println("emptyRedo");
        instance = new Grid();        
        exception.expect(IllegalStateException.class);
        try {
            instance.redo();
            fail("No exception thrown");
        } catch ( IllegalStateException e ) {   
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());    
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for applyAction">
    @Test
    public void applyNullAction() {
        System.out.println("applyNullAction");
        instance = new Grid();        
        exception.expect(IllegalArgumentException.class);
        try {
            instance.applyAction(null, false);
            fail("No exception thrown");
        } catch ( IllegalArgumentException e ) {   
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());    
        }
    }
    //</editor-fold>
}
