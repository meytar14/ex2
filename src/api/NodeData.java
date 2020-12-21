package api;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class NodeData implements node_data, Comparator<NodeData> {
    private int key;
    private GeoLocation GL;
    private double weight;
    private String info;
    private int tag;
    private double dist;
    private HashMap<Integer,edge_data> neighborsEdges;//the edges that this node is the src
    private HashSet<Integer>connectedTo;//the nodes keys that this node is the dest in their edges

    public NodeData(int key)
    {
        this.key=key;
        this.weight=0;
        this.GL=new GeoLocation(0,0,0);
        this.tag=0;
        this.info="";
        this.neighborsEdges=new HashMap<Integer, edge_data>();
        this.connectedTo=new HashSet<Integer>();
    }



    public NodeData(int key,GeoLocation GL,double weight,String info, int tag)
    {
        this.key=key;
        this.GL=GL;
        this.weight=weight;
        this.info=info;
        this.tag=tag;
        this.neighborsEdges=new HashMap<Integer, edge_data>();
    }
    public NodeData copy()
    {
        NodeData node=new NodeData(this.key,this.GL,this.weight,this.info,this.tag);
        return node;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        if(GL==null)
            return null;
        return this.GL;
    }

    @Override
    public void setLocation(geo_location p) {
        this.GL=(GeoLocation)p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
       this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    public edge_data getEdge(int dest)
    {
        return this.neighborsEdges.get(dest);
    }
    public void addNi(int dest, EdgeData e)
    {
        neighborsEdges.put(dest,e);
    }
    public void addConnectedTo(int src)
    {
        connectedTo.add(src);
    }
    public Collection<Integer> getNeighborsEdgeskeys()
    {
        return this.neighborsEdges.keySet();
    }
    public Collection<edge_data> getNeighborsEdgesValues()
    {
        return this.neighborsEdges.values();
    }
    public HashSet<Integer> getConnectedTo()
    {
        return this.connectedTo;
    }

    public edge_data removeNi(int dest)
    {
        if(this.neighborsEdges.containsKey(dest))
        {
            edge_data e=neighborsEdges.get(dest);
            neighborsEdges.remove(dest);
            return e;
        }
        return null;
    }

    @Override
    public int compare(NodeData o1, NodeData o2) {
        return Double.compare(o1.getTag(),o2.getTag());
    }
    public double getDist()
    {
        return this.dist;
    }
    public void setDist(double dist)
    {
        this.dist=dist;
    }
    public NodeData(NodeData.helpNodeData n)
    {
        this.key=n.id;
        String[] pos=n.pos.split(",");
        GeoLocation gl=new GeoLocation(Double.parseDouble(pos[0]),Double.parseDouble(pos[1]),Double.parseDouble(pos[2]));
        this.GL=gl;
        this.neighborsEdges=new HashMap<Integer, edge_data>();
        this.connectedTo=new HashSet<Integer>();
    }


    public class helpNodeData
    {
        String pos;
        int id;
        public helpNodeData()
        {
            pos=GL.x()+","+GL.y()+","+GL.z();
            id=key;
        }
    }
}
