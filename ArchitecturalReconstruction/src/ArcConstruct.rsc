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
	m = createM3FromEclipseProject(|project://eLib|);
	p = createOFG(|project://eLib|);
	//text(p);
	ofg = buildGraph(p);
	//text(ofg);
	
	// Visualize
	showDot(m, ofg, |home:///study/2imp25/test.dot|);
}