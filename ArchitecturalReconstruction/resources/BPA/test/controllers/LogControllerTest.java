/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JTextPane;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Ruud
 */
public class LogControllerTest {

    /**
     * The instance that is being tested
     */
    private LogController instance;
    /**
     * Holds the exception that we expect
     */
    private ExpectedException exception = ExpectedException.none();

    //<editor-fold defaultstate="collapsed" desc="Tests for the constructor">
    /**
     * Test creating a logcontroller with a null pane
     */
    @Test
    public void testLogControllerContructorNull() {
        System.out.println("testLogControllerContructorNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LogController(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the warning method">
    /**
     * Test sending a null warning message
     */
    @Test
    public void testWarningMessageNull() {
        System.out.println("testWarningMessageNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LogController(new JTextPane());
            instance.warning(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }
    
    /**
     * Test sending a valid warning message
     */
    @Test
    public void testWarningValidMessage() {
        System.out.println("testWarningValidMessage");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LogController(new JTextPane());
            instance.warning("valid message");            
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tests for the info method">
    /**
     * Test sending a null info message
     */
    @Test
    public void testInfoMessageNull() {
        System.out.println("testInfoMessageNull");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LogController(new JTextPane());
            instance.info(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Catch the exception that's supposed to be thrown and
            // check if there is a message associated with the exception
            assertNotNull("Message may not be empty", e.getMessage());
        }
    }

    /**
     * Test sending a valid info message
     */
    @Test
    public void testInfoValidMessage() {
        System.out.println("testInfoValidMessage");
        exception.expect(IllegalArgumentException.class);
        try {
            instance = new LogController(new JTextPane());
            instance.info("valid message");            
        } catch (Exception e) {
            fail("No exception should have been caught but caught: \n" + e);
        }
    }
    //</editor-fold>
}
