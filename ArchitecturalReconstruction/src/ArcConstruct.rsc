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
	
	// Visualize
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-eLib.dot|, true);
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-eLib-full.dot|, false);
	showDotNeato(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-eLib.neato|);
	
	m = createM3FromEclipseProject(|project://nekohtml-0.9.5//src//html//org/cyberneko//html|);
	p = createOFG(|project://nekohtml-0.9.5//src//html//org/cyberneko//html|);
	
	// Get relations between classes from propagating the ofg
	relations = getPropagations(p, m);
	
	// Visualize
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-0.9.5.dot|, true);
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-0.9.5-full.dot|, false);
	showDotNeato(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-0.9.5.neato|);
	
	m = createM3FromEclipseProject(|project://nekohtml-1.9.21//src//org//cyberneko//html|);
	p = createOFG(|project://nekohtml-1.9.21//src//org//cyberneko//html|);
	
	// Get relations between classes from propagating the ofg
	relations = getPropagations(p, m);
	
	// Visualize
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-1.9.21.dot|, true);
	showDot(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-1.9.21-full.dot|, false);
	showDotNeato(m, relations, |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/UML-neko-1.9.21.neato|);
}