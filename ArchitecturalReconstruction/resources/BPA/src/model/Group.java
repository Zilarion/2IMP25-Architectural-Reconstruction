package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The <em>abstract</em> group class, which holds a set of Cells.
 * It provides methods to manage the said set of Cells and check their validity.
 *
 * @author Ruud Andriessen 0770663
 * @since Saturday March 9
 */
public abstract class Group implements Iterable<Cell> {

    /**
     * The elements of the group
     */
    final protected ArrayList<Cell> group;

    /**
     * Constructor of a group
     *
     * @param group The group of elements which it should hold
     * @pre {@code group} != {@code null} && \forall{Cell c : group | c != null}
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public Group(ArrayList<Cell> group) throws IllegalArgumentException {
        if (group == null || group.size() <= 0) {
            throw new IllegalArgumentException("Can not initialize this group.");
        }
        for (Cell c : group) {
            if (c == null) {
                throw new IllegalArgumentException("Can not initialize group "
                        + "with null cell(s).");
            }
        }
        this.group = group;
    }

    /**
     * Check whether a cell is in this group
     *
     * @param c The cell to check
     * @return Whether {@code c} is in this group
     */
    public boolean contains(Cell c) {
        for (Cell cell : group) {
            if (cell.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether this group is violating a rule
     *
     * @return Whether this group violates it's rules
     */
    public abstract boolean isValid();

    /**
     * Get the elements of this group
     *
     * @return The elements of this group
     */
    public ArrayList<Cell> getElements() {
        return group;
    }

    /**
     * Get the size of the group
     *
     * @return The size of the group
     */
    public int size() {
        return group.size();
    }

    /**
     * The iterator related to the CompoundCommand class
     *
     * @return The iterator related to the CompoundCommand class
     */
    @Override
    public Iterator iterator() {
        Iterator<Cell> it = new Iterator<Cell>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < group.size()
                        && group.get(currentIndex) != null;
            }

            @Override
            public Cell next() {
                return group.get(currentIndex++);
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
