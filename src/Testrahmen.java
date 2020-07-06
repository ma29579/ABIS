import java.sql.SQLException;
import java.util.ArrayList;

public class Testrahmen {

    public static void main(String[] args) {

        Artikel a1 = new Artikel(1,"Artikel 1",9.99d,5,1);
        Artikel a2 = new Artikel(2,"Artikel 2",1.23d,6,5);
        Artikel a3 = new Artikel(3,"Artikel 3",1.0d,1,5);

        try {

            a1.insert();
            a2.insert();
            a3.insert();
            a1 = null;
            a1 = Artikel.find(1L);

            System.out.println("Ausgabe von Artikel-1 aus der Datenbank:");
            System.out.println("A1 = " + a1.toString());

            System.out.println("\nArtikel mit zu geringem Bestand:");

            ArrayList<Artikel> artikel = Artikel.sucheArtikelMitWenigBestand();

            for(Artikel a : artikel){
                System.out.println(a.toString());
            }

            System.out.println("\nÄnderung des Bestands von Artikel-2 auf 4 Einheiten!");

            a2.setBestand(4);
            artikel = Artikel.sucheArtikelMitWenigBestand();

            for(Artikel a : artikel){
                System.out.println(a.toString());
            }

            System.out.println("\nAusgabe des zuvor gelöschten Artikel-3:");

            a3.delete();
            a3 = Artikel.find(3L);

            System.out.println(a3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
