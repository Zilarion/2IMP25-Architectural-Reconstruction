module VisualizeUML

// Import m3
import lang::java::m3::Core;

// Import OFG
import Java2OFG;
import FlowLanguage;

// IO
import IO;

alias OFG = rel[loc from, loc to];

public str dotDiagram(M3 m, OFG ofg) {
	map[loc, str] fields = createFieldMap(m);
	map[loc, str] methods = createMethodMap(m);
	
  return "digraph classes {
         '  fontname = \"Bitstream Vera Sans\"
         '  fontsize = 8
         '  node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ]
         '  edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]
         '
         '  <for (cl <- classes(m)) { /* a for loop in a string template, just like PHP */>
         ' \"N<cl>\" [
         ' 	label=\"{
         '		<cl.path[1..] /* Class path */>|
         '		<fields[cl]>|
         '		<methods[cl]>
         ' 	}\" 
         ' ]<} /* this is the end of the for loop */>
         '
         '  <for (<from, to> <- m@extends) {>
         '  \"N<to>\" -\> \"N<from>\" [arrowhead=\"empty\"]<}>
         '}";
}

private map[loc, str] createFieldMap(M3 m) {
	map[loc, str] fields = ();
	for (cl <- classes(m)) {
		str field = "";
		for (f <- m@containment[cl], isField(f)) {
			if (/<x:[^\/]+$>/ := f.path) {
				field += "+<x> : type\\l";
			}
		}
		fields += (cl : field);
	}	
	return fields;
}

private map[loc, str] createMethodMap(M3 m) {
	map[loc, str] methods = ();
	for (cl <- classes(m)) {
		str field = "";
		// This part below is copy paste ugly, :TODO: find better way to get constructor first
		// Find constructors and put those first
		for (met <- m@containment[cl], isConstructor(met)) {
			if (/<x:[^\/]+$>/ := met.path) {
				field += "+<x> : type\\l";
			}
		}
		// Then find all methods and put those in
		for (met <- m@containment[cl], isMethod(met), !isConstructor(met)) {
			println(met);
			if (/<x:[^\/]+$>/ := met.path) {
				field += "+<x> : type\\l";
			}
		}
		methods += (cl : field);
	}	
	return methods;
}
 
public void showDot(M3 m, OFG ofg) = showDot(m, ofg, |home:///<m.id.authority>.dot|);
 
public void showDot(M3 m, OFG ofg, loc out) {
  writeFile(out, dotDiagram(m, ofg));
}