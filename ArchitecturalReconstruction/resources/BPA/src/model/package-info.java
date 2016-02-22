/**
 * Title: model <br />
 * 
 * The following package holds all classes related to the model
 * of the Binary Puzzle Assistant (BPA).
 * 
 * <strong>Clas(ses) involved:</strong>
 * <ul>
 * <li> Cell
 * <li> Grid
 * <li> Group
 * <li> LineGroup
 * <li> TripletGroup
 * </ul>
 * 
 * <strong>Enumeration(s) involved:</strong>
 * <ul>
 * <li> CellValue
 * </ul>
 * 
 * <strong>Functionality:</strong>
 * <ul>
 *  <li> A Cell class with a boolean locked and an enumeration to keep track 
 *  of its contents 
 *      <br />Specification (done), Test cases (done), Implementation (done)
 *  <li> A Grid class with following functionalities:
 *  <ul>
 *      <li> Method(s) to read a file from a given scanner 
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) to query the state of a given Cell through its coordinates
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) to modify the state of a given Cell through its coordinates
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) to represent a Grid in a string 
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) to check constraints for a Grid 
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) to keep track of the latest change in the Grid 
 *          <br />Specification (done), Test cases (done), Implementation (done)
 *  </ul>
 *  
 *  <li> An <em>(abstract)</em> Group class with a list of cells contained in the
 *  group and several methods with the following functionalities:
 *  <ul>
 *      <li> Method(s) related to the management of the group
 *      <br />Specification (done), Test cases (done), Implementation (done)
 *      <li> Method(s) related to the validity of the group 
 *      (whether it breaks any of the rules of a binary puzzle)
 *      <br />Specification (done), Test cases (done), Implementation (done) 
 *  </ul>
 * 
 *  <li> A LineGroup class which extends the group class and overrides
 *  functionality to make it related to a line group:
 *  <ul>
 *      <li> Method(s) related to checking the validity of the line group
 *      <br />Specification (done), Test cases (done), Implementation (done)
 *  </ul>
 *  
 *  <li> A TripletGroup class which extends the group class and overrides
 *  functionality to make it related to a triplet group:
 *  <ul>
 *      <li> Method(s) related to checking the validity of the triplet group
 *      <br />Specification (done), Test cases (done), Implementation (done)
 *  </ul> 
 * </ul>
 */


package model;