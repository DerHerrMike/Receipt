
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {


    public static void main(String[] args) throws IOException {


        Shop shop = new Shop();
        Item item = new Item();
        Receipt receipt = new Receipt();
        ReceiptItem rI = new ReceiptItem();
        Accounting accounting = new Accounting();
        Scanner scanner = new Scanner(System.in);

        Path pathItemsToTXT = Paths.get("output\\items.txt");
        if (Files.notExists(pathItemsToTXT)) {
            Files.createFile(pathItemsToTXT);
        }
        Path pathItemsToCSV = Paths.get("output\\items.csv");
        if (Files.notExists(pathItemsToCSV)) {
            Files.createFile(pathItemsToCSV);
        }
        Path pathBrandsToCSV = Paths.get("output\\brands.csv");
        if (Files.notExists(pathBrandsToCSV)) {
            Files.createFile(pathBrandsToCSV);
        }
        Path pathReceiptsToCSV = Paths.get("output\\receipts.csv");
        if (Files.notExists(pathBrandsToCSV)) {
            Files.createFile(pathBrandsToCSV);
        }

        List<Item> listWithLoadedItemsAvailable = LoadData.loadAllItems(pathItemsToTXT); //filled with all data from file to show availabe items;
//        List<Receipt> listWithLoadedReceipts = LoadData.loadAllReceipts(pathReceiptsToCSV);
//        List<ReceiptItem> listWithLoadedReceiptItems = LoadData.loadAllReceiptsItems(pathReceiptsToCSV);
        List<Double> averageReceiptVaDayList = new ArrayList<>();
        List<ReceiptItem> listAllReceiptItemsDay = new ArrayList<>();
        double tagesumsatz = 0;
        int counter = 0;

        shop.designNameSelection();
        shop.chooseName();
        System.out.println();
        System.out.println();

        while (true) {
            System.out.println();
            System.out.println("WILLKOMMEN BEI " + shop.getShopname() + " - Tools4Pros");
            String output = """
                                        
                     ---------------------------------------
                    |                                       | 
                    |    Items ins Lager hinzufügen = 1     |    
                    |    Items in Lager auflisten = 2       |
                    |    Items verkaufen = 3                |
                    |    Buchhaltung aufrufen = 4           |
                    |    Programm beenden = 9               |
                    |                                       |
                    |    Bitte Auswahl treffen!             |
                    |                                       |
                     ---------------------------------------
                    """;
            System.out.println(output);
            int auswahl = scanner.nextInt();
            scanner.nextLine();
            switch (auswahl) {

                case 1 -> item.addItem(pathItemsToTXT, pathBrandsToCSV, pathItemsToCSV, listWithLoadedItemsAvailable);
                case 2 -> {
                    assert listWithLoadedItemsAvailable != null;
                    item.displayItems(listWithLoadedItemsAvailable);
                }
                case 3 -> {
                    assert listWithLoadedItemsAvailable != null;
                    List<ReceiptItem> receiptItemListReturned = shop.sellItems(listWithLoadedItemsAvailable, rI);
                    listAllReceiptItemsDay.addAll(receiptItemListReturned);

                    double total = rI.getReceiptItemsTotal(receiptItemListReturned);
                    double averageReVa = receipt.calculateAverageReceiptsValue(total);
                    averageReceiptVaDayList.add(averageReVa);
                    tagesumsatz = accounting.incTagesumsatz(tagesumsatz, total);
                    Receipt newReceipt = receipt.createReceipt();
                    int receiptNumber = newReceipt.getReceiptNumber();
                    String shopName = newReceipt.getShopname();
                    String lcd = newReceipt.getTimestamp();
                    writeReceiptsToFile(pathReceiptsToCSV, receiptNumber, shopName, lcd);
                    receipt.printReceipt(shop,newReceipt, receiptItemListReturned, total);
                    counter++;
                }
                case 4 -> accounting.accountingMenu(shop, listAllReceiptItemsDay, counter, tagesumsatz, averageReceiptVaDayList);
                case 5 -> System.out.println("Du hast das geheime Menü gefunden!");
                case 9 -> {
                    System.out.println("Das Programm wird beendet!");
                    System.exit(0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + auswahl);
            }
        }
    }

//    public double incTagesumsatz(double tagesumsatz, double receiptTotal) {
//        return tagesumsatz + receiptTotal;
//    }

    public static void writeReceiptsToFile(Path receiptToFile, int receiptNumber, String shopName, String lcd) throws IOException {

        if (Files.notExists(receiptToFile)) {
            Files.createFile(receiptToFile);
        }
        String number = String.valueOf(receiptNumber);
        String entry = shopName + "," + number + "," + lcd + "\n";
        Files.write(
                receiptToFile,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }

//    public void accountingMenu(Shop shop, List<ReceiptItem> listAllReceiptItemsDay, int counter, double tagesumsatz, List<Double> averageReceiptVaDayList) {
//        System.out.println();
//        System.out.println("***********************************************");
//        System.out.println("ABRECHNUNG der Firma " + shop.getShopname());
//        System.out.println("***********************************************");
//        System.out.println();
//        System.out.println();
//        System.out.println("Anzahl der heute getätigten Einkäufe: " + counter);
//        System.out.println();
//        System.out.println("Die Tagesabrechnung der verkauften Produkte und deren Anzahl :");
//        System.out.println();
//        readoutItem(listAllReceiptItemsDay);
//        System.out.println();
//        System.out.println("Der Tagesumsatz gesamt beträgt: EUR " + tagesumsatz);
//        System.out.println();
//        double allValues = 0.0f;
//        for (Double aDouble : averageReceiptVaDayList) {
//            allValues += aDouble;
//        }
//        double aux = Math.pow(10, 2);
//        double average = Math.round((allValues / averageReceiptVaDayList.size()) * aux) / aux;
//        System.out.println("Die durchschnittliche Rechnungssumme des Tages beträgt EUR: " + average);
//        System.out.println();
//        averageReceiptVaDayList.sort(null);
//        int listlastpos = (averageReceiptVaDayList.size() - 1);
//        System.out.println("Die höchste Rechnung des Tages betrug EUR: " + averageReceiptVaDayList.get(listlastpos));
//        System.out.println();
//        System.out.println("Die niedrigste Rechnung des Tages betrug EUR: " + averageReceiptVaDayList.get(0));
//        System.out.println();
//        System.out.println("---TAGESUMSATZLISTE ENDE---");
//        System.out.println();
//        System.out.println("Zurück zum Menü  mit beliebiger Taste");
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
//    }
//
//    public void readoutItem(List<ReceiptItem> receiptItemListReturn) {
//
//        for (ReceiptItem item : receiptItemListReturn) {
//            System.out.println(item.stringify());
//            System.out.println();
//        }
//    }


}
