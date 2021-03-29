import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.opencsv.CSVWriter;


//TODO String.format

public class Driver {




    public static void main(String[] args) throws IOException {
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
        List<Item> inputListFromFile = new ArrayList<>(Objects.requireNonNull(readAllLines(path)));

        Shop shop = new Shop();
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
                                        
                    --------------------------------------
                    Items ins Lager hinzufügen = 1
                    Items in Lager auflisten = 2
                    Items verkaufen = 3
                    Buchhaltung aufrufen = 4
                    Programm beenden = 9
                    Bitte Auswahl treffen: 
                    """;
            System.out.println(output);
            int auswahl = scanner.nextInt();
            scanner.nextLine();
            switch (auswahl) {
                case 1 -> shop.addItem(path, brands, inputListFromFile);
                case 2 -> shop.displayItems(inputListFromFile);
                case 3 -> {
                    List<ReceiptItem> receiptItemListReturned = shop.sellItems(inputListFromFile);
                    listAllReceiptItemsDay.addAll(receiptItemListReturned);
                    int numberOfItems = 0;
                    for (ReceiptItem item : receiptItemListReturned) {
                        numberOfItems += item.getQuantity();
                    }
                    double total = shop.getReceiptItemsTotal(receiptItemListReturned);
                    tagesumsatz = driver.incTagesumsatz(tagesumsatz, total);
                    Receipt receipt = shop.createReceipt();
                    shop.printReceipt(receipt, receiptItemListReturned, total);
                    counter++;
                }

                case 4 -> driver.accountingMenu(shop, listAllReceiptItemsDay, counter, tagesumsatz);
                case 5 -> csvConverter();
                case 9 -> {
                    System.out.println("Das Programm wird beendet!");
                    System.exit(0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + auswahl);
            }
        }
    }

    public String getNameShop() {
        Shop shop = new Shop();

        return shop.getShopname();
    }

    public void accountingMenu(Shop shop, List<ReceiptItem> listAllReceiptItemsDay, int counter, double tagesumsatz) {
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
        System.out.println("");
        System.exit(0);

    }


    public static void csvConverter() throws IOException {

        List<String[]> csvData = createCsvDataSimple();

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("output\\test.csv"))) {
            writer.writeAll(csvData);
        }
    }

    private static List<String[]> createCsvDataSimple() {

        String[] header = {"id", "name", "address", "phone"};
        String[] record1 = {"1", "first name", "address 1", "11111"};
        String[] record2 = {"2", "second name", "address 2", "22222"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);

        return list;
    }

    public void readoutItem(List<ReceiptItem> receiptItemListReturn) {

        for (ReceiptItem item : receiptItemListReturn) {
            System.out.println(item.stringify());
            System.out.println();
        }
        System.out.println("---TAGESUMSATZLISTE ENDE---");
    }


    public double incTagesumsatz(double tagesumsatz, double receiptTotal) {
        return  tagesumsatz + receiptTotal;
    }


    // AUX
    private String convert() {
        return ",";
    }

    public void writeToFile(Path path) throws IOException {

        String object = convert();

        if (Files.notExists(path)) {
            Files.createFile(path);
        }

        Files.write(
                path,
                object.getBytes(),
                StandardOpenOption.APPEND);
    }

    public static List<Item> readAllLines(Path path) throws IOException {

        BufferedReader reader;
        List<Item> itemsExFile = new ArrayList<>();

        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {

            try {
                reader = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = reader.readLine();
                while (line != null) {
                    String[] ausgeleseneZeile = line.split(";");
                    //SKU
                    String skuF = ausgeleseneZeile[0];
                    //BRAND
                    String brandF = ausgeleseneZeile[1];
                    //NAME
                    String nameF = ausgeleseneZeile[2];
                    //PPU
                    double ppuF = Double.parseDouble(ausgeleseneZeile[3]);
                    Item objectExFile = new Item(skuF, brandF, nameF, ppuF);
                    itemsExFile.add(objectExFile);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return itemsExFile;
    }
}
