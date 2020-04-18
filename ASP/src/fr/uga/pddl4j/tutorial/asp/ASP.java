/**
 * faut pas oublier que nous sommes dans le repertoire 
 * 
 * src/fr/uga/pddl4j/tutorial/asp
 */

/*
 * voila l'equivalent du tutos en ligne
 * 
 * package  fr.uga.pddl4j.tutorial.asp 
 */


/***
 * ici on passe a la creation de la classe principale de notre 
 * planificateur A* elle correspond a l'etape 2/9 
 */
package fr.uga.pddl4j.tutorial.asp;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.StateSpacePlanner;
import fr.uga.pddl4j.util.Plan;


/**
 * Cette classe implémente un simple 
 * planificateur avancé basé sur l'algorithme A * et utilisant l'heuristique FF. 
 * @author eric
 * Le mot clé final s'applique aux variables de classe ou d'instance ou locales, aux méthodes, aux paramètres d'une méthode
 *	et aux classes. Il permet de rendre l'entité sur laquelle il s'applique non modifiable une fois qu'elle est déclarée pour une
 * méthode ou une classe et initialisée pour une variable.
 * Une variable qualifiée de final signifie que la valeur de la variable ne peut plus être modifiée une fois que celle−ci est
 * initialisée.
 */

public final class ASP extends AbstractStateSpacePlanner {
	   
  /**
   * ici on specifie les arguments du planificateur.
   * private signifie qu'elle peut etre utiliser uniquement dans la classe ou elle a été déclaré
   */
  private Properties arguments;
  
   
  /**
   * maintenant nous créons une instance de notre planificateur avec les arguments par défaut.
   * @param arguments les arguments du planificateur.
   */
  public ASP(final Properties arguments) {
    super();
    this.arguments = arguments;
  }
 
  
  
  /**
   * Resolution du probleme de planification et renvoie des premieres solution trouvé
   * @param problem represente le probleme a resoudre.
   * @return la solution de la recherhce ou null si elle n'existe pas.
   */
  //@Override
  public Plan search(final CodedProblem problem) {
    // a completer par l'algorithme de recherche
    return null;
  }
  
  
  /**
   * imprimer les options d'utilisation du planificateur Astar.
   */
  private static void printUsage() {
    final StringBuilder strb = new StringBuilder();
    strb.append("\nusage of PDDL4J:\n")
      .append("OPTIONS   DESCRIPTIONS\n")
      .append("-o <str>    operator file name\n")
      .append("-f <str>    fact file name\n")
      .append("-w <num>    the weight used in the a star seach (preset: 1.0)\n")
      .append("-t <num>    specifies the maximum CPU-time in seconds (preset: 300)\n")
      .append("-h          print this message\n\n");
    Planner.getLogger().trace(strb.toString());
  }
  
  
  /**
   * pour l' analyser ou parsing pour verifier la syntaxe pour verifier la syntaxe==parsing des arguments entrée en ligne de commande et renvoie les arguments du planificateur.
   * 
   * @param args correspond a la ligne de commande entrer.
   * @return renvoie les arguments du planificateur ou bien retourne null si les arguments entrer sont invalide.
   */
  private static Properties parseCommandLine(String[] args) {
    
    // recuperation des arguments par defaut de la super classe 
    final Properties arguments = StateSpacePlanner.getDefaultArguments();
    
    //  analyser ou parsing pour verifier la syntaxe pour verifier la syntaxe===parsing de la ligne de commande et mise a jour des arguments par defaut entré
    
    for (int i = 0; i < args.length; i += 2) {
      if ("-o".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
        if (!new File(args[i + 1]).exists()) return null;
        arguments.put(Planner.DOMAIN, new File(args[i + 1]));
      } else if ("-f".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
        if (!new File(args[i + 1]).exists()) return null;
        arguments.put(Planner.PROBLEM, new File(args[i + 1]));
      } else if ("-t".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
        final int timeout = Integer.parseInt(args[i + 1]) * 1000;
        if (timeout < 0) return null;
        arguments.put(Planner.TIMEOUT, timeout);
      } else if ("-w".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
        final double weight = Double.parseDouble(args[i + 1]);
        if (weight < 0) return null;
        arguments.put(StateSpacePlanner.WEIGHT, weight);
      } else {
        return null;
      }
    }
    // renvoie null si le domaine et le probleme de planification n'ont pas été spécifié en argument
    return (arguments.get(Planner.DOMAIN) == null 
        || arguments.get(Planner.PROBLEM) == null) ? null : arguments;
  }
  

  
  /**
   * Toutes les tâches de prétraitement qui doivent être effectuées pour trouver 
   * un plan de solution sont accessibles via l' objet ProblemFactory. 
   * Une instance de cette classe peut être obtenue avec l'instruction java suivante:
   */
  
  
  /**
   * la methode main de notre planificateur <code>ASP</code> est donner plus bas. la ligne de commande est donner a la suite
   * suivant:
   * <p>
   * <pre>
   * utilisation de ASP:
   *
   * OPTIONS   DESCRIPTIONS
   *
   * -o <i>str</i>   operator file name
   * -f <i>str</i>   fact file name
   * -w <i>num</i>   the weight used in the a star search (preset: 1)
   * -t <i>num</i>   specifies the maximum CPU-time in seconds (preset: 300)
   * -h              print this message
   *
   * </pre>
   * </p>
   *
   * @param args les arguments de la ligne de commande.
   */
   public static void main(String[] args) {
	 
	   // appel de la methode d' analyser ou parsing pour verifier la syntaxe=parsing dans le main pour la recuperation des arguments
	   
	   final Properties arguments = ASP.parseCommandLine(args);
	   if (arguments == null) {
	     ASP.printUsage();
	     System.exit(0);
	   }
	   
	   //ajoutons actuellement une instance de notre planificateur ASP pour manipuler les arguments pris en paramètres
	   final ASP planner = new ASP(arguments);
	   
	   /**
	    * Cette classe vous permet de:
	    * --Analyser le domaine PDDL et les fichiers de problème pour vérifier la syntaxe
	    * --Instancier et encoder le problème de planification défini dans ces deux fichiers 
	    * de manière compacte pour la recherche.
	    */
	   
	   final ProblemFactory factory = ProblemFactory.getInstance();
	   
	   
	   /*
	    * ici, on effectue l'analyse du code entrer en argument dans notre fonction d'analyse factory qui contient 
	    * toute les information sur l'analyse et les différentes opérations sur les domaines et les problèmes
	    */
	   File domain = (File) arguments.get(Planner.DOMAIN);
	   File problem = (File) arguments.get(Planner.PROBLEM);
	   ErrorManager errorManager = null;
	   try {
		   // analyse de la syntaxe et semantique des deux fichier problem et domaine par la methode parse() de l'objet factory
	     errorManager = factory.parse(domain, problem);
	   } catch (IOException e) {
	     Planner.getLogger().trace("\n unexpected error when parsing the PDDL planning problem description.");
	     System.exit(0);
	   }
	   
	   
	   /**
	    * avec se code nous avons la trace sur les différents erreurs qui apparaissent dans le code.
	    * elle spécifie que si pendant l'analyse du code des errors surviennent alors faudras les afficher et 
	    * si tous se passe bien alors afficher le message tous s'ai bien passé.
	    */
	   if (!errorManager.isEmpty()) {
		   errorManager.printAll();
		   System.exit(0);
		 } else {
		   Planner.getLogger().trace("\nparsing domain file done successfully");
		   Planner.getLogger().trace("\nparsing problem file done successfully\n");
		 }
   
   }
}