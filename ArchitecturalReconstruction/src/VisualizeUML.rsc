module VisualizeUML

// Import m3
import lang::java::m3::Core;

// Import OFG
import Java2OFG;
import FlowLanguage;

import IO;
import Type;
import Map;
import Relation;
import Set;
import String;
import util::ValueUI;


alias OFG = rel[loc from, loc to];

// String defs for easy access when creating dot file
str realisation 	= 	"edge[arrowhead = \"empty\"; style = \"dashed\"]\n";
str generalization 	= 	"edge[arrowhead = \"empty\"; style= \"solid\"]\n";
str association 	= 	"edge[arrowhead = \"open\"; style = \"solid\"]\n";
str dependency 		= 	"edge[arrowhead = \"open\"; style = \"dashed\"]\n";

// Global settings of any dot file generated
str dotSettings = "digraph classes {
		         ' fontname = \"Bitstream Vera Sans\"
		         ' fontsize = 8
		         ' node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ]
		         ' edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]
		         '";
        
/*
 * Creates a string with dot specifications for rendering from a model and ofg
 */        
public str createDotFile(M3 m, OFG ofg) {
	str dotStr = dotSettings;
	
	pathNames = invert(m@names);
	
	// Generate all class blocks
	for (cl <- classes(m)) {
		dotStr += generateClass(cl, m, pathNames);
	}
	
	// Generate all associations
	dotStr += generateAssociations(m, pathNames);
	dotStr += "}";
    return dotStr;
}

/*
 * Generates a class string for use in a dot file from a class, model and model pathNames
 */
private str generateClass(loc cl, M3 m, rel[loc, str] pathNames){
	return "\"<prettyLoc(cl, pathNames)>\" [
		'	label=\"{
		' 		<cl.path[1..]>|
		' 		<generateFields(cl, m, pathNames)>|
		' 		<generateMethods(cl, m, pathNames)>
		'	}\"
		' ]
		'";
}

/*
 * Creates a dot file string for associations of the given model and pathNames of that model
 */
private str generateAssociations(M3 m, rel[loc, str]  pathNames) {
	str associationStr = "";
	
	// Generalization
	associationStr += generalization;
	for (<from, to> <- m@extends) {
         associationStr += "<prettyLoc(to, pathNames)> -\> <prettyLoc(from, pathNames)>\n";
 	}
 	
 	// Realization
 	associationStr += realisation; 
 	for (<from, to> <- m@implements) {
 		associationStr += "<prettyLoc(to, pathNames)> -\> <prettyLoc(from, pathNames)>\n";
 	}
 	
 	// Associations
 	associationStr += association;
    // :TODO: add associations using OFG
    
    
    // Dependency 
    associationStr += dependency;
    // :TODO: add dependencies using OFG
     
	return associationStr;
}

/*
 * Generates a string of class fields for use in a dot file from a class, model and model pathNames
 */
private str generateFields(loc cl, M3 m, rel[loc, str] pathNames) {
	// Create set of location for each of our class fields
	set[loc] fields = {f | f <- m@containment[cl], isField(f) };
	
	// Only take the relevant types/modifiers for our fields
	types = domainR(m@types, fields);
	modifiers = domainR(m@modifiers, fields);

	str returnStr = "";

	// Loop over all fields
	for (f <- fields) {
		// Get the field name by using the pathNames variable
		str fieldName = prettyLoc(f, pathNames);
		
		// Get the type name and make it readable
		str typeName = prettify(getOneFrom(types[f]), pathNames);
		
		// Get all modifiers
		set[Modifier] fieldModifiers = modifiers[f];
		
		// Apply each modifier
		for (fModifier <- fieldModifiers) {
			fieldName = applyModifier(fieldName, fModifier);
		}
		
		// Create the resulting string
		returnStr += "<fieldName> : <typeName>\\l";		
	}
	return returnStr;
}

/*
 * Generates a string of class methods for use in a dot file from a class, model and model pathNames
 */
private str generateMethods(loc cl, M3 m, rel[loc, str] pathNames) {
	// Create set of location for each of our class fields
	//set[loc] constructors = {mt | mt <- m@containment[cl], isConstructor(mt) };
	set[loc] methods = {mt | mt <- m@containment[cl], isMethod(mt)};//, !isConstructor(mt) };
	
	// Only take the relevant types/modifiers for our methods
	types = domainR(m@types, methods);
	modifiers = domainR(m@modifiers, methods);
	
	str returnStr = "";
	
	for (mt <- methods) {
		// Get the method name by using the pathNames variable
		str methodName = prettyLoc(mt, pathNames);
		
		// Get the type name and make it readable
		str typeName = prettify(getOneFrom(types[mt]), pathNames);
		
		// Get all modifiers
		set[Modifier] methodModifiers = modifiers[mt];
		
		// Apply each modifier
		for (mModifier <- methodModifiers) {
			methodName = applyModifier(methodName, mModifier);
		}
		
		// Create the resulting string
		returnStr += "<methodName> : <typeName>\\l";
	}
	return returnStr;
}

private str prettyLoc(loc c, rel[loc, str] pathNames) {
	return getOneFrom(pathNames[c]);
}

private str applyModifier(str name, Modifier modifier) {
	switch(modifier){
		case \private():
			return " -  <name>";
		case \protected():
			return "# <name>";
		case \static():
			return "_<name>_";
		case \final():
			return toUpperCase(name);
		case \public():
			return "+ <name>";
	}
	println("Warning - Unknown modifier <modifier>");
	return "<name>";
}

/*
 * Creates a readable string from a TypeSymbol and the relation between locations and names of the model
 * This relation can be constructed for model m by calling invert(m@names)
 */
private str prettify(TypeSymbol t, rel[loc, str] pathNames) {
	switch(t) {
		case \int() : return "int";
		case \short() : return "short";
		case \long() : return "long";
		case \char() : return "char";
		case \boolean() : return "boolean";
		case \class(cn, ex) : return "<prettyLoc(cn, pathNames)>";
		case \interface(cn, ex) : return "<prettyLoc(cn, pathNames)>";
		case \void() : return "void";
	}
	println("Warning - Unknown Typesymbol: <t>");
	return "unknown";
} 
 
public void showDot(M3 m, OFG ofg) = showDot(m, ofg, |home:///<m.id.authority>.dot|);
 
public void showDot(M3 m, OFG ofg, loc out) {
  writeFile(out, createDotFile(m, ofg));
}