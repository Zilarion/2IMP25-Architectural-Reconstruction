package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JPanel;
import model.Cell;
import model.CellValue;
import model.Grid;

/**
 * The panel of the puzzle
 *
 * @author Ruud Andriessen, 0770663
 * @since Saturday March 9
 */
public class PuzzlePanel extends JPanel {

    /**
     * The puzzle to draw
     */
    private Grid puzzle;
    /**
     * Determines whether to show errors immediately
     */
    private boolean showErrorsImmediately;

    /**
     * Creates new form PuzzlePanel
     */
    public PuzzlePanel() {
        initComponents();
    }

    /**
     * Set the puzzle to draw on the panel
     *
     * @param p The said puzzle
     * @pre {@code p} != {@code null}
     */
    public void setPuzzle(Grid p) throws IllegalArgumentException {
        if (p == null) {
            throw new IllegalArgumentException("Puzzle may not be null.");
        }
        puzzle = p;
    }

    /**
     * Get a cell related to a certain point
     *
     * @param p The point to convert to a cell
     * @return The cell at this point or null if puzzle is not initialized
     */
    public Cell getCell(Point p) {
        if (puzzle != null) {
            int dx = this.getWidth() / puzzle.getWidth();
            int dy = this.getHeight() / puzzle.getHeight();
            int col = p.x / dx;
            int row = p.y / dy;
            Cell cell = null;
            try {
                cell = puzzle.getCell(col, row);
            } catch (IllegalArgumentException e) {
                // Ignore, used clicked on the little leftover space
            }
            return cell;
        }
        return null;
    }


    /*------------------------------------------
     * Methods related to painting the puzzle
     ---------------------------------------- */
    /**
     * Overridden paintComponents, paints the grid
     *
     * @param g The graphics pane to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (puzzle != null) {
            drawGrid(g);
        }

    }

    /**
     * Draws a grid for the puzzle.
     *
     * @pre puzzle != null
     * @param g The graphics pane to draw it on.
     */
    private void drawGrid(Graphics g) {
        int dx = this.getWidth() / puzzle.getWidth();
        int dy = this.getHeight() / puzzle.getHeight();

        for (int x = 0; x < puzzle.getWidth(); x++) {
            for (int y = 0; y < puzzle.getHeight(); y++) {
                drawRectangle(x, y, dx, dy, g);
            }
        }
    }

    private void drawRectangle(int x, int y, int dx, int dy, Graphics g) {
        Cell c = puzzle.getCell(x, y);

        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();

        // Get the string related to the strings value
        CellValue v = c.getValue();
        // If we have to process it, get the correct string
        String str;
        switch (v) {
            case ONE:
                str = "1";
                break;
            case ZERO:
                str = "0";
                break;
            default:
                str = ".";
        }

        int fontSize = Math.min(this.getWidth(), this.getHeight()) / 20 + 2;

        // Set the correct background color
        if (puzzle.isCompleted()) {
            g.setColor(Color.GREEN);
            g.fillRect(x * dx, y * dy, dx, dy);
        } else if (c.getLocked()) {
            g.setColor(Color.GRAY);
            g.fillRect(x * dx, y * dy, dx, dy);
        } else if (puzzle.getLastChanged() != null 
                && puzzle.getLastChanged().equals(c)) {
            g.setColor(Color.CYAN);
            g.fillRect(x * dx, y * dy, dx, dy);
        }

        // Set the font color
        if (puzzle.hasGroupViolation(c) && showErrorsImmediately) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        Font font = new Font("Arial", Font.PLAIN, fontSize);

        // Set the font
        g.setFont(font);

        // Find the size of string str in font 
        // in the current Graphics context g.
        FontMetrics fm = g.getFontMetrics(font);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(str, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());

        // Center text horizontally and vertically
        int fx = x * dx + (dx - textWidth) / 2;
        int fy = y * dy + (dy - textHeight) / 2 + fm.getAscent();

        // Draw the string
        g.drawString(str, fx, fy);

        // Draw the rectangle
        g.setColor(Color.BLACK);
        g.drawRect(x * dx, y * dy, dx, dy);
    }

    /**
     * Sets whether to show the errors with a red color immediately
     *
     * @param showErrors Whether to show the errors
     */
    public void setShowErrorsImmediately(boolean showErrors) {
        showErrorsImmediately = showErrors;
    }

    /**
     * Get whether we show errors immediately on change
     *
     * @return Whether we show errors immediately on change
     */
    public boolean getShowErrorsImmediately() {
        return showErrorsImmediately;
    }

    /**
     * Get the Grid that is currently active
     *
     * @return The grid that is currently active
     */
    public Grid getGrid() {
        return puzzle;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
