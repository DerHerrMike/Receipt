import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    protected List<Item> items;
    protected List<Receipt> listOfReceipts;
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

    public void addItem(Path path, List<Item> getItemsFromFile) throws IOException {

        Item item = new Item();
        int i = 0;
        while (i < item.defIterator()) {
            Item insert = item.itemCreator(path, getItemsFromFile);
            items.add(insert);
            i++;
            item.writeToFile(path);
        }
        System.out.println("Alle Items hinzugefügt und in Datei geschrieben!");
        System.out.println();
    }

    public void listItems(List<Item> getItemsFromFile) {

        System.out.println("--------------------------------");
        System.out.println();
        System.out.println("Items auf Lager: ");
        System.out.println();

        for (Item item : getItemsFromFile) {
            System.out.print("SKU: " + item.getSku() + " || ");
            System.out.print("Brand: " + item.getBrand() + " || ");
            System.out.print("Name: " + item.getName() + " || ");
            System.out.println("Stückpreis EUR: " + item.getPpu());
            System.out.println();
        }
        System.out.println("Alle Items auf Lager ausgegeben.");
    }

    public List<ReceiptItem> sellItems(List<Item> getItemsFromFile ) {

        List<ReceiptItem> receiptItemList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("Willkommen bei " + shopname + " - Tools4Pros");
        System.out.println();
        System.out.println("Folgende Produkte sind aktuell verfügbar:");
        System.out.println();
        for (Item item : getItemsFromFile) {
            System.out.print("SKU: " + item.getSku() + " || ");
            System.out.print("Brand: " + item.getBrand() + " || ");
            System.out.print("Name: " + item.getName() + " || ");
            System.out.println("Stückpreis EUR: " + item.getPpu());
            System.out.println();
        }

        boolean abbruch = false;
        while (!abbruch) {
            System.out.println("Welches Produkt möchtest du kaufen? Bitte SKU eingeben: ");
            String selectedSKU = scanner.nextLine();
            int iterationCounter = 0;
            for (int i = 0; i < getItemsFromFile.size(); i++) {//gets SKUs
                String compareSKU = getItemsFromFile.get(iterationCounter).getSku();
                if (!compareSKU.equalsIgnoreCase(selectedSKU)) {
                    iterationCounter++;
                }
            }
            int anzahl = getNumberOfItems();
            int skuPosition = iterationCounter;
            ReceiptItem itemsOnListItems = new ReceiptItem();
            itemsOnListItems.setItem(getItemsFromFile.get(skuPosition).getBrand() + ", " +getItemsFromFile.get(skuPosition).getName());
            itemsOnListItems.setPrice(getItemsFromFile.get(skuPosition).getPpu());
            String selectedItemBrandName = (getItemsFromFile.get(skuPosition).getBrand() + ", " + getItemsFromFile.get(skuPosition).getName());
            double selectedItemPrice = getItemsFromFile.get(skuPosition).getPpu();
            ReceiptItem bought = new ReceiptItem(selectedItemBrandName, anzahl, selectedItemPrice);
            receiptItemList.add(bought);
            System.out.println();
            System.out.println("Weiteres Produkt kaufen? (j/n): ");
            String furtherItems = scanner.nextLine();
            if (furtherItems.equalsIgnoreCase("n")) {
                abbruch = true;
            }
        }
        int anzahl;
        return receiptItemList;
    }


    public int getNumberOfItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Bitte gewünschte Anzahl eingeben: ");
        return scanner.nextInt();
    }

    public Receipt createReceipt(int numberOfItemsPurchased, List<ReceiptItem> itemsOnList) {

        Receipt r = new Receipt();
        LocalDateTime lcd = LocalDateTime.now();
        Receipt receipt = new Receipt(lcd, getShopname(), r.getNextValue());
        String receiptConverter = receipt.stringify();
        System.out.println(receiptConverter);
        System.out.println();
        double total = 0;
        int position = 0;
        for (int i = 0; i < numberOfItemsPurchased; i++) {
            ReceiptItem itemsOnReceipt = new ReceiptItem(itemsOnList.get(position).getItem(), itemsOnList.get(position).getQuantity(), itemsOnList.get(position).getPrice());
            total += itemsOnReceipt.getGross();
            String porConvert = itemsOnReceipt.stringify();
            System.out.println(porConvert);
            position++;
        }

        System.out.println("______________________________");
        System.out.println("______________________________" + System.getProperty("line.separator") +
                "Total inkl. USt.: " + Math.round(total * 100.0) / 100.0 + " EUR.");
        return receipt;

        //NEEDED for Accounting:
        //                System.out.println("Auslesen des return Objektes receipt aus createReceipt:");
        //                System.out.println(listOfReceipts.get(0).stringify());
        //                System.out.println();
        //                System.out.println(returned.get(0).stringify());
        //                System.out.println(returned.get(1).stringify());
        //                System.out.println(returned.get(2).stringify());//create loop
    }


    // G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }


}
