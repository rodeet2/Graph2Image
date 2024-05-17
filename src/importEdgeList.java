import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class importEdgeList {

    private static final Logger LOGGER = Logger.getLogger(importEdgeList.class.getName());
    static String path;

    public static Graph read(String file_path) {
        path = file_path;

        File file = new File(file_path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            LOGGER.log(Level.SEVERE, "File does not exist or is not readable: " + file_path);
            return null;
        }

        Graph g = new Graph();
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("#")){continue;}
                lineCount++;
                String[] items = line.split("\\s+");

                if (items.length == 3 && !items[0].isEmpty() && !items[1].isEmpty() && !items[2].isEmpty()) {
                    if (!g.nodeexits(items[0])) {
                        g.additem(items[0]);
                    }
                    if (!g.nodeexits(items[1])) {
                        g.additem(items[1]);
                    }
                    if(!g.get_weightofnodes().containsKey(items)) {
                    g.addedge(items[0], items[1], items[2]);
                    }

                } else {
                    LOGGER.log(Level.WARNING, "Unable to read line: " + lineCount + " (" + line + ")");
                }

                if (lineCount == 60) { 
                    return g;
                }

            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred while reading the file", e);
        }
        
        g.Name = file.getName().replace(".txt","");
        return g;
    }

    public static <T> void write(String file_path, Graph<T> graph) {
        if (file_path == null || file_path.isEmpty()) {
            LOGGER.log(Level.SEVERE, "File path is null or empty");
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file_path))) {
            String line = "";

            if (graph.get_weightofnodes().isEmpty()) {
                LOGGER.log(Level.INFO, "No edges found");
                for (Node<T> node : graph.getGrahList()) {
                    line = (String) node.getData();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } else {
                Set<Node<T>[]> keySet = graph.get_weightofnodes().keySet();
                for (Node<T>[] key : keySet) {
                    line = key[0].getData() + " " + key[1].getData() + " " + graph.getWeight(key);
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred while writing to file: " + file_path, e);
        }
    }
}
