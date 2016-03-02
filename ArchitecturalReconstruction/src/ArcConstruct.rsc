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

public void f(){
	// Create m3 model and program
	m = createM3FromEclipseProject(|project://eLib|);
	p = createOFG(|project://eLib|);
	
	// Get relations between classes from propagating the ofg
	relations = getPropagations(p, m);
	text(relations);

	// Visualize
	showDot(m, relations, |home:///study/2imp25/UML.dot|);
}