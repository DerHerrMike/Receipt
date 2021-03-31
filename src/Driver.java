
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver  {




    public static void main(String[] args) throws IOException {

        List<Double> averageReceiptVaDayList = new ArrayList<>();
        double tagesumsatz = 0;
        List<ReceiptItem> listAllReceiptItemsDay = new ArrayList<>();
        Driver driver = new Driver();
        //   List<ReceiptItem> aux = new ArrayList<>();
        Path brands = Paths.get("output\\brands.csv");
        if (Files.notExists(brands)) {
            Files.createFile(brands);
        }
        Path path = Paths.get("output\\items.txt");
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Path items = Paths.get("output\\items.csv");
        if (Files.notExists(brands)) {
            Files.createFile(brands);
        }
        Path receiptsToFile = Paths.get("output\\receipts.csv");
        if (Files.notExists(brands)) {
            Files.createFile(brands);
        }
        List<Item> listWithLoadedItemsAvailable = LoadData.loadAllItems(); //filled with all data from file to show availabe items;

        Shop shop = new Shop();
        Item item = new Item();
        Receipt receipt = new Receipt();
        ReceiptItem rI = new ReceiptItem();
        shop.designNameSelection();
        shop.chooseName();
        System.out.println();
        System.out.println();

        int counter = 0;
        Scanner scanner = new Scanner(System.in);
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
                    |    Bitte Auswahl treffen:             |
                     ---------------------------------------
                    """;
            System.out.println(output);
            int auswahl = scanner.nextInt();
            scanner.nextLine();
            switch (auswahl) {
                case 1 -> item.addItem(path, brands, items,listWithLoadedItemsAvailable);
                case 2 -> {
                    assert listWithLoadedItemsAvailable != null;
                    item.displayItems(listWithLoadedItemsAvailable);
                }
                case 3 -> {

                    assert listWithLoadedItemsAvailable != null;
                    List<ReceiptItem> receiptItemListReturned = shop.sellItems(listWithLoadedItemsAvailable,rI);
                    listAllReceiptItemsDay.addAll(receiptItemListReturned);

                    double total = rI.getReceiptItemsTotal(receiptItemListReturned);
                    double averageReVa = receipt.calculateAverageReceiptsValue(total);
                    averageReceiptVaDayList.add(averageReVa);

                    tagesumsatz = driver.incTagesumsatz(tagesumsatz, total);

                    Receipt newReceipt = receipt.createReceipt();

                    int receiptNumber = newReceipt.getReceiptNumber();
                    String shopName = newReceipt.getShopname();
                    String lcd = newReceipt.getTimestamp();
                    writeReceiptsToFile(receiptsToFile,receiptNumber,shopName,lcd);
                    receipt.printReceipt(newReceipt, receiptItemListReturned, total);
                    counter++;
                }

                case 4 -> driver.accountingMenu(shop, listAllReceiptItemsDay, counter, tagesumsatz,averageReceiptVaDayList);
                case 5 -> System.out.println("Case 5");
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
        String entry =  shopName+","+number +","+lcd+ "\n";
        Files.write(
                receiptToFile,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }

    public void accountingMenu(Shop shop, List<ReceiptItem> listAllReceiptItemsDay, int counter, double tagesumsatz, List<Double> averageReceiptVaDayList) {
        System.out.println();
        System.out.println("***********************************************");
        System.out.println("ABRECHNUNG der Firma " + shop.getShopname());
        System.out.println("***********************************************");
        System.out.println();
        System.out.println();
        System.out.println("Anzahl der heute getätigten Einkäufe: " + counter);
        System.out.println();
        System.out.println("Die Tagesabrechnung der verkauften Produkte und deren Anzahl :");
        System.out.println();
        readoutItem(listAllReceiptItemsDay);
        System.out.println();
        System.out.println("Der Tagesumsatz gesamt beträgt: EUR " + tagesumsatz);
        System.out.println();
        double allValues = 0.0f;
        for (Double aDouble : averageReceiptVaDayList) {
            allValues += aDouble;
        }
        double aux = Math.pow(10,2);
        double average = Math.round((allValues/averageReceiptVaDayList.size())*aux)/aux;
        System.out.println("Die durchschnittliche Rechnungssumme des Tages beträgt EUR: "+average);
        System.out.println();
        averageReceiptVaDayList.sort(null);
        int listlastpos = (averageReceiptVaDayList.size()-1);
        System.out.println("Die höchste Rechnung des Tages betrug EUR: "+averageReceiptVaDayList.get(listlastpos));
        System.out.println();
        System.out.println("Die niedrigste Rechnung des Tages betrug EUR: "+averageReceiptVaDayList.get(0));
        System.out.println();
        System.out.println("---TAGESUMSATZLISTE ENDE---");
        System.out.println();
        System.out.println("Zurück zum Menü  mit beliebiger Taste");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void readoutItem(List<ReceiptItem> receiptItemListReturn) {

        for (ReceiptItem item : receiptItemListReturn) {
            System.out.println(item.stringify());
            System.out.println();
        }
    }

    public double incTagesumsatz(double tagesumsatz, double receiptTotal) {
        return  tagesumsatz + receiptTotal;
    }

//    public static List<Item> readAllLines(Path path) throws IOException {
//
//        BufferedReader reader;
//        List<Item> itemsExFile = new ArrayList<>();
//
//        if (Files.size(path) < 1) {
//            System.out.println("Kein Eintrag in Datei!");
//            return null;
//        } else {
//
//            try {
//                reader = new BufferedReader(new FileReader(String.valueOf(path)));
//                String line = reader.readLine();
//                while (line != null) {
//                    String[] ausgeleseneZeile = line.split(",");
//                    //SKU
//                    String skuF = ausgeleseneZeile[0];
//                    //BRAND
//                    String brandF = ausgeleseneZeile[1];
//                    //NAME
//                    String nameF = ausgeleseneZeile[2];
//                    //PPU
//                    double ppuF = Double.parseDouble(ausgeleseneZeile[3]);
//                    Item objectExFile = new Item(skuF, brandF, nameF, ppuF);
//                    itemsExFile.add(objectExFile);
//                    line = reader.readLine();
//                }
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return itemsExFile;
//    }


}
