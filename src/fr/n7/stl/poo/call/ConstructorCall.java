package fr.n7.stl.poo.call;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.poo.methode.Constructor;
import fr.n7.stl.poo.declaration.PooDeclaration;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.poo.type.Instanciation;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Library;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructorCall implements Expression{
	
	Type type;
	List<Expression> parametres;
	
	
	public ConstructorCall(Type type) {
		super();
		this.type = type;
	}

	public ConstructorCall(Type type, List<Expression> parametres) {
		super();
		this.type = type;
		this.parametres = parametres;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		boolean result = true;
		
		Instanciation in = (Instanciation) this.type;
		result = in.resolve(_scope); 
		if(!in.getDeclaration().isClasse())
			Logger.error("We cant use an Interface as a Counstuctor");
		
		if(_scope.knows(in.getName()))
		{
			PooDeclaration c = (PooDeclaration) _scope.get(in.getName());
			for(Definition d : c.getDefinitions())
			{
				//TODO multiple constructors
				if(d.getConstructor() != null)
				{
					if( (d.getConstructor().getParametres() == null && this.parametres != null) 
					|| (d.getConstructor().getParametres() != null && this.parametres == null))
					{
						Logger.error("Constructor with those parameter doesn't exist");
					}	
					
					if(d.getConstructor() != null && this.parametres != null && d.getConstructor().getParametres().size() != this.parametres.size()) 
					{
						Logger.error("Constructor with those parameter doesn't exist");
					}
					
					if(this.parametres !=null)
					{
						for(int i=0 ; i < this.parametres.size() ; i++)
						{
							if(!this.parametres.get(i).getType().compatibleWith(d.getConstructor().getParametres().get(i).getType()))
								Logger.error("Type mismatch error");
						}
					}
					
				}
			}
			
			
		}
		
		if(this.parametres != null){	
			for(Expression e : this.parametres){
				result = result && e.resolve(_scope);
			}
			
		}
		
		return result;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		
		Fragment frag = _factory.createFragment();
		// Empiler les paramètres
		for (Expression arg : this.parametres){
			frag.append(arg.getCode(_factory));
		}

		// call
		Instanciation inst = (Instanciation) this.type;
		frag.add(_factory.createCall("FUNC_"+inst.getName()+"_"+1+"_START", Register.LB));
		//frag.add(_factory.createJump("FUNC_"+inst.getName()+"_"+1+"_START"));
		frag.addComment("Function "+inst.getName()+" call");

		return frag;
	}
}
