package fr.n7.stl.block.poo.methode;

import java.util.List;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodeSignature implements Declaration {
	
	Type type;
	String name;
	List<ParameterDeclaration> parametres;

	
	public MethodeSignature(String name, Type type, List<ParameterDeclaration> parametres) {
		super();
		this.type = type;
		this.name = name;
		this.parametres = parametres;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(_scope.accepts(this)){
			_scope.register(this);
		}
		else{
			Logger.error("The name of this method " + this.getName() + " already exists");
			return false;
		}
		
		return true;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		MethodeSignature other = (MethodeSignature) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parametres == null) {
			if (other.parametres != null)
				return false;
		} else if (!parametres.equals(other.parametres))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		
		return true;
	}

	
	
	
	
}
