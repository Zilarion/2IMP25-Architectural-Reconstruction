package model;

import java.util.ArrayList;

/**
 * A specific type of group, namely the triplet.
 * It overrides group functionality to make the group specific to a triplet. 
 *
 * @author Ruud Andriessen, 0770663
 */
public class TripletGroup extends Group {

    /**
     * Constructor of a group
     *
     * @param group The group of elements which it should hold
     * @pre {@code group} != {@code null} && \forall{Cell c : group | c != null}
     * && group.size() == 3
     * @throws IllegalArgumentException
     */
    public TripletGroup(ArrayList<Cell> group) throws IllegalArgumentException {
        super(group);
        if (group.size() != 3) {
            throw new IllegalArgumentException("Can not initialize this group.");
        }
    }

    /**
     * Test the triplet rules on this set of cells
     *
     * @return Whether the triplet rules holds on these cells
     */
    @Override
    public boolean isValid() {
        // Check the first cell value
        CellValue cv = group.get(0).getValue();
        for (Cell c : group) {
            // Check if we find another cellvalue or an empty cell
            if (c.getValue() != cv || c.getValue() == CellValue.EMPTY) {
                // If we find another, it's fine
                return true;
            }
        }
        // Violation found
        return false;
    }
}
