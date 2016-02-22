package solvers;

import commands.Command;
import java.util.ArrayList;
import model.Cell;
import model.CellValue;
import model.Grid;

/**
 * A decorator strategy finds an empty cell to which a given strategy
 * applies
 *
 * @author Ruud Andriessen, 0770663
 */
public class DecoratorStrategy {

    /**
     * Finds an strategy in a list of given strategies to apply to an empty cell
     *
     * @param grid The grid to apply it to
     * @param strategies The strategies to try and apply
     * @return The command to execute, or null if none found
     */
    public Command find(Grid grid, ArrayList<Strategy> strategies) {
        // Loop through all cells
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                // Get the corresponding cell
                Cell c = grid.getCell(i, j);

                // If it's empty, try to resolve it
                if (c.getValue() == CellValue.EMPTY) {
                    for (Strategy s : strategies) {
                        Command result = s.apply(c, grid);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
}
