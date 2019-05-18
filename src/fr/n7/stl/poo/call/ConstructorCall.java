package fr.n7.stl.poo.call;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class ConstructorCall implements Expression{

	public ConstructorCall(Type type, List<Expression> parametres) {
		super();
		this.type = type;
		this.parametres = parametres;
	}

	Type type;
	List<Expression> parametres;
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean result = true;
		for(Expression e : this.parametres){
			result = result && e.resolve(_scope);
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
