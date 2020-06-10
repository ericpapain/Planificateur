package fr.uga.pddl4j.Planner.tfd;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.TaskNetwork;

public class Node {

	private State state;
	private int operator;
	private Node parent;
	private LinkedList<Integer> tasksList;
	
	
	
	public Node() {
		this.state = new State();
		this.operator = Integer.MAX_VALUE;
		this.parent = null;
		this.tasksList = new LinkedList<Integer>();
	}
	
	
	
	public Node(Node parent, LinkedList<Integer> tasksList) {
		super();
		this.state = parent.getState();
		this.operator = Integer.MAX_VALUE;
		this.tasksList = tasksList;
		this.parent = null;
	}



	public Node(State state,List<Integer> tasksList) {
		super();
		this.state = state;
		this.operator = Integer.MAX_VALUE;
		this.parent = null;
		this.tasksList = (LinkedList<Integer>) tasksList;
	}



	public Node(int operator, Node parent,
			LinkedList<Integer> tasksList) {
		super();
		this.operator = operator;
		this.parent = parent;
		this.tasksList = tasksList;
	}


	public Node(Node node) {
		super();
		this.setState(node.getState()); 
		this.setOperator(node.getOperator());
		this.setParent(node.getParent());
		this.setTasksList(node.getTasksList());
	}
	

	public final State getState() {
		return state;
	}

	public final void setState(State state) {
		this.state = state;
	}

	public int getOperator() {
		return operator;
	}
	
	public void setOperator(int operator) {
		this.operator = operator;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public LinkedList<Integer> getTasksList() {
		return tasksList;
	}
	
	public void setTasksList(LinkedList<Integer> tasksList) {
		this.tasksList = tasksList;
	}
	
	///////////////nouvelle methode de manipulation/////////////////
	
	public final Boolean putListSubTasksAtTop(final List<Integer> subTasks){
		//List<Integer> taskList = this.getTasksList();
		return  this.tasksList.addAll(0, subTasks); //taskList.addAll(0, subTasks);
	}
	
	////////////
//
//	public static void main(String[] agrs){
//		
//		Node noeudParent = new Node();
//		Node noeudTest = new Node();
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
//		Node nodeurfil = new Node();
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
//		LinkedList<Node> openList = new LinkedList<>();
//		openList.add(noeudParent);
//		openList.push(noeudTest);
//		openList.push(nodeurfil);										//push l'insere en tete de la liste chainer
//		//System.out.println(openList.pop()); 
//		System.out.println(noeudParent); 
//		System.out.println(noeudTest); 
//		
//		System.out.println(openList);
//		
//	}
	
}
