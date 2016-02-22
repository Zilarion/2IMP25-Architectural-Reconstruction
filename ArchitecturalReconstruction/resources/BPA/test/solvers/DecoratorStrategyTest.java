/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;

import commands.Command;
import java.util.ArrayList;
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
public class DecoratorStrategyTest {

    /**
     * The instance that is being tested
     */
    private DecoratorStrategy instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for apply">
    /**
     * Test resolving a resolvable cell with a decorator strategy
     */
    @Test
    public void testResolveDecoratorValid1() {
        System.out.println("testResolveDecoratorValid1");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "1 1 . . \n"
                    + ". . . . \n"));
            instance = new DecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            Command a = instance.find(grid, s);
            a.execute();
            if (grid.getCell(0, 1).getValue() != CellValue.ZERO) {
                fail("Not correctly resolved");
            }
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test resolving a resolvable cell with a decorator strategy
     */
    @Test
    public void testResolveDecoratorValid2() {
        System.out.println("testResolveDecoratorValid2");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "0 . 0 . . .\n"
                    + ". . . . . .\n"
                    + ". . . . . .\n"
                    + ". . . . . .\n"));
            instance = new DecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            Command a = instance.find(grid, s);
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
    public void testResolveDecoratorInvalid() {
        System.out.println("testResolveDecoratorInvalid");
        exception.expect(IllegalArgumentException.class);
        Grid grid = new Grid();
        try {
            grid.loadPuzzle(new Scanner(
                    ". . . . \n"
                    + ". . . . \n"));
            instance = new DecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            Command a = instance.find(grid, s);
            if (a != null) {
                fail("Not correctly resolved");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
