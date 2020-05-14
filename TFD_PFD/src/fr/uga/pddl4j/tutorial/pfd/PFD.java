	package fr.uga.pddl4j.tutorial.pfd;
	
	import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
	
	import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.Method;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.TaskNetwork;
import fr.uga.pddl4j.tutorial.tfd.NodeTFD;
	
	
	public class PFD {
		
		  private Properties arguments;
	
		  public PFD(final Properties arguments) {
		    super();
		    this.arguments = arguments;
		  }

		  
  /**
   * procédure Total Order Forwad Decomposition
   */
  
  public Plan search(final Problem problem) {
	 
	  
	  return null;
}
		  
		  
		  
  
  
		  
		  
		/**
	   * recuperation du plan solution (backtrack sur les parents de chaque noeud et verification des actions utilisé)
	   */
	  private SequentialPlan extractPlan(final NodeTFD noeud, final Problem problem) {
	      NodeTFD n = noeud;
	      final SequentialPlan plan = new SequentialPlan();
	      while (n.getParent() != null) {
	          if (n.getOperator() < problem.getActions().size()) {
	              final Action a = problem.getActions().get(n.getOperator());
	              plan.add(0, a);
	          }
	          n = n.getParent();
	      }
	      return plan;
	  }
	  
	  
	  /**
	   * affichage de l'utilisation du planif (spécification des arguments pris en paramètre)
	   * tu pouras compléter les params en regardant le code de Damien
	   */
	  private static void printUsage() {
		    final StringBuilder strb = new StringBuilder();
		    strb.append("\nusage of PDDL4J:\n")
			  .append("OPTIONS   DESCRIPTIONS\n")
			  .append("-d <str>    operator file name\n")
			  .append("-p <str>    fact file name\n")
		      .append("-------------Procédure PFD-------------\n");
			    Planner.getLogger().trace(strb.toString());
	}
	  
	  /**
	   * parsing et analyse des fichiers domaines et probleme a resoudre
	   */
	  private static Properties parseCommandLine(String[] args) {
			final Properties arguments = Planner.getDefaultArguments();
			//IntExpression currentTask = problem.getRelevantTasks().get(currentTN.pop());
			//final Properties arguments = StateSpacePlanner.getDefaultArguments();
		   
		   for (int i = 0; i < args.length; i += 2) {
		     if ("-d".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
		       if (!new File(args[i + 1]).exists()) return null;
		       arguments.put(Planner.DOMAIN, new File(args[i + 1]));
		     } else if ("-p".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
		       if (!new File(args[i + 1]).exists()) return null;
		       arguments.put(Planner.PROBLEM, new File(args[i + 1]));
		     }
		   }
		   return (arguments.get(Planner.DOMAIN) == null || arguments.get(Planner.PROBLEM) == null) ? null : arguments;
		 }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Analyse de la ligne de commande et initialisation des arguments.
	      final Properties arguments = PFD.parseCommandLine(args);
	      if (arguments == null) {
	    	  PFD.printUsage();
	          System.exit(0);
	      }
	      
	   // creation d'une instance de notre planificateur utilisant la procédure PFD
	      final PFD planner = new PFD(arguments);
	
	  // creation d'une instance de problem factory pour analyser et encoder notre probleme de planif
	      final ProblemFactory factory = ProblemFactory.getInstance();
	
	  // recup fichier domaine et probleme
	      File domain = (File) arguments.get(Planner.DOMAIN);
	      File problem = (File) arguments.get(Planner.PROBLEM);
	      ErrorManager errorManager = null;
	      try {
	          errorManager = factory.parse(domain, problem);
	      } catch (IOException e) {
	          System.out.println("\nunexpected error when parsing the PDDL planning problem description.");
	          System.exit(0);
	      }
	
	  // affichage des erreur pendant l'encodage et l'analyse si elle existe
	      if (!errorManager.isEmpty()) {
	          errorManager.printAll();
	          System.exit(0);
	      }
	      
	  // trace de l'encodage du probleme
	      final int traceLevel = (Integer) arguments.get(Planner.TRACE_LEVEL);
	      factory.setTraceLevel(traceLevel);
	      final Problem pb = factory.encode();
	      System.out.println("\nEncodage du domaine terminé avec succes! \nEncodage du problème terminé avec succes! \n\nnotre problème à :\n\n"
	              +"- "+ pb.getActions().size() + " actions, "
	              +"\n- "+ pb.getMethods().size() + " methods, "
	              +"\n- "+ pb.getRelevantFluents().size() + " fluents, "
	              +"\n- "+ pb.getTasks().size() + " tasks\n");
	
	      final Plan plan = planner.search(pb);
	      if (plan != null) {
	          // Print plan information
	          System.out.println("Le plan solution trouvé:\n\n" + pb.toString(plan));
	          //System.out.println(String.format("%nplan total cost: %4.2f%n%n", plan.cost()));
	  } else {
	      System.out.println(String.format(String.format("%nno plan found%n%n")));
	   }
	      
	}
	
}
