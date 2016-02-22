package model;

/**
 * A specific cell in a binary puzzle with a boolean locked and an enumeration
 * to keep track of its contents.
 *
 * It provides several methods to manage it's contents.
 *
 * @author Ruud Andriessen, 0770663
 * @since Sunday February 24
 */
public class Cell {

    /**
     * Determines whether the cell is locked i.e. whether the value was given
     */
    private boolean locked;
    /**
     * Determines what the value of the cell is
     *
     * @inv If value == CellValue.EMPTY = > !locked
     */
    private CellValue value;

    /**
     * Default constructor.
     *
     * @param locked Whether the cell is locked.
     * @param value The starting value of a cell.
     * @pre value != null
     * @throws IllegalArgumentException If pre-condition is violated.
     */
    public Cell(boolean locked, CellValue value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Cell value may not be null.");
        }
        this.locked = locked;
        this.value = value;
    }

    /**
     * Get the value of the cell and whether it is locked.
     *
     * @return The value of the cell and whether it is locked.
     */
    @Override
    public String toString() {
        String str = "";
        if (value == CellValue.EMPTY) {
            str += ".  ";
        } else if (value == CellValue.ONE) {
            str += "1";
            if (locked) {
                str += "  ";
            } else {
                str += "*  ";
            }
        } else if (value == CellValue.ZERO) {
            str += "0";
            if (locked) {
                str += "  ";
            } else {
                str += "*  ";
            }
        }
        return str;
    }

    /**
     * Set the value of the cell
     *
     * @param value The new value of the cell
     * @throws IllegalArgumentException when precondition violated.
     * @throws IllegalStateException if this cell is locked.
     * @pre value != null && !locked
     */
    public void setValue(CellValue value) throws IllegalArgumentException,
            IllegalStateException {
        if (locked) {
            throw new IllegalStateException("The cell is locked,"
                    + " and cannot be changed");
        }
        if (value == null) {
            throw new IllegalArgumentException("The new state for this cell "
                    + "is not a valid state");
        }
        this.value = value;
    }

    /**
     * Set the value of the cell to the next possible value
     *
     * @throws IllegalStateException if the cell is locked
     */
    public void rotateValue() throws IllegalStateException {
        if (locked) {
            throw new IllegalStateException("The cell is locked,"
                    + " and cannot be changed");
        }
        value = value.next();
    }

    /**
     * Sets this cell to a certain lock state.
     *
     * @param locked Whether the cell should be locked or not.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Get the value of the cell
     *
     * @return The value of the cell
     */
    public CellValue getValue() {
        return value;
    }

    /**
     * Get whether the cell is locked
     *
     * @return Whether the cell is locked
     */
    public Boolean getLocked() {
        return locked;
    }
}
