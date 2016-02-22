package solvers;

import commands.Command;
import commands.CommandCellReplace;
import model.Cell;
import model.CellValue;
import model.Grid;

/**
 * A concrete strategy of the Strategy interface, this strategy contains the
 * ability to apply a contradiction strategy to a given cell
 *
 * @author Ruud Andriessen, 0770663
 */
public class ContradictionStrategy implements Strategy {

    /**
     * Applies a contradiciton strategy to a given cell
     *
     * @param cell The cell to apply the strategy to
     * @param grid The group for the cell to be checked against
     * @pre grid.contains(cell) && cell != null && grid != null
     * @return The command to be executed afterwards or null if none
     */
    @Override
    public Command apply(Cell cell, Grid grid) {
        CommandCellReplace cZero = new CommandCellReplace(cell, CellValue.ZERO);
        cZero.execute();
        if (grid.hasViolation()) {
            cZero.undo();
            return new CommandCellReplace(cell, CellValue.ONE);
        }
        cZero.undo();
        CommandCellReplace cOne = new CommandCellReplace(cell, CellValue.ONE);
        cOne.execute();
        if (grid.hasViolation()) {
            cOne.undo();
            return new CommandCellReplace(cell, CellValue.ZERO);
        }
        cOne.undo();
        return null;
    }
}
