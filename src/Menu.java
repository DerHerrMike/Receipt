import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Menu {

    protected String shopname;
    protected int userSelect;

    //empty constructor
    public Menu() {
    }

    public Menu(String shopname, int userSelect) {
        this.shopname = shopname;
        this.userSelect = userSelect;
    }
    //  dNS,cN,dM,s

    public void designNameSelection() {
        System.out.println("PROGRAMM ZU EIN- UND VERKAUF EINES KLEINEN UNTERNEHMENS");
        System.out.println("*******************************************************");
        System.out.println();
        System.out.println("Bitte zu Beginn den Firmennamen festlegen: ");
    }

    public void chooseName() {

        Scanner scanner = new Scanner(System.in);
        setShopname(scanner.nextLine());
    }

    public void designMenu() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println();
        System.out.println("WILLKOMMEN IN DER VERWALTUNG");
        System.out.println("****************************");
        System.out.println();
        System.out.println();
        System.out.println("Item hinzufügen = 1");
        System.out.println();
        System.out.println("Item-Bestand abfragen = 2");
        System.out.println();
        System.out.println("Items verkaufen = 3");
        System.out.println();
        System.out.println("Buchhaltung aufrufen = 4");
        System.out.println();
        System.out.println("Programm beenden = 0");
        System.out.println();
        System.out.println("Bitte treffen Sie eine Auswahl: ");
    }

    public void selector() {

        Scanner scanner = new Scanner(System.in);
        setUserSelect(scanner.nextInt());
    }


    //  G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(int userSelect) {
        this.userSelect = userSelect;
    }

}
