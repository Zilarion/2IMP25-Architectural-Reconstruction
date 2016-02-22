module FindClasses

import IO;
import lang::java::m3::Core;
import lang::java::jdt::m3::Core;
import analysis::m3::Core;

public void findClass(){
	//str file = readFile(|project://ArchitecturalReconstruction/resources/eLib/Book.java|);
	model = createM3FromDirectory(|file:///ArchitecturalReconstruction/resources/eLib|);
	//evalProgram(file);
}