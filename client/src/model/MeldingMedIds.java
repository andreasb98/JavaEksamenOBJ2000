package model;

public class MeldingMedIds extends Melding {
    private static final long serialVersionUID = 40;
    private int brukerId;
    private int partnerId;

    public MeldingMedIds(String handling) {
        super(handling);
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }
}

