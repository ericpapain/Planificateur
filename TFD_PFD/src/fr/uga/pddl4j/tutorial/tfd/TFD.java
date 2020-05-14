package fr.uga.pddl4j.tutorial.tfd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.Method;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.Task;
import fr.uga.pddl4j.problem.TaskNetwork;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;


public class TFD {

  private Properties arguments;

  public TFD(final Properties arguments) {
    super();
    this.arguments = arguments;
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
		  } else {//we are sure that we have at least one task in the tasknetwork
			  
			  //we pop the first id task in the task network we don't have to verified the predecessor list because in this cause all task is order
			  int task = currentNode.taskList().pop();
			  //System.out.println(currentNode.taskList().pop());
			  
			  //we have a first task, now try to verified that task is primitive or not! in the list of tasks of our problem we need to resolve
			  //Remember that we have just id of task it is key we can use it to find her representation in problem 
			  if (problem.getTasks().get(task).isPrimtive()){
				  //we take the state of our currentNode for find the pertinent operator for resolve this task
				  State state = currentNode.getState();
				  
				  //find a list of relevant operator for our task 
				  List<Integer> pertinentOperator = problem.getRelevantOperators().get(task);
				  
				  //verified now if the precondition of each pertinent operator is applicable to our state of current node
				  //in that case task is primitive, so the list of operator is action not a method
				  for (Integer operator : pertinentOperator) {
					// get the action correspond to the id of operator
					  Action action = problem.getActions().get(operator);
					  //verified the applicability now
					  if (state.satisfy(action.getPreconditions())) {
						  //here all conditions is satisfy and we will have the new state and new node for each operator it pertinent for our task
						NodeTFD successorNode = new NodeTFD(currentNode);
						//set the operator we use to substitute for this new node		
						successorNode.setOperator(operator);
						//set the parent for the current node who product this node
						successorNode.setParent(currentNode);
						//and we set the new state we correspond that to the apply condition effect in the new node
						successorNode.getState().apply(action.getCondEffects());
						//push our new node to the openlist
						//openList.push(successorNode);
						openList.add(successorNode);
					}  
				  }
				  
			} else {// in that case the task is compound we'll use a method to decompose our task
				//the same procedure, just our operator and subtask is new value
				//we take the state of our currentNode for find the pertinent operator for resolve this task
				  State state = currentNode.getState();
				   List<Integer> pertinentMethod = problem.getRelevantOperators().get(task);
				   
				   for (Integer operator : pertinentMethod) {
					   Method method = problem.getMethods().get(operator);
					   System.out.println(method.getName());
					   if (state.satisfy(method.getPreconditions())) {
						NodeTFD successorNode = new NodeTFD(currentNode);
						successorNode.setState(state);
						successorNode.setParent(currentNode);
						successorNode.setOperator(problem.getActions().size()+operator);
						//we use the new method pushSubtask to push substask in the list of tasks of our substitute node
						successorNode.pushSubTask(method);		
						//openList.push(successorNode);
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
	      final Properties arguments = TFD.parseCommandLine(args);
	      if (arguments == null) {
	    	  TFD.printUsage();
	          System.exit(0);
	      }
	      
	   // creation d'une instance de notre planificateur utilisant la procédure TFD
	      final TFD planner = new TFD(arguments);

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
