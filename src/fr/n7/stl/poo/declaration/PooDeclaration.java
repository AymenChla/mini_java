package fr.n7.stl.poo.declaration;

import java.util.LinkedList;
import java.util.List;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.poo.definition.Attribut;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.PooType;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class PooDeclaration implements Declaration {
	
	boolean isClasse;
	List<Definition> definitions;
	
	public boolean isClasse() {
		return isClasse;
	}

	public void setClasse(boolean isClasse) {
		this.isClasse = isClasse;
	}

	String name;
	List<GenericTypeDeclaration> genDeclarations;
	
	
	public PooDeclaration(String name) {
		super();
		this.name = name;
	}

	public PooDeclaration(String name, List<GenericTypeDeclaration> genDeclarations) {
		super();
		this.name = name;
		this.genDeclarations = genDeclarations;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	
	public Type getType()
	{
		if(this.isClasse) return PooType.ClassType;
		return PooType.InterfaceType;
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public String getName() {
		return this.name;
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}

	public List<Attribut> getStaticAttributsOfClass() {
		
		List<Attribut> attributs = new LinkedList<Attribut>();
		for(Definition i : definitions)
			if(i.getAttribut() != null && i.isStatic())
				attributs.add(i.getAttribut());
			
		return attributs;
	}
	
	public List<Methode> getStaticMethodesOfClass() {
		
		List<Methode> methodes = new LinkedList<Methode>();
		for(Definition i : definitions)
			if(i.getMethode() != null && i.isStatic())
				methodes.add(i.getMethode());
			
		return methodes;
	}
		
		
		
}
