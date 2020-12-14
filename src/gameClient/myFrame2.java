package gameClient;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import com.google.gson.Gson;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.util.List;

public class myFrame2 extends JFrame {

    graphPanel panel;
    private DWGraph_DS graph;
    private List<CL_Agent> agents;
    private List<CL_Pokemon> pokemons;
    private Arena _ar;


    myFrame2(){

        panel = new graphPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void update(Arena ar) {
        this._ar = ar;
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(20,this.getHeight()-20);
        Range2D frame = new Range2D(rx,ry);
        panel.updateFrame(_ar,frame);
    }
    public static void main(String[] args) {
        new myFrame2();
    }
    public void update(String graph,String agents,String pokemons)
    {
        Gson gson=new Gson();
        DWGraph_DS.helpDWGraph_DS g = gson.fromJson(graph, DWGraph_DS.helpDWGraph_DS.class);
        this.graph = new DWGraph_DS(g);

    }
}
