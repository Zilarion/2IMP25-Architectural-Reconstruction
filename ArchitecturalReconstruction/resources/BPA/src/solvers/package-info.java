/**
 * Title: solvers <br />
 * 
 * The following package holds all classes related to the solvers
 * of the Binary Puzzle Assistant (BPA).
 * 
 * <strong>Clas(ses) involved:</strong>
 * <ul>
 * <li> BacktrackListener
 * <li> Backtracker
 * <li> ContradictionStrategy
 * <li> DecoratorStrategy
 * <li> LineStrategy
 * <li> RecursiveDecoratorStrategy
 * <li> Strategy
 * <li> TripletStrategy
 * </ul>
 * 
 * <strong>Functionality:</strong>
 * <ul>
 *  <li> A BacktrackListener <em>interface</em>. It provides a setup for an
 *  <em>Observer Design Pattern</em> related to the backtracker
 *  <li> A Backtracker class which uses backtracking and applies strategies to
 *  find all/a solution to a given puzzle.
 *  <li> A ContradictionStrategy class which implements the interface strategy.
 *  It tries to fill up a cell by contradiction.
 *  <li> A DecoratorStrategy class which uses a set of strategies to try and 
 *  resolve an empty Cell in a given Grid.
 *  <li> A LineStrategy class which implements the interface strategy.
 *  It tries to fill up a cell by following the line constriction.
  * <li> A RecursiveDecoratorStrategy class which uses a set of strategies to 
  * try and resolve all empty Cells in a given Grid.
 *  <li> An Strategy <em>interface</em> which tries to fill up a cell by 
 *  a certain strategy.
 *  <li> A TripletStrategy class which implements the interface strategy.
 *  It tries to fill up a cell by following the triplet constriction.
 * </ul>
 */

package solvers;