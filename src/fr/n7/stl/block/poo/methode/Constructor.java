package fr.n7.stl.block.poo.methode;

import java.util.List;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class Constructor extends Definition implements Declaration, Instruction{
	

	Instanciation instanciation;
	List<ParameterDeclaration> parametres;
	Block bloc;
	
	
	
	public Constructor(Instanciation instanciation, List<ParameterDeclaration> parametres, Block bloc) {
		super();
		this.instanciation = instanciation;
		this.parametres = parametres;
		this.bloc = bloc;
	}

	@Override
	public String getName() {
		return this.instanciation.getName();
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public boolean checkType() {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

}
