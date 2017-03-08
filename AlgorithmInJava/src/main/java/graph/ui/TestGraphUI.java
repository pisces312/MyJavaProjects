package graph.ui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

public class TestGraphUI {
    public static void main(String [] args) {
        // Construct Model and Graph  
        GraphModel model = new DefaultGraphModel();
        JGraph graph = new JGraph(model);
        //        graph.sets
        //        graph.setSelectNewCells(true);
        //////////////////////////////////////////////////////////
        // Create Nested Map (from Cells to Attributes)  
        // ��Map�м�¼�������ԣ����еļ���ֵ����cell-cellAttribute  
        // ÿ��cellAttribute����һ��Map�������ֵ���Ǿ���һ��cell������-ֵ  
        Map attributes = new Hashtable();
        // ���½�����������(cell)Hello��World�����ֱ��������ǵ�����Map  
        // Create Hello Vertex  
        DefaultGraphCell hello = new DefaultGraphCell("Hello");
        // Create Hello Vertex Attributes  
        Map helloAttrib = new Hashtable();
        attributes.put(hello, helloAttrib);
        // Set bounds  
        Rectangle2D helloBounds = new Rectangle2D.Double(20, 20, 40, 20);
        GraphConstants.setBounds(helloAttrib, helloBounds);
        // Set black border  
        GraphConstants.setBorderColor(helloAttrib, Color.black);
        //////////////////////////////////////////////////////////
        // Add a Port  
        // ÿ������Ϊ���������������ڽӣ�������ӽڵ㣨cell��  
        DefaultPort hp = new DefaultPort();
        hello.add(hp);
        // Create World Vertex  
        DefaultGraphCell world = new DefaultGraphCell("World");
        // Create World Vertex Attributes  
        Map worldAttrib = new Hashtable();
        attributes.put(world, worldAttrib);
        // Set bounds  
        Rectangle2D worldBounds = new Rectangle2D.Double(140, 140, 40, 20);
        GraphConstants.setBounds(worldAttrib, worldBounds);
        // Set fill color  
        GraphConstants.setBackground(worldAttrib, Color.orange);
        GraphConstants.setOpaque(worldAttrib, true);
        // Set raised border  
        GraphConstants.setBorder(worldAttrib,
                BorderFactory.createRaisedBevelBorder());
        //////////////////////////////////////////////////////////
        // Add a Port  
        DefaultPort wp = new DefaultPort();
        world.add(wp);
        // ����������������ı�  
        // Create Edge  
        DefaultEdge edge = new DefaultEdge();
        // Create Edge Attributes  
        Map edgeAttrib = new Hashtable();
        attributes.put(edge, edgeAttrib);
        // Set Arrow  
        int arrow = GraphConstants.ARROW_CLASSIC;
        GraphConstants.setLineEnd(edgeAttrib, arrow);
        GraphConstants.setEndFill(edgeAttrib, true);
        //////////////////////////////////////////////////////////
        // Connect Edge  
        // �ߵ������˵�������������child�ڵ㣨port��  
        ConnectionSet cs = new ConnectionSet(edge, hp, wp);
        Object [] cells = new Object[] {edge, hello, world};
        // Insert into Model  
        // model�������  
        model.insert(cells, attributes, cs, null, null);
        //////////////////////////////////////////////////////////
        // Show in Frame  
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new JScrollPane(graph));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//        frame.pack();
        frame.add(graph);
        frame.setVisible(true);
    }
}
