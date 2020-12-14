package api;

import java.util.HashMap;
import java.util.Iterator;

public class reverse_DWGraph {
    private HashMap<Integer,node_data> nodes;

    public reverse_DWGraph(DWGraph_DS g)
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
                EdgeData e=new EdgeData(ni,nodeInThisGraph.getKey(),((EdgeData)g.getEdge(n.getKey(),ni)).getWeight());
                ((NodeData)nodes.get(ni)).addNi(nodeInThisGraph.getKey(),e);
            }
            Iterator<Integer> it=((NodeData)n).getConnectedTo().iterator();//iterator on the integers keys of the nodes that connected to this node
            int temp2;
            while(it.hasNext())
            {
                temp2=it.next();//the key of the node
                it.remove();
                NodeData node=(NodeData)nodes.get(temp2);
                EdgeData e=new EdgeData(nodeInThisGraph.getKey(),node.getKey(),((EdgeData)g.getEdge(node.getKey(),nodeInThisGraph.getKey())).getWeight());
                nodeInThisGraph.addNi(node.getKey(),e);
            }
        }
        this.nodes=nodes;
    }
    public node_data getNode(int key) {
        if(this.nodes.containsKey(key))
            return this.nodes.get(key);
        return null;
    }
}
