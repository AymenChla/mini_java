package fr.n7.stl.poo.type;

import java.util.List;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.block.ast.type.declaration.FieldDeclaration;
import fr.n7.stl.poo.declaration.ClasseDeclaration;
import fr.n7.stl.poo.declaration.PooDeclaration;
import fr.n7.stl.poo.definition.Definition;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class Instanciation implements Type,Expression {
	

	String name;
	List<Instanciation> instanciations;
	PooDeclaration declaration;
	private String label;
	
	
	public PooDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(PooDeclaration declaration) {
		this.declaration = declaration;
	}

	

	public Instanciation(String name, List<Instanciation> instanciations) {
		super();
		this.name = name;
		this.instanciations = instanciations;
	}

	public Instanciation(String name) {
		super();
		this.name = name;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		if(_scope.knows(this.name)){
			this.declaration = (PooDeclaration) _scope.get(this.name);
			
			boolean result = true;
			if( this.instanciations != null)
				for(Instanciation i : this.instanciations){
					result = result && i.resolve(_scope);
				}
			
			return result;
		}
		else{
			Logger.error("The Name " +this.name+" doesnt exist ...");
			return false;
		}
	}
	
	public Type getType()
	{
		return this.declaration.getType();
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		
		ClasseDeclaration cld = (ClasseDeclaration) this.declaration.getContainer();
		
		Fragment _result = _factory.createFragment();
		_result.add(_factory.createLoad(
				cld.getRegister(), 
				cld.getOffset(),
				this.length()));
		_result.addComment(this.toString());
		return _result;
	
	}

	@Override
	public boolean equalsTo(Type _other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compatibleWith(Type _other) {
		if(_other instanceof Instanciation)
		{
			Instanciation other = (Instanciation) _other;
			if(this.declaration.getName().equals(other.declaration.getName()))
			{
				return true;
			}
			
			
			if(other.declaration.isClasse())
			{
				//réference de type interface sur l'objet d'une classe implementant l'interface
				ClasseDeclaration cd = (ClasseDeclaration) this.declaration.getContainer();
				for(Instanciation i : cd.getImplementations())
				{
					if(this.declaration.getName().contentEquals(i.getName()))
						return true;
				}
				
				//réference de type classe sur l'objet d'une classe fils
				if(cd.getExtension() != null)
				{
					if(cd.getExtension().getInstanciation() == null) return false;
					String classeMere = cd.getExtension().getInstanciation().getName();
					if(classeMere.equals(other.declaration.getName()))
						return true;
				}
			}
			
			
		}
		
		return false;
	}

	@Override
	public Type merge(Type _other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		int _length = 0;
		ClasseDeclaration cld = (ClasseDeclaration) this.declaration.getContainer();
		for (Definition d : cld.getDefinitions()) {
			if(d.getAttribut() != null && d.getAttribut().isStatic())
			_length += d.getAttribut().getType().length();
		}
		return _length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String startLabel) {
		this.label = startLabel;
		
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	
}
