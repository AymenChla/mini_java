package fr.n7.stl.poo.call;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructorCall implements Expression{

	public ConstructorCall(Type type) {
		super();
		this.type = type;
	}

	public ConstructorCall(Type type, List<Expression> parametres) {
		super();
		this.type = type;
		this.parametres = parametres;
	}

	Type type;
	List<Expression> parametres;
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean result = true;
		
		Instanciation in = (Instanciation) this.type;
		result = in.resolve(_scope); 
		if(!in.getDeclaration().isClasse())
			Logger.error("We cant use an Interface as a Counstuctor");
		if(this.parametres != null){
			
			for(Expression e : this.parametres){
				result = result && e.resolve(_scope);
			}
			
		}
		
		return result;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
