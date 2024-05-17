import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {

    private ArrayList<Node<T>> grahList;
    private Node<T> head;
    private HashMap<Node<T>[],T> weightofnodes = new HashMap<Node<T>[], T>();
    public Boolean both_directions;
    public int primsDisconnectedNodesSize;
    public String Name;

    public Graph(){
        grahList = new ArrayList<Node<T>>();
        head = null;
         both_directions = false;
    }
     
    public String getName() {
        if(Name == null){
         return "Graph";
        }
       return Name;
    }

    public int get_total_nodes(){
        return grahList.size();
    }
    public void additem(T element){
        //creates new node with the value and adds to the list
        Node newnode = new Node(element);
        if(head==null){head=newnode;}
        grahList.add(newnode);

    }

    public void addedge(T source, T destination, T weight){        
        //Finds destination node, from given data of the node.
        //Finds source node
        // Adds desnode to source nodes neigbour

        Node<T> des = null;
        Node<T> src = null;
        for (Node<T> node : grahList) {
            if (node.getData().equals(destination)) {
               des = node;
                break;
            }
        }
        for (Node<T> node : grahList) {
            if (node.getData().equals(source)) {
               // add destination to this node
                node.addNodeArray(des);
                src = node;
                break;
            }
        }

    // Adds weight of two nodes into the hashmap 
        Node<T>[] nodeKeys = new Node[2];
        nodeKeys[0] = src;
        nodeKeys[1] = des;
        weightofnodes.put(nodeKeys, weight);

    }

    public ArrayList<Node<T>> getGrahList() {
        return grahList;
    }

    public Node gethead(){
        return head;
    }

    public void removeedge(T source, T destination){        
        //Finds source node, from given data of the node.
       //Removes destination node from source nodes list.

        Node<T> src = null;
        Node<T> des = null;
        for (Node<T> node : grahList) {
            if (node.getData().equals(source)) {
               src = node;
                break;
            }
        }

        for (Node<T> node : grahList) {
            if (node.getData().equals(destination)) {
               des = node;
                break;
            }
        }
        src.removeNodeArray(des);  

    }

    public void removefromlist(T item){
        //removing the node entirely from the graphlist
        for (Node<T> node : grahList) {
            if (node.getData().equals(item)) {
               grahList.remove(item);
                break;
            }
        }
    
    }

    public boolean nodeexits(String parts){
        for (Node<T> node : grahList) {
            if (node.getData().equals(parts)) {
                return true;
            }
        }
        return false;
    }

    public T getWeight(Node<T>[] nodes) {

        for (Node<T>[] keyNodes : weightofnodes.keySet()) {
            if (Arrays.equals(keyNodes, nodes)) {
                return weightofnodes.get(keyNodes);
            }
        }
        return null;
    }

  
    public void makeEdgesBidirectional() {
    // Temporary storage for new edges to avoid modifying the HashMap directly during iteration
    List<Node<T>[]> newEdges = new ArrayList<>();
    List<T> newWeights = new ArrayList<>();

    // Iterate over all existing edges
    for (Map.Entry<Node<T>[], T> entry : weightofnodes.entrySet()) {
        Node<T>[] nodes = entry.getKey();
        T weight = entry.getValue();

        // Prepare the reverse edge as an array
        Node<T>[] reverseEdge = new Node[]{nodes[1], nodes[0]};

        // Check if the reverse edge exists
        boolean exists = false;
        for (Node<T>[] key : weightofnodes.keySet()) {
            if (Arrays.equals(key, reverseEdge)) {
                exists = true;
                break;
            }
        }

        // If the reverse edge does not exist, prepare to add it
        if (!exists) {
            newEdges.add(reverseEdge);
            newWeights.add(weight);
        }
    }

    // Add all new reverse edges
    for (int i = 0; i < newEdges.size(); i++) {
        addedge(newEdges.get(i)[0].getData(), newEdges.get(i)[1].getData(), newWeights.get(i));
    }

    both_directions = true;
          
}
    
    public void print() {
        System.out.println("Printing Graph:");
        for (Node<T> node : grahList) {
            System.out.println(">" + node.getData());
            for (Node<T> arrayNode : node.getNodeArray()) {
                System.out.println("- " + arrayNode.getData());
            }
        }
    }

    public  HashMap<Node<T>[], T> get_weightofnodes() {
    return weightofnodes;
    }
}

