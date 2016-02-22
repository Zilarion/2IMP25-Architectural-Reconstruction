package solvers;

import commands.Command;
import commands.CommandCellReplace;
import model.Cell;
import model.CellValue;
import model.Grid;
import model.Group;
import model.TripletGroup;

/**
 * A concrete strategy of the Strategy interface, this strategy contains the
 * ability to apply a triplet strategy to a given cell
 *
 * @author Ruud Andriessen, 0770663
 */
public class TripletStrategy implements Strategy {

    /**
     * Applies a triplet strategy to a given cell
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
        // Count the amount of ones and zeros
        for (Group group : grid.getGroups()) {
            Command c = checkGroup(cell, group);
            if (c != null) {
                return c;
            }
        }
        return null;
    }

    private Command checkGroup(Cell cell, Group group) {
        int amountOfOne = 0;
        int amountOfZero = 0;
        // If the group is a triplet group && our cell is in it
        if (group instanceof TripletGroup && group.contains(cell)) {
            for (Cell c : group) {
                if (c.getValue() == CellValue.ONE) {
                    amountOfOne++;
                } else if (c.getValue() == CellValue.ZERO) {
                    amountOfZero++;
                }
            }
            // If the only empty cell is our cell
            if (cell.getValue() == CellValue.EMPTY) {
                // If the amount of zero's or ones is 2
                if (amountOfZero == 2) {
                    return new CommandCellReplace(cell, CellValue.ONE);
                } else if (amountOfOne == 2) {
                    return new CommandCellReplace(cell, CellValue.ZERO);
                }
            }
        }
        return null;
    }
}
