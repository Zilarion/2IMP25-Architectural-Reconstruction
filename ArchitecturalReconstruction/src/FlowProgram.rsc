module FlowProgram

// Import m3
import lang::java::m3::Core;

import analysis::flow::ObjectFlow;
import lang::java::flow::JavaToObjectFlow;
import List;
import Relation;

// Import OFG
import Java2OFG;
import FlowLanguage;

// ValueUI
import util::ValueUI;

alias OFG = rel[loc from, loc to];

OFG buildGraph(Program p)  
	= { <ap[i], fps[i]> | newAssign(x, cl, ctr, ap) <- p.statements, constructor(ctr, fps) <- p.decls, i <- index(ap) }
	+ { <cl + "this", x> | newAssign(x, cl, _, _) <- p.statements } 
	+ { <src, t> | assign(t, _, src) <- p.statements }
	+ { <recv, m + "this"> | call(_, _, recv, m, _) <- p.statements }	
	+ { <ap[i], fps[i]> | call(_, _, _, m, ap) <- p.statements, method(m, fps) <- p.decls, i <- index(ap) }
	+ { <m + "return", t> | call(t, _, _, m, _) <- p.statements }
  	;
  	
OFG propFast(OFG g, rel[loc, loc] gen, rel[loc, loc] kill, bool back) {
	OFG IN = {};
	OFG OUT = gen + (IN - kill);
	if (back) {
		g = g<1, 0>;
	}
	
	solve (IN, OUT) {
		IN = g o OUT;
		OUT = gen + IN - kill;
	}
	
	return OUT;
}

OFG forwardProp(OFG g, rel[loc, loc] gen, rel[loc, loc] kill) {
	return propFast(g, gen, kill, false);
}

OFG backwardProp(OFG g, rel[loc, loc] gen, rel[loc, loc] kill) {
	return propFast(g, gen, kill, true);
}

tuple[rel[loc, loc], rel[loc, loc]] getPropagations(Program p, M3 m){
	OFG ofg = buildGraph(p);
	
	associations = {};
	dependencies = {};	
	kill = {};
	gen = {<class + "this", class> | newAssign(_, class, _, _) <- p.statements, class in classes(m)};
	
	//Forward
	OFG forward = forwardProp(ofg, gen, kill);
	associations += getAssociations(forward, m);
	dependencies += getDependencies(forward, m);
	
	//Backward
	OFG backward = backwardProp(ofg, gen, kill);
	associations += getAssociations(backward, m);
	dependencies += getDependencies(backward, m);
	
	////Weakly forward uses the same gen variable
	weaklyForwardGen = gen;
	OFG weaklyForwardProp = forwardProp(ofg, weaklyForwardGen, kill);
	
	associations += getAssociations(weaklyForwardProp, m);
	dependencies += getDependencies(weaklyForwardProp, m);
	
	//Weakly backward
	weaklyBackwardGen = {<class, cast> | assign(_, cast, class) <- p.statements && cast in classes(m)};
	weaklyBackwardGen += {<method + "return", cast> | call(_, cast, _, method, _) <- p.statements && cast in classes(m)};
	OFG weaklyBackwardProp = backwardProp(ofg, weaklyBackwardGen, kill);
	
	associations += getAssociations(weaklyBackwardProp, m);
	dependencies += getDependencies(weaklyBackwardProp, m);
	
	// Remove relations with external classes
	associations = {<from, to> | <from, to> <- associations && from in classes(m) && to in classes(m)};
	dependencies = {<from, to> | <from, to> <- dependencies && from in classes(m) && to in classes(m)}; 
	
	//Remove self-loops
	associations = {<from, to> | <from, to> <- associations && from != to};
	dependencies = {<from, to> | <from, to> <- dependencies && from != to};
	
	//Find extended classes and remove superfluous relations
	associations = findExtensions(associations, m);
	dependencies = findExtensions(dependencies, m);
	
	tuple[rel[loc, loc], rel[loc, loc]] relations = <associations, dependencies>;
	return relations;
}

public rel[loc, loc] getAssociations(OFG ofg, M3 m){
	// Get associations (A { B b;})
	rel[loc, loc] fields = {<field, class> | <field, class> <- ofg, isField(field)};
	rel[loc, loc] associations = {<from, class> | <from, to> <- m@containment, <to, class> <- fields, class in classes(m)};
	
	return associations;
}

public rel[loc, loc] getDependencies(OFG ofg, M3 m){
	rel[loc, loc] dependencies = {};
	
	// Get all dependencies (class A { void f(B b) { b.g(); } })
	//Get parameters and variables
	par = {<from, to> | <from, to> <- ofg && from.scheme == "java+parameter"};
	var = {<from, to> | <from, to> <- ofg && from.scheme == "java+variable" && to in classes(m)};
	
	//Get methods for parameters
	mPar = {<from, to> | <from, to> <- m@containment && <method, to> <- par && method in classes(m)};
	dependencies += {<a, c> | <a, method> <- m@containment && <method, c> <- mPar};
	
	// Get all dependencies (class A { void f() { B b; … b.g(); } } )
	//Get methods for variables
	mVar = {<method, to> | <method, variable> <- m@containment && <variable, to> <- var};
	dependencies += {<a, b> | <a, method> <- m@containment && <method, b> <- mVar};
	
	//text(mPar);
	//text(mVar);
	return dependencies;
}

public rel[loc, loc] findExtensions(OFG ofg, M3 m){
	//All extended classes
	extendedClasses = {to | <from, to> <- m@extends};
	
	//Iterate over all classes and only leave the extended relation
	for(loc class <- classes(m)) {
	
		rel[loc, loc] relationsFromCurrentClass = {<from, to> | <from, to> <- ofg && from == class};
	
		for(loc extendedClass <- extendedClasses){
			rel[loc,loc] relationsFromChild = {<class, from> | <from, to> <- m@extends && to == extendedClass};
			
			if(relationsFromChild <= relationsFromCurrentClass){
				//Remove superfluous relations
				ofg -= relationsFromChild;
				
				//Add extension relation
				ofg += <class, extendedClass>;
			}
		}
	}
	return ofg;
}
