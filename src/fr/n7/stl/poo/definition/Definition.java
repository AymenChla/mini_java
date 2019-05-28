package fr.n7.stl.poo.definition;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Constructor;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class Definition implements Declaration{
	
	boolean publicOrPrivate;
	boolean isStatic;
	int finalOrAbstract;
	Attribut attribut;
	Methode methode;
	Constructor constructor;
	
	
	public Attribut getAttribut() {
		return attribut;
	}

	public void setAttribut(Attribut attribut) {
		this.attribut = attribut;
	}

	public Methode getMethode() {
		return methode;
	}

	public void setMethode(Methode methode) {
		this.methode = methode;
	}

	public Definition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Definition(boolean publicOrPrivate, boolean isStatic, int finalOrAbstract, Methode methode) {
		super();
		this.publicOrPrivate = publicOrPrivate;
		this.isStatic = isStatic;
		this.finalOrAbstract = finalOrAbstract;
		this.methode = methode;
		this.methode.setStatic(isStatic);
	}

	public Definition(boolean publicOrPrivate, boolean isStatic, int finalOrAbstract, Attribut attribut) {
		super();
		this.publicOrPrivate = publicOrPrivate;
		this.isStatic = isStatic;
		this.finalOrAbstract = finalOrAbstract;
		this.attribut = attribut;
		this.attribut.setStatic(isStatic);
		this.attribut.publicOrPrivate = this.publicOrPrivate;
	}
	
	public Definition(boolean publicOrPrivate, Constructor constructor)
	{
		this.publicOrPrivate = publicOrPrivate;
		this.constructor = constructor;
	}
	
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean result= true;
		
		if(this.attribut != null ) result = this.attribut.resolve(_scope);
		if(this.methode != null ) result  = this.methode.resolve(_scope);
		if(this.constructor != null ) result  = this.constructor.resolve(_scope);
		
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

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	
	public boolean checkType() {
		if(this.methode != null) 
			return this.methode.checkType();
		else if(this.attribut != null)
			return this.attribut.checkType();
		
		return this.constructor.checkType();
	}

	@Override
	public String getName() {
		if(this.methode != null) 
			return this.methode.getName();
		else if (this.attribut != null)
			return this.attribut.getName();
		
		return this.constructor.getName();
	}

	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	public int allocateMemory(Register register, int offset) {
		if(this.attribut != null ) {
			return this.attribut.allocateMemory(register, offset);
		}else if(this.methode != null){
			return this.methode.allocateMemory(register, offset);
		}
		
		//construct
		return this.constructor.allocateMemory(register, offset);
	}

	public boolean isPublicOrPrivate() {
		return publicOrPrivate;
	}

	public void setPublicOrPrivate(boolean publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}

		
	
	
}
