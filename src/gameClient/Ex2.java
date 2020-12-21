package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import gameClient.util.Range2D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Ex2 implements Runnable{
    private  myFrame2 _win;
    private login loginframe;
    private  Arena _ar;
    private ArrayList<CL_Agent> agents;
    private String[] args;
    private ArrayList<Thread>threads;
    private PriorityQueue<CL_Pokemon> pokemons;
    public static void main(String[] args) {
        Ex2 o=new Ex2();
        o.args=args;
        Thread client = new Thread(o);
        client.start();
    }

    public ArrayList<CL_Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<CL_Agent> agents) {
        this.agents = agents;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public void run() {
        int level_number;
        int id;
        if(args.length==2)
        {
            id=Integer.parseUnsignedInt(args[0]);
            level_number=Integer.parseUnsignedInt(args[1]);
        }
        else {
            loginframe = new login();
            Thread log=new Thread(loginframe);
            log.start();

            level_number=20;
        }
        game_service game=Game_Server_Ex2.getServer(level_number);
        init(game);
        _win.setVisible(true);
        game.startGame();
        directed_weighted_graph gg=_ar.getGraph();
        long dt=50;
        //
        for(Thread t:threads)
        {
            t.start();
        }
        //
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(game.isRunning())
                {
                    game.move();
                    try {
                        Thread.sleep(1000/10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        while (game.isRunning())//()
        {

            long timeToEnd=game.timeToEnd();
            updateAgents(game,gg);
            //moveAgants(game, gg);
            try {
                _win.update(_ar,timeToEnd,agents,gg);
                _win.repaint();
                Thread.sleep(dt);

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(Thread t:threads)
        {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAgents(game_service game, directed_weighted_graph gg) {
        String lg = game.getAgents();
        //java.util.List<CL_Agent> log = Arena.getAgents(lg, gg);
        try {
            JSONObject o=new JSONObject(lg);
            JSONArray arr=o.getJSONArray("Agents");
            for(int i=0;i<arr.length();i++)
            {
                JSONObject ttt = arr.getJSONObject(i).getJSONObject("Agent");
                agents.get(i).update(arr.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
        for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg); }
        _ar.setPokemons(cl_fs);
        //DW_Graph_Algo graph_algo=new DW_Graph_Algo();
       // graph_algo.init(gg);
        /*for(CL_Agent agent:agents.values())
        {
            if(agent.get_curr_fruit()==null&&cl_fs.size()>0)
            {
                CL_Pokemon closestPokemon = findClosestPokemon(cl_fs, agent, graph_algo);
                agent.set_curr_fruit(closestPokemon);
            }
        }*/
    }

   /* private  void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        java.util.List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        java.util.List<CL_Agent> log2=new ArrayList<>();
        for(int i=0;i<log.size();i++)
        {
            if(paths.get(log.get(i).getID()).size()==0&& log.get(i).getNextNode()==-1)
            {
                log2.add(log.get(i));
            }
        }
        ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
        for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
        for(int a = 0;a<cl_fs.size();a++) {
            System.out.println( a+")"+cl_fs.get(a).get_edge().getSrc());}
        Collections.sort(cl_fs, new Comparator<CL_Pokemon>() {//sort the pokemons array by value
            @Override
            public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        _ar.setPokemons(cl_fs);
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(gg);
        int min=Math.min(log.size(), cl_fs.size());
        for(int i=0;i<min;i++)
        {
            if(paths.get(log.get(i).getID()).size()==0&& log.get(i).getNextNode()==-1) {
                CL_Agent agent = log.get(i);
                CL_Pokemon closestPokemon = findClosestPokemon(cl_fs, agent, graph_algo);

                //CL_Pokemon pokemon=cl_fs.get(i);
                // CL_Agent closestAgent=findClosestAgent(pokemon,log2,graph_algo);
                paths.put(agent.getID(), choosePath(agent, closestPokemon, graph_algo));
            }
        }
        for(int i=0;i<log.size();i++) {
            CL_Agent ag = log.get(i);
            int dest = ag.getNextNode();
            if(dest==-1) {
                int id = ag.getID();
                dest = nextNode(paths, id);
                game.chooseNextEdge(id, dest);
            }
        }
    }*/

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
        DW_Graph_Algo graph_algo=new DW_Graph_Algo();
        graph_algo.init(gg);
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            List<CL_Pokemon> cl_fs =Arena.json2Pokemons(game.getPokemons());
           /* Collections.sort(cl_fs, new Comparator<CL_Pokemon>() {
                @Override
                public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                    return -Double.compare(o1.getValue(), o2.getValue());
                }
            });*/
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            ArrayList<Thread> threads=new ArrayList<>();
            ArrayList<CL_Agent> agents=new ArrayList<>();
            for(int a = 0;a<rs;a++) {
                //int ind = a%cl_fs.size();
                if(cl_fs.size()>0) {
                    CL_Pokemon c = cl_fs.remove(0);

                    CL_Agent temp = new CL_Agent(_ar.getGraph(),c.get_edge().getSrc());
                    temp.set_id(a);
                    temp.setGraph_algo(graph_algo);
                    temp.setGame(game);
                    temp.set_ar(_ar);
                    temp.set_curr_fruit(null);
                    agents.add(temp);
                    threads.add(new Thread(temp));
                    game.addAgent(c.get_edge().getSrc());
                }
                else
                {
                    CL_Agent temp = new CL_Agent(_ar.getGraph(), a);
                    temp.set_id(a);
                    temp.setGraph_algo(graph_algo);
                    temp.setGame(game);
                    temp.set_ar(_ar);
                    temp.set_curr_fruit(null);
                    agents.add(temp);
                    threads.add(new Thread(temp));
                    game.addAgent(a);
                }
            }
            setAgents(agents);
            setThreads(threads);
            _win.update(_ar,game.timeToEnd(),agents,gg);
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
