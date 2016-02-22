package solvers;

import commands.Command;
import model.Cell;
import model.Grid;

/**
 * The strategy interface, it allows for a cell to be applied a certain strategy
 * which will then give back all the actions required to apply said strategy.
 *
 * @author Ruud Andriessen, 0770663
 */
public interface Strategy {

    /**
     * Applies a certain strategy to a cell, with relation to the group
     *
     * @param cell The cell to check the strategy with
     * @param grid The grid which the cell is in
     * @pre grid.contains(cell) && cell != null && grid != null
     * @return The command to be executed afterwards or null if none
     */
    public Command apply(Cell cell, Grid grid);
}
