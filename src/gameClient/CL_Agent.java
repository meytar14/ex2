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

	public void set_ar(Arena _ar) {
		this._ar = _ar;
	}

	public DW_Graph_Algo getGraph_algo() {
		return graph_algo;
	}

	public void setGraph_algo(DW_Graph_Algo graph_algo) {
		this.graph_algo = graph_algo;
	}

	public CL_Agent (directed_weighted_graph g, int start_node) {
			_gg = g;
			setMoney(0);
			this._curr_node = _gg.getNode(start_node);
			_pos = _curr_node.getLocation();
			_id = -1;
			setSpeed(0);
			path=new ArrayList<>();
		}

	public game_service getGame() {
		return game;
	}

	public void setGame(game_service game) {
		this.game = game;
	}

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


	public void set_id(int _id) {
		this._id = _id;
	}

	public ArrayList<node_data> getPath() {
		return path;
	}

	public void setPath(ArrayList<node_data> path) {
		this.path = path;
	}

	//@Override
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
		public void setCurrNode(int src) {
			this._curr_node = _gg.getNode(src);
		}
		public boolean isMoving() {
			return this._curr_edge!=null;
		}
		public String toString() {
			return toJSON();
		}
		public String toString1() {
			String ans=""+this.getID()+","+_pos+", "+isMoving()+","+this.getValue();	
			return ans;
		}
		public int getID() {
			// TODO Auto-generated method stub
			return this._id;
		}
	
		public geo_location getLocation() {
			// TODO Auto-generated method stub
			return _pos;
		}

		
		public double getValue() {
			// TODO Auto-generated method stub
			return this._value;
		}


	public void set_curr_edge(edge_data _curr_edge) {
		this._curr_edge = _curr_edge;
	}

	public int getNextNode() {
			int ans = -2;
			if(this._curr_edge==null) {
				ans = -1;}
			else {
				ans = this._curr_edge.getDest();
			}
			return ans;
		}

		public double getSpeed() {
			return this._speed;
		}

		public void setSpeed(double v) {
			this._speed = v;
		}
		public CL_Pokemon get_curr_fruit() {
			return _curr_fruit;
		}
		public void set_curr_fruit(CL_Pokemon curr_fruit) {
			this._curr_fruit = curr_fruit;
		}
	private ArrayList<node_data> choosePath(CL_Agent ag, CL_Pokemon closestPokemon,DW_Graph_Algo graph_algo) {
		ArrayList<node_data> path=(ArrayList)graph_algo.shortestPath(ag.getSrcNode(),closestPokemon.get_edge().getSrc());
		if(path.size()>0) {
			path.remove(0);
		}
		path.add(graph_algo.getGraph().getNode(closestPokemon.get_edge().getDest()));
		return path;
		/*LinkedList<Integer> finalPath=new LinkedList<>();
		for(int i=0;i<path.size();i++)
		{
			finalPath.add(path.get(i).getKey());
		}
		if(finalPath.size()>0)
		{
			finalPath.poll();
		}
		finalPath.add(closestPokemon.get_edge().getDest());
		return finalPath;*/
	}

	private CL_Pokemon findClosestPokemon(List<CL_Pokemon> cl_fs, CL_Agent ag,DW_Graph_Algo graph_algo) {
		CL_Pokemon clososetPokemon=cl_fs.get(0);
		double Mindistance=graph_algo.shortestPathDist(ag.getSrcNode(),clososetPokemon.get_edge().getSrc());
		int index=0;
		for(int i=1;i<cl_fs.size();i++)
		{
			double thisDistance=graph_algo.shortestPathDist(ag.getSrcNode(),cl_fs.get(i).get_edge().getSrc());
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
	private int nextNode() {

		if (this.path.size() == 0) {
			List<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());//Arena.json2Pokemons(game.getPokemons()); // get pokemon array
			for (int a = 0; a < cl_fs.size(); a++) { Arena.updateEdge(cl_fs.get(a), graph_algo.getGraph()); } // get each pokemon edge
			CL_Pokemon closestPokemon = findClosestPokemon(cl_fs, this, graph_algo);
			this.set_curr_fruit(closestPokemon);
			this.path=choosePath(this, closestPokemon, graph_algo);

			System.out.println(this.path);
			return this.path.remove(0).getKey();
		}
		else{
			int nextNode = this.path.remove(0).getKey();
			//.out.println(nextNode);
			return nextNode;
		}
	}
		public void set_SDT(long ddtt) {
			long ddt = ddtt;
			if(this._curr_edge!=null) {
				double w = get_curr_edge().getWeight();
				geo_location dest = _gg.getNode(get_curr_edge().getDest()).getLocation();
				geo_location src = _gg.getNode(get_curr_edge().getSrc()).getLocation();
				double de = src.distance(dest);
				double dist = _pos.distance(dest);
				if(this.get_curr_fruit().get_edge()==this.get_curr_edge()) {
					 dist = _curr_fruit.getLocation().distance(this._pos);
				}
				double norm = dist/de;
				double dt = w*norm / this.getSpeed(); 
				ddt = (long)(1000.0*dt);
			}
			this.set_sg_dt(ddt);
		}
		
		public edge_data get_curr_edge() {
			return this._curr_edge;
		}
		public long get_sg_dt() {
			return _sg_dt;
		}
		public void set_sg_dt(long _sg_dt) {
			this._sg_dt = _sg_dt;
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
