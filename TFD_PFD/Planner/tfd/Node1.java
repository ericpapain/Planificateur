package fr.uga.pddl4j.Planner.tfd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import java.util.Comparator;

import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.TaskNetwork;

public class Node1 implements Comparable<Node1>{

	private State state;
	private int operator;
	private Node1  parent;
	private LinkedList<Integer> tasksList;
	
	//
	public Node1(final Node1 other) {
        this(new State(other.getState()), other.getTasksList(), other.getParent(), other.getOperator());
    }
	//
	public Node1() {
	    this(new State(), new LinkedList<Integer>(), null, Integer.MAX_VALUE);
    }
	//
	public Node1(final State state, final List<Integer> tasksList) {
        super();
        this.setState(state);
        this.setTasksList((LinkedList<Integer>) tasksList);
        this.setParent(null);
        this.setOperator(Integer.MAX_VALUE);
    }
	//
    public Node1(final State state, final List<Integer> taskList, final Node1 parent, final int operator) {
        super();
        this.setState(state);
        this.setTasksList((LinkedList<Integer>) taskList);
        this.setParent(parent);
        this.setOperator(operator);
    }
    //
	public final State getState() {
		return this.state;
	}

	public final void setState(State state) {
		this.state = state;
	}

	public int getOperator() {
		return this.operator;
	}
	
	public void setOperator(int operator) {
		this.operator = operator;
	}
	
	public Node1  getParent() {
		return parent;
	}
	
	public void setParent(Node1  parent) {
		this.parent = parent;
	}
	
	public LinkedList<Integer> getTasksList() {
		return this.tasksList;
	}
	
	public void setTasksList(LinkedList<Integer> tasksList) {
		this.tasksList = new LinkedList<Integer>(tasksList);
	}
	
    public final boolean putListSubTasksAtTop(List<Integer> tasks) {
    			List<Integer> taskList = this.getTasksList();
    		return  tasksList.addAll(0, tasks); //taskList.addAll(0, subTasks);
    }
    
    public int compareTo(Node1 node) {
        return (this.getTasksList().size() - node.getTasksList().size());
    }

	
//	// method compare to
//    public int compareTo(Node noeud) {
//        return this.getTasksList().size() - noeud.getTasksList().size();
//    }
//
//    /**
//     * Returns <code>true</code> if a node is equals to an other object. An object is equals to this node if and only
//     * if the other object is an instance of <code>TFDNode</code> and have the same state and the same task network.
//     *
//     * @param obj the object to be compared.
//     * @return <code>true</code> if a node is equals to an other object, <code>false</code> otherwise.
//     * @see java.lang.Object#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals(final Object obj) {
//        if (obj != null && obj instanceof Node) {
//            Node other = (Node) obj;
//            return this.getState().equals(other.getState()) && this.getTasksList().equals(other.getTasksList());
//        }
//        return false;
//    }
//
//    /**
//     * Returns the hash code value of the node.
//     *
//     * @return the hash code value of the node.
//     * @see java.lang.Object#hashCode()
//     */
//    @Override
//    public int hashCode() {
//        return Objects.hash(getState(), getTasksList());
//    }
	////////////
//
//	public static void main(String[] agrs){
//		
//		Node1  noeudParent = new Node1 ();
//		Node1  noeudTest = new Node1 ();
//		
//		LinkedList<Integer> tasksList = new LinkedList<>();
//		LinkedList<Integer> tasksList1 = new LinkedList<>();
//		LinkedList<Integer> tasksList2 = new LinkedList<>();
//		
//		tasksList.push(2222);
//		tasksList.push(12111);
//		tasksList.push(18888);
//		tasksList.push(17777);             // first in last out
//		
//		tasksList1.add(2);
//		tasksList1.add(12);
//		tasksList1.add(18);
//		tasksList1.add(17);             // first in first out
//		
//		tasksList2.addAll(0,tasksList);
//		tasksList2.add(12);
//		tasksList2.add(18);
//		tasksList2.add(17);             // first in first out
//		
//		noeudParent.setTasksList(tasksList2);
//		
//		noeudTest.setOperator(18);
//		noeudTest.setParent(noeudParent);
//		noeudTest.setState(null);
//		noeudTest.setTasksList(tasksList1);
//		
//		Node1  Node1 urfil = new Node1 ();
//		
//		
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().peek());            // selectionne l'element sans le supprimer
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().poll());            //pop et pull retire et supprime
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().pop());
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().add(19));           // envoiee en fin de la liste
//		System.out.println(noeudTest.getTasksList());
//		//System.out.println(noeudTest.getTasksList().addFirst(199));   // envoiee au debut de la liste
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(tasksList);
//		System.out.println(noeudTest.putListSubTasksAtTop(tasksList));              //coorespond a a.b = [a,b]
//		System.out.println(noeudTest.getTasksList());
//		
//		System.out.println(noeudTest.putListSubTasksAtTop(tasksList));              //coorespond a a.b = [a,b]
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().pop());             // prend le premier element et le supprime
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().pop());
//		System.out.println(noeudTest.getTasksList());
//		System.out.println(noeudTest.getTasksList().pop());
//		System.out.println(noeudTest.getTasksList());
//		int task = noeudTest.getTasksList().pop();
//		
//		System.out.println(task);
//		System.out.println(noeudTest.getTasksList());
//		noeudTest.getTasksList().push(task);                           // permet d'ajouter en tete de liste d'une liste chainée
//		System.out.println(noeudTest.getTasksList());
//		
//		LinkedList<Node1 > openList = new LinkedList<>();
//		openList.add(noeudParent);
//		openList.push(noeudTest);
//		openList.push(Node1 urfil);										//push l'insere en tete de la liste chainer
//		//System.out.println(openList.pop()); 
//		System.out.println(noeudParent); 
//		System.out.println(noeudTest); 
//		
//		System.out.println(openList);
//		
//	}
	
}
