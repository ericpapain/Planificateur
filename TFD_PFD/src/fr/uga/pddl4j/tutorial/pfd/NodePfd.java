package fr.uga.pddl4j.tutorial.shop2;


import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.TaskNetwork;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


public class Node implements Serializable {


    private State state;
    private TaskNetwork taskNetwork;
    private Node parent;
    private int performedOperation;

    //Constructeurs

    public Node() {
        this(new State(), new TaskNetwork());
    }

    public Node(State state, TaskNetwork taskNetwork) {
        super();
        this.state = state;
        this.taskNetwork = taskNetwork;
    }

    public Node(Node noeud) {
        this(new State(noeud.getState()), new TaskNetwork(noeud.getTaskNetwork()));
    }

    //Getters and Setters

    public State getState() {
        return this.state;
    }

    public void setState(final State state) {
        this.state = state;
    }


    public TaskNetwork getTaskNetwork() {
        return this.taskNetwork;
    }

    public void setTaskNetwork(final TaskNetwork taskNetwork) {
        this.taskNetwork = taskNetwork;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getPerformedOperation() {
        return this.performedOperation;
    }

    public void setPerformedOperation(int performedOperation) {
        this.performedOperation = performedOperation;
    }

    //Fonctions

    public boolean equals (Node node){
        //System.out.println(this.getState());
        //System.out.println(node.getState());
        if ( this.getState().equals(node.getState()) && this.getPerformedOperation() == node.getPerformedOperation() ) {
          System.out.println(node.getState());
          return (true);
        }
        return (false);
    }

}
