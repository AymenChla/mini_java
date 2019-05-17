package fr.n7.stl.poo.declaration;

import java.util.List;

import fr.n7.stl.block.poo.methode.MethodeSignature;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;

public class ClasseDeclaration extends ContainerDeclaration {
	
	int finaOrAbstract;
	PooDeclaration declaration;
	Extension extension;
	List<Instanciation> implementations; 
	List<Definition> definitions;
	public ClasseDeclaration(int finaOrAbstract, PooDeclaration declaration, Extension extension,
			List<Instanciation> implementations, List<Definition> definitions) {
		super();
		this.finaOrAbstract = finaOrAbstract;
		this.declaration = declaration;
		this.extension = extension;
		this.implementations = implementations;
		this.definitions = definitions;
	}
	
	
	
	
	
	
}
