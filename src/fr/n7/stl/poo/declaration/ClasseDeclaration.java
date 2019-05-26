package fr.n7.stl.poo.declaration;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;



import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.block.poo.methode.MethodeSignature;
import fr.n7.stl.poo.definition.Attribut;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.poo.type.PooType;
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
		this.declaration.setDefinitions(definitions);
		this.declaration.setContainer(this);
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
			
			//check if all methodes are implemented 
			InterfaceDeclaration id = (InterfaceDeclaration) i.getDeclaration().getContainer();
			
			for(MethodeSignature ms : id.getEntetes())
			{
				boolean found = false;
				for(Definition d : this.definitions)
				{
					if(d.getMethode().getEntete().equals(ms))
						found = true;
				}
				

				if(!found)
					Logger.error("Class must implement all methodes");
			}
			
		}
		
	
			
			
		HierarchicalScope<Declaration> newSymboleTable = new SymbolTable(_scope);
		
		for(Definition d : this.definitions){
			result = result && d.resolve(newSymboleTable); 
		}
		
		
		//System.out.println(newSymboleTable.toString());
		
		return result;
	}
	
	public boolean checkType()
	{
		boolean result = true;
		if(this.extension.instanciation != null)
		{
			Type t1 = this.extension.getType();
			if(!t1.equalsTo(PooType.ClassType))
			{
				Logger.error("extends must be used with class");
				return false;
			}
		}
		
		for(Instanciation i : this.implementations)
		{
			Type t2 = i.getType();
			if(!t2.equalsTo(PooType.InterfaceType))
			{
				Logger.error("implements must be used with Interface");
				return false;
			}
		}
		
		System.out.println(this.definitions.size());
		for(Definition d: this.definitions)
		{
			result = d.checkType() && result;
		}
		
	
		
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


	public Extension getExtension() {
		return extension;
	}


	public void setExtension(Extension extension) {
		this.extension = extension;
	}


	public List<Instanciation> getImplementations() {
		return implementations;
	}


	public void setImplementations(List<Instanciation> implementations) {
		this.implementations = implementations;
	}



	
	
	
	
}
