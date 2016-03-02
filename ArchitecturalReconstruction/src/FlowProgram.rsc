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
	+ { <m + "return", t> | call(t, _, _, m, _) <- p.statements }	// Method returns -> called from where
  	;
  	
OFG prop(OFG g, rel[loc,loc] gen, rel[loc,loc] kill, bool back) {
	  OFG IN = { };
	  OFG OUT = gen + (IN - kill);
	  gi = g<to,from>;
	  set[loc] pred(loc n) = gi[n];
	  set[loc] succ(loc n) = g[n];
	  
	  solve (IN, OUT) {
		    IN = { <n,\o> | n <- carrier(g), p <- (back ? pred(n) : succ(n)), \o <- OUT[p] };
		    OUT = gen + (IN - kill);
	  }
	  
	  return OUT;
}

tuple[rel[loc, loc], rel[loc, loc]] getPropagations(Program p, M3 m){
	OFG ofg = buildGraph(p);
	
	associations = {};
	dependencies = {};	
	kill = {};
	gen = {<class + "this", class> | newAssign(_, class, _, _) <- p.statements && class in classes(m)};
	
	//Forward
	OFG forwardProp = prop(ofg, gen, kill, false);
	associations += getAssociations(forwardProp, m);
	dependencies += getDependencies(forwardProp, m);
	
	//Weakly forward uses the same gen variable, so is the same
	OFG weaklyForwardProp = forwardProp;
	associations += getAssociations(weaklyForwardProp, m);
	dependencies += getDependencies(weaklyForwardProp, m);
	
	
	//Backward
	OFG backwardProp = prop(ofg, gen, kill, true);
	associations += getAssociations(backwardProp, m);
	dependencies += getDependencies(backwardProp, m);
	
	//Weakly backward
	weaklyBackwardGen = {<class, cast> | assign(_, cast, class) <- p.statements && cast in classes(m)};
	weaklyBackwardGen += {<method + "return", cast> | call(_, cast, _, method, _) <- p.statements && cast in classes(m)};
	OFG weaklyBackwardProp = prop(ofg, weaklyBackwardGen, kill, true);
	associations += getAssociations(weaklyBackwardProp, m);
	dependencies += getDependencies(weaklyBackwardProp, m);
	
	
	//Find extended classes and remove superfluous relations
	associations = findExtensions(associations, m);
	dependencies = findExtensions(dependencies, m);
	
	//Remove self-loops
	associations = {<from, to> | <from, to> <- associations && from != to};
	dependencies = {<from, to> | <from, to> <- dependencies && from != to};
	
	tuple[rel[loc, loc], rel[loc, loc]] relations = <associations, dependencies>;
	return relations;
}

public rel[loc, loc] getAssociations(OFG ofg, M3 m){
	rel[loc, loc] associations = {};
	
	rel[loc, loc] fields = {<field, class> | <field, class> <- ofg && isField(field)};
	associations += {<from, class> | <from, to> <- m@containment && <to, class> <- fields && class in classes(m)};
	
	return associations;
}

public rel[loc, loc] getDependencies(OFG ofg, M3 m){
	rel[loc, loc] dependencies = {};
	
	//Get parameters
	par = {<from, to> | <from, to> <- ofg && from.scheme == "java+parameter"};
	//Get methods for parameters
	mPar = {<from, to> | <from, to> <- m@containment && <a, to> <- par && a in classes(m)};
	dependencies += {<a, c> | <a, method> <- m@containment && <method, c> <- mPar};
	
	//Get variables
	var = {<from, to> | <from, to> <- ofg && from.scheme == "java+variable" && to in classes(m)};
	//Get methods for variables
	mVar = {<method, to> | <method, variable> <- m@containment && <variable, to> <- var};
	dependencies += {<a, b> | <a, method> <- m@containment && <method, b> <- mVar};
	
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
