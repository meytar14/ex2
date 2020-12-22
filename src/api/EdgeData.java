package api;

public class EdgeData implements edge_data{
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    /**
     * constructor
     * @param src
     * @param dest
     * @param weight
     */
    public EdgeData(int src, int dest, double weight)
    {
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }

    /**
     * return a deep copy of this edge.
     * @return
     */
    public EdgeData copy()
    {
        EdgeData e=new EdgeData(this.src,this.dest,this.weight);
        return e;
    }

    /**
     * return the src of this edge.
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * return the dest of this edge.
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * return the weight of this edge.
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * return the info of this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * set the info of this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    /**
     * return the tag of this edge.
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * set the tag of this edge.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;
    }
    public EdgeData(EdgeData.helpEdgeData e)
    {
        this.src=e.src;
        this.dest=e.dest;
        this.weight=e.w;
    }

    public class helpEdgeData
    {
        int src;
        double w;
        int dest;

        public int getDest() {
            return dest;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public double getW() {
            return w;
        }

        public void setW(double w) {
            this.w = w;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public helpEdgeData()
        {
            this.src=src;
            this.w=weight;
            this.dest=dest;
        }
    }
}
