package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.Gson;
import gameClient.util.Range2D;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ex2 implements Runnable{
    private  myFrame2 _win;
    private  Arena _ar;
    private String[] args;
    public static void main(String[] args) {
        Ex2 o=new Ex2();
        o.args=args;
        Thread client = new Thread(o);
        client.start();
    }

    @Override
    public void run() {
        int level_number=5;
        game_service game=Game_Server_Ex2.getServer(level_number);
        init(game);
        _win.setVisible(true);
        _win.update(_ar);
        _win.repaint();
        /*game.startGame();
        while (game.isRunning())
        {






        }*/
    }
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        Gson gson=new Gson();
        DWGraph_DS.helpDWGraph_DS graph = gson.fromJson(g, DWGraph_DS.helpDWGraph_DS.class);
        directed_weighted_graph gg= new DWGraph_DS(graph);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new myFrame2();
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        _win.setSize(d.width/2, d.height/2);
        _win.update(_ar);


        //_win.setVisible(true);
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            Collections.sort(cl_fs, new Comparator<CL_Pokemon>() {
                @Override
                public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                    return Double.compare(o1.getValue(), o2.getValue());
                }
            });

            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            ArrayList<CL_Agent> agentsList=new ArrayList<>();
            for(int a = 0;a<rs;a++) {
                //int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(a);
                int maxNode=Math.max(c.get_edge().getDest(),c.get_edge().getSrc());
                int minNode=Math.min(c.get_edge().getDest(),c.get_edge().getSrc());
                int nn;
                if(c.getType()<0 ) {nn = maxNode;}
                else {
                    nn=minNode;
                }
                CL_Agent temp=new CL_Agent(_ar.getGraph(),nn);
                agentsList.add(temp);
                game.addAgent(nn);
            }
            _win.update(_ar);
            _ar.setAgents(agentsList);
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
