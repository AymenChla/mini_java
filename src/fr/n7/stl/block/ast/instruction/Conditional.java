/**
 * 
 */
package fr.n7.stl.block.ast.instruction;

import java.util.Optional;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a conditional instruction.
 * @author Marc Pantel
 *
 */
public class Conditional implements Instruction {

	protected Expression condition;
	protected Block thenBranch;
	protected Optional<Block> elseBranch;

	public Conditional(Expression _condition, Block _then, Block _else) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = Optional.of(_else);
	}

	public Conditional(Expression _condition, Block _then) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = Optional.empty();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "if (" + this.condition + " )" + this.thenBranch + ((this.elseBranch.isPresent())?(" else " + this.elseBranch.get()):"");
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean resultat;
		
		if(this.condition.resolve(_scope)){
			
			resultat = this.thenBranch.resolve(_scope);
			if(this.elseBranch.isPresent()){
				resultat &= this.elseBranch.get().resolve(_scope);
			}
			return resultat;
			
			
		}
		else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		boolean _result = this.condition.getType().compatibleWith(AtomicType.BooleanType) && this.thenBranch.checkType();
		if(this.elseBranch.isPresent()) _result = _result && this.elseBranch.get().checkType();
		return _result;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		thenBranch.allocateMemory(_register, _offset);
		
		if(elseBranch.isPresent()) {
			elseBranch.get().allocateMemory(_register, _offset);
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment frag = _factory.createFragment();
		
		int idCond = _factory.createLabelNumber();
		
		frag.append(this.condition.getCode(_factory));
		
		//Test de la condition
		frag.add(_factory.createJumpIf("elseBranch"+ idCond,0));
		
		frag.append(thenBranch.getCode(_factory));
		
		frag.add(_factory.createJump("endCondition"+idCond));
		
		frag.addSuffix("elseBranch"+idCond+":");
		
		if(elseBranch.isPresent()) {
			frag.append(elseBranch.get().getCode(_factory));
		}
		
		frag.addSuffix("endCondition"+idCond+":");
		
		return frag;
		
		
	}

}
