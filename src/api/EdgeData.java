package api;

public class EdgeData implements edge_data{
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;
    public EdgeData(int src, int dest, double weight)
    {
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }
    public EdgeData copy()
    {
        EdgeData e=new EdgeData(this.src,this.dest,this.weight);
        return e;
    }
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
