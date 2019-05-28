/**
 * 
 */
package fr.n7.stl.block.ast;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.block.ast.instruction.Conditional;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.Iteration;
import fr.n7.stl.block.ast.instruction.Repetition;
import fr.n7.stl.block.ast.instruction.Return;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Represents a Block node in the Abstract Syntax Tree node for the Bloc language.
 * Declares the various semantics attributes for the node.
 * 
 * A block contains declarations. It is thus a Scope even if a separate SymbolTable is used in
 * the attributed semantics in order to manage declarations.
 * 
 * @author Marc Pantel
 *
 */
public class Block {

	/**
	 * Sequence of instructions contained in a block.
	 */
	protected List<Instruction> instructions;
	private int parametersSize;
	private int offset;
	private Register register;
	
	/**
	 * Constructor for a block.
	 */
	public Block(List<Instruction> _instructions) {
		this.instructions = _instructions;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String _local = "";
		for (Instruction _instruction : this.instructions) {
			_local += _instruction;
		}
		return "{\n" + _local + "}\n" ;
	}
	
	/**
	 * Inherited Semantics attribute to check that all identifiers have been defined and
	 * associate all identifiers uses with their definitions.
	 * @param _scope Inherited Scope attribute that contains the defined identifiers.
	 * @return Synthesized Semantics attribute that indicates if the identifier used in the
	 * block have been previously defined.
	 */
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean resultat = true;
		
		SymbolTable tableSymbole = new SymbolTable(_scope);
		for(Instruction instruction : instructions){
			resultat = instruction.resolve(tableSymbole) && resultat;
		}
		
		return resultat;
	}
	/**
	 * Synthesized Semantics attribute to check that an instruction if well typed.
	 * @return Synthesized True if the instruction is well typed, False if not.
	 */	
	public boolean checkType() {
		boolean _result  = true;
		for(Instruction i : instructions){
			_result = _result && i.checkType();
		}
		return _result;
	}

	/**
	 * Inherited Semantics attribute to allocate memory for the variables declared in the instruction.
	 * Synthesized Semantics attribute that compute the size of the allocated memory. 
	 * @param _register Inherited Register associated to the address of the variables.
	 * @param _offset Inherited Current offset for the address of the variables.
	 */	
	public void allocateMemory(Register _register, int _offset) {
		this.register = _register;
		int _length = _offset;
		for (Instruction i : this.instructions) {
			_length += i.allocateMemory(this.register, _length);
		}
		this.offset = _length - _offset;
	}
	
	public List<Type> getTypesOfReturn(){
		List<Type> types = new ArrayList<Type>();
		for(Instruction i: instructions) {
			if(i instanceof Conditional)
			{
				Conditional c = (Conditional) i;
				types.addAll(c.getTypesOfReturn());
			}
			else if(i instanceof Iteration)
			{
				Iteration it = (Iteration) i;
				types.addAll(it.getTypesOfReturn());
			}
			else if(i instanceof Return){
				types.add(((Return) i).getType());
			}
		}
		
		return types;
	}

	/**
	 * Inherited Semantics attribute to build the nodes of the abstract syntax tree for the generated TAM code.
	 * Synthesized Semantics attribute that provide the generated TAM code.
	 * @param _factory Inherited Factory to build AST nodes for TAM code.
	 * @return Synthesized AST for the generated TAM code.
	 */
	public Fragment getCode(TAMFactory _factory) {
		Fragment frag = _factory.createFragment();
		System.out.println();
		for(Instruction i : instructions) {
			frag.append(i.getCode(_factory));
		}
		return frag;
	}

	
	public Fragment getCode2(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();
		for (Instruction i : this.instructions) {
			if (i instanceof Return) {
				((Return)i).setParametersSize(this.parametersSize);
			}
			fragment.append(i.getCode(_factory));
		}
		return fragment;
	}
	
	
	
	public int getParametersSize() {
		return parametersSize;
	}


	public void setParametersSize(int parametersSize) {
		this.parametersSize = parametersSize;
	}


	public List<Instruction> getInstructions() {
		return instructions;
	}


	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}

	
}
