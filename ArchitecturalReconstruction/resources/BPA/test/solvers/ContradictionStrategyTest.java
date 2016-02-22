/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;

import commands.Command;
import java.util.Scanner;
import model.CellValue;
import model.Grid;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ruud
 */
public class ContradictionStrategyTest {

    /**
     * The instance that is being tested
     */
    private ContradictionStrategy instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for apply">
    /**
     * Test applying a valid contradiction strategy
     */
    @Test
    public void testResolveContradictionValid1() {
        System.out.println("testResolveContradictionValid1");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "1 . . . \n"
                    + ". 1 . . \n"));
            instance = new ContradictionStrategy();
            Command a = instance.apply(grid.getCell(1, 0), grid);
            a.execute();
            if (grid.getCell(1, 0).getValue() != CellValue.ZERO) {
                fail("Not correctly resolved");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test applying a valid contradiction strategy
     */
    @Test
    public void testResolveContradictionValid2() {
        System.out.println("testResolveContradictionValid2");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "0 . . 0 \n"
                    + ". . . . \n"));
            instance = new ContradictionStrategy();
            Command a = instance.apply(grid.getCell(1, 0), grid);
            a.execute();
            if (grid.getCell(1, 0).getValue() != CellValue.ONE) {
                fail("Not correctly resolved");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test resolving an unresolvable line group
     */
    @Test
    public void testResolveLineInvalid() {
        System.out.println("testResolveLineInvalid");
        exception.expect(IllegalArgumentException.class);
        Grid grid = new Grid();
        try {
            grid.loadPuzzle(new Scanner(
                    ". . . . \n"
                    + ". . . . \n"));
            instance = new ContradictionStrategy();
            Command a = instance.apply(grid.getCell(1, 0), grid);
            if (a != null) {
                fail("Not correctly resolved");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
