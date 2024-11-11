import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.*;
import java.util.*;
import org.apache.batik.svggen.*;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class makeimage<T> extends JFrame {

    JPanel panel;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = (int) screenSize.getWidth();
    private int screenheight = (int) screenSize.getHeight();
    ArrayList<Node<String>[]>  nodestodraw = new ArrayList<>();
    int TotalnumOfnodes;
    JScrollPane scrollPane = new JScrollPane();
    private boolean generateSVG = false;
    private Graphics2D svgGraphics;
    private DOMImplementation domImpl;
    private Document document;
    private SVGGraphics2D svgGenerator;
    int startX =0;
    int startY =0;
    private int max_down;
    private int max_up;
    private int max_right;
    private int max_left;
    Boolean drawingcomplte = false;

    public makeimage(ArrayList listofedges, int numnodes) {
        nodestodraw = listofedges;
        TotalnumOfnodes = numnodes;

        setTitle("Graph");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        panel = new CustomPanel();
        panel.setPreferredSize(new Dimension(getWidth(),getHeight()));
        panel.setLayout(null);
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

        scrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawOnCanvas((Graphics2D) g);
        }
    }


    private void drawOnCanvas(Graphics2D g2d) {

        int drawnsofar = 0;
        HashMap<Node<String>, int[]> nodeLocations = new HashMap<>();
        Boolean openTonewRoot = false;
        boolean[][] canvas = new boolean[5000][5000];
        // Starting coordinates
        startX = screenWidth / 2;
        startY = screenheight / 2;
        max_up = startY;
        max_down = startY;
        max_left = startX;
        max_right = startX;
        int gap = 100; // Gap between nodes

        while (drawnsofar < TotalnumOfnodes) {
           if(drawnsofar < TotalnumOfnodes-1){drawingcomplte = true;}
            for (int i = 0; i < nodestodraw.size(); i++) {
                if (i == (nodestodraw.size()) - 1) { openTonewRoot = true; }
                Node<String>[] array = nodestodraw.get(i);
                Node<String> node1 = array[0];
                Node<String> node2 = array[1];   
                int x1, y1;
                if (i == 0 && !nodeLocations.containsKey(node1)) {
                    //for the very first node, which is not in nodelocations, and i is 0.
                    x1 = startX;
                    y1 = startY;
                    int[] location1 = {x1, y1};
                    nodeLocations.put(node1, location1);
                    canvas[x1][y1] = true;
                    update_max_values(x1, y1); 
                    drawNode(g2d, node1, x1, y1);
                    drawnsofar++;
                } else if (!nodeLocations.containsKey(node1)) {
                    //for when node is not in nodelocations, i not 0
                    if (nodeLocations.containsKey(node2)) {
                    //but its is connected to a node that is in nodeloactions and drawn. So will be drawn
                    //branching from the already drawn node.
                        int[] location1 = nodeLocations.get(node2);
                        int x2 = location1[0];
                        int y2 = location1[1];
                        //check if location is free else find another new location to draw
                        int[] newXY = check_location(x2,y2,canvas,gap);
                        x1 = newXY[0];
                        y1 = newXY[1];
                        
                        canvas[x1][y1] = true;
                        int[] location3 = {x1, y1};
                        drawNode(g2d, node1, x1, y1);
                        nodeLocations.put(node1, location3);
                        update_max_values(x1, y1);
                        drawnsofar++;
                    } else {
                    //both nodes not in nodelocations, as not drawn, and also not first root, so new root to be made.
                    //but still should wait for one cycle, to draw remaing nodes, then be open to make new roots
                        if (openTonewRoot) {
                            x1 = startX + gap;
                            y1 = startY;
                            while(canvas[x1][y1]){      //change location if already taken
                            x1 = x1 + gap;
                            y1 = startY;
                            }
                            int[] location1 = {x1, y1};
                            canvas[x1][y1] = true;
                            if(!nodeLocations.containsKey(node1)){drawNode(g2d, node1, x1, y1); 
                            }
                            nodeLocations.put(node1, location1);
                            update_max_values(x1, y1);
                            startX = x1;
                            drawnsofar++;
                            openTonewRoot = false; //closes, has wait for another cycle again.
                        } else { continue; //skips this node, until one cycle
                    }
                }}else {
                    //In nodelocations, load x1, y1, to calculate the connected edge
                    int[] location1 = nodeLocations.get(node1);
                    x1 = location1[0];
                    y1 = location1[1];
                }
                 //check if location is free else find another new location to draw
                 int x2 = 0, y2 = 0;         
                if (node2 != null && nodeLocations.get(node2) == null) {
                    int[] newXY = check_location(x1,y1,canvas,gap);
                    x2 = newXY[0];
                    y2 = newXY[1];
                    canvas[x2][y2] = true;
                    int[] location2 = {x2, y2};
                    if(!nodeLocations.containsKey(node2)){drawNode(g2d, node2, x2, y2);                         
                    }
                    nodeLocations.put(node2, location2);
                    update_max_values(x2, y2);
                    drawnsofar++;
                }
            }
            //draw lines and arrows
            for (int i = 0; i < nodestodraw.size(); i++) {
                Node<String>[] array = nodestodraw.get(i);
                Node<String> node1 = array[0];
                Node<String> node2 = array[1];  
                if (node2 != null && nodeLocations.get(node1) != null && nodeLocations.get(node2) != null) {
                    // Calculate the midpoint, and add arrow
                    int x1 = nodeLocations.get(node1)[0];
                    int y1 = nodeLocations.get(node1)[1];
                    int x2 = nodeLocations.get(node2)[0];
                    int y2 = nodeLocations.get(node2)[1];
                    g2d.drawLine(x1, y1, x2, y2);
                    int midX = (x1 + x2) / 2;
                    int midY = (y1 + y2) / 2;
                    double angle = Math.atan2(y2 - y1, x2 - x1);
                    int arrowSize = 10;

int arrowX1 = (int) (midX - arrowSize * Math.cos(angle - Math.PI / 6));
int arrowY1 = (int) (midY - arrowSize * Math.sin(angle - Math.PI / 6));
int arrowX2 = (int) (midX - arrowSize * Math.cos(angle + Math.PI / 6));
int arrowY2 = (int) (midY - arrowSize * Math.sin(angle + Math.PI / 6));

int[] xPoints = {midX, arrowX1, arrowX2};
int[] yPoints = {midY, arrowY1, arrowY2};
int[] yPoints_c = {midY-7, arrowY1-14, arrowY2-14};
if(node1 == node2){g2d.fillPolygon(xPoints,yPoints_c, 3);}else{g2d.fillPolygon(xPoints,yPoints, 3);}

                }
            }
        }
    }

    private void drawNode(Graphics2D g2d, Node<String> node, int x, int y) {
        int circleRadius = 20; 
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x - circleRadius, y - circleRadius, 2 * circleRadius, 2 * circleRadius);
        String nodeName = node.getData();
        if (nodeName.length() > 10) {
            nodeName = nodeName.substring(0, 10) + "..";
        }
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(nodeName);
        int textX = x - textWidth / 2;
        int textY = y + fm.getAscent() / 2;
        g2d.drawString(nodeName, textX, textY);
    }

    // public void captureScreenshotAndSave() {
    //     try {
    //         File outputFile = new File("graph_image.png");
    //         ImageIO.write(canvasImage, "png", outputFile);
    //         System.out.println("Screenshot saved as: " + outputFile.getAbsolutePath());
    //     } catch (IOException ex) {
    //         ex.printStackTrace();
    //     }
    // }

    // public void scheduleScreenshot() {
    //     Timer timer = new Timer();
    //     timer.schedule(new TimerTask() {
    //         @Override
    //         public void run() {
    //             captureScreenshotAndSave();
    //         }
    //     }, 7000); // 1000 milliseconds = 1 second
    // }

    public void saveAsSVG(String fileName) {
    
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
        try {
            domImpl = SVGDOMImplementation.getDOMImplementation();
            document = domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
            svgGenerator = new SVGGraphics2D(document);
        
       // Calculate the dimensions of the drawing
     if(drawingcomplte){
        //how much it spans, plus extra
        int drawingWidth = Math.abs(startX - max_left) + Math.abs(max_right - startX) + 100;
       int drawingHeight = Math.abs(max_up - startY) + Math.abs(startY - max_down) +100;
       panel.setPreferredSize(new Dimension(getWidth()+drawingWidth,getHeight()+drawingHeight));

       svgGenerator.setSVGCanvasSize(new Dimension(drawingWidth, drawingHeight));
// Calculate the center of the graphic
int graphicCenterX = max_left + (max_right - max_left) / 2;
int graphicCenterY = max_up + (max_down - max_up) / 2;

// Calculate the center of the viewport
int viewportCenterX = drawingWidth / 2;
int viewportCenterY = drawingHeight / 2;

// Calculate offsets to center the graphic within the viewport
int offsetX = viewportCenterX - graphicCenterX;
int offsetY = viewportCenterY - graphicCenterY;
//Bring to the center
svgGenerator.translate(offsetX,offsetY);

     }
            generateSVG = true;
            drawOnCanvas(svgGenerator);
            generateSVG = false;
            boolean useCSS = true;
            try (Writer out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
                svgGenerator.stream(out, useCSS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update_max_values(int x, int y){
     if(x<max_left){max_left = x;}
     if(x>max_right){max_right = x;}
     if(y<max_up){max_up = y;}
     if(y>max_down){max_down = y;}
    }

    public int[] check_location(int x,int y, boolean[][] canvas, int gap){
    
        if (!canvas[x][y + gap]) { //down
            x = x;
            y = y + gap;
        } else if (!canvas[x - gap][y + gap]) { //down left
            x = x - gap;
            y = y + gap;
        } else if (!canvas[x + gap][y + gap]) { //down right
            x = x + gap;
            y = y + gap;
        } else if (!canvas[x - gap][y]) {  // left
            x = x - gap;
            y = y;
        } else if (!canvas[x + gap][y]) { // right
            x = x + gap;
            y = y;
        } else if (!canvas[x - gap][y - gap]) { //up left
            x = x - gap;
            y = y - gap;
        } else if (!canvas[x + gap][y - gap]) { // up right
            x = x + gap;
            y = y - gap;
        } else if (!canvas[x][y - gap]) {//up
            x = x;
            y = y - gap;
        } else {
            int newgap = gap + gap;
            int[] newCoordinates = check_location(x, y, canvas, (newgap));
            x = newCoordinates[0];
            y = newCoordinates[1];
        }
        
        int[] coordinates = {x, y};
        return coordinates;
    }

}
