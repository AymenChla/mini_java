package fr.n7.stl.poo.declaration;

import java.util.*;

import fr.n7.stl.block.poo.methode.MethodeSignature;
import fr.n7.stl.poo.type.Instanciation;

public class InterfaceDeclaration extends ContainerDeclaration{

		PooDeclaration declaration;
		List<Instanciation> extensions;
		List<MethodeSignature> entetes;
		
		
		public InterfaceDeclaration(PooDeclaration declaration, List<Instanciation> extensions,
				List<MethodeSignature> entetes) {
			super();
			this.declaration = declaration;
			this.extensions = extensions;
			this.entetes = entetes;
		}


		
		
		
}
