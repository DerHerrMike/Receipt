import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<ReceiptItem> sellItems(List<Item> getItemsFromFile, ReceiptItem rI) throws IOException {

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
            details4Accounting.add(String.valueOf(anzahl)); //TODO here I have sku and quantity in String List details4accounting
            scanner.nextLine();

            int skuPosition = iterationCounter;
            ReceiptItem receiptItem = rI.createOneReceiptItem(pathToReceiptItems, getItemsFromFile, anzahl, skuPosition);
            //61 also calls writeOneReceiptItemtoFile
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

//    private ReceiptItem createOneReceiptItem(Path pathToReceiptItems, List<Item> getItemsFromFile, int anzahl, int skuPosition) throws IOException {
//
//        String item = getItemsFromFile.get(skuPosition).getBrand() + ", " + getItemsFromFile.get(skuPosition).getName();
//        double ppu = getItemsFromFile.get(skuPosition).getPpu();
//        double grossThisReceiptItem = ppu * anzahl;
//        BigDecimal bigDecimalPPU = new BigDecimal(ppu);
//        BigDecimal bigDecGrossThisRecItem = new BigDecimal(grossThisReceiptItem); //Price PPU not changed to gross
//
//        writeOneReceiptItemtoFile(pathToReceiptItems, item, bigDecimalPPU, anzahl, bigDecGrossThisRecItem);
//        return new ReceiptItem(item, anzahl, bigDecimalPPU);
//    }
//
//
//    public void writeOneReceiptItemtoFile(Path pathToReceiptItems, String oneReceiptItem, BigDecimal ppu, int anzahl, BigDecimal bigDecGrossThisRecItem) throws IOException {
//
//        if (Files.notExists(pathToReceiptItems)) {
//            Files.createFile(pathToReceiptItems);
//        }
//        String ppu1 = String.valueOf(ppu);
//        String grossConvert = String.valueOf(bigDecGrossThisRecItem);//TODO  total or gross?
//        String anzahl1 = String.valueOf(anzahl);
//        String entry = oneReceiptItem + "," + ppu1 + "," + anzahl1 + "," + grossConvert + "\n";       // receiptItem = ITEM+BRAND
//        Files.write(
//                pathToReceiptItems,
//                entry.getBytes(),
//                StandardOpenOption.APPEND);
//    }

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
