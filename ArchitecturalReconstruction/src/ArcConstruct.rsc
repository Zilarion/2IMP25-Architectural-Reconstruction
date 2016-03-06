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

public void reconstruct(loc project, loc output, str filename) {
	// Create m3 model and program
	m = createM3FromEclipseProject(project);
	p = createOFG(project);
	
	// Get relations between classes from propagating the ofg
	relations = getPropagations(p, m);
	
	// Visualize
	showDot(m, relations, output + (filename + ".dot"), true, filename);
	showDot(m, relations, output + (filename + "-full.dot"), false, filename);
	showDotNeato(m, relations, output + (filename + ".neato"), false, filename);
	showDotNeato(m, relations, output + (filename + "-full.neato"), true, filename);
}

public void neakoReconstruct(loc project, loc output, str filename) {
	m = createM3FromEclipseProject(project);
	p = createOFG(project);
	relations = getPropagations(p, m);
	
	showDotNeato(m, relations, output + (filename + ".neato"), false, filename);
}

public void reconstructA2(){
	loc output = |home:///study/2imp25/2IMP25-Architectural-Reconstruction/output/|;
	reconstruct(|project://eLib|, output, "UML-eLib");
	reconstruct(|project://nekohtml-0.9.5//src//html//org/cyberneko//html|, output, "UML-neko-0.9.5");
	reconstruct(|project://nekohtml-1.9.21//src//org//cyberneko//html|, output, "UML-neko-1.9.21");
}

public void reconstructNeko() {
	loc output = |home:///study/2imp25/2IMP25-Architectural-Reconstruction/neko/|;
	neakoReconstruct(|project://nekohtml-0.8//src//html//org/cyberneko//html|, output, "0.8.00");
	neakoReconstruct(|project://nekohtml-0.9//src//html//org/cyberneko//html|, output, "0.9.00");
	neakoReconstruct(|project://nekohtml-0.9.1//src//html//org/cyberneko//html|, output, "0.9.01");
	neakoReconstruct(|project://nekohtml-0.9.2//src//html//org/cyberneko//html|, output, "0.9.02");
	neakoReconstruct(|project://nekohtml-0.9.3//src//html//org/cyberneko//html|, output, "0.9.03");
	neakoReconstruct(|project://nekohtml-0.9.4//src//html//org/cyberneko//html|, output, "0.9.04");
	neakoReconstruct(|project://nekohtml-0.9.5//src//html//org/cyberneko//html|, output, "0.9.05");
	neakoReconstruct(|project://nekohtml-1.9.6//src//org/cyberneko//html|, output, "1.9.06");
	neakoReconstruct(|project://nekohtml-1.9.6.1//src//org/cyberneko//html|, output, "1.9.06.1");
	neakoReconstruct(|project://nekohtml-1.9.6.2//src//org/cyberneko//html|, output, "1.9.06.2");
	neakoReconstruct(|project://nekohtml-1.9.7//src//org/cyberneko//html|, output, "1.9.07");
	neakoReconstruct(|project://nekohtml-1.9.8//src//org/cyberneko//html|, output, "1.9.08");
	neakoReconstruct(|project://nekohtml-1.9.9//src//org/cyberneko//html|, output, "1.9.09");
	neakoReconstruct(|project://nekohtml-1.9.10//src//org/cyberneko//html|, output, "1.9.10");
	neakoReconstruct(|project://nekohtml-1.9.11//src//org/cyberneko//html|, output, "1.9.11");
	neakoReconstruct(|project://nekohtml-1.9.12//src//org/cyberneko//html|, output, "1.9.12");
	neakoReconstruct(|project://nekohtml-1.9.13//src//org/cyberneko//html|, output, "1.9.13");
	neakoReconstruct(|project://nekohtml-1.9.14//src//org/cyberneko//html|, output, "1.9.14");
	neakoReconstruct(|project://nekohtml-1.9.15//src//org/cyberneko//html|, output, "1.9.15");	
	neakoReconstruct(|project://nekohtml-1.9.21//src//org/cyberneko//html|, output, "1.9.21");
}