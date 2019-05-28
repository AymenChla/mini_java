package fr.n7.stl.block.poo.methode;

import java.util.Collections;
import java.util.LinkedList;
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
	String label;
	private Register register;
	private int offset;
	int allocation_length;
	private String id;
	
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
		
			this.register = _register;
	        this.offset = _offset;

	     // Calculer les offset des paramtre (en négatif car avant LB)
			this.allocation_length = 0;
			if(this.parametres!=null)
			for (int k = this.parametres.size()-1; k>=0; k--) {
				ParameterDeclaration p =  this.parametres.get(k);
				this.allocation_length += p.getType().length();
				p.setOffset(- this.allocation_length);

			}
			this.bloc.allocateMemory(Register.LB, 3); // offset par defaut 3 pour laisser la place aux donnees de CALL et RETURN
			return 0;
	}
	@Override
	public Fragment getCode(TAMFactory _factory) {
		
		this.id = Integer.toString(_factory.createLabelNumber());
		Fragment frag = _factory.createFragment();

		// Placer le code de la fonction (en indiquant la taille du type pour que le block ne l'efface pas
		frag.append(this.bloc.getCode(_factory));
		// Placer un return s'il n'y en a pas (type void seulement)
		
		// Placer les etiquettes
		frag.addPrefix(this.getStartLabel()+":");
		frag.addSuffix(this.getEndLabel()+":");
		// Placer un jump au tout début pour eviter la fonction
		Fragment frag_jump = _factory.createFragment();
		frag_jump.add(_factory.createJump(this.getEndLabel()));

		// POP des paramètre (déjà fait par Return)

		frag_jump.append(frag);
		return frag_jump;
	}

	public String getStartLabel(){
		if (this.id == null){
			Logger.error("Function label was call before function is declared");
		}

		return "FUNC_"+this.instanciation.getName()+"_"+this.id+"_START";
	}
	public String getEndLabel(){
		if (this.id == null){
			Logger.error("Function label was call before function is declared");
		}
		return "FUNC_"+this.instanciation.getName()+"_"+this.id+"_END";
	}

	public List<ParameterDeclaration> getParametres() {
		return parametres;
	}

	public void setParametres(List<ParameterDeclaration> parametres) {
		this.parametres = parametres;
	}
	
	
}
