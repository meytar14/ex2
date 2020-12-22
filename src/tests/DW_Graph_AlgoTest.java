package tests;

import api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DW_Graph_AlgoTest {

    @org.junit.Test
    public void init() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(graph);
        assertEquals(graph_algo.getGraph(), graph);
    }

    @org.junit.Test
    public void getGraph() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(graph);
        assertEquals(graph_algo.getGraph(), graph);
    }


    @org.junit.Test
    public void isConnected() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
       // graph_algo.init(graph);
        //assertFalse(graph_algo.isConnected());
        GeoLocation g3=new GeoLocation(2,2,2);
        NodeData n3=new NodeData(1,g3,3.3,"d",0);
        graph.addNode(n3);
        graph.connect(3,2,4);
       graph.connect(2,1,2);
        graph.connect(1,3,2);
        graph_algo.init(graph);
        assertTrue(graph_algo.isConnected());
    }

    @org.junit.Test
    public void shortestPathDist() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(1,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        GeoLocation g3=new GeoLocation(3,3,3);
        NodeData n3=new NodeData(3,g3,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.connect(1,2,3);
        graph.connect(2,3,3);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(graph);
       double f= graph_algo.shortestPathDist(1,3);
        assertEquals(graph_algo.shortestPathDist(1,3),6.0,0.0001);
        graph.connect(1,3,2);
        graph_algo.init(graph);
        assertEquals(graph_algo.shortestPathDist(1,3),2,0.0001);
    }

    @org.junit.Test
    public void shortestPath() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(1,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        GeoLocation g3=new GeoLocation(3,3,3);
        NodeData n3=new NodeData(3,g3,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.connect(1,2,3);
        graph.connect(2,3,3);
        graph.connect(1,3,2);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(graph);
        List<node_data> l=graph_algo.shortestPath(1,3);
        ArrayList<node_data>l2=new ArrayList<>();
        l2.add(n);
        l2.add(n2);
        l2.add(n3);
        for(int i=0;i<l.size();i++)
        {
            assertEquals(l.remove(0),l2.remove(0));
        }
    }

    @org.junit.Test
    public void save() {
        DWGraph_DS g=new DWGraph_DS();
        g.addNode(new NodeData(3));
        DW_Graph_Algo graph=new DW_Graph_Algo();
        graph.init(g);
        graph.save("./src/save.json");
    }

    @org.junit.Test
    public void load() {
        DW_Graph_Algo graph=new DW_Graph_Algo();
        graph.load("./data/A0");
        System.out.println();
    }
}