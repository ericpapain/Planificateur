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

public class TotalOrderPlanner1 {

	  private Properties arguments;

	  public TotalOrderPlanner1(final Properties arguments) {
	    super();
	    this.arguments = arguments;
	  }
	  
	  
	  
	  /**
	   * procédure Total Order Forwad Decomposition
	   */
	  
	  public Plan search(final Problem problem) {
		  
		  LinkedList<Node> openList = new LinkedList<>();

		 final Node root = new Node(problem.getInitialState(), problem.getInitialTaskNetwork().getTasks());

		  openList.add(root);
		  
		  Plan plan = null;
		  
		  while (!openList.isEmpty() && plan==null ) {
				//System.out.println("liste de taches initial du noeud root :"+problem.getInitialTaskNetwork().getTasks());

			final Node nodeInProgress = openList.pop();
			
			if (nodeInProgress.getTasksList().isEmpty()) {
				plan = this.extractPlan(nodeInProgress, problem);
			} else {
				//tasklist is not empty for our current node being processed
				// we pop the first task in the tasklist of our curent node
				int taskPop = nodeInProgress.getTasksList().pop();
				final State stateTaskPop = nodeInProgress.getState();
				//System.out.println("nom de la tache dépiler est primmitive ? :"+problem.getTasks().get(taskPop).isPrimtive());
				//relevant op 
				final List<Integer> active = problem.getRelevantOperators().get(taskPop);		
				//verified if that is primitive or compound task
				//System.out.println("affichage de la liste des relevants operator pour la tache : "+problem.getTasks().get(taskPop).getSymbol()+" : est :"+active);
				
				if (problem.getTasks().get(taskPop).isPrimtive()) {
					// taskpop is primitive now we try to add a new node in openlist who is produced by this task
					for (Integer op : active) {
						//action who match with the relevant operation
						final Action action = problem.getActions().get(op);
						//nmae of action
						//System.out.println("affichage de l'action qui correspondant au relevant : "+action.getName());
						//verified that state of our node correspond to the state of application action can resolve our task
						if (stateTaskPop.satisfy(action.getPreconditions())){
							final Node successorNode = new Node(nodeInProgress);
							//successor contains the next task of current node(tasklist) and the same state
							// we are going to change all know...
							successorNode.getState().apply(action.getCondEffects());
							successorNode.setParent(nodeInProgress);
							successorNode.setOperator(op);
							
							//
							//System.out.println("premiere action : "+action.getName());
							//add new node in top of open list
							openList.add(successorNode);
						}
					}
					
				} else {
					// task is compound
					int i = 1;
					//System.out.println("taille des relevant pour cette tache : "+active.size());
					for (Integer op : active) {
						final Method method = problem.getMethods().get(op);
						if (stateTaskPop.satisfy(method.getPreconditions())){
							final Node successorNode = new Node(nodeInProgress);
							
							//method come after list of action
							successorNode.setOperator(op + problem.getActions().size());
							successorNode.setParent(nodeInProgress);
							//
							System.out.println("list avant ajout tache methode : "+successorNode.getTasksList());
							System.out.println("list de tache parend node : "+nodeInProgress.getTasksList());
							
							//System.out.println("sous tache de la methode : "+method.getName()+" : est : "+method.getSubTasks()+"\n");
							successorNode.putListSubTasksAtTop(method.getSubTasks());
							//
							System.out.println("list apres ajout tache methode : "+successorNode.getTasksList());
							System.out.println("premiere methode : "+method.getName());		
							System.out.println("noeud construit n°: "+i+" est "+successorNode.getTasksList()+"\n\n");
							
							openList.add(successorNode);
							i++;
						}
					}
				}
			}
		}
		
		return plan;
	  }
	  
	  
	  /**
	     * Extract a plan from a solution node for the specified planning problem.
	     *
	     * @param node    the solution node.
	     * @param problem the problem to be solved.
	     * @return the solution plan or null is no solution was found.
	     */
	    private SequentialPlan extractPlan(final Node node, final Problem problem) {
	        Node n = node;
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
	     * The main method of the <code>TFDPlanner</code> example. The command line syntax is as
	     * follow:
	     *
	     * <pre>
	     * usage of TFDPlanner:
	     *
	     * OPTIONS   DESCRIPTIONS
	     *
	     * -d <i>str</i>   operator file name
	     * -p <i>str</i>   fact file name
	     * -t <i>num</i>   specifies the maximum CPU-time in seconds (preset: 300)
	     * -h              print this message
	     *
	     * </pre>
	     *
	     * <p>
	     * Commande line example:
	     * <code>java -cp build/libs/pddl4j-x.x.x.jar fr.uga.pddl4j.planners.htn.tfd.TFDPlanner</code><br>
	     * <code>  -d src/test/resources/benchmarks/rover_total_ordered/domain.hddl</code><br>
	     * <code>  -p src/test/resources/benchmarks/rover_total_ordered/pb01.hddl</code><br>
	     * </p>
	     * @param args the arguments of the command line.
	     */
	    public static void main(final String[] args) {

	        // Parse the commande line and initialize the arguments of the planner.
	        final Properties arguments = TotalOrderPlanner1.parseCommandLine(args);
	        if (arguments == null) {
	            TotalOrderPlanner1.printUsage();
	            System.exit(0);
	        }

	        // Create an instance of the TFDPlanner Planner
	        final TotalOrderPlanner1 planner = new TotalOrderPlanner1(arguments);

	        // Create an instance of the problem factory to parse and encode the domain and problem file
	        final ProblemFactory factory = ProblemFactory.getInstance();

	        // Get the domain file and problem file and parse the hddl files.
	        File domain = (File) arguments.get(Planner.DOMAIN);
	        File problem = (File) arguments.get(Planner.PROBLEM);
	        ErrorManager errorManager = null;
	        try {
	            errorManager = factory.parse(domain, problem);
	        } catch (IOException e) {
	            System.out.println("\nunexpected error when parsing the PDDL planning problem description.");
	            System.exit(0);
	        }

	        // Print the syntax errors if detected
	        if (!errorManager.isEmpty()) {
	            errorManager.printAll();
	            System.exit(0);
	        }

	        // Encode the problem into compact representation
	        final int traceLevel = (Integer) arguments.get(Planner.TRACE_LEVEL);
	        factory.setTraceLevel(6);
	        final Problem pb = factory.encode();
	        System.out.println("\nencoding problem done successfully ("
	                + pb.getActions().size() + " actions, "
	                + pb.getMethods().size() + " methods, "
	                + pb.getRelevantFluents().size() + " fluents, "
	                + pb.getTasks().size() + " tasks)\n");

	        final long start = System.currentTimeMillis();
	        final Plan plan = planner.search(pb);
	        final long end = System.currentTimeMillis();
	        if (plan != null) {
	            // Print plan information
	            System.out.println("found plan as follows:\n" + pb.toString(plan));
	            System.out.println(String.format("plan total cost: %4.2f", plan.cost()));
	            System.out.println(String.format("search time: %4.3fs%n%n", ((end - start) / 1000.0)));
	        } else {
	            System.out.println(String.format(String.format("%nno plan found%n%n")));
	        }
	    }

	    /**
	     * Print the usage of the TFDPlanner planner.
	     */
	    private static void printUsage() {
	        final StringBuilder strb = new StringBuilder();
	        strb.append("\nusage of TFDPlanner:\n")
	                .append("OPTIONS   DESCRIPTIONS\n")
	                .append("-d <str>    hddl domain file name\n")
	                .append("-p <str>    hddl problem file name\n")
	                .append("-l <num>    trace level\n")
	                .append("-t <num>    specifies the maximum CPU-time in seconds (preset: 300)\n")
	                .append("-h          print this message\n\n");
	        Planner.getLogger().trace(strb.toString());
	    }

	    /**
	     * Parse the command line and return the planner's arguments.
	     *
	     * @param args the command line.
	     * @return the planner arguments or null if an invalid argument is encountered.
	     */
	    private static Properties parseCommandLine(String[] args) {

	        // Get the default arguments from the super class
	        final Properties arguments = Planner.getDefaultArguments();

	        // Parse the command line and update the default argument value
	        for (int i = 0; i < args.length; i += 2) {
	            if ("-d".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
	                if (!new File(args[i + 1]).exists()) {
	                    return null;
	                }
	                arguments.put(Planner.DOMAIN, new File(args[i + 1]));
	            } else if ("-p".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
	                if (!new File(args[i + 1]).exists()) {
	                    return null;
	                }
	                arguments.put(Planner.PROBLEM, new File(args[i + 1]));
	            } else if ("-t".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
	                final int timeout = Integer.parseInt(args[i + 1]) * 1000;
	                if (timeout < 0) {
	                    return null;
	                }
	                arguments.put(Planner.TIMEOUT, timeout);
	            } else if ("-l".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
	                final int level = Integer.parseInt(args[i + 1]);
	                arguments.put(Planner.TRACE_LEVEL, level);
	            } else {
	                return null;
	            }
	        }
	        // Return null if the domain or the problem was not specified
	        return (arguments.get(Planner.DOMAIN) == null
	                || arguments.get(Planner.PROBLEM) == null) ? null : arguments;
	    }
	}
