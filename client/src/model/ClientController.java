package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    Socket s;
    ObjectOutputStream outToServer;
    ObjectInputStream inFromServer;
    public ClientController() {
        //init();
    }
    public int register(String navn, String kjønn, int alder, List<String> interesser, String bosted, String telefon) {
        init();
        Bruker b = new Bruker(navn, kjønn, alder, interesser, bosted, telefon);
        MeldingMedBruker melding = new MeldingMedBruker("registrerBruker");
        melding.setBruker(b);
        try {
            outToServer.writeObject(melding);
            Integer brukerId =  (Integer)inFromServer.readObject();
            System.out.println(brukerId);
            if(brukerId > 0) {
                // Lagre til fil
                writeToFileNonBinary(brukerId);
                return 1;
            } else {
                return -1;
            }
        } catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public ArrayList<BrukerMedCount> personSøk(int brukerId, String kjønn, int minAlder, int maxAlder, int antall) {
        init();
        MeldingPersonsøk melding = new MeldingPersonsøk("personSøk");
        melding.setPersonSøk(new PersonSøk(brukerId, kjønn, minAlder, maxAlder));
        melding.setAntall(antall);
        ArrayList<BrukerMedCount> brukere;
        try {
            outToServer.writeObject(melding);
            // Unchecked casting men vi veit jo hva som kommer
            brukere = (ArrayList<BrukerMedCount>) inFromServer.readObject();
            return brukere;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Bruker requestInformation(int brukerId, int partnerId) {
        init();
        MeldingMedIds melding = new MeldingMedIds("requestInformation");
        melding.setBrukerId(brukerId);
        melding.setPartnerId(partnerId);
        try {
            outToServer.writeObject(melding);
            Bruker b = (Bruker)inFromServer.readObject();
            return b;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Bruker> visInteresserte(int brukerId) {
        init();
        MeldingMedSingleId melding = new MeldingMedSingleId("visInteresserte");
        melding.setBrukerId(brukerId);
        List<Bruker> brukere;
        try {
            outToServer.writeObject(melding);
            brukere = (List<Bruker>) inFromServer.readObject();
            return brukere;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }





    public void init() {
        try {
            s = new Socket("localhost", 4999);
            outToServer = new ObjectOutputStream(s.getOutputStream());
            inFromServer = new ObjectInputStream(s.getInputStream());

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFileNonBinary(int id)  {
        try {
            FileWriter fw = new FileWriter(new File("brukerId.txt"));

            fw.write("ID: "+ id);
            fw.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getIDFromFile() {
        try {
            File filename = new File("brukerId.txt");
            if(!filename.isFile()) {
                return -1;
            }
            Scanner s = new Scanner(filename);
            if(s.hasNext()) {
                String linje = s.nextLine();
                String[] dataTab = linje.split(":");
                return Integer.parseInt(dataTab[1].trim());
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

}
