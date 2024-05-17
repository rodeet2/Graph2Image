import java.util.ArrayList;

public class Node<T> {

    private T data;
    private ArrayList<Node<T>> nodeArray;

    public Node( T d){
        data = d;
        nodeArray = new ArrayList<Node<T>> ();
    }
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ArrayList<Node<T>> getNodeArray() {
        return nodeArray;
    }

    public void addNodeArray(Node<T> given_item) {
			nodeArray.add(given_item);		
    }
    
    public void removeNodeArray(Node<T> given_item) {
        nodeArray.remove(given_item);		
}

public boolean isConnectedTo(Node<T> node) {
    return nodeArray.contains(node);
}



@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node<?> node = (Node<?>) obj;
        return data != null ? data.equals(node.data) : node.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

}
