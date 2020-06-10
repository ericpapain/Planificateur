package fr.uga.pddl4j.Planner.tfd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
//import java.util.PriorityQueue;
import java.util.Properties;

import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
//import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.Method;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
//import fr.uga.pddl4j.tutorial.tfd.NodeTFD;

public class TotalOrderPlanner {

	  private Properties arguments;

	  public TotalOrderPlanner(final Properties arguments) {
	    super();
	    this.arguments = arguments;
	  }
	  

	  /**
	   * recuperation du plan solution (backtrack sur les parents de chaque noeud et verification des actions utilisé)
	   */
	  private SequentialPlan extractPlan(final Node noeud, final Problem problem) {
	      Node n = noeud;
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
			  .append("-p <str>    fact file name\n");
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
	   * procédure Total Order Forwad Decomposition
	   */
	  
	  public Plan search(final Problem problem) {
		  
		  LinkedList<Node> openList = new LinkedList<>();

		  Node root = new Node(problem.getInitialState(), problem.getInitialTaskNetwork().getTasks());

		  openList.add(root);
		  
		  Plan plan = null;
		  
		  while (!openList.isEmpty() && plan==null ) {
			Node nodeInProgress = openList.pop();
			
			if (nodeInProgress.getTasksList().isEmpty()) {
				plan = this.extractPlan(nodeInProgress, problem);
			} else {
				//tasklist is not empty for our current node being processed
				// we pop the first task in the tasklist of our curent node
				int taskPop = nodeInProgress.getTasksList().pop();
				State stateTaskPop = nodeInProgress.getState();
						
				//verified if that is primitive or compound task
				
				if (problem.getTasks().get(taskPop).isPrimtive()) {
					//check a relevant operations can resolve our task
					List<Integer> active = problem.getRelevantOperators().get(taskPop);
					// taskpop is primitive now we try to add a new node in openlist who is produced by this task
					for (Integer op : active) {
						//action who match with the relevant operation
						Action action = problem.getActions().get(op);
						//verified that state of our node correspond to the state of application action can resolve our task
						if (nodeInProgress.getState().satisfy(action.getPreconditions())){
							Node successorNode = new Node(nodeInProgress);
							//successor contains the next task of current node(tasklist) and the same state
							// we are going to change all know...
							successorNode.getState().apply(action.getCondEffects());
							successorNode.setParent(nodeInProgress);
							successorNode.setOperator(op);
							//add new node in top of open list
							openList.add(successorNode);
						}
					}
					
				} else {
					// task is compound*
					//relevant op 
					List<Integer> active = problem.getRelevantOperators().get(taskPop);
					
					for (Integer op : active) {
						Method method = problem.getMethods().get(taskPop);
						if (nodeInProgress.getState().satisfy(method.getPreconditions())){
							Node successorNode = new Node(nodeInProgress);
							//method come after list of action
							successorNode.setOperator(op + problem.getActions().size());
							successorNode.setParent(nodeInProgress);
							successorNode.putListSubTasksAtTop(method.getSubTasks());
							
							openList.add(successorNode);
						}
					}
				}
			}
		}
		
		return plan;
	  }
	  
	  
	  
	  
	  
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			
			// Analyse de la ligne de commande et initialisation des arguments.
		      final Properties arguments = TotalOrderPlanner.parseCommandLine(args);
		      if (arguments == null) {
		    	  TotalOrderPlanner.printUsage();
		          System.exit(0);
		      }
		      
		   // create one instance of our planner
		      final TotalOrderPlanner planner = new TotalOrderPlanner(arguments);

	      // creation of one instance of problem factory for encoding and parsing our problem
		      final ProblemFactory factory = ProblemFactory.getInstance();

	      // upload our domain and problem files
		      File domain = (File) arguments.get(Planner.DOMAIN);
		      File problem = (File) arguments.get(Planner.PROBLEM);
		      ErrorManager errorManager = null;
		      try {
		          errorManager = factory.parse(domain, problem);
		      } catch (IOException e) {
		          System.out.println("\nunexpected error when parsing the PDDL planning problem description.");
		          System.exit(0);
		      }
		
	      // print error while our parsing and encoding method is running
		      if (!errorManager.isEmpty()) {
		          errorManager.printAll();
		          System.exit(0);
		      }
		      
	      // listing of trace of our running encoding and parsing fonction
		      final int traceLevel = (Integer) arguments.get(Planner.TRACE_LEVEL);
		      factory.setTraceLevel(traceLevel);
		      final Problem pb = factory.encode();
		      System.out.println("\nEncodage du domaine terminé avec succes! \nEncodage du problème terminé avec succes! \n\nnotre problème à :\n\n"
		              +"- "+ pb.getActions().size() + " actions, "
		              +"\n- "+ pb.getMethods().size() + " methods, "
		              +"\n- "+ pb.getRelevantFluents().size() + " fluents, "
		              +"\n- "+ pb.getTasks().size() + " tasks\n"
		              +"\n- "+ pb.getInitialTaskNetwork().getOrderingConstraints().getClass().getName() + " matrice de contrainte entres les taches\n");

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