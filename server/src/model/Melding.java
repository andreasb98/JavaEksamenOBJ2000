package model;

import java.io.Serializable;

public class Melding implements Serializable {
    private static final long serialVersionUID = 1;
    protected String handling;


    public Melding(String handling) {
        this.handling = handling;

    }
    /*
    public PersonSøk getPersonSøk() {
        return personSøk;
    }

    public void setPersonSøk(PersonSøk personSøk) {
        this.personSøk = personSøk;
    }

     */

    public String getHandling() {
        return handling;
    }

    public void setHandling(String handling) {
        this.handling = handling;
    }
    /*
    public Bruker getBruker() {
        return bruker;
    }

    public void setBruker(Bruker bruker) {
        this.bruker = bruker;
    }

     */
}

