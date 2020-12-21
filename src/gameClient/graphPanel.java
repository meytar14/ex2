package gameClient;

import api.*;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class graphPanel extends JPanel {
    private DWGraph_DS graph;
    private List<CL_Agent> agents;
    private List<CL_Pokemon> pokemons;
    private Range2D dim;
    private long timeToEnd;
    private String grade;


    public graphPanel()
    {
        super();

    }

    @Override
     public void paint(Graphics g) {

        super.paint(g);
        if(graph==null||agents==null||pokemons==null)
            return;
        g.setFont(new Font("name",Font.PLAIN,18));
        g.setColor(Color.blue);
        NodeData temp;
        Range rx = new Range(10,dim.get_x_range().get_length()-10);
        Range ry = new Range(20,dim.get_y_range().get_length()-10);
        Range2D frame = new Range2D(rx,ry);
        Range2Range r2r=Arena.w2f(graph,frame);
        g.drawString("time:"+timeToEnd,15,30);
        for(node_data n:graph.getV())//draw the nodes of this graph
        {
            g.setColor(Color.blue);
           geo_location l1 =r2r.world2frame(n.getLocation());
            g.fillOval((int)l1.x()-5,(int)l1.y()-5,15,15);
            g.setFont(new Font("ariel",Font.BOLD,12));
            g.drawString(String.valueOf(n.getKey()),(int)l1.x()-5,(int)l1.y()-7);
            temp=(NodeData)n;
            g.setColor(Color.black);
            for(int dest:temp.getNeighborsEdgeskeys())//draw the edges
            {
                node_data ni=graph.getNode(dest);
                geo_location l2 =r2r.world2frame(ni.getLocation());
                g.drawLine((int)l1.x(),(int)l1.y(),(int)l2.x(),(int)l2.y());
            }
        }
        for(CL_Pokemon p:pokemons)//draw the pokemons
        {
            if(p.getType()==-1)
            {
                g.setColor(Color.green);
                geo_location l2 =r2r.world2frame(p.getLocation());
                g.fillOval((int) l2.x()-5, (int) l2.y()-5, 10, 10);
            }
            else
            {
                g.setColor(Color.yellow);
                geo_location l2 =r2r.world2frame(p.getLocation());
                g.fillOval((int) l2.x()-5, (int) l2.y()-5, 10, 10);
            }
        }
        double grade=0;
        for (CL_Agent a:agents)//draw the agents
        {
            grade=grade+a.getValue();
            g.setColor(Color.red);
            geo_location l3 =r2r.world2frame(a.getLocation());
            g.fillOval((int)l3.x()-5,(int)l3.y()-5,10,10);
        }
        g.setFont(new Font("name",Font.PLAIN,18));
        g.setColor(Color.blue);
        g.drawString("grade:"+grade,15,45);
    }

    /**
     * this function update thr graphPanel
     * @param _ar
     * @param dim
     * @param timeToEnd
     * @param agents
     */
    public void updateFrame(Arena _ar, Range2D dim, long timeToEnd,List<CL_Agent> agents)
    {
        this.graph=(DWGraph_DS) _ar.getGraph();
        this.pokemons=_ar.getPokemons();
        this.dim=dim;
        this.timeToEnd=timeToEnd;
        this.grade=grade;
        this.agents=agents;

    }
}
