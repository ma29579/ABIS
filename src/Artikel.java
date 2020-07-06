import java.sql.SQLException;
import java.util.ArrayList;

public class Artikel {

    private long artikelNummer;
    private String bezeichnung;
    private double preis;
    private int bestand;
    private int mindestBestand;

    private ArtikelDAO artikelDAO = ArtikelDAO.getInstance();

    public Artikel(long artikelNummer, String bezeichnung, double preis, int bestand, int mindestBestand) {
        this.artikelNummer = artikelNummer;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.bestand = bestand;
        this.mindestBestand = mindestBestand;
    }

    public Artikel(){

    }

    @Override
    public String toString() {
        return "Artikel{" +
                "artikelNummer=" + artikelNummer +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", preis=" + preis +
                ", bestand=" + bestand +
                ", mindestBestand=" + mindestBestand +
                '}';
    }

    public void insert(){
        try {
            this.artikelDAO.create(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() throws SQLException {
        this.artikelDAO.delete(this,artikelNummer);
    }

    public static Artikel find(Long id) throws SQLException {
        return ArtikelDAO.getInstance().read(id);
    }

    public static ArrayList<Artikel> sucheArtikelMitWenigBestand() throws SQLException {
        return ArtikelDAO.getInstance().read();
    }

    public long getArtikelNummer() {
        return artikelNummer;
    }

    public void setArtikelNummer(long artikelNummer) throws SQLException {
        this.artikelNummer = artikelNummer;
        artikelDAO.update(this);
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) throws SQLException {
        this.bezeichnung = bezeichnung;
        artikelDAO.update(this);
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) throws SQLException {
        this.preis = preis;
        artikelDAO.update(this);
    }

    public int getBestand() {
        return bestand;
    }

    public void setBestand(int bestand) throws SQLException {
        this.bestand = bestand;
        artikelDAO.update(this);
    }

    public int getMindestBestand() {
        return mindestBestand;
    }

    public void setMindestBestand(int mindestBestand) throws SQLException {
        this.mindestBestand = mindestBestand;
        artikelDAO.update(this);
    }
}
