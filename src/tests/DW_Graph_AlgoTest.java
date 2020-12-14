package tests;

import api.DWGraph_DS;
import api.DW_Graph_Algo;
import api.NodeData;

import static org.junit.Assert.*;

public class DW_Graph_AlgoTest {

    @org.junit.Test
    public void init() {
    }

    @org.junit.Test
    public void getGraph() {
    }

    @org.junit.Test
    public void copy() {
    }

    @org.junit.Test
    public void isConnected() {
    }

    @org.junit.Test
    public void shortestPathDist() {
    }

    @org.junit.Test
    public void shortestPath() {
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