module FindClasses

import IO;
import analysis::m3::AST;
import lang::java::m3::AST; 
import Java2OFG;

public void findClass(){
	myModel = createAstsFromDirectory(|file:///Users/ruudandriessen/study/2imp25/2IMP25-Architectural-Reconstruction/ArchitecturalReconstruction/resources/eLib|
, true);
	ofg = createOFG(myModel);
	// dographviz
	print(graph);
}