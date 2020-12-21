package tests;

import api.DWGraph_DS;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeDataTest {

    @Test
    public void copy() {
    }

    @Test
    public void getKey() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3,"w",0);
        assertEquals(3,n.getKey());
    }

    @Test
    public void getLocation() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3,"w",0);
        assertEquals(g,n.getLocation());
    }

    @Test
    public void setLocation() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3,"w",0);
        GeoLocation g2=new GeoLocation(1,2,2);
        assertEquals(g2,n.getLocation());
    }

    @Test
    public void getWeight() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        assertEquals(3.3,n.getWeight());
    }

    @Test
    public void setWeight() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        n.setWeight(3.8);
        assertEquals(3.8,n.getWeight());
    }

    @Test
    public void getInfo() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        assertEquals("w",n.getInfo());
    }

    @Test
    public void setInfo() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        n.setInfo("tt");
        assertEquals("tt",n.getInfo());
    }

    @Test
    public void getTag() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        assertEquals(0,n.getTag());
    }

    @Test
    public void setTag() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        n.setTag(6);
        assertEquals(6,n.getTag());
    }

    @Test
    public void getEdge() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        EdgeData e=new EdgeData(3,2,4);
        n.addNi(n2.getKey(),e);
        assertEquals(n.getEdge(2),e);
    }

    @Test
    public void addNi() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        EdgeData e=new EdgeData(3,2,4);
        n.addNi(n2.getKey(),e);
        assertTrue(n.getNeighborsEdgeskeys().contains(n2.getKey()));

    }

    @Test
    public void addConnectedTo() {
        DWGraph_DS graph=new DWGraph_DS();
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(n.getKey(),n2.getKey(),4);
        assertTrue(n2.getConnectedTo().contains(n.getKey()));
    }

    @Test
    public void removeNi() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        GeoLocation g2=new GeoLocation(2,2,2);
        NodeData n2=new NodeData(2,g2,3.3,"d",0);
        EdgeData e=new EdgeData(3,2,4);
        n.addNi(n2.getKey(),e);
        n.removeNi(n2.getKey());
        assertFalse(n.getNeighborsEdgeskeys().contains(n2.getKey()));
    }

    @Test
    public void getDist() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        n.setDist(7);
        assertEquals(n.getDist(),7);
    }

    @Test
    public void setDist() {
        GeoLocation g=new GeoLocation(1,1,1);
        NodeData n=new NodeData(3,g,3.3,"w",0);
        n.setDist(7);
        assertEquals(n.getDist(),7);
    }
}