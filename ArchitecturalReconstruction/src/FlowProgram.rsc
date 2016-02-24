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

alias OFG = rel[loc from, loc to];

OFG buildGraph(Program p)  
	= { <as[i], fps[i]> | newAssign(x, cl, c, as) <- p.statements, constructor(c, fps) <- p.decls, i <- index(as) }
	+ { <cl + "this", x> | newAssign(x, cl, _, _) <- p.statements }
  	/* + ... etc */ 
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