package fr.n7.stl.poo.declaration;

import java.util.*;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.MethodeSignature;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.poo.type.PooType;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;


public class InterfaceDeclaration extends ContainerDeclaration{

		PooDeclaration declaration;
		List<Instanciation> extensions;
		List<MethodeSignature> entetes;
		
		
		public InterfaceDeclaration(PooDeclaration declaration, List<Instanciation> extensions,
				List<MethodeSignature> entetes) {
			super();
			this.declaration = declaration;
			this.extensions = extensions;
			this.entetes = entetes;
			this.declaration.setContainer(this);
		}
		

	public boolean resolvePre(HierarchicalScope<Declaration> _scope) {
	
		if(_scope.knows(this.declaration.getName())){
			Logger.error("The name " +this.declaration.getName()+" of this Interface is already used ");
			return false;
		}
		this.declaration.setClasse(false);
		_scope.register(this.declaration);
		
		return true;
	}
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean result = true;
		
		for(Instanciation i : this.extensions){
			result = result && i.resolve(_scope); 
		}
		
		HierarchicalScope<Declaration> tbs = new SymbolTable(_scope);
		
		for(MethodeSignature m : this.entetes){
			result = result && m.resolve(tbs); 
		}
		
		return result;
	}
	
	public boolean checkType()
	{
		boolean result = true;
		
		for(Instanciation i : this.extensions)
		{
			Type t2 = i.getType();
			if(!t2.equalsTo(PooType.ClassType))
			{
				Logger.error("extends must be used with Class");
				return false;
			}	
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


	public List<MethodeSignature> getEntetes() {
		return entetes;
	}


	public void setEntetes(List<MethodeSignature> entetes) {
		this.entetes = entetes;
	}
		
		
		
}
