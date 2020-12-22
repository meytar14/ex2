package gameClient;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import com.google.gson.Gson;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myFrame2 extends JFrame {

    graphPanel panel;
    private DWGraph_DS graph;
    private List<CL_Agent> agents;
    private List<CL_Pokemon> pokemons;
    private Arena _ar;

    /**
     * constructor of the game frame (myFrame2)
     */
    myFrame2(){

        panel = new graphPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        agents=new ArrayList<>();
    }

    /**
     * this function linking between the update function in the graphPanel and the main program Ex2 and passes to "update" function all the
     * variabels she needs.
     * @param ar
     * @param timeToEnd
     * @param agents
     */
    public void update(Arena ar,long timeToEnd, ArrayList<CL_Agent> agents) {

        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(20,this.getHeight()-20);
        Range2D frame = new Range2D(rx,ry);
        panel.updateFrame(ar,frame,timeToEnd,agents);
    }
    public static void main(String[] args) {
        new myFrame2();
    }

}
