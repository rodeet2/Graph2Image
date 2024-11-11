Graph2Image is a tool that can create SVG vector image files from a Graph. 
v1.0.0

#### Image Export:
Generates images in SGV vector format.
Default file locations for image exports, edge list imports, and exports are the same directory as the program.
Default image file name is Graph_0. If already exists, the name is incremented.

![Forest](forest.png)

#### Design Logic:

The function takes a list of nodes to be drawn, where Node1 and Node2 are connected.
Starting Node: If Node1 is the first node in the list, it's drawn as the root node.

Connection Logic:
If Node1 is already drawn, it uses its location to find a new position for Node2.
If Node1 hasn’t been drawn but Node2 has, it uses Node2's location to determine where to draw Node1.
If neither Node1 nor Node2 has been drawn, the algorithm considers creating a new root node, but only after completing one full cycle of checking the node list to ensure no connections are missed.

Space Checking:
To position a new node, it checks empty spaces in this order: down, right, left, and up.
If no space is available, it increases the gap size and tries again, recursively, until it finds a empty spot.

New Root Creation:
When there’s a node unconnected to any previously drawn nodes, a new root node is created a bit apart from the initial root.
The creation of a new root is restricted to after a complete cycle through the list, ensuring that all nodes with possible connections to the existing tree are drawn first.
Once a new root is created, the gate for new roots closes again until the next cycle.



#### Node Visualization:
Nodes are created gravitating downwards in a tree-like structure.
Arrows point towards the connected node, positioned at the center of each line.
The program may fail if node limt exceeds 60.
#### Import Edge List:
Edge lists need to be in .txt format.
Nodes should be separated by a space (" ").
Integer is expected for weight.
Content: Each line should contain three items: Node1 Node2 weight.
Export: Edge lists exported from the program follows the same format.
Example: Node1 Node2 10

#### Dependencies and libraries:

batik-parser-1.17.jar
batik-dom-1.17.jar
batik-xml-1.17.jar
batik-ext-1.17.jar
batik-extension-1.17.jar
batik-svggen-1.17.jar
batik-css-1.17.jar
batik-i18n-1.17.jar
batik-svg-dom-1.17.jar
batik-awt-util-1.17.jar
batik-constants-1.17.jar
xml-apis-1.4.01.jar
xml-apis-ext-1.3.04.jar
batik-util-1.17.jar
batik-anim-1.17.jar

https://xmlgraphics.apache.org/batik/download.html

## Run
Download the recent release, Graph2Image.JAR
Have JRE installed.

###Running using mac terminal:
chmod 755 Graph2Image.jar
java -jar Graph2Image.jar
