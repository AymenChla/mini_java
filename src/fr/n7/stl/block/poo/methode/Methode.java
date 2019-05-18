package fr.n7.stl.block.poo.methode;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class Methode extends Definition implements Declaration,Instruction{
	
	MethodeSignature entete;
	Block bloc;
	
	
	
	public Methode(MethodeSignature entete, Block bloc) {
		this.entete = entete;
		this.bloc = bloc;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		if(!_scope.accepts(this)){
			Logger.error("Erreur Resolve Variable Declaration");
			return false;
		}
		else{
			 _scope.register(this);
			  HierarchicalScope<Declaration> nvlTable = new SymbolTable(_scope);
			  boolean result = true;
			  for(ParameterDeclaration p : this.entete.parametres){
				  result = p.getType().resolve(_scope) && result;
				  if(nvlTable.accepts(p)) {
					  nvlTable.register(p);
				  }
				  else {
					  return false;
				  }
			  }
			  this.bloc.resolve(nvlTable);
			  return result;
		}
		
	}
	
	public Type getType()
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public boolean checkType() {
		return this.bloc.getTypeOfReturn().compatibleWith(this.getType());
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return this.entete.name;
	}
}
