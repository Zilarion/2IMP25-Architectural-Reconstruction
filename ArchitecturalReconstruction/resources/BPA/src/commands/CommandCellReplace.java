package commands;

import model.Cell;
import model.CellValue;

/**
 * A Command with the CommandType being REPLACEVALUE
 *
 * @author Ruud Andriessen, 0770663
 */
public class CommandCellReplace extends Command<CellValue> {

    /**
     * Constructor for setting up an command.
     *
     * @param cell The cell the command is applied to.
     * @param becomes What the cell should become after the command
     * @pre {@code cell} != {@code null} {@code becomes} != {@code null}
     * && {@code cell.value} != {@code null}
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public CommandCellReplace(Cell cell, CellValue becomes) throws 
            IllegalArgumentException {
        super(CommandType.REPLACEVALUE, cell, becomes);
    }
}
