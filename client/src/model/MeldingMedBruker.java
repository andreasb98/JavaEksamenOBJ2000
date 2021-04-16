package model;

public class MeldingMedBruker extends Melding {
    private static final long serialVersionUID = 10;
    Bruker bruker;
    public MeldingMedBruker(String handling) {
        super(handling);
    }

    public Bruker getBruker() {
        return bruker;
    }

    public void setBruker(Bruker bruker) {
        this.bruker = bruker;
    }
}
