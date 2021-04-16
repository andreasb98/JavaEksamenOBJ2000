import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)  {
        ObjectInputStream inFromClient;
        ObjectOutputStream outToClient;
            try{
                Datasource.getInstance().open();
                ServerSocket ss = new ServerSocket(4999);
                while(true) {
                    Socket s = ss.accept();

                    System.out.println("client connected");
                    // Server mottar
                    inFromClient = new ObjectInputStream(s.getInputStream());
                    outToClient = new ObjectOutputStream(s.getOutputStream());
                    Melding meldingFraClient = (Melding)inFromClient.readObject();
                    // registrerBruker, Søke kjønn og alder, Match med Count, Stalkers
                    if("registrerBruker".equals(meldingFraClient.getHandling())) {
                        MeldingMedBruker m = (MeldingMedBruker)meldingFraClient;
                        Bruker b = m.getBruker();
                        Integer value = Datasource.getInstance().insertInteresser(b);
                        outToClient.writeObject(value);
                        /*
                        if(value > 0) {
                            Integer brukerId = value;
                            outToClient.writeObject(brukerId);
                        } else {
                            Integer brukerId = value;
                            outToClient.writeObject(brukerId);
                        }

                        */
                    } else if("personSøk".equals(meldingFraClient.getHandling())) {
                        MeldingPersonsøk m = (MeldingPersonsøk)meldingFraClient;
                        PersonSøk p = m.getPersonSøk();
                        ArrayList<BrukerMedCount> brukereUt = (ArrayList<BrukerMedCount>) Datasource.getInstance().queryPartnerMatch(p.getId(), p.getKjønn(), "", p.getMinAlder(), p.getMaxAlder(), m.getAntall());
                        outToClient.writeObject(brukereUt);
                    } else if("requestInformation".equals(meldingFraClient.getHandling())) {
                        MeldingMedIds m = (MeldingMedIds)meldingFraClient;
                        Bruker b = Datasource.getInstance().findTrueLove(m.getBrukerId(), m.getPartnerId());
                        outToClient.writeObject(b);
                    } else if("visInteresserte".equals(meldingFraClient.getHandling())) {
                        MeldingMedSingleId m = (MeldingMedSingleId)meldingFraClient;
                        List<Bruker> brukere = Datasource.getInstance().showTrueLove(m.getBrukerId());
                        outToClient.writeObject(brukere);
                    }
                }

            } catch(IOException | ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());

            }









    }
}
