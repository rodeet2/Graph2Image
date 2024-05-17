import java.io.File;
import java.util.Scanner;

public class App {

    Graph thegraph;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * @param <T>
     * @param args
     * @throws Exception
     */

    public static <T> void main(String[] args) throws Exception {

       Graph<T> thegraph = new Graph();  ;

       mainmenu(thegraph);
      
    }

    public static void mainmenu(Graph thegraph){
        System.out.println("1. Add Nodes");
        System.out.println("2. Add Edges");
        System.out.println("3. Remove Edges");
        System.out.println("4. Make Image");
        System.out.println("5. Minimum Span Graph Image");
        System.out.println("6. Import from edge list");
        System.out.println("7. Export to edge list");
    
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "1":
                addmenu(thegraph);
                break;
            case "2":
                addedgemenu(thegraph);
                break;
            case "3":
                removeedgemenu(thegraph);
                break;
            case "4":
                makeimagemenu(thegraph);
                break;
            case "5":
                makeimage_min(thegraph);
                break;
            case "6":
                import_edge_list(thegraph);
                break;
            case "7":
                export_edge_list(thegraph);
                break;
            default:
                System.out.println("Invalid Input");
                mainmenu(thegraph);
                break;
        }
    
    }

    public static<T>void addmenu(Graph <T> g){
        while (true) {
            System.out.println("Add Nodes, enter 'd' when done");
            T currentinput = (T) scanner.nextLine();
            if (currentinput.equals("d")) {
                System.out.println("Items are added!");
                break;
            } else if (currentinput.equals("")) {
                System.out.println("Null!");
            } else if (g.nodeexits((String)currentinput)) {
                System.out.println("Already Exists!");
            } else {
                g.additem(currentinput);
            }
        }
        mainmenu(g);
    }

    public static<T>void addedgemenu(Graph <T> g){
        while (true) {
            System.out.println("Add Edges between two Nodes and weight separated with comma, enter 'd' when done");
            T currentinput = (T) scanner.nextLine();
            if (currentinput.equals("d")) {
                System.out.println("Edges saved!");
                break;
            } else {
                String[] parts = ((String.valueOf(currentinput)).replaceAll("\\s", "").split(",", 0));
                if (formatcheck(currentinput, parts, g, 2)) {
                    g.addedge((T) parts[0], (T) parts[1], (T) parts[2]);
                } else {
                    System.out.println("Unable to add edges! Check format and node name!");
                }
            }
        }
        mainmenu(g);
    }

    public static<T>void removeedgemenu(Graph <T> g){
        while (true) {
            System.out.println("Remove edges, enter two nodes separated with comma, enter 'd' when done");
            T currentinput = (T) scanner.nextLine();
            if (currentinput.equals("d")) {
                System.out.println("Edges Removed!");
                break;
            } else {
                String[] parts = ((String.valueOf(currentinput)).replaceAll("\\s", "").split(",", 0));
                if (formatcheck(currentinput, parts, g, 1)) {
                    g.removeedge((T) parts[0], (T) parts[1]);
                } else {
                    System.out.println("Unable to remove edges! Check format and node name!");
                }
            }
        }
        mainmenu(g);
    
    }

public static void makeimagemenu(Graph  g){
    String extenstion = ".svg";
   makeimage image = new makeimage(graphanalysis.depthFirstSearch(g),g.get_total_nodes());
   String increamentName = file_increment(g.getName()+"_0",extenstion);
   image.saveAsSVG(increamentName);
   mainmenu(g);
   return;
}

public static void makeimage_min(Graph  g){
    String extenstion = "_min.svg";
    if(g.get_weightofnodes().isEmpty()){
        System.out.println("Null! No connections in graph");}
    else if(((Node)g.getGrahList().get(0)).getNodeArray().isEmpty()){
        System.out.println("Disconnected Root");
    }else{
        makeimage image = new makeimage(graphanalysis.primmsalgo(g), g.primsDisconnectedNodesSize);
        String increamentName = file_increment(g.getName()+"_0",extenstion);
        image.saveAsSVG(increamentName);
    }
      mainmenu(g);
      return;
 }
 

public static void import_edge_list(Graph  g){
    System.out.println("Enter the file name of the txt file:  ");
    String path =  scanner.nextLine();
    if(!path.contains(".txt")){path = path + ".txt";}
    Graph edgelist_Graph = importEdgeList.read(path);
    if(!(edgelist_Graph==null)){ 
    g=null;   
    System.out.println("-Import completed\n");}
    mainmenu(edgelist_Graph);
    return;
}

public static void export_edge_list(Graph  g){
    System.out.println("Enter the file name of the text file:  ");
    String path =  scanner.nextLine();
    if(!path.contains(".txt")){path = path + ".txt";}
    if(!(g==null)){ importEdgeList.write(path, g); 
    System.out.println("-Export completed\n"); g=null;}else{ System.out.println("-Null Graph!\n");}
    mainmenu(g);
    return;
}

public static void print(Graph  g){
    g.print();
    mainmenu(g);
     return;
}


public static <T> boolean formatcheck(T input, String[] parts, Graph<T> g, int expectedparts){
       
    if(parts.length<=expectedparts){return false;}

    //check if there is no coma
   if (!String.valueOf(input).contains(",")){return false;}

    //check if there was more than three commas
    int count = 0;
    for (int i = 0; i < String.valueOf(input).length(); i++) {
        if (String.valueOf(input).charAt(i) == ',') {
            count++;
            if (count > 2) {
                return false;
            }
    }
}

//if node not exixts
if(!g.nodeexits(parts[0])){return false;}
if(!g.nodeexits(parts[1])){return false;}

return true;
}

public static String file_increment(String name, String extention){
File file = new File(name+extention);
String[] name_version = name.split("_",0);  
int version = 0;
    while (file.exists()) {
        version = version+1;
        file = null;
        file = new File(name_version[0]+"_"+version+extention);
    }
    name = name_version[0]+"_"+ version+extention;
    file = null;
return name;

}}