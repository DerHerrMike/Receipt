import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    protected List<Item> items;
    protected List<ReceiptItem> listOfItemsForReceipt;
    protected String shopname;

    //constructor


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
        String nameshop = scanner.nextLine();
        setShopname(nameshop);
    }


    public void addItem(Path path, List<Item> getItemsFromFile) throws IOException {

        Item item = new Item();
        int i = 0;
        while (i < item.defIterator()) {
            Item insert = item.itemCreator(path, getItemsFromFile);
            getItemsFromFile.add(insert);
            item.writeToFile(path);
            i++;
        }
        System.out.println("Alle Items hinzugefügt und in Datei geschrieben!");
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void displayItems(List<Item> getItemsFromFile) {

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
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public List<ReceiptItem> sellItems(List<Item> getItemsFromFile) {

        Scanner scanner = new Scanner(System.in);
        List<ReceiptItem> receiptItemList = new ArrayList<>();
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
            System.out.println();
            System.out.println("Bitte gewünschte Anzahl eingeben: ");
            int anzahl = scanner.nextInt();
            scanner.nextLine();
            int skuPosition = iterationCounter;
            ReceiptItem itemsOnListItems = new ReceiptItem();
            itemsOnListItems.setItem(getItemsFromFile.get(skuPosition).getBrand() + ", " + getItemsFromFile.get(skuPosition).getName());
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
        return receiptItemList;
    }


//    public int setNumberOfItems() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println();
//        System.out.println("Bitte gewünschte Anzahl eingeben: ");
//        return scanner.nextInt();
//    }

    public Receipt createReceipt() {

        Receipt r = new Receipt();
        LocalDateTime lcd = LocalDateTime.now();
        return new Receipt(lcd, shopname, r.getNextValue());
    }
//
//    public void addReceiptItemsToList(int numberOfItemsPurchased, List<ReceiptItem> listForReceipt) {
//
//        double total = 0;
//        int position = 0;
//        for (int i = 0; i < numberOfItemsPurchased; i++) {
//            ReceiptItem itemsOnReceipt = new ReceiptItem(listForReceipt.get(position).getItem(), listForReceipt.get(position).getQuantity(), listForReceipt.get(position).getPrice());
//            listForReceipt.add(itemsOnReceipt);
//            position++;
//        }
//    }

    public double getReceiptItemsTotal(int numberOfItemsPurchased, List<ReceiptItem> listForReceipt) {

        double total = 0;
        int i = 0;
        while (i < listForReceipt.size()) {
            total += listForReceipt.get(i).getGross();

//            ReceiptItem itemsOnReceipt = new ReceiptItem(listForReceipt.get(position).getItem(), listForReceipt.get(position).getQuantity(), listForReceipt.get(position).getPrice());
//            total += itemsOnReceipt.getGross();
//            position++;
            i++;
        }
        return total;
    }


    public void printReceipt(Receipt receipt, List<ReceiptItem> listOfItemsForReceipt, double total) {

        Scanner scanner = new Scanner(System.in);
        String receiptConverter = receipt.stringify();
        System.out.println(receiptConverter);
        System.out.println();
        for (int i = 0; i < listOfItemsForReceipt.size(); i++) {

            String returnForSringify = listOfItemsForReceipt.get(i).stringify();
            System.out.println(returnForSringify);
        }
        System.out.println("______________________________");
        System.out.println("______________________________" + System.getProperty("line.separator") +
                "Total inkl. USt.: " + Math.round(total * 100.0) / 100.0 + " EUR.");
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
        scanner.nextLine();
    }

    //NEEDED for Accounting:
    //                System.out.println("Auslesen des return Objektes receipt aus createReceipt:");
    //                System.out.println(listOfReceipts.get(0).stringify());
    //                System.out.println();
    //                System.out.println(returned.get(0).stringify());
    //                System.out.println(returned.get(1).stringify());
    //                System.out.println(returned.get(2).stringify());//create loop

    // G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}
