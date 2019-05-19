package fr.n7.stl.poo.definition;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.call.ConstructorCall;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class Attribut implements Declaration {
	
	Type type;
	String ident;
	Expression expression;
	
	
	
	public Attribut(Type type, String ident) {
		super();
		this.type = type;
		this.ident = ident;
	}

	public Attribut(Type type, String ident, Expression expression) {
		super();
		this.type = type;
		this.ident = ident;
		this.expression = expression;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean result = type.resolve(_scope);
		if(this.expression != null){
			result = ((ConstructorCall)expression).resolve(_scope);
			Declaration d = new VariableDeclaration(this.ident, this.type, this.expression);
			if(_scope.accepts(d))
			{
				_scope.register(d);
				result = this.expression.resolve(_scope);
			}
			else{
				Logger.error("This attribut " + this.ident + " already exists");
			}
		}
		else{
			Declaration d = new VariableDeclaration(this.ident, this.type, null);
			if(_scope.accepts(d))
			{
				_scope.register(d);
			}
			else{
				Logger.error("This attribut " + this.ident + " already exists");
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

	@Override
	public String getName() {
		return this.ident;
	}
}
