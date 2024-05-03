import java.util.Scanner;

public class Test {
	

    
    

    public static void main(String[] args) {
        Knihovna knihovna = new Knihovna();
        Scanner scanner = new Scanner(System.in);
        
        knihovna.nacistZDatabaze();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            knihovna.aktualizovatDatabazi();
            
        }));
        
        while (true) {
        	System.out.println("\nVolby:");
            System.out.println("1. Přidat knihu");
            System.out.println("2. Upravit knihu");
            System.out.println("3. Smazat knihu");
            System.out.println("4. Označit knihu jako vypůjčenou");
            System.out.println("5. Označit knihu jako vrácenou");
            System.out.println("6. Vypsat všechny knihy");
            System.out.println("7. Vyhledat knihu");
            System.out.println("8. Vypsat knihy daného autora");
            System.out.println("9. Vypsat knihy podle žánru");
            System.out.println("10. Vypsat vypůjčené knihy");
            System.out.println("11. Uložení informace o vybrané knize do souboru");
            System.out.println("12. Načtení všech informací o dané knize ze souboru");
            System.out.println("0. Konec");
            System.out.print("Zadejte volbu: ");

            int volba = scanner.nextInt();
            scanner.nextLine(); // Ošetření konce řádku po čísle

            switch (volba) {
                
            case 1:
                System.out.print("Zadejte název knihy: ");
                String nazev = scanner.nextLine();
                System.out.print("Zadejte autora knihy: ");
                String autor = scanner.nextLine();
                System.out.print("Zadejte žánr knihy: ");
                String zanr = scanner.nextLine();
                System.out.print("Zadejte typ knihy (1 = Roman, 2 = Ucebnice, Enter = Neurčito): ");
                String typ = scanner.nextLine();
                typ = typ.equals("1") ? "Roman" : typ.equals("2") ? "Ucebnice" : "Neurčito";
                System.out.print("Zadejte rok vydání: ");
                int rokVydani = scanner.nextInt();                
                
                scanner.nextLine(); // Ošetření konce řádku po čísle
                Kniha kniha = new Kniha(nazev, autor, zanr, typ, rokVydani, true);
                knihovna.pridatKniha(kniha);
               
                break;

                case 2:
                    System.out.print("Zadejte název knihy k úpravě: ");
                    String nazevKeZmene = scanner.nextLine();
                    System.out.print("Zadejte nového autora (nebo Enter pro ponechání): ");
                    String novyAutor = scanner.nextLine();
                    System.out.print("Zadejte nový žánr (nebo Enter pro ponechání): "); 
                    String novyZanr = scanner.nextLine(); 
                    System.out.print("Zadejte typ knihy (1 = Roman, 2 = Ucebnice, Enter = Neurčito): ");
                    String NovyTyp = scanner.nextLine();
                    NovyTyp = NovyTyp.equals("1") ? "Roman" : NovyTyp.equals("2") ? "Ucebnice" : "Neurčito";
                    System.out.print("Zadejte nový rok vydání (nebo -1 pro ponechání): ");
                    int novyRokVydani = scanner.nextInt();
                    
                    scanner.nextLine(); 
                    knihovna.upravitKniha(nazevKeZmene, novyAutor.isEmpty() ? null : novyAutor, novyZanr.isEmpty() ? null : novyZanr,NovyTyp.isEmpty() ? null : NovyTyp, novyRokVydani == -1 ? null : novyRokVydani, null); // Přidáme nový žánr do metody upravitKniha              
                    break;
                case 3:
                    System.out.print("Zadejte název knihy, kterou chcete smazat: ");
                    String nazevKeSmazani = scanner.nextLine();
                    knihovna.smazatKniha(nazevKeSmazani);
                    System.out.println("Kniha '" + nazevKeSmazani + "' byla smazána.");
                    break;
                case 4:
                    System.out.print("Zadejte název knihy, kterou chcete označit jako vypůjčenou: ");
                    String nazevVypujcene = scanner.nextLine();
                    knihovna.oznacitJakoVypujcenou(nazevVypujcene);
                    System.out.println("Kniha '" + nazevVypujcene + "' byla označena jako vypůjčená.");
                    break;
                case 5:
                    System.out.print("Zadejte název knihy, kterou chcete označit jako vrácenou: ");
                    String nazevVracene = scanner.nextLine();
                    knihovna.oznacitJakoVracenou(nazevVracene);
                    System.out.println("Kniha '" + nazevVracene + "' byla označena jako vrácená.");
                    break;
                case 6:
                    knihovna.vypsatKnihy();
                    break;
                case 7:
                    System.out.print("Zadejte název knihy, kterou chcete vyhledat: ");
                    String nazev1 = scanner.nextLine();
                    knihovna.vyhledatKnihu(nazev1);
                    break;
                case 8:
                    System.out.print("Zadejte jméno autora: ");
                    String autor1 = scanner.nextLine();
                    knihovna.vypsatKnihyAutora(autor1);
                    break;
                case 9:
                    System.out.print("Zadejte žánr knih, které chcete vypsat: ");
                    String zanr1 = scanner.nextLine();
                    knihovna.vypsatKnihyPodleZanru(zanr1);
                    break;
                case 10:
                    knihovna.vypsatVypujceneKnihy();
                    break;
                case 11:
                    System.out.print("Zadejte název knihy, kterou chcete uložit: ");
                    String nazevKnihy = scanner.nextLine();
                    System.out.print("Zadejte název souboru pro uložení informací o knize: ");
                    String nazevSouboru = scanner.nextLine();
                    knihovna.ulozitKnihuDoSouboru(nazevKnihy, nazevSouboru);
                    break;
                case 12:
                	System.out.print("Zadejte název souboru s informacemi o knize: ");
                    String nazevSouboru1 = scanner.nextLine();
                    knihovna.nacistKnihuZeSouboru(nazevSouboru1);
                    break;
                case 0:
                	
                    return;
                default:
                    System.out.println("Neplatná volba.");
                    break;
            }
        }
    }
    
}
