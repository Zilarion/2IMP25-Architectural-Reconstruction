package controllers;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * The controller that manages the output log.
 *
 * @author Ruud Andriessen, 0770663
 */
public class LogController {

    /**
     * The textpane that is used to output the messages
     */
    private JTextPane textPane;

    /**
     * Default constructor
     *
     * @param pane The textpane to print on
     * @pre pane != null
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public LogController(JTextPane pane) throws IllegalArgumentException {
        if (pane == null) {
            throw new IllegalArgumentException(
                    "Cannot instantiate a LogController on a null pane");
        }
        textPane = pane;
    }

    /**
     * Print a warning message
     *
     * @param str The message to print
     * @pre str != null
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public void warning(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Can not print a null message");
        }
        append(str, "Warning");
    }

    /**
     * Print an info message
     *
     * @param str The message to print
     * @pre str != null
     * @throws IllegalArgumentException If pre-condition is violated
     */
    public void info(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Can not print a null message");
        }
        append(str, "Info");
    }

    /**
     * Append a message to the related textPane with a certain type
     *
     * @param msg The message
     * @param type The type of message
     */
    private void append(String msg, String type) {
        Style style = textPane.addStyle("LogStyle", null);

        switch (type) {
            case "Warning":
                StyleConstants.setForeground(style, Color.red);
                break;
            default:
                StyleConstants.setForeground(style, Color.black);
        }
        StyledDocument doc = textPane.getStyledDocument();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

        try {
            doc.insertString(doc.getLength(), type + " [" + sdf.format(date) + 
                    "] - " + msg + "\n", style);
        } catch (BadLocationException e) {
            System.out.println(e.getMessage());
        }        
    }
}
