package fr.n7.stl.poo.call;

import fr.n7.stl.block.ast.expression.AbstractIdentifier;
import fr.n7.stl.block.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.definition.Attribut;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class AttributAssignment extends AbstractIdentifier implements AssignableExpression{
	
	 private Attribut attribut;
	 
	 public Attribut getAttribut() {
		return attribut;
	}

	public void setAttribut(Attribut attribut) {
		this.attribut = attribut;
	}

	public AttributAssignment(String attribut_name) {
		 super(attribut_name);
	}
	
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (((HierarchicalScope<Declaration>)_scope).knows(this.name)) {
			Declaration _declaration = _scope.get(this.name);
			
			if (_declaration instanceof Attribut) {
				this.attribut = ((Attribut) _declaration);
				if(this.attribut.isFinal())
				{
					Logger.error("Can't assign final attribut");
					return false;
				}
				return true;
			}
			else {
				Logger.error(this.name + " is not an attribut.");
				return false;
			}
		} else {
			Logger.error("The attribut " + this.name + " has not been found.");
			return false;	
		}
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
