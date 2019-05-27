package fr.n7.stl.block.poo.methode;

import java.util.List;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.declaration.ClasseDeclaration;
import fr.n7.stl.poo.declaration.PooDeclaration;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

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
		if(_scope.knows(this.getName()))
		{
			PooDeclaration pod = (PooDeclaration)_scope.get(this.getName());
			this.instanciation.setDeclaration(pod);
			if(this.getName().equals(this.instanciation.getDeclaration().getName()))
			{
				boolean result = true;
				HierarchicalScope<Declaration> scope = new SymbolTable(_scope);
				if(this.parametres != null)
					for(ParameterDeclaration param : this.parametres)
					{
						result = param.resolve(scope) && result;
					}
				return this.bloc.resolve(scope) && result;
			}
			else {
				Logger.error("Constructor must have the same name of the class");
			}
		}
		else {
			Logger.error("Constructor must have the same name of the class");
		}
		
		return false;
	}

	@Override
	public boolean checkType() {
		return this.bloc.checkType();
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	public List<ParameterDeclaration> getParametres() {
		return parametres;
	}

	public void setParametres(List<ParameterDeclaration> parametres) {
		this.parametres = parametres;
	}

	
}
