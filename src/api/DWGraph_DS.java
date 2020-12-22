package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> nodes;
    private int EdgeSize;
    private int MC;

    /**
     * constructor
     */
    public DWGraph_DS()
    {
        this.nodes=new HashMap<Integer, node_data>();
    }

    /**
     * a help constructor.
     * @param g
     */
    public DWGraph_DS(DWGraph_DS.helpDWGraph_DS g)
    {
         nodes=new HashMap<Integer, node_data>();

        for(NodeData.helpNodeData n:g.Nodes)
        {
            NodeData temp= new NodeData(n);
            nodes.put(temp.getKey(),temp);
        }
        int src;
        int dest;
        double w;
        for(EdgeData.helpEdgeData e:g.Edges)
        {
            src=e.src;
            dest=e.dest;
            w=e.w;
            connect(src,dest,w);
        }
        this.nodes=nodes;
    }
    /**
     * constructor, make a deep copy from the input graph.
     * @param g
     */
    public DWGraph_DS(DWGraph_DS g)
    {
        HashMap<Integer,node_data> nodes=new HashMap<Integer, node_data>();
        NodeData temp;
        for(node_data n:g.getV())
        {
            temp=((NodeData)n).copy();
            nodes.put(temp.getKey(),temp);
        }
        for(node_data n:g.getV())//connect between the nodes in this graph
        {
            NodeData nodeInThisGraph =(NodeData)nodes.get(n.getKey());
            for(int ni:((NodeData)n).getNeighborsEdgeskeys())
            {
                EdgeData e=((EdgeData)g.getEdge(n.getKey(),ni)).copy();
                nodeInThisGraph.addNi(ni,e);
            }
            Iterator<Integer> it=((NodeData)n).getConnectedTo().iterator();//iterator on the integers keys of the nodes that connected to this node
            int temp2;
            while(it.hasNext())
            {
                temp2=it.next();//the key of the node
                it.remove();
                NodeData node=(NodeData)nodes.get(temp2);
                EdgeData e=((EdgeData)g.getEdge(node.getKey(),nodeInThisGraph.getKey())).copy();
                node.addNi(nodeInThisGraph.getKey(),e);
            }
        }
        this.nodes=nodes;
        this.MC=g.getMC();
        this.EdgeSize=g.edgeSize();
    }

    /**
     * return the node with this key, if this graph doesnt contain this node than return null.
     * @param key - the node_id
     * @return
     */
    @Override
    public node_data getNode(int key) {
        if(this.nodes.containsKey(key))
            return this.nodes.get(key);
        return null;
    }

    /**
     * return the edge between src and dest.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        return ((NodeData)this.nodes.get(src)).getEdge(dest);
    }

    /**
     * add this node to the graph.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(),n);
        MC++;
    }

    /**
     * connect between src to dest.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(nodes.get(src)!=null &&nodes.get(dest)!=null&& src!=dest && w>=0)
        {
            EdgeData e=new EdgeData(src,dest,w);
            ((NodeData)nodes.get(src)).addNi(dest,e);
            ((NodeData)nodes.get(dest)).addConnectedTo(src);
            MC++;
            EdgeSize++;
        }
    }

    /**
     * return a collection of all the nodes in the graph
     * @return
     */
    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    /**
     * return a collection of all the edges that this node is the src of.
     * @param node_id
     * @return
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return ((NodeData)nodes.get(node_id)).getNeighborsEdgesValues();
    }

    /**
     * remove this node from the graph.
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        if(nodes.containsKey(key))
        {
            node_data node=nodes.get(key);
            for(int ni:((NodeData)node).getNeighborsEdgeskeys())//delete all the edges that starts in this key
            {
                ((NodeData)node).removeNi(ni);
            }
            Iterator<Integer> it=((NodeData)node).getConnectedTo().iterator();//iterator on the integers keys of the nodes that connected to this node
            int temp;
            while(it.hasNext())//delete all the edges that ends in this key
            {
                temp=it.next();
                it.remove();
                ((NodeData)nodes.get(temp)).removeNi(key);
            }
            nodes.remove(key);
            return node;
        }
        return null;
    }

    /**
     * remove this edge from the graph.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data e=((NodeData)nodes.get(src)).removeNi(dest);
        if(e!=null)
        {
            EdgeSize--;
            MC++;
            return e;
        }
        return null;
    }

    /**
     * return the number of nodes in the graph.
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * return the number of edges in the graph.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.EdgeSize;
    }

    /**
     * return the number of actions we did on the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }


    public class helpDWGraph_DS
    {
        ArrayList<EdgeData.helpEdgeData> Edges=new ArrayList<EdgeData.helpEdgeData>();
        ArrayList<NodeData.helpNodeData> Nodes=new ArrayList<NodeData.helpNodeData>();
        public helpDWGraph_DS()
        {
            for(node_data n:getV())//adding all the nodes in the graph to "nodes"
            {
                NodeData temp=(NodeData)n;
                NodeData.helpNodeData t=temp.new helpNodeData();
                Nodes.add(t);
                for(edge_data e:getE(n.getKey()))//adding all the edges that start at n to "edges"
                {
                    EdgeData edge=(EdgeData)e;
                    EdgeData.helpEdgeData e2=edge.new helpEdgeData();
                    Edges.add(e2);
                }
            }
        }
    }
}
