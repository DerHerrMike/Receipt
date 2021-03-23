import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Shop {

    private List<Item> items;
    protected String shopname;

    //constructor
    public Shop(String shopname) {
        this.shopname = shopname;
    }
    //no-args constructor
    public Shop() {
    }

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

    public void addItem(Path path) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Item item = new Item();
        int i = 0;
        while (i < item.defIterator()) {
            Item insert = item.itemCreator(path, items);
            items.add(insert);
            i++;
            item.writeToFile(path);
        }
        System.out.println("Alle Items hinzugefügt und in Datei geschrieben!");
        System.out.println();
    }




    public void listItems(){

    }

    public void sellItems(){

    }


    // G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
