package model;

import java.io.Serializable;

public class PersonSøk implements Serializable {
    private static final long serialVersionUID = 4;
    private int id;
    private String kjønn;
    private int minAlder;
    private int maxAlder;

    public PersonSøk(int id, String kjønn, int minAlder, int maxAlder) {
        this.id = id;
        this.kjønn = kjønn;
        this.minAlder = minAlder;
        this.maxAlder = maxAlder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKjønn() {
        return kjønn;
    }

    public void setKjønn(String kjønn) {
        this.kjønn = kjønn;
    }

    public int getMinAlder() {
        return minAlder;
    }

    public void setMinAlder(int minAlder) {
        this.minAlder = minAlder;
    }

    public int getMaxAlder() {
        return maxAlder;
    }

    public void setMaxAlder(int maxAlder) {
        this.maxAlder = maxAlder;
    }
}
