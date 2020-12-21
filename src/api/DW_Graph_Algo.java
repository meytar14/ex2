package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class DW_Graph_Algo implements dw_graph_algorithms {
    private DWGraph_DS g;

    public DW_Graph_Algo()
    {
        this.g=null;
    }

    public DW_Graph_Algo(directed_weighted_graph g)
    {
        if(g instanceof DWGraph_DS)
        {
            this.g=(DWGraph_DS)g;
        }
        this.g=null;
    }
    @Override
    public void init(directed_weighted_graph g) {
        if(g instanceof DWGraph_DS)
        {
            this.g=(DWGraph_DS)g;
        }
        else {
            this.g = null;
        }
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS graph=new DWGraph_DS(this.g);
        return  graph;
    }

    @Override
    public boolean isConnected() {
        if(g.nodeSize()==0)
            return true;
        if(this.g.nodeSize()-1>this.g.edgeSize())
            return false;
        Iterator<node_data> it=g.getV().iterator();
        node_data n= it.next();
        LinkedList<node_data> nextToVisit =new LinkedList<node_data>();
        HashSet<Integer> visited =new HashSet<Integer>();//the nodes we already visited
        nextToVisit.add(n);
        visited.add(n.getKey());
        NodeData temp;
        while (!nextToVisit.isEmpty())
        {
            temp=(NodeData)nextToVisit.poll();
            for(int ni: temp.getNeighborsEdgeskeys())
            {
                if(!visited.contains(ni))
                {
                    nextToVisit.add(g.getNode(ni));
                    visited.add(ni);
                }
            }
        }
        if(visited.size()!=g.nodeSize())//check if node n connected to all the nodes in the graph
            return false;
        else//if n is connected to all the nodes in the graph, then we check if all the nodes in the graph connected to n
            {
            nextToVisit.clear();
            visited.clear();
            reverse_DWGraph reverseG=new reverse_DWGraph(g);
            node_data node=reverseG.getNode(n.getKey());
            nextToVisit.add(node);
            visited.add(node.getKey());
                while (!nextToVisit.isEmpty())
                {
                    temp=(NodeData)nextToVisit.poll();
                    for(int ni: temp.getNeighborsEdgeskeys())
                    {
                        if(!visited.contains(ni))
                        {
                            nextToVisit.add(g.getNode(ni));
                            visited.add(ni);
                        }
                    }
                }
                if(visited.size()==g.nodeSize())//check if node n connected to all the nodes in the graph
                    return true;
            return false;
            }

        /*
        if(this.g.nodeSize()-1>this.g.edgeSize())
            return false;
        for(node_data n:this.g.getV())
        {
            LinkedList<node_data> nextToVisit =new LinkedList<node_data>();
            HashSet<Integer> visited =new HashSet<Integer>();//the nodes we already visited
            nextToVisit.add(n);
            visited.add(n.getKey());
            NodeData temp;
            while (!nextToVisit.isEmpty())
            {
                temp=(NodeData)nextToVisit.poll();
                for(int ni: temp.getNeighborsEdgeskeys())
                {
                    if(!visited.contains(ni))
                    {
                        nextToVisit.add(g.getNode(ni));
                        visited.add(ni);
                    }
                }
            }
            if(visited.size()!=g.nodeSize())
                return false;
        }
        return true;*/
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        shortestPath(src,dest);
        return g.getNode(dest).getWeight();
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        HashMap<Integer,node_data> parent=new HashMap<Integer,node_data>();//<key of the node,his parent>
       NodeData c = (NodeData) g.getNode(src);
        PriorityQueue<NodeData> pq = new PriorityQueue<NodeData>(g.getV().size(),c);//storage in a min-qeue the next nodes we need to visit
        HashSet<node_data> visited=new HashSet<node_data>();
        for (node_data n:this.g.getV()) // set all the tags of the nodes in the graph to 0
        {
            n.setWeight(-1);
        }
        c.setTag(0);
        pq.add(c);
        while(!pq.isEmpty())
        {
            NodeData n= pq.poll();
            if(!visited.contains(n)) {
                visited.add(n);
                if(n.getKey()==dest)
                    break;
                for (int ni:n.getNeighborsEdgeskeys())
                {
                    NodeData neighbor=(NodeData)g.getNode(ni);
                    double dist=n.getWeight()+g.getEdge(n.getKey(), ni).getWeight();
                    if(neighbor.getWeight()==-1||neighbor.getWeight()>dist)
                    {
                        neighbor.setWeight(dist);
                        parent.put(ni, n);
                        pq.add((NodeData)g.getNode(ni));
                    }
                }
            }
        }
        if(parent.containsKey(dest))
        {
            ArrayList<node_data> RePath=new ArrayList<node_data>();//path from dest to src
            RePath.add(g.getNode(dest));
            node_data next=parent.get(dest);
            /*while(next!=null)
            {
                RePath.add(next);
                next=parent.get(next.getKey());
            }*/
            for(int i=0;i<parent.size();i++)
            {
                RePath.add(next);
                if(next.getKey()==src) {
                    break;
                }
                else {
                    next = parent.get(next.getKey());
                }
            }
            ArrayList<node_data> path=new ArrayList<node_data>();//path from src to dest
            int count=0;
            for(int i=RePath.size()-1;i>=0;i--)//insert the nodes of the shortest path from RePath to path
            {
                path.add(count,RePath.get(i));
                count++;
            }
            return path;
        }
        else if(visited.contains(g.getNode(dest)))
        {
            ArrayList<node_data> Path=new ArrayList<node_data>();//path from dest to src
            Path.add(g.getNode(dest));
            return Path;
        }
        return null;
    }

    @Override
    public boolean save(String file) {
       try
       {
           Gson gson =new GsonBuilder().setPrettyPrinting().create();
           DWGraph_DS.helpDWGraph_DS graph=g.new helpDWGraph_DS();
           String toWrite= gson.toJson(graph);
           FileWriter fw=new FileWriter(file);
           File f=new File(file);
           if(f.exists() && f.canWrite())
           {
               fw.write(toWrite);
               fw.flush();
               fw.close();
               return true;
           }
       }
       catch (IOException e)
       {
           e.printStackTrace();
           return false;
       }
       return false;
    }

    @Override
    public boolean load(String file) {
        try {
            Gson gson = new Gson();
            BufferedReader br=new BufferedReader(new FileReader(file));
            DWGraph_DS.helpDWGraph_DS graph = gson.fromJson(br, DWGraph_DS.helpDWGraph_DS.class);
            this.g = new DWGraph_DS(graph);
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
