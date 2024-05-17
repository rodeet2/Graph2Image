import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class graphanalysis<T> {


    public static <T> ArrayList<Node<T>[]> depthFirstSearch(Graph<T> g) {
        ArrayList<Node<T>> visitedList = new ArrayList<>();
        ArrayList<Node<T>[]> nodepaths = new ArrayList<>();
        // Perform DFS traversal from each unvisited node
        for (Node<T> node : g.getGrahList()) {
            if (!visitedList.contains(node)) {
                Stack<Node<T>> stack = new Stack<>();
                stack.push(node);
                while (!stack.empty()) {
                    Node<T> currentNode = stack.pop();
                    if (!visitedList.contains(currentNode)) {
                        visitedList.add(currentNode);
                        for (Node<T> neighbor : currentNode.getNodeArray()) {
                            // Add edge to the ArrayList
                            Node<T>[] edge = (Node<T>[]) new Node[2];
                            edge[0] = currentNode;
                            edge[1] = neighbor;
                            nodepaths.add(edge);
                            if (!visitedList.contains(neighbor)) {
                                stack.push(neighbor);
                            }
                        }
                    }
                }
            }
        }
    
        // Check for isolated nodes and add them to nodepaths
        for (Node<T> node : g.getGrahList()) {
            boolean isIsolated = true;
            for (Node<T>[] path : nodepaths) {
                if (path[0] == node || path[1] == node) {
                    isIsolated = false;
                    break;
                }
            }
            if (isIsolated) {
                Node<T>[] isolatedNode = (Node<T>[]) new Node[2];
                isolatedNode[0] = node;
                isolatedNode[1] = null; // Mark the "to" node as null for isolated nodes
                nodepaths.add(isolatedNode);
            }
        }
    
        // Print and return nodepaths
        // for (Node<T>[] path : nodepaths) {
        //     System.out.println("Printing the array that will be sent: ");
        //     if (path[1] != null) {
        //         System.out.print("(" + path[0].getData() + "," + path[1].getData() + ")");
        //     } else {
        //         System.out.print("(" + path[0].getData() + ", Isolated)");
        //     }
        // }
        return nodepaths;
    }
    
    public static <T> void breathfirstsearch( Graph g){ 
  
        Queue<Node<T>> st = new LinkedList<>();

        st.add(g.gethead());  //define starting node
        ArrayList<Node<T>> visitedlist = new ArrayList<>(); //have a visted list
        Node<T> currentnode = null; 
        while (!st.isEmpty()){
        currentnode = st.remove();
        if(!visitedlist.contains(currentnode)){
           visitedlist.add(currentnode);
           System.out.println("Visited: " + currentnode.getData()); 
            for (Node<T> element : currentnode.getNodeArray()) {
                if (!visitedlist.contains(element)) { st.add(element);};
            }    
        }

    }
    }

    public static <T> ArrayList<Node<T>[]> primmsalgo(Graph g){
        
        Node<T> currentnode;
        int count = 0;
        ArrayList<Node<T>> visited = new ArrayList<>();
        ArrayList<Node<T>[]> tovisitpath = new ArrayList<>();
        ArrayList<Node<T>[]> pathtaken = new ArrayList<>();
        currentnode = g.gethead();
        visited.add(currentnode);
        
        if(!g.both_directions ){g.makeEdgesBidirectional();}
        
        while (visited.size() < g.getGrahList().size() ) {
            count++;
           // System.out.println("Entered visited this many times: " + count + " " + "Total nodes: "+ g.get_total_nodes() );
            // Get neighbors from current node, check if visited, add to tovisitpath as path
            ArrayList<Node<T>> neighborNodes = currentnode.getNodeArray();
            
            for (Node<T> node : neighborNodes) {
                if (!visited.contains(node)) {  
                    Node<T>[] path = new Node[2];
                    path[0] = currentnode;
                    path[1] = node;
                    tovisitpath.add(path);
                }
            }
        
            // System.out.println("Paths Available: ");
            // for (Node<T>[] path : tovisitpath) {
            //     System.out.println("(" + path[0].getData() + " -> " + path[1].getData() + ")");
            // }
        
            
          // Selecting the path with the least weight that leads to an unvisited node
Node<T>[] bestPathOption = null;
int minWeight = Integer.MAX_VALUE;
for (int i = 0; i < tovisitpath.size(); i++) {
    Node<T>[] path = tovisitpath.get(i);
    if (!visited.contains(path[1])) {  // Ensure the endpoint is not visited
           try {
            int weight =  Integer.parseInt((String) g.getWeight(path)); 
            if (weight < minWeight) {
                bestPathOption = path;
                minWeight = weight;
            }
           } catch (Exception e) {
             System.out.println("Unable to process weight of edges");
             continue;
           }
    }
}
            // Check if a valid path is found
            if (bestPathOption != null) {
                // Make it the current node
                currentnode = bestPathOption[1];
                // Add to visited
                visited.add(currentnode);
                // Remove best path from tovisitpath
                tovisitpath.remove(bestPathOption);
                //Add to pathtaken
                pathtaken.add(bestPathOption);
                //System.out.println("Choosen road: " + bestPathOption[0].getData() + " " + bestPathOption[1].getData());
                // Remove any paths leading to visited nodes
                Iterator<Node<T>[]> it = tovisitpath.iterator();
                while (it.hasNext()) {
                    Node<T>[] path = it.next();
                    if (visited.contains(path[1])) {
                        it.remove();
                    }
                }

            } else {
                // If no valid path is found, break out of the loop
                //System.out.println("No valid paths remaining, but not all nodes are visited.");
                break;
            }
        }
        g.primsDisconnectedNodesSize = visited.size();
        return pathtaken;
    }
    
}



