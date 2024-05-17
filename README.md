Graph2Image is a tool that can create SVG vector image files from a Graph. 
v1.0.0

#### Image Export:
Generates images in SGV vector format.
Default file locations for image exports, edge list imports, and exports are the same directory as the program.
Default image file name is Graph_0. If already exists, the name is incremented.

![Forest](forest.png)

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