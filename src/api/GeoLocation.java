package api;

public class GeoLocation implements geo_location {
    private double x;
    private double y;
    private double z;

    /**
     * constructor
     * @param x
     * @param y
     * @param z
     */
   public GeoLocation(double x, double y, double z)
   {
       this.x=x;
       this.z=z;
       this.y=y;
   }

    /**
     * return the x of this location.
     * @return
     */
    @Override
    public double x() {
        return this.x;
    }

    /**
     * return the y of this location.
     * @return
     */
    @Override
    public double y() {
        return this.y;
    }

    /**
     * return the z of this location.
     * @return
     */
    @Override
    public double z() {
        return this.z;
    }

    /**
     * return the distance from this location to the input location.
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow((this.x-g.x()),2)+Math.pow((this.y-g.y()),2)+Math.pow((this.z-g.z()),2));
    }
}
