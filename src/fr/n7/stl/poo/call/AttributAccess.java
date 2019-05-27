package fr.n7.stl.poo.call;

import fr.n7.stl.block.ast.expression.AbstractAttribut;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.poo.declaration.ClasseDeclaration;
import fr.n7.stl.poo.definition.Attribut;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributAccess extends AbstractAttribut implements Expression {

	public AttributAccess(Expression _record, String _name) {
		super(_record,_name);
	}
	

	
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment frag = this.record.getCode(_factory);
		Instanciation inst = (Instanciation) this.record;
		
		int recordLength = inst.length();
		int attributLength = this.attribut.getType().length();
		int attributOffset = this.attribut.getOffset();
		
		frag.add(_factory.createPop(0, recordLength - attributOffset - attributLength));
		
		frag.add(_factory.createPop(attributLength, attributOffset));
		
		return frag;
	}
	
	
}
