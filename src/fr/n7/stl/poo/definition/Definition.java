package fr.n7.stl.poo.definition;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class Definition {
	
	boolean publicOrPrivate;
	boolean isStatic;
	int finalOrAbstract;
	Attribut attribut;
	Methode methode;
	
	public Definition(boolean publicOrPrivate, boolean isStatic, int finalOrAbstract, Methode methode) {
		super();
		this.publicOrPrivate = publicOrPrivate;
		this.isStatic = isStatic;
		this.finalOrAbstract = finalOrAbstract;
		this.methode = methode;
	}

	public Definition(boolean publicOrPrivate, boolean isStatic, int finalOrAbstract, Attribut attribut) {
		super();
		this.publicOrPrivate = publicOrPrivate;
		this.isStatic = isStatic;
		this.finalOrAbstract = finalOrAbstract;
		this.attribut = attribut;
	}
	
	
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	
	public Type getType()
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
}
