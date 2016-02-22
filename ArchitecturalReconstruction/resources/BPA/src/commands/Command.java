package commands;

import model.Cell;
import model.CellValue;

/**
 * A Command which can be apply to a certain cell
 *
 * @author Ruud Andriessen, 0770663
 * @since Sunday February 24
 */
public class Command<Symbol> {

    /**
     * This actions type
     */
    private CommandType type;
    /**
     * The Cell on which the action was applied
     */
    private Cell cell;
    /**
     * The value which it was
     */
    private Symbol prev;
    /**
     * The value which it becomes
     */
    private Symbol next;
    /**
     * Whether the command is executed
     */
    private boolean executed;

    /**
     * Constructor for classes extending the Command
     */
    protected Command() {
    }

    /**
     * Constructor for setting up an command.
     *
     * @param type The type of command that is applied to the {@code cell}
     * @param cell The cell the command is applied to.
     * @param becomes What the cell should become after the command
     * @pre {@code type} != {@code null} && {@code cell} != {@code null}
     * && {@code becomes} != {@code null} && {@code cell.value} != {@code null}
     * @throws IllegalArgumentException If precondition violated
     */
    public Command(CommandType type, Cell cell, Symbol becomes) throws 
            IllegalArgumentException {
        if (type == null || cell == null
                || becomes == null || cell.getValue() == null) {
            throw new IllegalArgumentException(
                    "Command may not be initialized with null (a) argument(s).");
        }
        this.type = type;
        this.cell = cell;
        this.next = becomes;
        if (becomes instanceof CellValue) {
            this.prev = (Symbol) cell.getValue();
        } else if (becomes instanceof Boolean) {
            this.prev = (Symbol) cell.getLocked();
        }
    }

    /**
     * Get what its previous value is
     *
     * @return What the previous value is
     */
    public Symbol getPrev() {
        return prev;
    }

    /**
     * Get what its next value is
     *
     * @return What the next value is
     */
    public Symbol getNext() {
        return next;
    }

    /**
     * Get the cell which the action is applied to.
     *
     * @return The cell which the action is applied to.
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Get whether the action is executed
     *
     * @return Whether the action is executed
     */
    public boolean getExecuted() {
        return executed;
    }

    /**
     * Get what the CommandType is
     *
     * @return The CommandType of this action.
     */
    public CommandType getActionType() {
        return type;
    }

    /**
     * Undoes this action
     *
     * @post executed = false
     */
    public void undo() {
        if (type == CommandType.REPLACEVALUE) {
            cell.setValue((CellValue) prev);
            executed = false;
        } else if (type == CommandType.CHANGELOCKED) {
            cell.setLocked((Boolean) prev);
            executed = false;
        }
    }

    /**
     * Executes this action
     *
     * @pre executed != true
     * @throws IllegalStateException If pre-condition is violated
     * @post executed = true
     */
    public void execute() throws IllegalStateException {
        if (executed) {
            throw new IllegalStateException("Action can not be executed twice");
        }
        if (type == CommandType.REPLACEVALUE) {
            cell.setValue((CellValue) next);
            executed = true;
        } else if (type == CommandType.CHANGELOCKED) {
            cell.setLocked((Boolean) next);
            executed = true;
        }
    }
}
