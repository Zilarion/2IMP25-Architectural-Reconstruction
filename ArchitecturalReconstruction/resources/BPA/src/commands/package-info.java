/**
 * Title: commands<br />
 * 
 * The following package holds all classes related to the command functionality
 * of the Binary Puzzle Assistant (BPA).
 * 
 * <p>
 * Note that this package implements the <em>Command design pattern</em> and the 
 * <em>Composite design pattern</em>.
 * </p>
 * 
 * <strong>Clas(ses) involved:</strong>
 * <ul>
 *  <li> Command
 *  <li> CommandCellLocked
 *  <li> CommandCellReplace
 *  <li> CommandType
 *  <li> CompoundCommand
 * </ul>
 * 
 * <strong>Enumeration(s) involved</strong>
 * <ul>
 *  <li>CommandType</li>  
 * </ul>
 * <strong>Functionality:</strong>
 * <ul>
 *  <li> A Command class which has a type of command, a Cell it's applied to and 
 *  the new cell state which has the following functionalities:
 *  <ul>
 *      <li> Method(s) for managing and checking the state of the command.
 *      <br /> Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) for undoing and redoing the command.
 *      <br /> Specification (done), Test cases (done), Implementation (done)
 *  </ul>
 *  
 *  <li> A CommandCellLocked class which is a type of command namely of the type 
 *  CHANGELOCKED. 
 *  <li> A CommandCellReplace class which is a type of command namely of the 
 *  type REPLACEVALUE. 
 * 
 *  <li> A CompoundCommand class, which is a composition class of commands. It
 *  holds a set of commands and has the following functionalities:
 *  <ul>
 *      <li> Method(s) for managing and checking the state of the command.
 *      <br /> Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) for undoing and redoing the command.
 *      <br /> Specification (done), Test cases (done), Implementation (done)
 *  </ul>
 */
package commands;