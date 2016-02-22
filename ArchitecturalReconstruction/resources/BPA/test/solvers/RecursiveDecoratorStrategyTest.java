/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;

import commands.Command;
import commands.CompoundCommand;
import java.util.ArrayList;
import java.util.Scanner;
import model.CellValue;
import model.Grid;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ruud
 */
public class RecursiveDecoratorStrategyTest {

    /**
     * The instance that is being tested
     */
    private RecursiveDecoratorStrategy instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Test cases for apply">
    /**
     * Test resolving a resolvable cell with a decorator strategy
     */
    @Test
    public void testResolveRecursiveDecoratorValid1() {
        System.out.println("testResolveRecursiveDecoratorValid1");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "1 1 . . \n"
                    + ". . . . \n"));
            instance = new RecursiveDecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            CompoundCommand a = instance.find(grid, s);
            a.execute();
            if (!grid.isCompleted()) {
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
    public void testResolveRecursiveDecoratorValid2() {
        System.out.println("testResolveRecursiveDecoratorValid2");
        exception.expect(IllegalArgumentException.class);
        try {
            Grid grid = new Grid();
            grid.loadPuzzle(new Scanner(
                    "0 . 0 . . . \n"
                    + ". . . . . . \n"));
            instance = new RecursiveDecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            CompoundCommand a = instance.find(grid, s);
            a.execute();
            if (grid.getCell(1, 0).getValue() != CellValue.ONE ||
                    grid.getCell(0, 1).getValue() != CellValue.ONE ||
                    grid.getCell(1, 1).getValue() != CellValue.ZERO ||
                    grid.getCell(2, 1).getValue() != CellValue.ONE) {
                fail("Not correctly resolved result");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }

    /**
     * Test resolving an unresolvable line group
     */
    @Test
    public void testResolveRecursiveDecoratorInvalid() {
        System.out.println("testResolveRecursiveDecoratorInvalid");
        exception.expect(IllegalArgumentException.class);
        Grid grid = new Grid();
        try {
            grid.loadPuzzle(new Scanner(
                    ". . . . \n"
                    + ". . . . \n"));
            instance = new RecursiveDecoratorStrategy();
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            s.add(new ContradictionStrategy());
            s.add(new LineStrategy());
            s.add(new TripletStrategy());
            CompoundCommand a = instance.find(grid, s);
            if (!a.isEmpty()) {
                fail("Not correctly resolved");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
