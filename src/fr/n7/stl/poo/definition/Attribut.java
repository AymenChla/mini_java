package fr.n7.stl.poo.definition;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.poo.call.ConstructorCall;
import fr.n7.stl.poo.type.PooType;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class Attribut extends Definition implements Declaration,Instruction {
	
	Type type;
	String ident;
	Expression expression;
	boolean isFinal;
	boolean isStatic;
	
	Register register;
	int offset;
	
	
	
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Attribut(Type type, String ident) {
		super();
		this.type = type;
		this.ident = ident;
	}

	public Attribut(Type type, String ident, Expression expression, boolean isFinal) {
		super();
		this.type = type;
		this.ident = ident;
		this.expression = expression;
		this.isFinal = isFinal;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean result = type.resolve(_scope);
		if(this.expression != null){
			result = expression.resolve(_scope);
			//Declaration d = new VariableDeclaration(this.ident, this.type, this.expression);
			if(_scope.accepts(this))
			{
				_scope.register(this);
				result = this.expression.resolve(_scope);
			}
			else{
				Logger.error("This attribut " + this.ident + " already exists");
			}
		}
		else{
			//Declaration d = new VariableDeclaration(this.ident, this.type, null);
			if(_scope.accepts(this))
			{
				_scope.register(this);
			}
			else{
				Logger.error("This attribut " + this.ident + " already exists");
			}
			
		}
		 
		 
		 return result;
		
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public Fragment getCode(TAMFactory _factory)
	{
		Fragment frag = _factory.createFragment();
		
		frag.add(_factory.createPush(this.getType().length()));
		
		if(this.expression != null)
			frag.append(expression.getCode(_factory));
		
		frag.add(_factory.createStore(this.register, this.offset, this.type.length()));
		
		return frag;
	}

	@Override
	public String getName() {
		return this.ident;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;

	}
	
	public boolean checkType() {
		if(this.expression != null)
		{
			return this.type.compatibleWith(this.expression.getType());
		}
		return true;
	}
	
	public int allocateMemory(Register register, int offset) {
		if(this.isStatic)
		{
			this.register = register;
			this.offset = offset;
			return this.type.length();	
		}
		return 0;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
