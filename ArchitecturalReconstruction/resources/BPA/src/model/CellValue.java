package model;

/**
 * The types of cell values. It provides functionality to get the next and 
 * previous enumeration.
 * 
 * @author Ruud Andriessen, 0770663
 * @since Sunday February 24
 */
public enum CellValue {

    ZERO, ONE, EMPTY;
    
    public CellValue next() {
        switch(this) {
            case ZERO:
                return ONE;
            case ONE:
                return EMPTY;
            case EMPTY:
                return ZERO;
        }
        return null;
    }    
    
    public CellValue prev() {
        switch(this) {
            case ZERO:
                return EMPTY;
            case ONE:
                return ZERO;
            case EMPTY:
                return ONE;
        }
        return null;
    }
}
