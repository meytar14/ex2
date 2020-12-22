package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CL_Agent implements Runnable {
		public static final double EPS = 0.0001;
		private static int _count = 0;
		private static int _seed = 3331;
		private int _id;
	//	private long _key;
		private geo_location _pos;
		private double _speed;
		private edge_data _curr_edge;
		private node_data _curr_node;
		private directed_weighted_graph _gg;
		private CL_Pokemon _curr_fruit;
		private long _sg_dt;
		private game_service game;
		private double _value;
		private ArrayList<node_data> path;
		private DW_Graph_Algo graph_algo;
		private  int dest;
		private Arena _ar;

	public Arena get_ar() {
		return _ar;
	}

	/**
	 * set the Arena of the agent
	 * @param _ar
	 */
	public void set_ar(Arena _ar) {
		this._ar = _ar;
	}


	/**
	 * set the grap_Algo of the agent
	 * @param graph_algo
	 */
	public void setGraph_algo(DW_Graph_Algo graph_algo) {
		this.graph_algo = graph_algo;
	}

	/**
	 * constructor
	 * @param g
	 * @param start_node
	 */
	public CL_Agent (directed_weighted_graph g, int start_node) {
			_gg = g;
			setMoney(0);
			this._curr_node = _gg.getNode(start_node);
			_pos = _curr_node.getLocation();
			_id = -1;
			setSpeed(0);
			path=new ArrayList<>();
		}

	/**
	 * return the game_Service of the agent
	 * @return
	 */
	public game_service getGame() {
		return game;
	}

	/**
	 * set the game_Service of the agent
	 * @param game
	 */
	public void setGame(game_service game) {
		this.game = game;
	}

	/**
	 * update all the data of the agent
	 * @param json
	 */
	public void update(String json) {
			JSONObject line;
			try {
				// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
				line = new JSONObject(json);
				JSONObject ttt = line.getJSONObject("Agent");
				int id = ttt.getInt("id");
				if(id==this.getID() || this.getID() == -1) {
					if(this.getID() == -1) {_id = id;}
					double speed = ttt.getDouble("speed");
					String p = ttt.getString("pos");
					Point3D pp = new Point3D(p);
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					double value = ttt.getDouble("value");
					this._pos = pp;
					this.setCurrNode(src);
					this.setSpeed(speed);
					this.setNextNode(dest);
					this.setMoney(value);
					this.dest=dest;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

	/**
	 * set the id of the agent
	 * @param _id
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	//@Override

	/**
	 * return the curr node of the agent
	 * @return
	 */
		public int getSrcNode() {return this._curr_node.getKey();}
		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this._id+","
					+ "\"value\":"+this._value+","
					+ "\"src\":"+this._curr_node.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+_pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;	
		}
		private void setMoney(double v) {_value = v;}

	/**
	 * set the next node of the agent
	 * @param dest
	 * @return
	 */
		public boolean setNextNode(int dest) {
			boolean ans = false;
			int src = this._curr_node.getKey();
			this._curr_edge = _gg.getEdge(src, dest);
			if(_curr_edge!=null) {
				ans=true;
			}
			else {_curr_edge = null;}
			return ans;
		}

	/**
	 * set the current node of the agent
	 * @param src
	 */
		public void setCurrNode(int src) {
			this._curr_node = _gg.getNode(src);
		}

	/**
	 * return true if the agent is moving, else return false
	 * @return
	 */
		public boolean isMoving() {
			return this._curr_edge!=null;
		}
		public String toString() {
			return toJSON();
		}

	/**
	 * return the ID of the agent
	 * @return
	 */
		public int getID() {
			// TODO Auto-generated method stub
			return this._id;
		}

	/**
	 * return the location of the agent
	 * @return
	 */
		public geo_location getLocation() {
			// TODO Auto-generated method stub
			return _pos;
		}

	/**
	 * return the value of the agent
	 * @return
	 */
	public double getValue() {
			// TODO Auto-generated method stub
			return this._value;
		}

	/**
	 * return the next node of the agent, return -1 if the agent is already on a node.
	 * @return
	 */
	public int getNextNode() {
			int ans = -2;
			if(this._curr_edge==null) {
				ans = -1;}
			else {
				ans = this._curr_edge.getDest();
			}
			return ans;
		}

	/**
	 * return the speed of the agent
	 * @return
	 */
		public double getSpeed() {
			return this._speed;
		}

	/**
	 * set  the speed of the agent
	 * @param v
	 */
		public void setSpeed(double v) {
			this._speed = v;
		}

	/**
	 * return the pokemon that the agent is chasing after
	 * @return
	 */
		public CL_Pokemon get_curr_fruit() {
			return _curr_fruit;
		}

	/**
	 * set the pokemon that the agent is chasing after
	 * @param curr_fruit
	 */
		public void set_curr_fruit(CL_Pokemon curr_fruit) {
			this._curr_fruit = curr_fruit;
		}

	/**
	 * find and return the shortest path from the agent to this pokemon.
	 * @param ag
	 * @param closestPokemon
	 * @param graph_algo
	 * @return
	 */
	private ArrayList<node_data> choosePath(CL_Agent ag, CL_Pokemon closestPokemon,DW_Graph_Algo graph_algo) {
		ArrayList<node_data> path=(ArrayList)graph_algo.shortestPath(ag.getSrcNode(),closestPokemon.get_edge().getSrc());
		if(path==null)
		{
			path=new ArrayList<>();
		}
		if(path.size()>0) {
			path.remove(0);
		}
		path.add(graph_algo.getGraph().getNode(closestPokemon.get_edge().getDest()));
		return path;
	}

	/**
	 * find and return the closest pokemon to this agent.
	 * @param cl_fs
	 * @param ag
	 * @param graph_algo
	 * @return
	 */
	private CL_Pokemon findClosestPokemon(List<CL_Pokemon> cl_fs, CL_Agent ag,DW_Graph_Algo graph_algo) {
		CL_Pokemon clososetPokemon=cl_fs.get(0);
		double Mindistance=graph_algo.shortestPathDist(ag.getSrcNode(),clososetPokemon.get_edge().getDest());
		int index=0;
		for(int i=1;i<cl_fs.size();i++)
		{
			double thisDistance=graph_algo.shortestPathDist(ag.getSrcNode(),cl_fs.get(i).get_edge().getDest());
			if(Mindistance>thisDistance)
			{
				Mindistance=thisDistance;
				clososetPokemon=cl_fs.get(i);
				index=i;
			}
		}
		cl_fs.remove(index);
		return clososetPokemon;
	}

	/**
	 * return the next node that in the path to the pokemon
	 * @return
	 */
	private int nextNode() {

		if (this.path.size() == 0) {
			List<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());//Arena.json2Pokemons(game.getPokemons()); // get pokemon array
			for (int a = 0; a < cl_fs.size(); a++) { Arena.updateEdge(cl_fs.get(a), graph_algo.getGraph()); } // get each pokemon edge
			CL_Pokemon closestPokemon = findClosestPokemon(cl_fs, this, graph_algo);
			this.set_curr_fruit(closestPokemon);
			this.path=choosePath(this, closestPokemon, graph_algo);
			return this.path.remove(0).getKey();
		}
		else{
			int nextNode = this.path.remove(0).getKey();
			if(path.size()>=4) {
				if (nextNode == this.path.get(3).getKey()) {
					this.path = new ArrayList<>();
					return -1;
				}

			}
			return nextNode;
		}
	}

	@Override
	public void run()
	{
		while (!game.isRunning())
		{

		}
		while (game.isRunning()) {
			if(dest==-1)
			{
				int destenation = nextNode();
				if(destenation!=-1) {
					game.chooseNextEdge(getID(), destenation);
				}

			}
			try
			{
				Thread.sleep(2); // sleep for 1 millisecond
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}



		}



	}
}
