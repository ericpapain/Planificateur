package fr.uga.pddl4j.tutorial.tfd;

import java.util.LinkedList;
import java.util.List;

import fr.uga.pddl4j.problem.Method;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.TaskNetwork;

public class NodeTFD {

	//operateur du noeud
	private int operator;
	
	//reseau de tache du noeud
	private TaskNetwork taskNetwork;
	
	//etat du noeud
	private State state;
	
	//noeud parent
	private NodeTFD parent;

    /**
     * constructeur a partir d'un autre noeud
     */
    public NodeTFD(final NodeTFD noeud){
    	this.state = noeud.state;
        this.taskNetwork = noeud.taskNetwork;
        this.parent = noeud.parent;
        this.operator = noeud.operator;
    }
	
	/**
	 * creation par defaut du noeud
	 */
	public NodeTFD() {
		super();
		this.state = null;
		this.parent = null;
		this.operator = Integer.MAX_VALUE;
		this.taskNetwork = null;
	}
	
	/**
	 * in the creation of new node we have a max value to specified de max value of the numeric in java 2^31 -1 
	 * @param state2
	 * @param taskNetwork2
	 */
	public NodeTFD(final State state, final TaskNetwork taskNetwork){
		super();
        this.state = state;
        this.taskNetwork = taskNetwork;
        this.parent = null;
        this.operator = Integer.MAX_VALUE;
	}

	
	public NodeTFD(State state, NodeTFD parent, TaskNetwork taskNetwork, int operator) {
		super();
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.taskNetwork = taskNetwork;
	}

	/**
	 * 
	 * @return
	 */
    public final int getOperator() {
		return operator;
	}
    
    /**
     * 
     * @param operator
     */
	public final void setOperator(int operator) {
		this.operator = operator;
	}
	
	/**
	 * 
	 * @return
	 */
	public final TaskNetwork getTaskNetwork() {
		return taskNetwork;
	}
	
	/**
	 * 
	 * @param taskNetwork
	 */
	public final void setTaskNetwork(TaskNetwork taskNetwork) {
		this.taskNetwork = taskNetwork;
	}
	
	/**
	 * 
	 * @return
	 */
	public final State getState() {
		return state;
	}
	
	/**
	 * 
	 * @param state
	 */
	public final void setState(State state) {
		this.state = state;
	}

	/**
	 * 
	 * @return
	 */
	public final NodeTFD getParent() {
		return parent;
	}
	
	/////////we add the new method of the node
	/**
	 * get tasks list of our task network
	 */
	public LinkedList<Integer> taskList() {
		return (LinkedList<Integer>) this.getTaskNetwork().getTasks();
	}
	/**
	 * push a subtask
	 * @param method
	 * @param currentNode
	 */
	public void pushSubTask(Method method) {
		List<Integer> listSubTasksMethod = method.getSubTasks();
		this.getTaskNetwork().getTasks().addAll(0, listSubTasksMethod);
	}
	/**
	 * 
	 * @param parent
	 */
	public final void setParent(NodeTFD parent) {
		this.parent = parent;
	}

}
