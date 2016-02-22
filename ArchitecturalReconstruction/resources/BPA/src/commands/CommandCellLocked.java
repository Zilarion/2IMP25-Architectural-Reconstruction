package commands;

import model.Cell;

/**
 * A Command with the CommandType being CHANGELOCKED
 *
 * @author Ruud Andriessen, 0770663
 */
public class CommandCellLocked extends Command<Boolean> {

    /**
     * Constructor for setting up a command.
     *
     * @param cell The cell the command is applied to.
     * @param becomes What the cell should become after the command
     * @pre {@code cell} != {@code null} {@code becomes} != {@code null}
     * && {@code cell.value} != {@code null}
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public CommandCellLocked(Cell cell, Boolean becomes) throws
            IllegalArgumentException {
        super(CommandType.CHANGELOCKED, cell, becomes);
    }
}
