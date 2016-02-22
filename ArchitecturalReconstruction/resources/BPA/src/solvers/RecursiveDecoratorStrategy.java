package solvers;

import commands.Command;
import commands.CompoundCommand;
import java.util.ArrayList;
import model.Grid;

/**
 * A decorator strategy that repeatedly finds a strategy to apply to an empty 
 * cell until no further changes occur
 *
 * @author Ruud Andriessen, 0770663
 */
public class RecursiveDecoratorStrategy {

    /**
     * Finds a strategy in a list of given strategies to apply to an empty cell
     *
     * @param grid The grid to apply it to
     * @param strategies The strategies to try and apply
     * @return The command(s) to execute, or null if none
     */
    public CompoundCommand find(Grid grid, ArrayList<Strategy> strategies) {
        CompoundCommand<Command> commands = new CompoundCommand<Command>();
        DecoratorStrategy decStrat = new DecoratorStrategy();
        Command result = decStrat.find(grid, strategies);
        // While there is a change
        while (result != null) {
            // Add it to our commands
            commands.add(result);
            // Execute it
            result.execute();
            // Get a new result
            result = decStrat.find(grid, strategies);        
        }
        // Undo all commands at the end
        commands.undo();
        return commands;
    }
}
