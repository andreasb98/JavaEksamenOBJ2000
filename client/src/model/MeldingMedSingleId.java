package model;

public class MeldingMedSingleId extends Melding {
    private static final long serialVersionUID = 1000;
    private int brukerId;

    public MeldingMedSingleId(String handling) {
        super(handling);
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }
}
