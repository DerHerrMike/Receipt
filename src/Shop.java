import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    private String shopname;
    private int purchaseCounter;


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


    public void addItem(Path path, Path brands, Path itemPath, List<Item> getItemsFromFile) throws IOException {

        Item item = new Item();
        int i = 0;
        while (i < item.defIterator()) {
            Item insert = item.itemCreator(path, brands, itemPath,getItemsFromFile);
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

    public List<ReceiptItem> sellItems(List<Item> getItemsFromFile) throws IOException {

        Scanner scanner = new Scanner(System.in);
        List<ReceiptItem> receiptItemList = new ArrayList<>();
        List<String> details4Accounting = new ArrayList<>();
        Path pathToReceiptItems = Paths.get("output\\receiptItems.csv");

        System.out.println();
        System.out.println("Willkommen bei " + getShopname() + " - Tools4Pros");
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
                } else {
                    details4Accounting.add(selectedSKU);
                }
            }
            System.out.println();
            System.out.println("Bitte gewünschte Anzahl eingeben: ");
            int anzahl = scanner.nextInt();
            details4Accounting.add(String.valueOf(anzahl));
            scanner.nextLine();
            int skuPosition = iterationCounter;
            ReceiptItem receiptItem = getRecieptItem(pathToReceiptItems,getItemsFromFile,anzahl,skuPosition);
            receiptItemList.add(receiptItem);
            System.out.println();
            System.out.println("Weiteres Produkt kaufen? (j/n): ");
            String furtherItems = scanner.nextLine();
            if (furtherItems.equalsIgnoreCase("n")) {
                int purchaseCount = (getPurchaseCounter() + 1);
                setPurchaseCounter(purchaseCount);
                System.out.println("Dies war heute der " + getPurchaseCounter() + ". Einkauf.");
                abbruch = true;
            }
        }
        return receiptItemList;
    }

    private ReceiptItem getRecieptItem(Path pathToReceiptItems, List<Item> getItemsFromFile, int anzahl, int skuPosition) throws IOException {

        String item = getItemsFromFile.get(skuPosition).getBrand() + ", " + getItemsFromFile.get(skuPosition).getName();
        double ppu = getItemsFromFile.get(skuPosition).getPpu();
        BigDecimal price = new BigDecimal(ppu);
        writeReceiptItemstoFile(pathToReceiptItems,item,ppu,price);
        return new ReceiptItem(item, anzahl, price);
    }

    public Receipt createReceipt() throws IOException {

        Receipt r = new Receipt();
        LocalDateTime lcd = LocalDateTime.now();
        return new Receipt(lcd, shopname, r.getNextValue());
    }

    public void writeReceiptItemstoFile(Path pathToReceiptItems, String receiptItems, double ppu, BigDecimal price) throws IOException {

        if (Files.notExists(pathToReceiptItems)) {
            Files.createFile(pathToReceiptItems);
        }
        String ppu1 = String.valueOf(ppu);
        String price1 = String.valueOf(price);
        String entry =  receiptItems+","+ppu1 +","+price1+ "\n";
        Files.write(
                pathToReceiptItems,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }

    public double getReceiptItemsTotal(List<ReceiptItem> listForReceipt) {

        double total = 0;
        int i = 0;
        while (i < listForReceipt.size()) {
            total += listForReceipt.get(i).getGross();
            i++;
        }
        return total;
    }

    //TODO BigDecimal von total
    public void printReceipt(Receipt receipt, List<ReceiptItem> listOfItemsForReceipt, double total) {

        Scanner scanner = new Scanner(System.in);
        String receiptConverter = receipt.stringify();
        System.out.println(receiptConverter);
        System.out.println();
        for (ReceiptItem item : listOfItemsForReceipt) {

            String returnForSringify = item.stringify();
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


    // AUX
    private String convert() {
        return ";";
    }




    // G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getPurchaseCounter() {
        return purchaseCounter;
    }

    public void setPurchaseCounter(int purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }
}
