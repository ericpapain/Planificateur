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
   * we compute the task who don't have a predecessor in our taskNetwork
   */
  public List<Integer> taskDontHavePredecessor(TaskNetwork tasknetwork) {
	  //the list of that tasks
	  LinkedList<Integer> listTasks = new LinkedList<Integer>();
	  if (tasknetwork.isTotallyOrdered()== false) {
		  //compute their transitive closure
		 tasknetwork.transitiveClosure();
	     //we search that task in the tasknetwork
		 for (int i = 0; i < tasknetwork.getOrderingConstraints().columns(); i++) {
	         if (tasknetwork.getOrderingConstraints().getColumn(i).cardinality() == 0) {
	            //collect all the tasks with no predecessor
	        	 listTasks.add(i);
	        }
	    }
	}
	return listTasks;
}
		  
  /**
   * procédure Total Order Forwad Decomposition
   * @param problem
   * @return
   */
  
  public Plan search(final Problem problem) {
	  /**
	   * create the empty plan
	   */
	  Plan plan = null;
	  
	  /**
	   * construct the root node of our search
	   */
	  NodeTFD rootNode = new NodeTFD(problem.getInitialState(), null, problem.getInitialTaskNetwork(), Integer.MAX_VALUE);
	  
	  /**
	   * create our openList browse all nodes
	   */
	  LinkedList<NodeTFD> openList = new LinkedList<NodeTFD>();
	  
	  //add our root node 
	  openList.add(rootNode);
	  
	  /**
	   * Beginning of our Total Forward Decomposition for STN
	   */
	  
	  while (!openList.isEmpty()&&plan==null) {
		// we pop the first node in our openList
		  NodeTFD currentNode = openList.poll();
		  
		  //we verified that our node have a Tasks in they Tasknetwork, if no the process can end and we can extract our plan 
		  
		  if (currentNode.getTaskNetwork().isEmpty()) {
			  plan = this.extractPlan(currentNode, problem);
		  } else {
			  //list of tasks with no predecessor 
			  List<Integer> taskWithNoPred = taskDontHavePredecessor(currentNode.getTaskNetwork());
			  //we get the current state of that tasks
			  State state = currentNode.getState();
			  //visit of all the node with no predecessor
			  for (Integer task : taskWithNoPred) {
				  if (problem.getTasks().get(task).isPrimtive()){					  
					  List<Integer> pertinentOperator = problem.getRelevantOperators().get(task);
					  
					  for (Integer operator : pertinentOperator) {
						  Action action = problem.getActions().get(operator);
						  if (state.satisfy(action.getPreconditions())) {
							NodeTFD successorNode = new NodeTFD(currentNode);
							successorNode.setOperator(operator);
							successorNode.setParent(currentNode);
							successorNode.getState().apply(action.getCondEffects());
							/**
							 * change the predecessor contrainst ask it to  Damien and Fiorino
							 */
							//openList.push(successorNode);
							openList.add(successorNode);
						}  
					  }
					  
				} else {// in that case the task is compound we'll use a method to decompose our task
					//the same procedure, just our operator and subtask is new value
					   List<Integer> pertinentMethod = problem.getRelevantOperators().get(task);
					   
					   for (Integer operator : pertinentMethod) {
						   Method method = problem.getMethods().get(operator);
							  // System.out.println(method.getName());
						   if (state.satisfy(method.getPreconditions())) {
							NodeTFD successorNode = new NodeTFD(currentNode);
							successorNode.setState(state);
							successorNode.setParent(currentNode);
							successorNode.setOperator(problem.getActions().size()+operator);
							/**
							 * change the predecessor contrainst ask it to  Damien and Fiorino
							 */		
							//openList.push(successorNode);
							openList.add(successorNode);
						}
					}	
				}
			}
		  }
		  
	  }
	  	 
	  return plan;
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
