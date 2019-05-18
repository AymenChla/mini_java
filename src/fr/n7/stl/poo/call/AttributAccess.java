package fr.n7.stl.poo.call;

import fr.n7.stl.block.ast.expression.AbstractField;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributAccess extends AbstractField implements Expression {

	public AttributAccess(Expression _record, String _name) {
		super(_record, _name);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
