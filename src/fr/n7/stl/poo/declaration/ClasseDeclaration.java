package fr.n7.stl.poo.declaration;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.Typed;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ClasseDeclaration extends ContainerDeclaration implements Type {
	
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
	
	
	public boolean resolvePre(HierarchicalScope<Declaration> _scope) {
		if(_scope.knows(this.declaration.getName())){
			Logger.error("The name " +this.declaration.getName()+" of this Class is already used ");
			return false;
		}
		this.declaration.setClasse(true);
		_scope.register(this.declaration);
		return true;
	}
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean result = true;
		
		
		if(this.extension.isPresent())
		{
			result = extension.resolve(_scope);
		}
		
		for(Instanciation i : this.implementations){
			result = result && i.resolve(_scope); 
		}
		
		HierarchicalScope<Declaration> newSymboleTable = new SymbolTable(_scope);
		
		for(Definition d : this.definitions){
			result = result && d.resolve(newSymboleTable); 
		}
		
	System.out.println(newSymboleTable.toString());
		
		return result;
	}
	
	public Type getType()
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}


	@Override
	public boolean equalsTo(Type _other) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean compatibleWith(Type _other) {
		
		if(_other.equals(extension.getType()))
			return true;
		for(Instanciation i : this.implementations){
			if (_other.equals(i.getType())) return true;
		}
		
		return false;
					
	}
	
	public String getName(){
		return this.declaration.name;
	}


	@Override
	public Type merge(Type _other) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}


	public List<Definition> getDefinitions() {
		return definitions;
	}


	public List<Methode> getMethodsOfClass() {
		List<Methode> methods = new LinkedList<Methode>();
		for(Definition i : definitions)
			if(i instanceof Methode)
				methods.add((Methode) i);
		return methods;
	}
		
		
	
	
	
	
}
