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
 * @author Ruud Andriessen, 0770663
 */
public class LineStrategyTest {

    /**
     * The instance that is being tested
     */
    private LineStrategy instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for apply">
    /**
     * Test resolving a resolvable line group
     */
    @Test
    public void testResolveLineValid1() {
        System.out.println("testResolveLineValid1");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "1 1 . . \n"
                    + ". . . . \n"));
            instance = new LineStrategy();
            Command a = instance.apply(grid.getCell(2, 0), grid);
            a.execute();
            if (grid.getCell(2, 0).getValue() != CellValue.ZERO) {
                fail("Not correctly resolved");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test resolving a resolvable line group
     */
    @Test
    public void testResolveLineValid2() {
        System.out.println("testResolveLineValid1");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "0 . . . \n"
                    + ". . . . \n"));
            instance = new LineStrategy();
            Command a = instance.apply(grid.getCell(0, 1), grid);
            a.execute();
            if (grid.getCell(0, 1).getValue() != CellValue.ONE) {
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
                    "0 . . . \n"
                    + ". . . . \n"));
            instance = new LineStrategy();
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
