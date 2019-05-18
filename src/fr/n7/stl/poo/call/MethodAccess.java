package fr.n7.stl.poo.call;

import java.lang.reflect.Method;
import java.util.List;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.FunctionDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.poo.declaration.ClasseDeclaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodAccess implements Expression{

	Expression expression;
	String methode;
	List<Expression> parametres;
	
	public MethodAccess(Expression expression, String methode) {
		super();
		this.expression = expression;
		this.methode = methode;
	}

	public MethodAccess(Expression expression, String methode, List<Expression> parametres) {
		super();
		this.expression = expression;
		this.methode = methode;
		this.parametres = parametres;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean result = this.expression.resolve(_scope);
		Type typeClasse = this.expression.getType();
		
		if(typeClasse instanceof ClasseDeclaration){
			ClasseDeclaration cld = (ClasseDeclaration) typeClasse;
			cld = (ClasseDeclaration) _scope.get(cld.getName());
			for(Methode m : cld.getMethodsOfClass())
				if(m.getName().equals(this.methode))
					return true;
			Logger.error("The name of this method does not exist ");
		}else{
			Logger.error("It s not an object");
		}
		
		return false;
		
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException( "Semantics getType is undefined in ConditionalExpression.");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException( "Semantics getType is undefined in ConditionalExpression.");
	}

}
