package model;

import java.io.Serializable;

public class BrukerMedCount implements Comparable<BrukerMedCount>, Serializable {
    private static final long serialVersionUID = 3;
    private Bruker bruker;
    private int count;
    public BrukerMedCount(Bruker b, int count) {
        this.bruker = b;
        this.count = count;
    }

    public Bruker getBruker() {
        return bruker;
    }

    public void setBruker(Bruker bruker) {
        this.bruker = bruker;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(BrukerMedCount o) {
        return o.getCount()-this.count;
    }

    @Override
    public String toString() {
        return "BrukerMedCount{" +
                "bruker=" + bruker +
                ", count=" + count +
                '}';
    }
}
