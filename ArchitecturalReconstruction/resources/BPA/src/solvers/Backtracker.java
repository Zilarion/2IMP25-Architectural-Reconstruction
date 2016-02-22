package solvers;

import commands.Command;
import commands.CommandCellReplace;
import commands.CompoundCommand;
import java.util.ArrayList;
import model.Cell;
import model.CellValue;
import model.Grid;

/**
 * The backtracker class implements a backtracker which solves a given grid
 *
 * @author Ruud Andriessen, 0770663
 */
public class Backtracker {

    /**
     * The flag whether the worker should abort
     */
    private boolean abort;
    /**
     * The flag whether a solution is found
     */
    private boolean foundSolution;
    /**
     * Whether the backtracker should stop if only one solution is found
     */
    private boolean breakAtSolutionFound;
    /**
     * The grid that is used by the backtracker
     */
    private Grid grid;
    /**
     * The list of all solutions
     */
    private ArrayList<String> solutions;

    private BacktrackListener listener;
    /**
     * Default constructor for the backtracker
     */
    public Backtracker() {
        // Initialize
        abort = false;
        solutions = new ArrayList<String>();
        foundSolution = false;
        listener = null;
    }

    /**
     * Whether the backtracker found a solution already
     *
     * @return Whether a solution was found already
     */
    public boolean foundSolution() {
        return foundSolution;
    }

    /**
     * Requests the calculation to be aborted (at a convenient moment).
     */
    public void abort() {
        this.abort = true;
    }

    /**
     * Applies strategies on the puzzle
     *
     * @return {@code true} if a solution is found, {@code false} otherwise.
     */
    private boolean applyStrategies() {
        RecursiveDecoratorStrategy rds = new RecursiveDecoratorStrategy();
        ArrayList<Strategy> s = new ArrayList<Strategy>();
        s.add(new ContradictionStrategy());
        s.add(new LineStrategy());
        s.add(new TripletStrategy());        
        CompoundCommand actions = rds.find(grid, s);
        if (!actions.isEmpty()) {
            // Apply the action
            if (applyBackTrackAction(actions)) {
                // SOLUTION! done!
                grid.undo();
                return true;
            } else {
                // No solution, keep trying
                boolean res = findSolutions();
                grid.undo();
                return res;
            }
        }
        return false;
    }

    /**
     * Try to recursively find solutions to the puzzle
     *
     * @return {@code true} if a puzzle is found || abort
     */
    private boolean findSolutions() {
        if (breakAtSolutionFound && foundSolution || abort) {
            return true;
        }
        if (!applyStrategies()) {
            if (bruteForce()) {
                return true;
            }
        }
        return true;
    }

    /**
     * Try to find solutions using brute force
     *
     * @return {@code true} if a solution is found.
     */
    private boolean bruteForce() {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                Cell c = grid.getCell(i, j);
                if (c.getValue() == CellValue.EMPTY) {
                    return fillBit(c);
                }
            }
        }
        return false;
    }

    /**
     * Fills in a bit in a cell, first 0 then 1.
     * @param c The cell to fill in
     * @return Whether a solution was found
     */
    private boolean fillBit(Cell c) {
        for (int fill = 0; fill < 2; fill++) {
            CommandCellReplace a = null;
            if (fill == 0) {
                a = new CommandCellReplace(c, CellValue.ZERO);
            } else if (fill == 1) {
                a = new CommandCellReplace(c, CellValue.ONE);
            }
            if (!applyBackTrackAction(a)) {
                // no solution, keep trying if there is no violation
                if (!grid.hasViolation()) {
                    findSolutions();
                }
            }
            grid.undo();
        }
        return false;
    }

    private boolean applyBackTrackAction(Command a) {
        // Apply the action
        grid.applyAction(a, true);
        if (grid.hasViolation()) {
            return false;
        } else if (grid.isCompleted()) {
            foundSolution = true;
            solutions.add(grid.toString());
            if (listener != null) {
                listener.solutionGenerated(grid.toString());
            }
            return true;
        }
        return false;
    }

    /**
     * Tries to solve the puzzle given to it
     *
     * @param puzzle The puzzle to solve
     * @param findOne If the backtracker should stop if a solution is found
     * @pre puzzle != null
     * @return The solved grid(s)
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public Grid solve(Grid puzzle, boolean findOne, 
            BacktrackListener listener) throws IllegalArgumentException {
        if (puzzle == null) {
            throw new IllegalArgumentException(
                    "Can not solve a puzzle which is null");
        }
        this.listener = listener;
        breakAtSolutionFound = findOne;
        // Set the puzzle to our current state
        grid = puzzle;

        // Find all the solutions
        findSolutions();
        return grid;
    }
}
