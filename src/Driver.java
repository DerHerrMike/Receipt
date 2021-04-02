
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
            int auswahl = 0;
            boolean correctSelection = false;
            while (!correctSelection) {

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
                String selection = scanner.nextLine();
                if (selection.equals("1") || selection.equals("2") || selection.equals("3") || selection.equals("4") || selection.equals("5") || selection.equals("9")) {
                    auswahl = Integer.parseInt(selection);
                    correctSelection = true;
                } else {
                    System.out.println("Ungültige Auswahl getroffen! Weiter mit beliebiger Taste!");
                    scanner.nextLine();
                    System.out.println();
                }
            }
            switch (auswahl) {

                case 1 -> item.addItem(pathItemsToTXT, pathBrandsToCSV, pathItemsToCSV, listWithLoadedItemsAvailable);
                case 2 -> {
                    assert listWithLoadedItemsAvailable != null;
                    item.displayItems(listWithLoadedItemsAvailable);
                }
                case 3 -> {
                    if (listWithLoadedItemsAvailable == null) {
                        System.out.println("Zur Zeit befinden sich keine Produkte im Lager. Bitte zunächst im Menü mit '1' Items hinzufügen!");
                        System.out.println("Zurück mit beliebiger Taste!");
                        scanner.nextLine();
                    }

                    List<ReceiptItem> receiptItemListReturned = shop.sellItems(listWithLoadedItemsAvailable, rI);
                    listAllReceiptItemsDay.addAll(receiptItemListReturned);

                    double total = rI.getReceiptItemsTotal(receiptItemListReturned);
                    double averageReVa = receipt.calculateAverageReceiptsValue(total);
                    averageReceiptVaDayList.add(averageReVa);
                    tagesumsatz = accounting.incTagesumsatz(tagesumsatz, total);
                    Receipt newReceipt = receipt.createReceipt(receipt);
                    int receiptNumber = newReceipt.getReceiptNumber();
                    String shopName = newReceipt.getShopname();
                    String lcd = newReceipt.getTimestamp();
                    writeReceiptsToFile(pathReceiptsToCSV, receiptNumber, shopName, lcd);
                    receipt.printReceipt(shop, newReceipt, receiptItemListReturned, total, item);
                    counter++;
                }
                case 4 -> accounting.accountingMenu(shop, listAllReceiptItemsDay, counter, tagesumsatz, averageReceiptVaDayList, item);
                case 5 -> {
                    System.out.println();
                    listWithLoadedItemsAvailable=item.deleteItem(pathItemsToTXT,pathBrandsToCSV,pathItemsToCSV,listWithLoadedItemsAvailable);
                }
                case 7 -> System.out.println("Du hast das geheime Menü gefunden! Code: X5C7");
                case 9 -> {
                    System.out.println("Das Programm wird beendet!");
                    System.exit(0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + auswahl);
            }
        }
    }

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

}
