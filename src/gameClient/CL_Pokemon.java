package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.Comparator;

public class CL_Pokemon implements Comparator<CL_Pokemon> {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;

	/**
	 * the constructor
	 * @param p
	 * @param t
	 * @param v
	 * @param s
	 * @param e
	 */
	public CL_Pokemon (Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int edge = p.getInt("edge");
			double value=p.getDouble("value");


		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}

	/**
	 * return the edge that the pokemon is on
	 * @return
	 */
	public edge_data get_edge() {
		return _edge;
	}

	/**
	 * set the edge of the pokemon
	 * @param _edge
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	/**
	 * return the location of the pokemon
	 * @return
	 */
	public Point3D getLocation() {
		return _pos;
	}

	/**
	 * return the type of the pokemon
	 * @return
	 */

	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}

	/**
	 * return the value of the pokemon
	 * @return
	 */
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	/**
	 * comare between the values of the pokemons
	 * @param o1
	 * @param o2
	 * @return
	 */
	public int compare(CL_Pokemon o1, CL_Pokemon o2) {
		return Double.compare(o1.getValue(),o2.getValue());
	}
}
