package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.ast.type.declaration.FieldDeclaration;
import fr.n7.stl.block.poo.methode.Methode;
import fr.n7.stl.poo.declaration.ClasseDeclaration;
import fr.n7.stl.poo.declaration.PooDeclaration;
import fr.n7.stl.poo.definition.Attribut;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.util.Logger;

/**
 * Common elements between left (Assignable) and right (Expression) end sides of assignments. These elements
 * share attributes, toString and getType methods.
 * @author Marc Pantel
 *
 */
public abstract class AbstractAttribut implements Expression {

	protected Expression record;
	protected String name;
	protected Attribut attribut;
	protected boolean isStatic = false;
	
	/**
	 * Construction for the implementation of a record field access expression Abstract Syntax Tree node.
	 * @param _record Abstract Syntax Tree for the record part in a record field access expression.
	 * @param _name Name of the field in the record field access expression.
	 */
	public AbstractAttribut(Expression _record, String _name) {
		this.record = _record;
		this.name = _name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.record + "." + this.name;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean result = this.record.resolve(_scope);
		if(record instanceof Instanciation)
		{
			Instanciation inst = (Instanciation) this.record;
			
			if(_scope.knows(inst.getName()))
			{
				if(_scope.get(inst.getName()) instanceof PooDeclaration)
				{	
					((Instanciation) record).setDeclaration((PooDeclaration) _scope.get(inst.getName()));
					
					
					PooDeclaration cld = (PooDeclaration) _scope.get(inst.getName());
					cld = (PooDeclaration) _scope.get(cld.getName());
					for(Attribut a : cld.getStaticAttributsOfClass())
						if(a.getName().equals(this.name))
						{
							this.attribut = a;
							this.isStatic = true;
							return result;
						}
					Logger.error("The name of this attribut does not exist or not static attribut");
				}
				else{
					Logger.error("class doesn't exist");
				}
			}
		}
		else{
			Logger.error("########");
		}
		
		return false;
	}

	/**
	 * Synthesized Semantics attribute to compute the type of an expression.
	 * @return Synthesized Type of the expression.
	 */
	public Type getType() {
		Instanciation inst = (Instanciation) this.record;
		ClasseDeclaration cld = (ClasseDeclaration) inst.getDeclaration().getContainer();
		for(Definition d : cld.getDefinitions())
		{
			if(d.getAttribut() != null && d.getName().equals(this.name))
			{
				return d.getAttribut().getType();
			}
		}
		
		return AtomicType.ErrorType;
		
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	
}