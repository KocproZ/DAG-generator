package ovh.kocproz.dag;

import java.util.LinkedList;
import java.util.List;

/**
 * Parent --> Child
 *
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class Node<T> {

    private List<Node<T>> parents;
    private List<Node<T>> children;
    private T object;

    protected Node(T object) {
        this.object = object;
        parents = new LinkedList<>();
        children = new LinkedList<>();
    }

    public T getObject() {
        return object;
    }

    List<Node<T>> getParents() {
        return parents;
    }

    List<Node<T>> getChildren() {
        return children;
    }

    public void addParents(Node<T>... dep) {
        for (Node<T> n : dep) {
            n.addChild(this);
            parents.add(n);
        }
    }

    void addParent(Node<T> parent) {
        if (parent == this) throw new CycleFoundException(this.toString() + "->" + this.toString());
        parents.add(parent);
        if (parent.getChildren().contains(this)) return;
        parent.addChild(this);
    }

    void addChild(Node<T> child) {
        if (child == this) throw new CycleFoundException(this.toString() + "->" + this.toString());
        children.add(child);
        if (child.getParents().contains(this)) return;
        child.addParent(this);
    }

    @Override
    public String toString() {
        return "Node{" +
                "parents=" + parents.size() +
                ", children=" + children.size() +
                ", object=" + object.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node)
            return object.equals(((Node<T>) obj).getObject());
        else return false;
    }
}