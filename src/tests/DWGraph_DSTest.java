package tests;

import api.DWGraph_DS;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.junit.Test;

import static org.junit.Assert.*;

public class DWGraph_DSTest {

    @Test
    public void getNode() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        assertEquals(graph.getNode(3),n);
    }

    @Test
    public void getEdge() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(n.getKey(),n2.getKey(),4);
        EdgeData e=new EdgeData(3,2,4);
        assertEquals(graph.getEdge(3,2).getSrc(),e.getSrc());
        assertEquals(graph.getEdge(3,2).getDest(),e.getDest());
    }

    @Test
    public void addNode() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        graph.addNode(n);
        assertTrue(graph.getV().contains(n));
    }

    @Test
    public void connect() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(3,2,3);
        assertTrue(n.getNeighborsEdgeskeys().contains(2));
    }

    @Test
    public void getV() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        NodeData n3=new NodeData(2,g2,3.3,"d",0);

        graph.addNode(n);
        graph.addNode(n2);
        assertTrue(graph.getV().contains(n));
        assertTrue(graph.getV().contains(n2));
        assertFalse(graph.getV().contains(n3));
    }


    @Test
    public void removeNode() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.removeNode(3);
        assertFalse(graph.getV().contains(3));
    }

    @Test
    public void removeEdge() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(3,2,3);
        graph.removeEdge(3,2);
        assertFalse(n.getNeighborsEdgeskeys().contains(2));
    }

    @Test
    public void nodeSize() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        assertEquals(graph.nodeSize(),2);
    }

    @Test
    public void edgeSize() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(3,2,3);
        assertEquals(graph.edgeSize(),1);
    }

    @Test
    public void getMC() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(3,2,3);
        assertEquals(graph.getMC(),3);
    }
}