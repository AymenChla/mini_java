/**
 * 
 */
package fr.n7.stl.block.ast.expression;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.declaration.FunctionDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.FunctionType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Abstract Syntax Tree node for a function call expression.
 * @author Marc Pantel
 *
 */
public class FunctionCall implements Expression {

	/**
	 * Name of the called function.
	 * TODO : Should be an expression.
	 */
	protected String name;
	
	/**
	 * Declaration of the called function after name resolution.
	 * TODO : Should rely on the VariableUse class.
	 */
	protected FunctionDeclaration function;
	
	/**
	 * List of AST nodes that computes the values of the parameters for the function call.
	 */
	protected List<Expression> arguments;
	
	/**
	 * @param _name : Name of the called function.
	 * @param _arguments : List of AST nodes that computes the values of the parameters for the function call.
	 */
	public FunctionCall(String _name, List<Expression> _arguments) {
		this.name = _name;
		this.function = null;
		this.arguments = _arguments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String _result = ((this.function == null)?this.name:this.function) + "( ";
		Iterator<Expression> _iter = this.arguments.iterator();
		if (_iter.hasNext()) {
			_result += _iter.next();
		}
		while (_iter.hasNext()) {
			_result += " ," + _iter.next();
		}
		return  _result + ")";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		Declaration nameFunction = _scope.get(this.name);
		if(nameFunction instanceof FunctionDeclaration ){
			this.function = (FunctionDeclaration)nameFunction;
			boolean result = true;
			for(Expression e : this.arguments){
				result = e.resolve(_scope) && result;
			}
			return result;
		}
		else{
			throw new SemanticsUndefinedException( "Error FunctionCall : Function Called Does not Exist.");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		return this.function.getType();
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException( "Semantics getCode is undefined in FunctionCall.");
	}


}
