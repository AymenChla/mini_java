
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20141204 (SVN rev 60)
//----------------------------------------------------

package fr.n7.stl.block;

/** CUP generated interface containing symbol constants. */
public interface sym {
  /* terminals */
  public static final int UL_Second = 25;
  public static final int UL_Sinon = 37;
  public static final int UL_Egal = 8;
  public static final int UL_Point = 4;
  public static final int UL_Double_Plus_Prefixe = 11;
  public static final int UL_This = 62;
  public static final int UL_Double_Egal = 34;
  public static final int UL_Point_Exclamation = 7;
  public static final int UL_Chaine = 65;
  public static final int UL_Retour = 41;
  public static final int UL_Type_Flottant = 50;
  public static final int UL_Asterisque = 16;
  public static final int UL_Vrai = 70;
  public static final int UL_Inferieur = 30;
  public static final int UL_Double_Moins_Prefixe = 12;
  public static final int UL_Parenthese_Fermante = 23;
  public static final int UL_Caractere = 66;
  public static final int UL_Crochet_Fermant = 29;
  public static final int UL_Parenthese_Ouvrante = 22;
  public static final int UL_Type_Caractere = 47;
  public static final int UL_Point_Virgule = 2;
  public static final int UL_Type_Booleen = 46;
  public static final int UL_Definition_Constante = 42;
  public static final int UL_Accolade_Fermante = 27;
  public static final int UL_Accolade_Ouvrante = 26;
  public static final int UL_Point_Interrogation = 6;
  public static final int UL_Inferieur_Egal = 32;
  public static final int UL_Superieur = 31;
  public static final int UL_Private = 57;
  public static final int UL_Nombre_Flottant = 68;
  public static final int UL_Nul = 69;
  public static final int UL_Exclamation_Egal = 35;
  public static final int UL_Virgule = 3;
  public static final int UL_Public = 56;
  public static final int UL_Oblique = 17;
  public static final int UL_Crochet_Ouvrant = 28;
  public static final int UL_Abstract = 55;
  public static final int UL_Double_Esperluette = 21;
  public static final int UL_Identificateur = 64;
  public static final int UL_Type_Vide = 51;
  public static final int UL_Protected = 61;
  public static final int UL_Implements = 60;
  public static final int UL_Moins = 14;
  public static final int UL_Enregistrement = 44;
  public static final int EOF = 0;
  public static final int UL_Deux_Points = 5;
  public static final int UL_Type_Chaine = 48;
  public static final int UL_Final = 54;
  public static final int UL_IdentificateurType = 63;
  public static final int error = 1;
  public static final int UL_Nouveau = 39;
  public static final int UL_Type_Entier = 49;
  public static final int UL_Interface = 52;
  public static final int UL_Static = 58;
  public static final int UL_Extends = 53;
  public static final int UL_Class = 59;
  public static final int UL_Double_Barre = 20;
  public static final int UL_Afficher = 40;
  public static final int UL_Plus = 13;
  public static final int UL_Esperluette = 19;
  public static final int UL_Enumeration = 45;
  public static final int UL_Superieur_Egal = 33;
  public static final int UL_Double_Plus = 9;
  public static final int UL_Tant_Que = 38;
  public static final int UL_Faux = 71;
  public static final int UL_Definition_Type = 43;
  public static final int UL_Si = 36;
  public static final int UL_Nombre_Entier = 67;
  public static final int UL_Pour_Cent = 18;
  public static final int UL_Double_Moins = 10;
  public static final int UL_Premier = 24;
  public static final int UL_Moins_Unaire = 15;
  public static final String[] terminalNames = new String[] {
  "EOF",
  "error",
  "UL_Point_Virgule",
  "UL_Virgule",
  "UL_Point",
  "UL_Deux_Points",
  "UL_Point_Interrogation",
  "UL_Point_Exclamation",
  "UL_Egal",
  "UL_Double_Plus",
  "UL_Double_Moins",
  "UL_Double_Plus_Prefixe",
  "UL_Double_Moins_Prefixe",
  "UL_Plus",
  "UL_Moins",
  "UL_Moins_Unaire",
  "UL_Asterisque",
  "UL_Oblique",
  "UL_Pour_Cent",
  "UL_Esperluette",
  "UL_Double_Barre",
  "UL_Double_Esperluette",
  "UL_Parenthese_Ouvrante",
  "UL_Parenthese_Fermante",
  "UL_Premier",
  "UL_Second",
  "UL_Accolade_Ouvrante",
  "UL_Accolade_Fermante",
  "UL_Crochet_Ouvrant",
  "UL_Crochet_Fermant",
  "UL_Inferieur",
  "UL_Superieur",
  "UL_Inferieur_Egal",
  "UL_Superieur_Egal",
  "UL_Double_Egal",
  "UL_Exclamation_Egal",
  "UL_Si",
  "UL_Sinon",
  "UL_Tant_Que",
  "UL_Nouveau",
  "UL_Afficher",
  "UL_Retour",
  "UL_Definition_Constante",
  "UL_Definition_Type",
  "UL_Enregistrement",
  "UL_Enumeration",
  "UL_Type_Booleen",
  "UL_Type_Caractere",
  "UL_Type_Chaine",
  "UL_Type_Entier",
  "UL_Type_Flottant",
  "UL_Type_Vide",
  "UL_Interface",
  "UL_Extends",
  "UL_Final",
  "UL_Abstract",
  "UL_Public",
  "UL_Private",
  "UL_Static",
  "UL_Class",
  "UL_Implements",
  "UL_Protected",
  "UL_This",
  "UL_IdentificateurType",
  "UL_Identificateur",
  "UL_Chaine",
  "UL_Caractere",
  "UL_Nombre_Entier",
  "UL_Nombre_Flottant",
  "UL_Nul",
  "UL_Vrai",
  "UL_Faux"
  };
}

