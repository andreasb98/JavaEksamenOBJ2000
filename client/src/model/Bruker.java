package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bruker implements Serializable {
    private static final long serialVersionUID = 2;
    private int id;
    private String navn;
    private String kjønn;
    private int alder;
    private List<String> interesser = new ArrayList<String>();
    private String bosted;
    private String telefonummer;

    public Bruker() {

    }

    public Bruker(String navn, String kjønn, int alder, List<String> interesser, String bosted, String telefonummer) {
        this.navn = navn;
        this.kjønn = kjønn;
        this.alder = alder;
        this.interesser = interesser;
        this.bosted = bosted;
        this.telefonummer = telefonummer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getKjønn() {
        return kjønn;
    }

    public void setKjønn(String kjønn) {
        this.kjønn = kjønn;
    }

    public int getAlder() {
        return alder;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    public List<String> getInteresser() {
        return interesser;
    }

    public void setInteresser(List<String> interesser) {
        this.interesser = interesser;
    }

    public String getBosted() {
        return bosted;
    }

    public void setBosted(String bosted) {
        this.bosted = bosted;
    }

    public String getTelefonummer() {
        return telefonummer;
    }

    public void setTelefonummer(String telefonummer) {
        this.telefonummer = telefonummer;
    }

    public void getStuff() {
        for(String s : this.interesser) {
            System.out.println(s);
        }
    }

    @Override
    public String toString() {
        return "Bruker{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", kjønn='" + kjønn + '\'' +
                ", alder=" + alder +
                ", interesser=" + interesser +
                ", bosted='" + bosted + '\'' +
                ", telefonummer='" + telefonummer + '\'' +
                '}';
    }
    /*
    @Override
    public int compareTo(Bruker o) {
        return o.getAlder()-this.alder;
    }

     */
}

