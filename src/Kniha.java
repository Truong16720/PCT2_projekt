public class Kniha {
    private String nazev;
    private String autor;
    private String zanr; // Přidání žánru
    private int rokVydani;
    private boolean jeDostupna;
    private String typ;

    public Kniha(String nazev, String autor, String zanr, String typ, int rokVydani, boolean jeDostupna) {
        this.nazev = nazev;
        this.autor = autor;
        this.zanr = zanr;
        this.rokVydani = rokVydani;
        this.jeDostupna = jeDostupna;
        this.typ = typ; // Uložení typu
    }
  

    // Gettery a settery
    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public int getRokVydani() {
        return rokVydani;
    }

    public void setRokVydani(int rokVydani) {
        this.rokVydani = rokVydani;
    }
    
    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
    	this.typ = typ; 
    }

    public boolean isJeDostupna() {
        return jeDostupna;
    }

    public void setJeDostupna(boolean jeDostupna) {
        this.jeDostupna = jeDostupna;
    }
    @Override
    public String toString() {
        return String.format("Název: %s, Autor: %s, Žánr: %s, Typ: %s, Rok vydání: %d, Dostupnost: %s",
                             nazev, autor, zanr, typ, rokVydani, jeDostupna ? "Dostupná" : "Vypůjčená");
    }
}
