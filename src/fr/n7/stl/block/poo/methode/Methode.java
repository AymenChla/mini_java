package fr.n7.stl.block.poo.methode;

import java.util.List;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Assignment;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.call.AttributAccess;
import fr.n7.stl.poo.call.AttributAssignment;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class Methode extends Definition implements Declaration,Instruction{
	
	MethodeSignature entete;
	Block bloc;
	boolean isStatic = false;
	
	
	
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

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
			  if(this.entete.parametres != null)
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
			  
			  
			  if(this.isStatic())
			  {
				  
				  for(Instruction i: this.bloc.getInstructions())
				  {
					  if(i instanceof Assignment)
					  {
						  
						  Assignment assignment = (Assignment) i;
						
						  if(assignment.getAssignable() instanceof AttributAssignment)
						  {
							 
							  AttributAssignment access = (AttributAssignment) assignment.getAssignable();
							if(!access.getAttribut().isStatic())
							{
								Logger.error("Can't access to not static attribut inside static methode");
							}
						  }	
						  
						  if(assignment.getValue() instanceof AttributAssignment)
						  {

							AttributAssignment access = (AttributAssignment) assignment.getValue();
							if(!access.getAttribut().isStatic())
							{
								Logger.error("Can't access to not static attribut inside static methode");
							}
						  }	
					
					  }
				  }
			  }
			  
			  
			  
			  return result;
		}
		
	}
	
	public Type getType()
	{
		return this.entete.getType();
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		throw new SemanticsUndefinedException("Semantics getCode is not implemented in PointerAccess.");
	}

	@Override
	public boolean checkType() {
		
		List<Type> types = this.bloc.getTypesOfReturn();
		
		if(!(types.isEmpty() && this.getType().equals(AtomicType.VoidType)))
			for(Type t : types)
			{
				if(!t.compatibleWith(this.getType()))
						return false;
			}
		return true;
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

	public MethodeSignature getEntete() {
		return entete;
	}

	public void setEntete(MethodeSignature entete) {
		this.entete = entete;
	}
	
}
