import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Knihovna {
    private List<Kniha> knihy;
 


    private static final String URL = "jdbc:mysql://localhost:3306/knihy";
    private static final String USER = "root";
    private static final String PASSWORD = "Truong1234";

  



    
    
    public Knihovna() {
        this.knihy = new ArrayList<>();
    }

    public void pridatKniha(Kniha kniha) {
        knihy.add(kniha);
    }

    public void upravitKniha(String nazev, String novyAutor, String novyZanr,String novyTyp, Integer novyRokVydani, Boolean novaDostupnost) {
        for (Kniha kniha : knihy) {
            if (kniha.getNazev().equals(nazev)) {
                if (novyAutor != null && !novyAutor.isEmpty()) {
                    kniha.setAutor(novyAutor);
                }
                if (novyZanr != null && !novyZanr.isEmpty()) {
                    kniha.setZanr(novyZanr);  // Nastaví nový žánr, pokud byl zadán
                }
                if (novyTyp != null) kniha.setTyp(novyTyp);  
                
                if (novyRokVydani != null && novyRokVydani >= 0) { // Předpokládáme, že -1 není platný rok
                    kniha.setRokVydani(novyRokVydani);
                }
                // Dostupnost můžete aktualizovat podobným způsobem, pokud to vaše aplikace vyžaduje
                break;
            }
        }
    }
    
    public void smazatKniha(String nazev) {
        knihy.removeIf(kniha -> kniha.getNazev().equals(nazev));
    }
    
    public void oznacitJakoVypujcenou(String nazev) {
        knihy.stream()
            .filter(kniha -> kniha.getNazev().equals(nazev) && kniha.isJeDostupna())
            .findFirst()
            .ifPresent(kniha -> kniha.setJeDostupna(false));
    }

    public void oznacitJakoVracenou(String nazev) {
        knihy.stream()
            .filter(kniha -> kniha.getNazev().equals(nazev) && !kniha.isJeDostupna())
            .findFirst()
            .ifPresent(kniha -> kniha.setJeDostupna(true));
    }
    public void vypsatKnihy() {
        knihy.stream()
            .sorted(Comparator.comparing(Kniha::getNazev))
            .forEach(kniha -> System.out.println(kniha.toString()));
    }
    public void vyhledatKnihu(String nazev) {
        Kniha nalezenaKniha = knihy.stream()
                                   .filter(kniha -> kniha.getNazev().equalsIgnoreCase(nazev))
                                   .findFirst()
                                   .orElse(null);

        if (nalezenaKniha != null) {
            System.out.println(nalezenaKniha);
        } else {
            System.out.println("Kniha s názvem '" + nazev + "' nebyla nalezena.");
        }
    }
    public void vypsatKnihyAutora(String autor) {
        knihy.stream()
             .filter(kniha -> kniha.getAutor().equalsIgnoreCase(autor))
             .sorted(Comparator.comparingInt(Kniha::getRokVydani))
             .forEach(System.out::println);
    }
    public void vypsatKnihyPodleZanru(String zanr) {
        System.out.println("Knihy v žánru '" + zanr + "':");
        boolean nalezeno = false;
        for (Kniha kniha : knihy) {
            if (kniha.getZanr().equalsIgnoreCase(zanr)) {
                System.out.println(kniha);
                nalezeno = true;
            }
        }
        if (!nalezeno) {
            System.out.println("V tomto žánru nebyly nalezeny žádné knihy.");
        }
    }
    public void vypsatVypujceneKnihy() {
        System.out.println("Vypůjčené knihy:");
        boolean vypujceneKnihyNalezeny = false;
        for (Kniha kniha : knihy) {
            if (!kniha.isJeDostupna()) { // Kontrola, zda kniha není dostupná (tj. je vypůjčená)
                System.out.println("Název: " + kniha.getNazev() + ", Typ: " + kniha.getTyp());
                vypujceneKnihyNalezeny = true;
            }
        }
        if (!vypujceneKnihyNalezeny) {
            System.out.println("Nebyly nalezeny žádné vypůjčené knihy.");
        }
    }
    public void ulozitKnihuDoSouboru(String nazevKnihy, String nazevSouboru) {
        Kniha nalezenaKniha = null;
        for (Kniha kniha : knihy) {
            if (kniha.getNazev().equalsIgnoreCase(nazevKnihy)) {
                nalezenaKniha = kniha;
                break;
            }
        }

        if (nalezenaKniha == null) {
            System.out.println("Kniha '" + nazevKnihy + "' nebyla nalezena.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(nazevSouboru)) {
            writer.println("Název: " + nalezenaKniha.getNazev());
            writer.println("Autor: " + nalezenaKniha.getAutor());
            writer.println("Žánr: " + nalezenaKniha.getZanr());
            writer.println("Typ: " + nalezenaKniha.getTyp());
            writer.println("Rok vydání: " + nalezenaKniha.getRokVydani());
            writer.println("Dostupnost: " + (nalezenaKniha.isJeDostupna() ? "Dostupná" : "Vypůjčená"));
            System.out.println("Informace o knize byly uloženy do souboru: " + nazevSouboru);
        } catch (FileNotFoundException e) {
            System.out.println("Soubor '" + nazevSouboru + "' nebylo možné vytvořit nebo otevřít.");
        }
    }
    public void nacistKnihuZeSouboru(String nazevSouboru) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nazevSouboru))) {
            String nazev = reader.readLine().split(": ")[1];
            String autor = reader.readLine().split(": ")[1];
            String zanr = reader.readLine().split(": ")[1];
            String typ = reader.readLine().split(": ")[1];
            int rokVydani = Integer.parseInt(reader.readLine().split(": ")[1]);
            boolean jeDostupna = reader.readLine().split(": ")[1].equals("Dostupná");

            Kniha kniha = new Kniha(nazev, autor, zanr,typ, rokVydani, jeDostupna);
            System.out.println("Načtená kniha: " + kniha);
            
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst soubor '" + nazevSouboru + "'.");
        } catch (Exception e) {
            System.out.println("Došlo k chybě při načítání informací o knize ze souboru.");
        }
    }
    
 
    
    
    public void nacistZDatabaze() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM knihy";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String nazev = rs.getString("nazev");
                    String autor = rs.getString("autor");
                    String zanr = rs.getString("zanr");
                    String typ = rs.getString("typ");
                    int rokVydani = rs.getInt("rok_vydani");
                    boolean jeDostupna = rs.getBoolean("je_dostupna");
                    Kniha kniha = new Kniha(nazev, autor, zanr, typ, rokVydani, jeDostupna);
                    knihy.add(kniha);
                }
            }
        } catch (SQLException e) {
            System.out.println("Nepodařilo se načíst data z databáze: " + e.getMessage());
        }
    }
    
    public void aktualizovatDatabazi() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        
            String sqlDelete = "DELETE FROM knihy";
            try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {
                pstmtDelete.executeUpdate();
            }

          
            String sqlInsert = "INSERT INTO knihy (nazev, autor, zanr, typ, rok_vydani, je_dostupna) VALUES (?, ?, ?, ?, ?, ?)";
            for (Kniha kniha : knihy) {
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    pstmtInsert.setString(1, kniha.getNazev());
                    pstmtInsert.setString(2, kniha.getAutor());
                    pstmtInsert.setString(3, kniha.getZanr());
                    pstmtInsert.setString(4, kniha.getTyp());
                    pstmtInsert.setInt(5, kniha.getRokVydani());
                    pstmtInsert.setBoolean(6, kniha.isJeDostupna());
                    pstmtInsert.executeUpdate();
                }
            }
            System.out.println("Databáze byla aktualizována.");
        } catch (SQLException e) {
            System.out.println("Nepodařilo se aktualizovat databázi: " + e.getMessage());
        }
    }

 
    
} 
