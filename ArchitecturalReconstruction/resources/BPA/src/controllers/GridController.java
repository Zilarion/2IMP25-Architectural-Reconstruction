package controllers;

import commands.Command;
import commands.CompoundCommand;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Grid;
import solvers.ContradictionStrategy;
import solvers.DecoratorStrategy;
import solvers.LineStrategy;
import solvers.RecursiveDecoratorStrategy;
import solvers.Strategy;
import solvers.TripletStrategy;

/**
 * The controller that manages the grid.
 *
 * @author Ruud Andriessen, 0770663
 */
public class GridController extends Component {

    /**
     * The current game state
     */
    Grid gameState;
    /**
     * Whether a certain strategy should be applied
     */
    private boolean applyTripletStrategy;
    private boolean applyLineStrategy;
    private boolean applyContradictionStrategy;
    private boolean applyUntilNoChange;

    /**
     * Default constructor.
     */
    public GridController() {
        gameState = null;
        applyTripletStrategy = false;
        applyLineStrategy = false;
        applyContradictionStrategy = false;
        applyUntilNoChange = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Methods related to I/O">
    /**
     * Loads a certain file from a file location
     *
     * @param f The file which the puzzle is loaded from
     * @pre {@code f} != null
     * @return String with related information for the user
     */
    private String load(File file) {
        // Check the file
        try {
            fileCheck(file);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

        // Scanner setup
        Scanner scanner;
        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
        gameState = new Grid();
        try {
            gameState.loadPuzzle(scanner);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return null;
        }
        return "File loaded from: " + file.getAbsolutePath();
    }

    /**
     * Loads a certain file from a location picked by the user.
     *
     * @return String with related information for the user
     */
    public String load() {
        // Setup filechooser
        JFileChooser fc = new JFileChooser();
        int option = fc.showOpenDialog(this);

        if (option != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        // Initialize file
        File f = fc.getSelectedFile();

        return load(f);
    }

    /**
     * Load in a grid from a string
     *
     * @param s The string to load from
     * @return The loaded Grid
     */
    public Grid loadString(String s) {
        Grid g = new Grid();
        g.loadPuzzle(new Scanner(s));
        return g;
    }

    /**
     * Save the current state to a file at a location.
     *
     * @param file The file to save
     * @pre {@code file} != null && {@code gameState} != {@code null}
     * @return String with related information for the user
     * @throws IllegalStateException If pre-condition violated
     */
    private String save(File file) throws IllegalStateException {
        if (gameState == null) {
            throw new IllegalStateException("No active game, cannot save.");
        }
        String content = gameState.toString();

        try {
            // If file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return "File saved at: " + file.getAbsolutePath();
    }

    /**
     * Saves a file from a file chooser
     *
     * @return String with related information for the user.
     */
    public String save() {
        if (gameState == null ){
            return null;
        }
        // declare JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // let the user choose the destination file
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            // indicates whether the user still wants to export the settings
            boolean doExport = true;

            // indicates whether to override an already existing file
            boolean overrideExistingFile = false;

            // get destination file
            File destinationFile =
                    new File(fileChooser.getSelectedFile().getAbsolutePath());

            // check if file already exists
            while (doExport && destinationFile.exists()
                    && !overrideExistingFile) {
                // let the user decide whether to override the existing file
                overrideExistingFile = (JOptionPane.showConfirmDialog(this,
                        "Replace file?",
                        "Export Settings",
                        JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION);

                // let the user choose another file if the 
                // existing file shall not be overridden
                if (!overrideExistingFile) {
                    if (fileChooser.showSaveDialog(this)
                            == JFileChooser.APPROVE_OPTION) {
                        // get new destination file
                        destinationFile = new File(fileChooser.getSelectedFile()
                                .getAbsolutePath());
                    } else {
                        // seems like the user does not want 
                        // to export the settings any longer
                        doExport = false;
                    }
                }
            }

            // perform the actual export
            if (doExport) {
                return save(destinationFile);
            }
        }
        return null;
    }

    /**
     * Check a file for any exceptions
     *
     * @param f The file to be checked
     * @throws IOException If the file does not exist, or is {@code null}.
     */
    private void fileCheck(File f) throws IOException {
        if (f == null) {
            throw new IOException("File cannot be null.");
        } else if (!f.exists()) {
            throw new IOException("File and/or path does not exist.");
        }
    }
    //</editor-fold>

    
    /**
     * Applies strategies currently active on the active gameState
     */
    public void applyStrategies() {
        if (gameState != null) {
            ArrayList<Strategy> s = new ArrayList<Strategy>();
            if (applyTripletStrategy) {
                s.add(new TripletStrategy());
            }
            if (applyLineStrategy) {
                s.add(new LineStrategy());
            }
            if (applyContradictionStrategy) {
                s.add(new ContradictionStrategy());
            }

            if (applyUntilNoChange) {
                CompoundCommand c = new RecursiveDecoratorStrategy()
                        .find(gameState, s);
                if (!c.isEmpty()) {
                    gameState.applyAction(c, true);
                }
            } else {
                Command c = new DecoratorStrategy()
                        .find(gameState, s);
                if (c != null) {
                    gameState.applyAction(c, true);
                }
            }
        }
    }

    /**
     * Gets the current gameState
     *
     * @return The game state
     */
    public Grid getGameState() {
        return gameState;
    }

    /**
     * Sets the current game state
     *
     * @param g The new game state
     */
    public void setGameState(Grid g) {
        gameState = g;
    }

    /**
     * Sets whether line strategies should be applied
     *
     * @param active Whether the strategy is active
     */
    public void setLineStrategy(boolean active) {
        applyLineStrategy = active;
        applyStrategies();
    }

    /**
     * Sets whether triplet strategies should be applied
     *
     * @param active Whether the strategy is active
     */
    public void setTripletStrategy(boolean active) {
        applyTripletStrategy = active;
        applyStrategies();
    }

    /**
     * Sets whether contradiction strategies should be applied
     *
     * @param active Whether the strategy is active
     */
    public void setContradictionStrategy(boolean active) {
        applyContradictionStrategy = active;
        applyStrategies();
    }

    /**
     * Sets whether strategies should be applied until none can be applied
     * anymore
     *
     * @param active Whether it should be applied until none can
     */
    public void setApplyUntilNoChange(boolean active) {
        applyUntilNoChange = active;
        applyStrategies();
    }
}
