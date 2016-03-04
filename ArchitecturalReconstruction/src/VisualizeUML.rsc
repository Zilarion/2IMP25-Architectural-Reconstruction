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
import List;
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
public str createDotFile(M3 m, tuple[rel[loc, loc], rel[loc, loc]] relations, bool classOnly) {
	str dotStr = dotSettings;
	
	pathNames = invert(m@names);
	
	// Generate all class blocks
	for (cl <- classes(m)) {
		dotStr += generateClass(cl, m, pathNames, classOnly);
	}
	
	// Generate all associations
	dotStr += generateAssociations(m, pathNames, relations);
	dotStr += "}";
    return dotStr;
}

/*
 * Generates a class string for use in a dot file from a class, model and model pathNames
 */
private str generateClass(loc cl, M3 m, rel[loc, str] pathNames, bool classOnly){
	str fields = "";
	str methods = "";
	if (!classOnly) { 
		fields = generateFields(cl, m, pathNames);
		methods = generateMethods(cl, m, pathNames);
	};
	return "\"<prettyLoc(cl, pathNames)>\" [
		'	label=\"{
		' 		<cl.path[1..]>|
		' 		<fields>|
		' 		<methods>
		'	}\"
		' ]
		'";
}

/*
 * Creates a dot file string for associations of the given model and pathNames of that model
 */
private str generateAssociations(M3 m, rel[loc, str]  pathNames, tuple[rel[loc, loc], rel[loc, loc]] relations) {
	str associationStr = "";
	
	// Generalization
	associationStr += generalization;
	for (<from, to> <- m@extends) {
         associationStr += "<prettyLocDep(to, pathNames)> -\> <prettyLocDep(from, pathNames)>\n";
 	}
 	
 	// Realization
 	associationStr += realisation; 
 	for (<from, to> <- m@implements) {
 		associationStr += "<prettyLocDep(to, pathNames)> -\> <prettyLocDep(from, pathNames)>\n";
 	}
 	
 	// Associations
 	associationStr += association;
	for (<from, to> <- relations[0]) {
 		associationStr += "<prettyLocDep(to, pathNames)> -\> <prettyLocDep(from, pathNames)>\n";
 	}
    
    // Dependency 
    associationStr += dependency;
    for (<from, to> <- relations[1]) {
 		associationStr += "<prettyLocDep(to, pathNames)> -\> <prettyLocDep(from, pathNames)>\n";
 	}
     
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
	// Create set of locations for each of our class methods
	set[loc] methods = {mt | mt <- m@containment[cl], isMethod(mt)};
	
	// Only take the relevant types/modifiers for our methods
	types = domainR(m@types, methods);
	modifiers = domainR(m@modifiers, methods);
	
	list[str] rList = [];
	
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
		
		str parStr = "";
		for (mParam <- m@containment[mt]) {
			mType = m@types[mParam];
			parStr += prettyLoc(mParam, pathNames) + " : " + prettify(getOneFrom(mType), pathNames) + ", ";
		}
		if (parStr != "") 
			parStr = substring(parStr, 0, size(parStr) - 2);
		
		// Create the resulting string
		rList += "<methodName>(<parStr>) : <typeName>\\l";
	}
	
	// Sort list to get a prettier UML
	rList = sort(rList, bool(str a, str b){ 
		str aStart = substring(a, 0, 1);
		str bStart = substring(b, 0, 1);
		if (aStart != bStart && aStart == "+") {
			return true;
		}
		if (aStart == "#" && bStart == "+") {
			return false;
		}
		if (aStart == "#" && bStart == "-") {
			return true;
		}
		if (aStart == "-" && aStart != bStart) {
			return false;
		}
		return a < b;
 	});
 	
	str resultStr = "";
	for (r <- rList) {
		resultStr += r;
	}
	return resultStr;
}

private str prettyLoc(loc c, rel[loc, str] pathNames) {
	if (size(pathNames[c]) == 0) {
		println("Warning - we do not have a pretty name for: ");
		println("\"" + c.path[1..] + "\"");
		return "";
	}
	return getOneFrom(pathNames[c]);
}

private str prettyLocDep(loc c, rel[loc, str] pathNames) {
	if (size(pathNames[c]) == 0) {
		println("Warning - we do not have a pretty dep name for: ");
		println("\"" + c.path[1..] + "\"");
		return "\"" + c.path[1..] + "\"";
	}
	return getOneFrom(pathNames[c]);
}

private str applyModifier(str name, Modifier modifier) {
	switch(modifier){
		case \private():
			return "- <name>";
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
		// Base types
		case \int() : return "int";
		case \short() : return "short";
		case \long() : return "long";
		case \char() : return "char";
		case \boolean() : return "boolean";
		case \void() : return "void";
		case \object() : return "object";
		case \byte() : return "byte";
		// Base class/interface types
		case \class(cn, ex) : 
			return "<prettyLoc(cn, pathNames)>";
		case \interface(cn, ex) : 
			return "<prettyLoc(cn, pathNames)>";
		case \method(ml, fp, returnType, x) :
			return prettify(returnType, pathNames);
		case \array(classType, count) : 
			return prettify(classType, pathNames) + "[<count>]";
		case \constructor(loc cn, classType) : 
			return prettyLoc(cn, pathNames); // :TODO: currently returns class name, perhaps leave empty?
	}
	println("Warning - Unknown Typesymbol: <t>");
	return "unknown";
} 

private str prettify(list[TypeSymbol] tSymbols, rel[loc,str] pathNames) {
	str result = "";
	for (t <- tSymbols) {
		result += prettify(t, pathNames) + ", ";
	}	
	if (result == "")
		return result;
		
	int size = size(result);
	result = substring(result, 0, size - 2);
	return result;
}
 
public void showDot(M3 m, tuple[rel[loc, loc], rel[loc, loc]] relations, bool classOnly) = showDot(m, relations, |home:///<m.id.authority>.dot|, classOnly);
 
public void showDot(M3 m, tuple[rel[loc, loc], rel[loc, loc]] relations, loc out, bool classOnly) {
  writeFile(out, createDotFile(m, relations, classOnly));
}