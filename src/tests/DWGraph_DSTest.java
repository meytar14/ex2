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
    }

    @Test
    public void connect() {
    }

    @Test
    public void getV() {
    }

    @Test
    public void getE() {
    }

    @Test
    public void removeNode() {
    }

    @Test
    public void removeEdge() {
    }

    @Test
    public void nodeSize() {
    }

    @Test
    public void edgeSize() {
    }

    @Test
    public void getMC() {
    }
}