/**
 * 
 */
package fr.n7.stl.block.ast.instruction.declaration;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for a formal parameter in a function declaration.
 * @author Marc Pantel
 */
public class ParameterDeclaration implements Declaration {
	
	/**
	 * Name of the formal parameter
	 */
	protected String name;
	
	/**
	 * AST node for the type of the formal parameter
	 */
	protected Type type;
	
	/**
	 * Offset of the formal parameter in the list of parameters for the function
	 * i.e. the size of the memory allocated to the previous parameters
	 */
	protected int offset;
	protected Register register;
	/**
	 * Builds an AST node for a formal parameter declaration
	 * @param _name : Name of the formal parameter
	 * @param _type : AST node for the type of the formal parameter
	 */
	public ParameterDeclaration(String _name, Type _type) {
		this.name = _name;
		this.type = _type;
		this.offset = -1; // This value should never occur...
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Declaration#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.type + " " + this.name;
	}

	/**
	 * Provide the type of the formal parameter in the list of formal parameters for the function
	 * @return Type of the formal parameter
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Provide the offset of the formal parameter in the list of formal parameters for the function
	 * @return Offset of the formal parameter
	 */
	public int getOffset() {
		return this.offset;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		ParameterDeclaration other = (ParameterDeclaration) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public void setOffset(int i) {
		this.offset = i;
		
	}

	public boolean resolve(HierarchicalScope<Declaration> scope) {
		VariableDeclaration declaration = new VariableDeclaration(this.name, this.type, null);
		if(scope.accepts(declaration))
		{
			scope.register(declaration);
			return true;
		}
		Logger.error("parameter already exist");
		return false;
	}

	public int allocateMemory(Register _register, int _offset) {
		this.register = _register;
        this.offset = _offset - this.getType().length();
        return this.getType().length();
	}
	
	
	
    public Fragment getCode(TAMFactory _factory) {
        Fragment _fragment = _factory.createFragment();

        int _length = this.getType().length();
        _fragment.add(_factory.createLoad(this.register, this.offset, _length));

        return _fragment;
    }
	
}
