package fr.uga.pddl4j.tutorial.pfd;

import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.AbstractOperator;
import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.Method;
import fr.uga.pddl4j.problem.TaskNetwork;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.ConditionalEffect;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.encoding.IntExpression;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.util.BitMatrix;

import fr.uga.pddl4j.tutorial.pfd.Node;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.List;
import java.util.Properties;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.BitSet;
import java.time.Duration;
import java.time.Instant;


public final class PFD extends AbstractPlanner {

    private Properties arguments;

    /**
     * Creates a new planner with default parameters.
     */

     public PFD() {
         super();
     }


    /**
     * Creates a new planner with arguments.
     */
    public PFD(final Properties arguments) {
        super();
        this.arguments = arguments;
    }



    /**
     * The search function of the planner.
     */
    public Plan search(final Problem problem) {
        final Logger logger = this.getLogger();
        Objects.requireNonNull(problem);
        logger.trace("* starting PFD*\n");

        //Get initial states and task network
        State initialState = problem.getInitialState();
        TaskNetwork currentTN = problem.getInitialTaskNetwork();

        //Create first node
        Node firstNode = new Node(initialState, currentTN);
        firstNode.setParent(null);

        //Create the list of nodes to explore
        LinkedList<Node> toExplore = new LinkedList<Node>();
        toExplore.add(firstNode);

        //Create the list of nodes already explored
        LinkedList<Node> exploredNodes = new LinkedList<Node>();
        exploredNodes.add(firstNode);
        int nodesExplored = 0;

        Plan plan = null;

        Planner.getLogger().trace("Entering while\n");

        //Start searching
        while (!toExplore.isEmpty() && plan == null) {
            //Get the first node in the list of open nodes
            Node currentNode = toExplore.pollFirst();
            nodesExplored++;

            // If all the tasks have been done, we have a valid plan
            if (currentNode.getTaskNetwork().isEmpty()) {
                plan = this.extractPlan(currentNode, problem);
            } else {
              State currentState = currentNode.getState();

              //List of first tasks
              LinkedList<Integer> FirstTasks = new LinkedList<Integer>();

              //Calculating transitive closure of the constraints matrix
              System.out.println("Constraint matrix before TC : " + currentNode.getTaskNetwork().getOrderingConstraints());
              currentNode.getTaskNetwork().setOrderingConstraints(transitiveClosureWarshall(currentNode.getTaskNetwork().getOrderingConstraints()));
              System.out.println("Constraint matrix after TC : "+ currentNode.getTaskNetwork().getOrderingConstraints());
              BitMatrix matrix = currentNode.getTaskNetwork().getOrderingConstraints();

              //For all tasks
              System.out.println("Liste de taches: " + currentNode.getTaskNetwork().getTasks());
              for (int i = 0; i < currentNode.getTaskNetwork().getTasks().size(); i++) {
                //System.out.println(matrix.getRow(i).toString());

                // If all the bits on the collumn are at 0 (id est if the task takes place before all others)

                if (matrix.getColumn(i).cardinality() == 0 ) {
                  //We add the task to the list of tasks with no predecessor
                  FirstTasks.add(currentNode.getTaskNetwork().getTasks().get(i));
                }

              }
              System.out.println("The list of first tasks is "+ FirstTasks);


              //For each task with no predecessor do
              for (int taskId : FirstTasks) {
                Planner.getLogger().trace("\n" + "Starting task " + taskId +"\n");
                //Planner.getLogger().trace(problem.getTasks().get(taskId).toString() + "\n");

                //ACTION
                if (problem.getTasks().get(taskId).isPrimtive()){
                  Planner.getLogger().trace("PRIMITIVE\n");

                  List<Integer> possibleActions = problem.getRelevantOperators().get(taskId);
                  System.out.println("The list of possible actions is "+ possibleActions.toString());

                  //Copying current Task Network
                  TaskNetwork newTn = new TaskNetwork(currentNode.getTaskNetwork().getTasks(), null);

                  currentNode.getTaskNetwork().setOrderingConstraints(transitiveClosureWarshall(currentNode.getTaskNetwork().getOrderingConstraints()));


                  //Removing current task from the list
                  int currentTaskIndex = newTn.getTasks().indexOf(taskId);
                  newTn.getTasks().remove(currentTaskIndex);

                  //Creating the new ordering constraints matrix
                  newTn.setOrderingConstraints(new BitMatrix(newTn.getTasks().size()));
                  for (int i = 0; i< newTn.getOrderingConstraints().rows(); i++) {
                    for (int j = 0; j< newTn.getOrderingConstraints().rows(); j++) {

                      if (i < currentTaskIndex) {

                        if (j < currentTaskIndex) {
                          if (currentNode.getTaskNetwork().getOrderingConstraints().get(i,j)) {
                            newTn.getOrderingConstraints().set(i,j);
                          } else {
                            newTn.getOrderingConstraints().clear(i,j);
                          }

                        } else {
                          if (currentNode.getTaskNetwork().getOrderingConstraints().get(i,j+1)) {
                            newTn.getOrderingConstraints().set(i,j);
                          } else {
                            newTn.getOrderingConstraints().clear(i,j);
                          }
                        }

                      } else {

                        if (j < currentTaskIndex) {
                          if (currentNode.getTaskNetwork().getOrderingConstraints().get(i+1,j)) {
                            newTn.getOrderingConstraints().set(i,j);
                          } else {
                            newTn.getOrderingConstraints().clear(i,j);
                          }

                        } else {
                          if (currentNode.getTaskNetwork().getOrderingConstraints().get(i+1,j+1)) {
                            newTn.getOrderingConstraints().set(i,j);
                          } else {
                            newTn.getOrderingConstraints().clear(i,j);
                          }
                        }

                      }
                    }
                  }
                  System.out.println(newTn.getOrderingConstraints().toString() + newTn.getTasks().size() + "\n");
                  newTn.setOrderingConstraints(transitiveClosureWarshall(newTn.getOrderingConstraints()));

                  //For each possible action, we check its applicability and push it if applicable
                  for (int actionId : possibleActions) {
                    Action chosenAction = problem.getActions().get(actionId);
                    Planner.getLogger().trace("Choosing action "+chosenAction.getName()+"\n");
                    if (currentState.satisfy(chosenAction.getPreconditions())) {
                      Node childNode = new Node(currentNode);
                      childNode.setParent(currentNode);
                      childNode.setPerformedOperation(actionId);
                      childNode.setTaskNetwork(newTn);
                      childNode.getState().apply(chosenAction.getCondEffects());
                      toExplore.push(childNode);
                    }
                  }
                }

                //METHOD
                else {
                  List<Integer> possibleMethods = problem.getRelevantOperators().get(taskId);
                  System.out.println("The list of possible methods is "+ possibleMethods.toString());

                  //for all possible methods
                  for (int methodId : possibleMethods) {
                    Method chosenMethod = problem.getMethods().get(methodId);
                    System.out.println("Using "+ problem.getMethods().get(methodId).getName());

                    if (currentState.satisfy(chosenMethod.getPreconditions())) {

                        //Building the new Task Network
                        TaskNetwork newTn = new TaskNetwork(currentNode.getTaskNetwork().getTasks(), null);

                        System.out.println(currentNode.getTaskNetwork().getOrderingConstraints().toString() + currentNode.getTaskNetwork().getTasks().size() + "\n");

                        //We remove the current task from the tasks list
                        int currentTaskIndex = newTn.getTasks().indexOf(taskId);
                        System.out.println("Avant" + newTn.getTasks().size());
                        newTn.getTasks().remove(currentTaskIndex);
                        System.out.println("Après" + newTn.getTasks().size());
                        System.out.println("Subtasks" + chosenMethod.getSubTasks());

                        //We create the new ordering constraints matrix
                        newTn.setOrderingConstraints(new BitMatrix(newTn.getTasks().size() + chosenMethod.getSubTasks().size()));
                        for (int i = 0; i< newTn.getOrderingConstraints().rows(); i++) {
                          for (int j = 0; j< newTn.getOrderingConstraints().rows(); j++) {

                            if (i < currentTaskIndex) {

                              if (j < currentTaskIndex) {
                                  if (currentNode.getTaskNetwork().getOrderingConstraints().get(i,j)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else if (j < newTn.getTasks().size()) {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(i,j+1)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(i,currentTaskIndex)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }
                              }

                            } else if (i < newTn.getTasks().size()) {

                              if (j < currentTaskIndex) {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(i+1,j)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else if (j < newTn.getTasks().size()) {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(i+1,j+1)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(i+1,currentTaskIndex)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }
                              }

                            } else {

                              if (j < currentTaskIndex) {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(currentTaskIndex,j)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else if (j < newTn.getTasks().size()) {
                                if (currentNode.getTaskNetwork().getOrderingConstraints().get(currentTaskIndex,j+1)) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }

                              } else {
                                if (chosenMethod.getTaskNetwork().getOrderingConstraints().get(i - newTn.getTasks().size() , j - newTn.getTasks().size())) {
                                  newTn.getOrderingConstraints().set(i,j);
                                } else {
                                  newTn.getOrderingConstraints().clear(i,j);
                                }
                              }
                            }
                          }
                        }
                        System.out.println(chosenMethod.getTaskNetwork().getOrderingConstraints().toString() + chosenMethod.getTaskNetwork().getTasks().size()  + "\n");
                        System.out.println(newTn.getOrderingConstraints().toString() + newTn.getTasks().size() + "\n");

                        // Calculating transitive closure
                        newTn.setOrderingConstraints(transitiveClosureWarshall(newTn.getOrderingConstraints()));

                        System.out.println(newTn.getOrderingConstraints().toString() +"\n");

                        // Adding subtasks to the list of tasks
                        newTn.getTasks().addAll(chosenMethod.getSubTasks());


                        // Creating the new node
                        Node childNode = new Node(currentNode);
                        childNode.setParent(currentNode);
                        childNode.setPerformedOperation(problem.getActions().size() + methodId);
                        childNode.getTaskNetwork().setTasks(newTn.getTasks());
                        childNode.getTaskNetwork().setOrderingConstraints(newTn.getOrderingConstraints());

                        System.out.println(childNode.getTaskNetwork().getOrderingConstraints().toString() +"\n");
                        System.out.println(isNotCyclic(childNode.getTaskNetwork().getOrderingConstraints()));


                        // If the task network is acyclic and has not been explored, we add the node to the queue
                        if (isNotCyclic(childNode.getTaskNetwork().getOrderingConstraints())) {
                          // Different behavior even without recursion in the domain definition if we don't do the isExplored? check
                          // toExplore.push(childNode);
                          if (!isExplored(childNode,exploredNodes)) {
                            toExplore.push(childNode);
                            exploredNodes.add(childNode);
                            System.out.println("Child pushed");
                          } else {
                            System.out.println("Node already explored!");
                          }
                        } else {
                          System.out.println("Cyclic");
                        }
                    } else { System.out.println("Doesn't satisfy preconditions"); }
                  }
                }
              }
            }
        }
        return plan;

    }

    // Extract plan from node with no open tasks
    private SequentialPlan extractPlan(Node node, Problem problem) {

        Planner.getLogger().trace("Extracting Plan\n");
        final SequentialPlan plan = new SequentialPlan();
        while (node.getParent() != null) {
            if (node.getPerformedOperation() < problem.getActions().size()) {
                Action action = problem.getActions().get(node.getPerformedOperation());
                plan.add(0, action);
                Planner.getLogger().trace("Extracting an action\n");
            }
            node = node.getParent();
        }
        return plan;

    }

    // Modified version of Damien's method for calculating transitive closure
    public final BitMatrix transitiveClosureDamien(BitMatrix matrix) {
        final int size = matrix.rows();
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (matrix.get(i, j)
                            || (matrix.get(i, k)
                                && matrix.get(k, j))) {
                        matrix.set(i, j);
                    } else {
                        matrix.clear(i, j);
                    }
                }
            }
        }
        return (matrix);
    }

    // My method for calculating transitive closure
    public static BitMatrix transitiveClosureWarshall(BitMatrix matrix) {
        for (int k = 0 ; k < matrix.rows() ; k++ ) {
          for (int i = 0 ; i < matrix.rows() ; i++ ) {
            if ( matrix.get(i,k) ) {
              for (int j = 0 ; j < matrix.rows() ; j++ ) {
                if (matrix.get(k,j)) {
                  matrix.set(i,j);
                }
              }
            }
          }
        }
      return (matrix);
    }

    // Tests whether an ordering constraints matrix is cyclic or not
    public static boolean isNotCyclic(BitMatrix matrix) {
        for (int i = 0 ; i < matrix.rows() ; i++ ) {
          if (matrix.get(i,i)) {
            return (false);
          }
        }
      return (true);
    }

    // Tests whether a node has already been explored or not
    public static boolean isExplored(Node node, LinkedList<Node> exploredNodes) {
        for (int i = 0 ; i < exploredNodes.size() ; i++ ) {
          // Uses the method in the Node class to check if the nodes are identical
          if (node.equals(exploredNodes.get(i))) {
            return (true);
          }
        }
      return (false);
    }

    // Prints how the planner should be used
    private static void printUsage() {
      final StringBuilder strb = new StringBuilder();
      strb.append("\nusage of PDDL4J:\n")
        .append("OPTIONS   DESCRIPTIONS\n")
        .append("-d <str>    operator file name\n")
        .append("-p <str>    fact file name\n");
      Planner.getLogger().trace(strb.toString());
    }

    // Parses the command line
    private static Properties parseCommandLine(String[] args) {

    final Properties arguments = Planner.getDefaultArguments();

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



    // Main method, where the magic is made
    public static void main(String[] args) {

 	   final Properties arguments = PFD.parseCommandLine(args);

 	   if (arguments == null) {
 	     PFD.printUsage();
 	     System.exit(0);
 	   }

     //creating and instantiating a new planner
 	   final PFD planner = new PFD(arguments);

 	   final ProblemFactory factory = ProblemFactory.getInstance();

 	   File domain = (File) arguments.get(Planner.DOMAIN);
 	   File problem = (File) arguments.get(Planner.PROBLEM);
     ErrorManager errorManager = null;

     //Trying to parse the domain and problem given as arguments
 	   try {
 	     errorManager = factory.parse(domain, problem);
 	   } catch (IOException e) {
 	     Planner.getLogger().trace("\n unexpected error when parsing the PDDL planning problem description.");
 	     System.exit(0);
 	   }


 	   if (!errorManager.isEmpty()) {
 		   errorManager.printAll();
 		   System.exit(0);
 		 } else {
 		   Planner.getLogger().trace("\nparsing domain file done successfully");
 		   Planner.getLogger().trace("\nparsing problem file done successfully\n");
 		 }

 	   	//recuperation des param du probleme encoder
      final int traceLevel = (Integer) arguments.get(Planner.TRACE_LEVEL);
      factory.setTraceLevel(traceLevel);
 	   	final Problem pb = factory.encode();

 	   	Planner.getLogger().trace("\nencoding problem done successfully ("
     		    + pb.getActions().size() + " Actions, "
     		    + pb.getTasks().size() + " Task)\n"
     		    + pb.getRelevantFluents().size() + " Fluents)\n");


      // Strat the timer
      Instant start = Instant.now();
      //System.out.println(Instant.now());

      // Making the planner do its job
      Plan myPlan = planner.search(pb);



      // Printing the plan and relevant information for the user
      if (myPlan == null) {
          Planner.getLogger().trace("PFD found no valid plan to solve this problem.\n");
      } else {
          Instant end = Instant.now();
          //System.out.println(Instant.now());
          Duration timeElapsed = Duration.between(start, end);

          System.out.println("found plan as follows:\n\n" + pb.toString(myPlan));
          System.out.println(String.format("%nplan total cost: %4.2f%n%n", myPlan.cost()));
          System.out.println("Problem complexity indicator: " + (pb.getActions().size() + pb.getTasks().size() + pb.getRelevantFluents().size()));
          System.out.println("Time elapsed in ms: "+ timeElapsed.toMillis());
      }
      Planner.getLogger().trace("end\n");

    }

}
