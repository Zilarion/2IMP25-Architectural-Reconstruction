package model;

import commands.Command;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * A grid of a binary puzzle it contains a list of cells representing the
 * current state.
 *
 * It has the ability to manage and keep track of the current state using
 * commands implemented using the <em>Command Design Pattern</em>.
 *
 * @author Ruud Andriessen, 0770663
 * @since Sunday February 24
 */
public class Grid {

    /**
     * The width and height of the model
     */
    private int width, height;
    /**
     * The current model state
     */
    private ArrayList<ArrayList<Cell>> state;
    /**
     * The list of all groups in the grid
     */
    private ArrayList<Group> groups;
    /**
     * The actions that have been performed
     */
    private Stack<Command> pastActions;
    /**
     * The actions that still have to be performed
     */
    private Stack<Command> futureActions;

    /**
     * Default constructor for a grid, initializes up variables
     */
    public Grid() {
        pastActions = new Stack<Command>();
        futureActions = new Stack<Command>();
    }

    /**
     * Checks whether the invariant related to the state dimensions holds.
     *
     * @return Whether the invariant of the state dimensions holds.
     */
    private boolean checkInvariant() {
        if (state == null
                || state.size() % 2 != 0
                || state.get(0).size() % 2 != 0
                || state.size() <= 0
                || state.get(0).size() <= 0) {
            // The invariant does not hold.
            return false;
        } else if (width != state.get(0).size()
                || height != state.size()) {
            // The invariant does not hold.
            return false;
        } else if (!cellsAreNotNull()) {
            return false;
        }
        // Invariant holds
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Methods related to gameState changes">
    /**
     * Undoes the last action.
     *
     * @pre {@code !pastActions.isEmpty()}
     * @throws IllegalStateException If pre-condition is violated.
     */
    public void undo() throws IllegalStateException {
        if (pastActions.isEmpty()) {
            throw new IllegalStateException("Cannot undo an action");
        }
        Command a = pastActions.pop();
        a.undo();
        futureActions.push(a);
    }

    /**
     * Undoes commands until the pastActions are empty
     */
    public void undoAll() {
        while (!pastActions.isEmpty()) {
            undo();
        }
    }

    /**
     * Redo the last action.
     *
     * @pre {@code !futureActions.isEmpty()}
     * @throws IllegalStateException If pre-condition is violated.
     */
    public void redo() throws IllegalStateException {
        if (futureActions.isEmpty()) {
            throw new IllegalStateException("Cannot redo an action");
        }
        Command a = futureActions.pop();
        a.execute();
        pastActions.push(a);
    }

    /**
     * Redoes commands until the futureActions are empty
     */
    public void redoAll() {
        while (!futureActions.isEmpty()) {
            redo();
        }
    }

    /**
     * Clears all non-locked cells and both stacks
     */
    public void clear() {
        // Clear all non locked cells
        for (ArrayList<Cell> cellLine : state) {
            for (Cell c : cellLine) {
                if (!c.getLocked()) {
                    c.setValue(CellValue.EMPTY);
                }
            }
        }
        // Clear both stacks
        pastActions = new Stack<Command>();
        futureActions = new Stack<Command>();
    }

    /**
     * Applies a certain action.
     *
     * @param a The action to be applied
     * @param stored Whether the action should be saved for undo/redoing
     * @pre {@code a} != {@code null}
     * @throws IllegalArgumentException If pre-condition is violated ||
     * @throws IllegalStateException When the action throws an exception.
     */
    public void applyAction(Command a, boolean stored) throws
            IllegalArgumentException, IllegalStateException {
        if (a == null) {
            throw new IllegalArgumentException("Cannot apply a null action");
        }
        // Execute the action
        try {
            a.execute();
        } catch (IllegalStateException e) {
            throw e;
        }

        // If the future actions are not empty
        if (!futureActions.isEmpty()) {
            // Check the next action
            Command na = futureActions.pop();

            // If it's not same
            if (na.getNext() != a.getNext()
                    || na.getPrev() != a.getPrev()
                    || na.getCell() != a.getCell()
                    || na.getActionType() != a.getActionType()) {
                // Clear the next actions
                futureActions.clear();
            }
        }
        // Save it as past action, if wanted
        if (stored) {
            pastActions.push(a);
        }
    }
    //</editor-fold>

    private boolean cellsAreNotNull() {
        for (ArrayList<Cell> cells : state) {
            for (Cell c : cells) {
                if (c == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loads a puzzle from a certain file.
     *
     * @param scanner The scanner that is used.
     * @pre scanner != null
     * @post invariant holds && groups != null
     * @throws IllegalArgumentException If pre-condition is violated.
     */
    public void loadPuzzle(Scanner scanner) throws IllegalArgumentException {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanning method invalid.");
        }

        // Load the file, line by line  
        state = new ArrayList<ArrayList<Cell>>();
        while (scanner.hasNextLine()) {
            String newLine = scanner.nextLine();
            ArrayList<Cell> line = loadLine(newLine);
            state.add(line);
        }
        if (state.size() == 0 || state.get(0).size() == 0) {
            throw new IllegalArgumentException("Invalid file");
        }
        
        // Set the dimensions
        width = state.get(0).size();
        height = state.size();

        for (int i = 1; i < state.size(); i++) {
            if (state.get(i).size() != width) {
                throw new IllegalArgumentException("File is not valid. "
                        + "Not all lines have the same length");
            }
        }

        // Make sure the state invariant holds
        if (!checkInvariant()) {
            throw new IllegalArgumentException("Invalid file invariant.");
        }
        assignGroups();
    }

    /**
     * Loads a line to the current state of the model
     *
     * @param line The line to load
     * @param row The row we are loading this line in
     * @pre {@code line} != {@code null} && 0 <= {@code row} < {@code height}
     * @post {@code state} contains the given line at row: {@code row}
     * @throws IllegalArgumentException If pre-condition is violated.
     */
    private ArrayList<Cell> loadLine(String line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Cannot load a null line.");
        }
        ArrayList<Cell> myArray = new ArrayList<Cell>();
        for (int i = 0; i < line.length(); i++) {
            char newChar = line.charAt(i);
            if (newChar == '0') {
                /* Note, if i+1 >= line.length then charAt(i+1) is never executed.
                 * And hence these statements can not be swapped around.
                 * If they are, line.charAt(i+1) may cause an out of bounds exception.
                 */
                if (i + 1 < line.length() && line.charAt(i + 1) == '*') {
                    myArray.add(new Cell(false, CellValue.ZERO));
                } else {
                    myArray.add(new Cell(true, CellValue.ZERO));
                }
            } else if (newChar == '1') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '*') {
                    myArray.add(new Cell(false, CellValue.ONE));
                } else {
                    myArray.add(new Cell(true, CellValue.ONE));
                }
            } else if (newChar == '.') {
                myArray.add(new Cell(false, CellValue.EMPTY));
            }
        }
        return myArray;
    }

    /**
     * Assigns all cells to groups
     *
     * @pre state != null
     * @post groups != null
     */
    private void assignGroups() {
        // Make sure we initialize the group array
        groups = new ArrayList<Group>();

        // Assign rows to groups
        for (int row = 0; row < height; row++) {
            assignLineGroup(state.get(row));
        }

        // Assign columns to groups
        for (int col = 0; col < width; col++) {
            ArrayList<Cell> column = new ArrayList<Cell>();
            for (int row = 0; row < height; row++) {
                column.add(state.get(row).get(col));
            }
            assignLineGroup(column);
        }
    }

    /**
     * Checks if the puzzle is completed without rule violation.
     *
     * @return Whether the puzzle is completed.
     */
    public boolean isCompleted() {
        // Make sure all cells are non-empty (ZERO or ONE)
        for (ArrayList<Cell> cellRow : state) {
            for (Iterator<Cell> it = cellRow.iterator(); it.hasNext();) {
                Cell c = it.next();
                if (c.getValue() == CellValue.EMPTY) {
                    return false;
                }
            }
        }
        // Check if all groups are valid/are not violating the rules
        for (Group g : groups) {
            if (!g.isValid()) {
                return false;
            }
        }
        // It's completed!
        return true;
    }

    /**
     * Assign arraylist to a line group
     *
     * @param list The line of cells to be assigned
     */
    private void assignLineGroup(ArrayList<Cell> list) {
        groups.add(new LineGroup(list));
        for (int i = 0; i < list.size(); i++) {
            if (i > 0 && i < list.size() - 1) {
                ArrayList<Cell> tripletGroup = new ArrayList<Cell>();
                tripletGroup.add(list.get(i - 1));
                tripletGroup.add(list.get(i));
                tripletGroup.add(list.get(i + 1));
                groups.add(new TripletGroup(tripletGroup));
            }
        }

    }

    /**
     * Returns a cell at certain coordinate.
     *
     * @param col the col of the cell on the puzzle (or x coordinate)
     * @param row the row of the cell on the puzzle (or y coordinate)
     * @param newValue The new value of the cell at the coordinates
     * @pre 0 <= {@code col} < width && 0 <= {@code row} < height && {@code
     * newValue} != null
     * @throws IllegalArgumentException If pre-condition is violated.
     * @throws IllegalStateException If the cell is locked
     */
    public void setCell(int col, int row, CellValue newValue) throws
            IllegalArgumentException, IllegalStateException {
        if (row < 0 || col < 0 || col >= width || row >= height) {
            throw new IllegalArgumentException(
                    "isLocked pre-condition violated: "
                    + "(" + col + "," + row + ") is out of bounds.");
        } else if (newValue == null) {
            throw new IllegalArgumentException("Cannot set a cell to null");
        }
        state.get(row).get(col).setValue(newValue);
    }

    /**
     * Check whether a cell is in a violating group
     *
     * @param c The cell to check
     * @pre {@code c} != {@code null} && {@code groups} != {@code null}
     * @throws IllegalArgumentException If pre-condition is violated
     * @return Whether {@code c} violates a property
     */
    public boolean hasGroupViolation(Cell c) throws IllegalArgumentException {
        if (c == null) {
            throw new IllegalArgumentException(
                    "Can not check group violation of null");
        }
        for (Group g : groups) {
            if (g.getElements().contains(c) && !g.isValid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether there is a violating in any of the groups
     *
     * @return Whether there is a violating in any of the groups
     */
    public boolean hasViolation() {
        for (Group g : groups) {
            if (!g.isValid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a cell at certain coordinate.
     *
     * @param col the col of the cell on the puzzle (or x coordinate)
     * @param row the row of the cell on the puzzle (or y coordinate)
     * @pre 0 <= {@code col} < width && 0 <= {@code row} < height
     * @throws IllegalArgumentException If pre-condition is violated.
     * @return The cell at ({@code col}, {@code row}).
     */
    public Cell getCell(int col, int row) throws IllegalArgumentException {
        if (row < 0 || col < 0 || col >= width || row >= height) {
            throw new IllegalArgumentException(
                    "isLocked pre-condition violated: "
                    + "(" + col + "," + row + ") is out of bounds.");
        }
        return state.get(row).get(col);
    }

    /**
     * Translates the current puzzle model to a string.
     *
     * @return The string made out of the puzzle model.
     */
    @Override
    public String toString() {
        String str = "";
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell c = state.get(row).get(col);
                str += c.toString();
            }
            str += System.lineSeparator();
        }
        return str;
    }

    /**
     * Get the width of the grid.
     *
     * @return The width of the grid.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the grid.
     *
     * @return The height of the grid.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the groups of the grids.
     *
     * @return The groups of the grid
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * Get the last changed cell in the grid
     *
     * @return The last changed cell in the grid, or null if none
     */
    public Cell getLastChanged() {
        if (pastActions.isEmpty()) {
            return null;
        }
        Command c = pastActions.pop();
        pastActions.push(c);
        return c.getCell();
    }
}
