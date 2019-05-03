/**
 * 
 */
package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.declaration.TypeDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for a type conversion expression.
 * @author Marc Pantel
 *
 */
public class TypeConversion implements Expression {

	protected Expression target;
	protected Type type;
	protected String name;

	public TypeConversion(Expression _target, String _type) {
		this.target = _target;
		this.name = _type;
		this.type = null;
	}
	
	public TypeConversion(Expression _target, Type _type) {
		this.target = _target;
		this.name = null;
		this.type = _type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (this.type == null) {
			return "(" + this.name + ") " + this.target;
		} else {
			return "(" + this.type + ") " + this.target;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (this.type == null) {
			if(_scope.knows(this.name)) {
				Declaration local = _scope.get(this.name);
				if (local instanceof TypeDeclaration) {
					this.type = ((TypeDeclaration)local).getType();
					return this.target.resolve(_scope);
				} else {
					Logger.error(this.name + " is not a declared type.");
					this.target.resolve(_scope);
					return false;
				}
			} else {
				Logger.error(this.name + " is not a declared type.");
				this.target.resolve(_scope);
				return false;
			}
		} else {
			return this.target.resolve(_scope);
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		return _factory.createFragment();
	}

}
