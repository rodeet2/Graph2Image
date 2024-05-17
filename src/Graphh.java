import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Graphh<T> {

    // This uses hashmap instead of Arraylist to store the Nodes
    // It also stores the neighbor arraylist with the grahlist and not with the nodes, when making edges. 

HashMap<Node<T>, ArrayList<T>> grahList;
HashMap<T, Node<T>> datatonode;

public Graphh(){
    grahList = new HashMap<Node<T>, ArrayList<T>>();    
}

public void additem(T element){
    // Adds a node with element, and arrylist for storing neighbor nodes
    Node<T> newnode = new Node<T>(element);
    grahList.put(newnode,new ArrayList<>());
    datatonode.put(element, newnode);
}


public void addedge(T source, T destination){

for (Entry<Node<T>, ArrayList<T>> entry : grahList.entrySet()) {
    Node<T> key = entry.getKey();
    if (key.getData().equals(source)) {
        grahList.get(key).add(destination);
    }
}

}

public void removeedge(T source, T destination){
    
    for (Entry<Node<T>, ArrayList<T>> entry : grahList.entrySet()) {
        Node<T> key = entry.getKey();
        if (key.getData().equals(source)) {
            grahList.get(key).remove(destination);
        }
    }

    }

    public void print() {
        System.out.println("Graph details: ");
        for (HashMap.Entry<Node<T>, ArrayList<T>> entry : grahList.entrySet()) {
            Node<T> node = entry.getKey();
            ArrayList<T> neighbors = entry.getValue();
            StringBuilder neighborList = new StringBuilder();
            for (T neighbor : neighbors) {
                neighborList.append(neighbor).append(", ");
            }
            if (neighborList.length() > 0) {
                // Remove the trailing comma and space
                neighborList.setLength(neighborList.length() - 2);
            }
            System.out.println("\t" + node + " -> " + neighborList);
        }
    }

}
