package solvers;

import commands.Command;
import commands.CommandCellReplace;
import model.Cell;
import model.CellValue;
import model.Grid;
import model.Group;
import model.LineGroup;

/**
 * A concrete strategy of the Strategy interface, this strategy contains the
 * ability to apply a line strategy to a given cell
 *
 * @author Ruud Andriessen, 0770663
 */
public class LineStrategy implements Strategy {

    /**
     * Applies a line strategy to a given cell
     *
     * @param cell The cell to check the strategy with
     * @param grid The grid which the cell is in
     * @pre grid.contains(cell) && cell != null && grid != null
     * @return The command to be executed afterwards or null if none
     */
    @Override
    public Command apply(Cell cell, Grid grid) {
        if (cell.getLocked()) {
            // If it's locked we can't do anything
            return null;
        }

        for (Group group : grid.getGroups()) {
            if (group instanceof LineGroup && group.contains(cell)) {
                int size = group.size();
                int amountOne = 0;
                int amountZero = 0;
                for (Cell c : group) {
                    CellValue cv = c.getValue();
                    switch (cv) {
                        case ONE:
                            amountOne++;
                            if (amountOne >= size / 2) {
                                return new CommandCellReplace(cell,
                                        CellValue.ZERO);
                            }
                            break;
                        case ZERO:
                            amountZero++;
                            if (amountZero >= size / 2) {
                                return new CommandCellReplace(cell,
                                        CellValue.ONE);
                            }
                            break;
                    }
                }
            }
        }
        return null;
    }
}
