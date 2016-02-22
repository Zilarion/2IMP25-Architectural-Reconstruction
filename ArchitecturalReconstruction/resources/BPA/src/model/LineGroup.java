package model;

import java.util.ArrayList;

/**
 * A specific type of group, namely the line. 
 * It overrides group functionality to make the group specific to a line. 
 * 
 * @author Ruud Andriessen, 0770663
 */
public class LineGroup extends Group {

    /**
     * Constructor of a Line Group
     *
     * @param group The group of elements which it should hold
     * @pre {@code group} != {@code null} && \forall{Cell c : group | c != null}
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public LineGroup(ArrayList<Cell> group) throws IllegalArgumentException {
        super(group);
    }

    /**
     * Test the line rules on this set of cells
     *
     * @return Whether the line rules holds on these cells
     */
    @Override
    public boolean isValid() {
        int zeros = 0, ones = 0;
        for (Cell c : group) {
            if (c.getValue() == CellValue.ZERO) {
                zeros++;
            } else if (c.getValue() == CellValue.ONE) {
                ones++;
            }
        }
        if (zeros > group.size() / 2 || ones > group.size() / 2) {
            return false;
        }
        return true;
    }
}
