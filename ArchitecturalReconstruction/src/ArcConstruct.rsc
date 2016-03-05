module ArcConstruct

// Personal lib
import FlowProgram;
import VisualizeUML;

// IO
import IO;

// Import m3
import lang::java::m3::Core;
import lang::java::jdt::m3::AST;
import lang::java::jdt::m3::Core;

// Java2ofg
import Java2OFG;
import FlowLanguage;

// ValueUI
import util::ValueUI;

public void reconstruct(loc project, loc output) {
	// Create m3 model and program
	m = createM3FromEclipseProject(project);
	p = createOFG(project);
	
	// Get relations between classes from propagating the ofg
	relations = getPropagations(p, m);
	
	// Visualize
	showDot(m, relations, output + "UML-eLib.dot", true);
	showDot(m, relations, output + "UML-eLib-full.dot", false);
	showDotNeato(m, relations, output + "UML-eLib.neato");
}

public void reconstructA2(){
	loc output = |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/|;
	reconstruct(|project://eLib|, output);
	reconstruct(|project://nekohtml-0.9.5//src//html//org/cyberneko//html|, output);
	reconstruct(|project://nekohtml-1.9.21//src//org//cyberneko//html|, output);
}