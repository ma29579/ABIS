import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtikelDAO extends AbstractDAO<Artikel> {

    private static ArtikelDAO instance = new ArtikelDAO();

    private ArtikelDAO(){
        super();
    }

    public static ArtikelDAO getInstance() {
        return instance;
    }

    public ArrayList<Artikel> read() throws SQLException {
        ResultSet results = this.abstractMultipleRead();

        ArrayList<Artikel> artikel = new ArrayList<>();
        while (results.next()) {

            long artikelNummer = results.getLong("Artikelnr");
            String bezeichnung = results.getString("Bezeichnung");
            double preis = results.getDouble("Preis");
            int bestand = results.getInt("Bestand");
            int mindestBestand = results.getInt("Mindestbestand");

            Artikel tmp = new Artikel(artikelNummer, bezeichnung, preis, bestand, mindestBestand);
            artikel.add(tmp);
        }

        return artikel;
    }

    public Artikel read(Long id) throws SQLException {
        return this.abstractSingleRead(id);
    }

    @Override
    protected String findStatement() {
        return "SELECT * FROM Artikel WHERE Artikelnr = ?";
    }

    @Override
    protected String deleteStatement() {
        return "DELETE FROM Artikel WHERE Artikelnr = ?";
    }

    @Override
    protected String updateStatement() {
        return "UPDATE Artikel SET Bezeichnung = ?, Preis = ?, Bestand = ?, Mindestbestand = ? WHERE Artikelnr = ?";
    }

    @Override
    protected String whereStatement() {
        return "SELECT * FROM Artikel WHERE Bestand < Mindestbestand";
    }

    @Override
    protected String insertStatement() {
        return "INSERT INTO Artikel (Artikelnr, Bezeichnung, Preis, Bestand, Mindestbestand) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected Long getKey(Artikel object) {
        return object.getArtikelNummer();
    }

    @Override
    protected Connection getConnection() {
        return this.db;
    }

    @Override
    protected void doUpdate(Artikel object) {

        PreparedStatement updateStatement = null;

        try {
            updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setString(1, object.getBezeichnung());
            updateStatement.setDouble(2, object.getPreis());
            updateStatement.setInt(3, object.getBestand());
            updateStatement.setInt(4, object.getMindestBestand());
            updateStatement.setLong(5, object.getArtikelNummer());
            updateStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Artikel doLoad(Long id, ResultSet rs) {

        Artikel neuerArtikel = new Artikel();
        try {
            neuerArtikel.setArtikelNummer(rs.getLong("Artikelnr"));
            neuerArtikel.setBezeichnung(rs.getString("Bezeichnung"));
            neuerArtikel.setBestand(rs.getInt("Bestand"));
            neuerArtikel.setMindestBestand(rs.getInt("Mindestbestand"));
            neuerArtikel.setPreis(rs.getDouble("Preis"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return neuerArtikel;
    }


    @Override
    protected long doInsert(Artikel object) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = db.prepareStatement(insertStatement());
        insertStatement.setLong(1,object.getArtikelNummer());
        insertStatement.setString(2, object.getBezeichnung());
        insertStatement.setDouble(3, object.getPreis());
        insertStatement.setInt(4, object.getBestand());
        insertStatement.setInt(5, object.getMindestBestand());

        insertStatement.execute();
        return object.getArtikelNummer();
    }

}
