package commands;

import java.util.ArrayList;
import java.util.Iterator;
import model.Cell;

/**
 * An CompoundCommand of Commands
 *
 * @author Ruud Andriessen, 0770663
 */
public class CompoundCommand<Symbol> extends Command implements Iterable<Command> {

    /**
     * A list of all the commands this compoundcommand contains
     */
    private ArrayList<Command<Symbol>> commands;

    /**
     * Default CompoundCommand constructor, creates an empty list
     */
    public CompoundCommand() {
        super();
        commands = new ArrayList<Command<Symbol>>();
    }

    /**
     * Adds an action to the list
     *
     * @param a The command to add
     * @pre a != null
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public void add(Command a) throws IllegalArgumentException {
        if (a == null) {
            throw new IllegalArgumentException(
                    "Can not add a null action to the list");
        }
        commands.add(a);
    }

    /**
     * Clears all of the commands our of the list
     */
    public void clear() {
        commands.clear();
    }

    /**
     * Executes all commands in the list
     */
    @Override
    public void execute() {
        for (Command a : commands) {
            a.execute();
        }
    }

    /**
     * Undoes all commands in the list
     */
    @Override
    public void undo() {
        for (Command a : commands) {
            a.undo();
        }
    }

    /**
     * Checks whether the list is empty
     *
     * @return Whether is the list empty or not
     */
    public boolean isEmpty() {
        return commands.isEmpty();
    }

    /**
     * Get the action at index i
     *
     * @param i The index of the command
     * @pre 0 < i < actions.size()
     * @throws IndexOutOfBoundsException If pre-condition is violated
     */
    public Command get(int i) throws IndexOutOfBoundsException {
        return commands.get(i);
    }

    /**
     * Get the cell of the last command
     * @return The last cell of this command
     */
    @Override
    public Cell getCell() {
        return commands.get(commands.size()-1).getCell();
    }
    
    
    /**
     * Get the size of the command list
     *
     * @return The size of the list
     */
    public int size() {
        return commands.size();
    }

    /**
     * The iterator related to the CompoundCommand class
     *
     * @return The iterator related to the CompoundCommand class
     */
    @Override
    public Iterator iterator() {
        Iterator<Command> it = new Iterator<Command>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < commands.size()
                        && commands.get(currentIndex) != null;
            }

            @Override
            public Command next() {
                return commands.get(currentIndex++);
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException(
                        "Remove is not supported");
            }
        };
        return it;
    }
}
