package commands;

import model.Cell;
import model.CellValue;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test action methods
 *
 * @author: Ruud Andriessen, 0770663
 * @date: Sunday February 24
 */
public class CommandTest {

    /**
     * The instance that is being tested
     */
    private Command instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for the constructor">   
    /**
     * Test creating an action with a type == null and cell == null
     */
    @Test
    public void createActionNull() {
        System.out.println("createCellNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new Command(null, null, null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test creating an action with a cell == null
     */
    @Test
    public void createActionOneNull() {
        System.out.println("createCellOneNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new Command(CommandType.REPLACEVALUE, null, null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test creating an action with valid parameters
     */
    @Test
    public void createActionValid() {
        System.out.println("createActionValid");
        try {
            instance = new Command(
                    CommandType.REPLACEVALUE,
                    new Cell(false, CellValue.EMPTY),
                    CellValue.ONE);
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
        if (instance == null) {
            fail("Action not initialized");
        }
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Test cases for undo">
    /**
     * Test undoing an action without executing it first (state should not
     * change)
     */
    @Test
    public void undoCommand() {
        System.out.println("undoCommand");
        instance = new Command(
                CommandType.REPLACEVALUE,
                new Cell(false, CellValue.EMPTY),
                CellValue.ONE);
        instance.undo();
        if (instance.getCell().getValue() != CellValue.EMPTY) {
            fail("Action not properly undone");
        }
    }

    /**
     * Test undoing an Command after executing it
     */
    @Test
    public void undoCommandAfterExecute() {
        System.out.println("undoCommandAfterExecute");
        instance = new Command(
                CommandType.REPLACEVALUE,
                new Cell(false, CellValue.EMPTY),
                CellValue.ONE);
        instance.execute();
        instance.undo();
        if (instance.getCell().getValue() != CellValue.EMPTY) {
            fail("Action not properly undone");
        }
    }

    /**
     * Test undoing an edit mode action without executing it first (state should
     * not change)
     */
    @Test
    public void executeCommandEditMode() {
        System.out.println("executeCommandEditMode");
        instance = new Command(
                CommandType.CHANGELOCKED,
                new Cell(false, CellValue.EMPTY),
                true);
        instance.execute();
        if (!instance.getCell().getLocked()) {
            fail("Command not properly executed");
        }
    }

    /**
     * Test undoing an edit mode action without executing it first (state should
     * not change)
     */
    @Test
    public void undoCommandEditMode() {
        System.out.println("undoCommandEditMode");
        instance = new Command(
                CommandType.CHANGELOCKED,
                new Cell(false, CellValue.EMPTY),
                true);
        instance.undo();
        if (instance.getCell().getLocked()) {
            fail("Command not properly undone");
        }
    }

    /**
     * Test undoing an edit mode action after executing it
     */
    @Test
    public void undoCommandAfterExecuteEditMode() {
        System.out.println("undoCommandAfterExecuteEditMode");
        instance = new Command(
                CommandType.CHANGELOCKED,
                new Cell(false, CellValue.EMPTY),
                true);
        instance.execute();
        instance.undo();
        if (instance.getCell().getLocked()) {
            fail("Command not properly executed and/or undone");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Test cases for execute">
    /**
     * Test executing an action
     */
    @Test
    public void executeAction() {
        System.out.println("executeAction");
        instance = new Command(
                CommandType.REPLACEVALUE,
                new Cell(false, CellValue.EMPTY),
                CellValue.ONE);
        instance.execute();
        if (instance.getCell().getValue() != CellValue.ONE) {
            fail("Action not properly executed");
        }
    }
    //</editor-fold>
}
