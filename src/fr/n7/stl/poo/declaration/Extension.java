package fr.n7.stl.poo.declaration;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class Extension implements Type {
	
	Instanciation instanciation;
	
	public Extension() {
	}
	public Extension(Instanciation instanciation) {
		this.instanciation = instanciation;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.instanciation.resolve(_scope);
	}
	
	public Type getType()
	{
		return this.instanciation.getType();
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	public boolean isPresent() {
		if (this.instanciation == null){
			return false;
		}
		return true;
	}
	@Override
	public boolean equalsTo(Type _other) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean compatibleWith(Type _other) {
		// TODO Auto-generated method stub
		return false;
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
	public Instanciation getInstanciation() {
		return instanciation;
	}
	public void setInstanciation(Instanciation instanciation) {
		this.instanciation = instanciation;
	}
	
	
}
