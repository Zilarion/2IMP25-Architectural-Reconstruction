module FindClasses

import IO;
//import analysis::m3::AST;
//import lang::java::m3::AST; 

// Import m3
import lang::java::m3::Core;
import lang::java::jdt::m3::AST;
import lang::java::jdt::m3::Core;


import Java2OFG;
import util::ValueUI;

public void findClass(){
	//ast = createAstsFromDirectory(|file:///Users/ruudandriessen/study/2imp25/2IMP25-Architectural-Reconstruction/ArchitecturalReconstruction/resources/eLib|
	// , true);
	model = createM3FromEclipseProject(|project://eLib|);
	//ofg = createOFG(|project://resources/eLib|);
	
	//text(model);
	// dographviz
	//print(graph);
}

//data FlowProgram = flowProgram(set[FlowDecl] decls, set[FlowStm] statements);
//
//alias OFG = rel[loc from, loc to];
//
//OFG buildGraph(Program p) = {};