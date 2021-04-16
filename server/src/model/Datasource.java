package model;

import java.sql.*;
import java.util.*;

public class Datasource {

    public static final String DB_NAME = "match.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Skole\\Eksamen OBJ2000\\server\\" + DB_NAME;


    // Bruker tabell
    public static final String TABLE_BRUKER = "bruker";
    public static final String COLUMN_BRUKER_ID = "id";
    public static final String COLUMN_BRUKER_NAVN = "navn";
    public static final String COLUMN_BRUKER_KJONN = "kjønn";
    public static final String COLUMN_BRUKER_ALDER = "alder";
    public static final String COLUMN_BRUKER_BOSTED = "bosted";
    public static final String COLUMN_BRUKER_TELEFON = "telefon";

    // Interesse tabell
    public static final String TABLE_INTERESSER = "interesser";
    public static final String COLUMN_INTERESSER_ID = "id";
    public static final String COLUMN_INTERESSER_BESKRIVELSE = "beskrivelse";
    public static final String COLUMN_INTERESSER_BRUKERFK = "brukeridfk";

    // Match tabell
    public static final String TABLE_MATCH = "match";
    public static final String COLUMN_MATCH_ID = "id";
    public static final String COLUMN_MATCH_BRUKER_ID_1 = "Etterspurt";
    public static final String COLUMN_MATCH_BRUKER_ID_2 = "Utlevert";


    // Tabell for brukere
    public static final String CREATE_TABLE_BRUKER = "CREATE TABLE IF NOT EXISTS " + TABLE_BRUKER + "(\n"
            + COLUMN_BRUKER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n"
            + COLUMN_BRUKER_NAVN + " TEXT, \n"
            + COLUMN_BRUKER_KJONN + " TEXT, \n"
            + COLUMN_BRUKER_ALDER + " INTEGER, \n"
            + COLUMN_BRUKER_BOSTED + " TEXT, \n"
            + COLUMN_BRUKER_TELEFON + " TEXT UNIQUE \n"
            + ");";

    // Tabell for interesser
    public static final String CREATE_TABLE_INTERESSER = "CREATE TABLE IF NOT EXISTS " + TABLE_INTERESSER + "(\n"
            + COLUMN_INTERESSER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n"
            + COLUMN_INTERESSER_BESKRIVELSE + " TEXT, \n"
            + COLUMN_INTERESSER_BRUKERFK + " INTEGER, \n"
            + " FOREIGN KEY(" + COLUMN_INTERESSER_BRUKERFK + ") REFERENCES " + TABLE_BRUKER + '(' + COLUMN_BRUKER_ID + "));";

    // Tabell for match
    public static final String CREATE_TABLE_MATCH = "CREATE TABLE IF NOT EXISTS " + TABLE_MATCH + "(\n"
            + COLUMN_MATCH_BRUKER_ID_1 + " INTEGER, \n"
            + COLUMN_MATCH_BRUKER_ID_2 + " INTEGER, \n"
            + "UNIQUE(" + COLUMN_MATCH_BRUKER_ID_1 + ", " + COLUMN_MATCH_BRUKER_ID_2 + "), "
            + " FOREIGN KEY (" + COLUMN_MATCH_BRUKER_ID_1 + ") REFERENCES " + TABLE_BRUKER + '(' + COLUMN_BRUKER_ID + ") \n"
            + " FOREIGN KEY (" + COLUMN_MATCH_BRUKER_ID_2 + ") REFERENCES " + TABLE_BRUKER + '(' + COLUMN_BRUKER_ID + "));";





    // PREPARED STATEMENT -> BRUKER
    public static final String INSERT_BRUKER = "INSERT INTO " + TABLE_BRUKER +
            '(' + COLUMN_BRUKER_NAVN + ", " + COLUMN_BRUKER_KJONN + ", "
            + COLUMN_BRUKER_ALDER + ", " + COLUMN_BRUKER_BOSTED + ", " + COLUMN_BRUKER_TELEFON
            + ") VALUES(?,?,?,?,?)";

    // PREPARED STATEMENT -> BRUKER INTERESSER
    public static final String INSERT_INTERESSER = "INSERT INTO " + TABLE_INTERESSER +
            '(' + COLUMN_INTERESSER_BESKRIVELSE + ", " + COLUMN_INTERESSER_BRUKERFK
            + ") VALUES(?,?)";

    // QUERY BRUKER CHECK
    public static final String QUERY_BRUKER_CHECK = "SELECT * FROM " + TABLE_BRUKER +
            " WHERE " + COLUMN_BRUKER_TELEFON +  " = ?";

    public static final String QUERY_BRUKER = "SELECT * FROM " + TABLE_BRUKER +
            " WHERE " + COLUMN_BRUKER_ID + " = ?";


    // QUERY PARTNER
    public static final String QUERY_INTERESSE_COUNT = "SELECT COUNT(*) FROM " + TABLE_INTERESSER +
            " WHERE " + COLUMN_INTERESSER_BRUKERFK  + " = ?" +
            " AND " + COLUMN_INTERESSER_BESKRIVELSE + " IN(SELECT " + COLUMN_INTERESSER_BESKRIVELSE +
            " FROM " + TABLE_INTERESSER + " WHERE " + COLUMN_INTERESSER_BRUKERFK + " = ?)";

    public static final String QUERY_PARTNER = "SELECT " + COLUMN_BRUKER_KJONN + ", " + COLUMN_BRUKER_ALDER + ", " + COLUMN_BRUKER_BOSTED + ", " + COLUMN_BRUKER_ID +
            " FROM " + TABLE_BRUKER +
            " WHERE " + COLUMN_BRUKER_KJONN + " = ?" +
            " AND " + COLUMN_BRUKER_ALDER + " BETWEEN ? AND ?";

    public static final String QUERY_INTERESSE_BESK = "SELECT " + COLUMN_INTERESSER_BESKRIVELSE +
            " FROM " + TABLE_INTERESSER +
            " WHERE " + COLUMN_INTERESSER_BRUKERFK + " = ?";

    public static final String QUERY_PARTNER_TREFF = "SELECT * FROM " + TABLE_BRUKER +
            " WHERE " + COLUMN_BRUKER_ID + " = ?";

    // QUERY MATCH
    public static final String INSERT_INTO_MATCH = "INSERT INTO " + TABLE_MATCH + "( " + COLUMN_MATCH_BRUKER_ID_1 + ", " + COLUMN_MATCH_BRUKER_ID_2 + ") " +
            "VALUES(?,?);";

    public static final String QUERY_FIND_SEEKERS = "SELECT " + COLUMN_MATCH_BRUKER_ID_1 + " FROM " + TABLE_MATCH + " WHERE " + COLUMN_MATCH_BRUKER_ID_2 + " = ?;";
    public static final String QUERY_ALREADY_SEARCHED = "SELECT COUNT(*) FROM " + TABLE_MATCH + " WHERE " + COLUMN_MATCH_BRUKER_ID_1 + " = ? AND " + COLUMN_MATCH_BRUKER_ID_2 + " = ?;";

    private Connection conn;


    // Bruker
    private PreparedStatement insertBruker;
    private PreparedStatement insertInteresser;
    private PreparedStatement queryBrukerTelefon;
    private PreparedStatement queryBrukerId;


    // Partner/match
    private PreparedStatement queryMatch;
    private PreparedStatement queryInteresseBesk;
    private PreparedStatement queryInteresseCount;
    private PreparedStatement queryPartnerTreff;
    private PreparedStatement insertMatch;
    private PreparedStatement queryFindSeekers;
    private PreparedStatement queryAlreadySearched;


    private static Datasource instance = new Datasource();

    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            Statement statement = conn.createStatement();
            statement.execute(CREATE_TABLE_BRUKER);
            statement.execute(CREATE_TABLE_INTERESSER);
            statement.execute(CREATE_TABLE_MATCH);


            // Bruker
            insertBruker = conn.prepareStatement(INSERT_BRUKER, Statement.RETURN_GENERATED_KEYS);
            insertInteresser = conn.prepareStatement(INSERT_INTERESSER);
            queryBrukerTelefon = conn.prepareStatement(QUERY_BRUKER_CHECK);
            queryBrukerId = conn.prepareStatement(QUERY_BRUKER, Statement.RETURN_GENERATED_KEYS);

            // Partner / Match
            queryMatch = conn.prepareStatement(QUERY_PARTNER);
            queryInteresseBesk = conn.prepareStatement(QUERY_INTERESSE_BESK);
            queryInteresseCount = conn.prepareStatement(QUERY_INTERESSE_COUNT);
            queryPartnerTreff = conn.prepareStatement(QUERY_PARTNER_TREFF);
            insertMatch = conn.prepareStatement(INSERT_INTO_MATCH);
            queryFindSeekers = conn.prepareStatement(QUERY_FIND_SEEKERS);
            queryAlreadySearched = conn.prepareStatement(QUERY_ALREADY_SEARCHED);


            return true;
        }catch (SQLException e) {
            System.out.println("Kan ikke koble opp DB " + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }


    public void close() {

        try {
            if (insertBruker != null) {
                insertBruker.close();
            }

            if (insertInteresser != null) {
                insertInteresser.close();
            }

            if (queryBrukerTelefon != null) {
                queryBrukerTelefon.close();
            }

            if (queryBrukerId != null) {
                queryBrukerId.close();
            }

            if (queryMatch != null) {
                queryMatch.close();
            }

            if (queryInteresseBesk != null) {
                queryInteresseBesk.close();
            }

            if (queryInteresseCount != null) {
                queryInteresseCount.close();
            }

        }catch (SQLException e) {
            System.out.println("Kan ikke lukke forbindelse " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Bruker> showTrueLove(int brukerId) throws SQLException {
        // Sjekk om Id eksister i databasen
        if(!brukerCheckId(brukerId)) {
            return null;
        }
        ArrayList<Bruker> brukereUt = new ArrayList<Bruker>();
        ArrayList<Integer> matchIds = new ArrayList<Integer>();
        queryFindSeekers.setInt(1, brukerId);
        ResultSet results = queryFindSeekers.executeQuery();
        while(results.next()) {
            matchIds.add(results.getInt(1));
        }
        if(matchIds.size() < 1) {
            return null;
        }
        for(Integer i : matchIds) {
            queryBrukerId.setInt(1, i);
            ResultSet result = queryBrukerId.executeQuery();
            Bruker b = new Bruker();
            b.setId(result.getInt(1));
            b.setNavn(result.getString(2));
            b.setKjønn(result.getString(3));
            b.setAlder(result.getInt(4));
            b.setBosted(result.getString(5));
            b.setTelefonummer(result.getString(6));
            brukereUt.add(b);
        }
        return brukereUt;
    }

    public Bruker findTrueLove(int brukerId, int partnerId) throws SQLException {
        // Sjekker om IDs eksisterer i databasen.
        if(!brukerCheckId(brukerId) || !brukerCheckId(partnerId)) {
            return null;
        }
        queryPartnerTreff.setInt(1, partnerId);
        ResultSet results = queryPartnerTreff.executeQuery();
        Bruker bruker = new Bruker();
        bruker.setId(results.getInt(1));
        bruker.setNavn(results.getString(2));
        bruker.setKjønn(results.getString(3));
        bruker.setAlder(results.getInt(4));
        bruker.setBosted(results.getString(5));
        bruker.setTelefonummer(results.getString(6));


        queryInteresseBesk.setInt(1, partnerId);
        ResultSet r = queryInteresseBesk.executeQuery();
        ArrayList<String> interesser = new ArrayList<>();
        while(r.next()) {
            interesser.add(r.getString(1));
        }
        bruker.setInteresser(interesser);


        // Sjekk om brukerId er i etterspørsel og partnerId er i utlevert
        queryAlreadySearched.setInt(1, brukerId);
        queryAlreadySearched.setInt(2, partnerId);
        ResultSet res = queryAlreadySearched.executeQuery();
        int count = res.getInt(1);
        if(count <= 0) {
            insertMatch.setInt(1, brukerId);
            insertMatch.setInt(2, partnerId);
            insertMatch.executeUpdate();
            return bruker;
        } else {
            return bruker; // Uten insert i matchtabell
        }





    }

    public int insertInteresser(Bruker bruker) throws SQLException {
        int brukerId = insertBruker(bruker);
        System.out.println(brukerId);

        if (brukerId < 1) {
            return -1;
        }

        List<String> interesser = bruker.getInteresser();
        if (interesser.size() < 1) {
            return -1;
        }

        for (String s : interesser) {
            insertInteresser.setString(1, s);
            insertInteresser.setInt(2, brukerId);
            insertInteresser.executeUpdate();
        }
        return brukerId;
    }


    public int insertBruker(Bruker bruker) throws SQLException{

        System.out.println(bruker);
        System.out.println(bruker.getTelefonummer());
        if (brukerCheckTlf(bruker.getTelefonummer())) {
            System.out.println("Bruker allerede registrert (tlf)");
            return -1;
        }




        insertBruker.setString(1, bruker.getNavn());
        insertBruker.setString(2, bruker.getKjønn());
        insertBruker.setInt(3, bruker.getAlder());
        insertBruker.setString(4, bruker.getBosted());
        insertBruker.setString(5, bruker.getTelefonummer());

        int affectedRows = insertBruker.executeUpdate();
        if (affectedRows != 1) {
            return -1;
        }


        ResultSet generatedKeys = insertBruker.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }else {
            System.out.println("Kunne ikke finne id for bruker");
            return -1;
        }

    }


    //    Serveren gjør så et søk i databasen, alle personer med match på kjønn og aldersintervall
//    rangeres etter hvor godt interessene matcher. De ti beste treffene sendes så tilbake til klienten.
    public List<BrukerMedCount> queryPartnerMatch(int id, String kjønn, String bosted,  int minAlder, int maxAlder, int antall) {
        try {
            if(!brukerCheckId(id) || antall < 1) {
                return null;
            }

            queryMatch.setString(1, kjønn);
            queryMatch.setInt(2, minAlder);
            queryMatch.setInt(3, maxAlder);


            ResultSet results = queryMatch.executeQuery();
            List<Bruker> brukere = new ArrayList<>();
            while (results.next()) {
                Bruker bruker = new Bruker();
                bruker.setKjønn(results.getString(1));
                bruker.setAlder(results.getInt(2));
                bruker.setBosted(results.getString(3));
                bruker.setId(results.getInt(4));
                brukere.add(bruker);
            }


            List<BrukerMedCount> countArray = new ArrayList<>();
            int antallUt = antall;
            int teller = 1;

            for (Bruker b : brukere) {
                if (teller > antallUt) {
                    break;
                }
                // Skip deg selv
                if(b.getId() == id) {
                    continue;
                }
                queryInteresseCount.setInt(1, id);
                queryInteresseCount.setInt(2, b.getId());

                ResultSet resultsCount = queryInteresseCount.executeQuery();

                int value = resultsCount.getInt(1);
                BrukerMedCount bmc;
                if (value > 0) {
                    bmc = new BrukerMedCount(b, value);
                } else {
                    bmc = new BrukerMedCount(b, 0);
                }
                countArray.add(bmc);
                teller++;
            }



            for (BrukerMedCount bc : countArray) {

                queryInteresseBesk.setInt(1, bc.getBruker().getId());

                ResultSet r = queryInteresseBesk.executeQuery();

                List<String> intrList = new ArrayList<>();
                while (r.next()) {
                    intrList.add(r.getString(1));
                }
                bc.getBruker().setInteresser(intrList);

            }

            Collections.sort(countArray);
            return countArray;


        }catch (SQLException e) {
            System.out.println("QUERY match/partner feilet " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    private boolean brukerCheckTlf(String telefon) throws SQLException{
        queryBrukerTelefon.setString(1, telefon);
        ResultSet results = queryBrukerTelefon.executeQuery();

        if (results.next()) {
            return true;
        }else {
            return false;
        }

    }

    private boolean brukerCheckId(int id) throws SQLException{
        queryBrukerId.setInt(1, id);
        ResultSet result = queryBrukerId.executeQuery();

        if (result.next()) {
            return true;
        } else {
            return false;
        }

    }

}
