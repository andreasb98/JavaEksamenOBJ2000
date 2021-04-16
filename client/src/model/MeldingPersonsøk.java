package model;

public class MeldingPersonsøk extends Melding {
    private static final long serialVersionUID = 20;
    private PersonSøk personSøk;
    private int antall;
    public MeldingPersonsøk(String handling) {
        super(handling);
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public PersonSøk getPersonSøk() {
        return personSøk;
    }

    public void setPersonSøk(PersonSøk personSøk) {
        this.personSøk = personSøk;
    }
}
