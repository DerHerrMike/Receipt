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

public class Driver {

    List<ReceiptItem> listAllReceiptItemsDay = new ArrayList<>();
    protected double tagesumsatz;
    int counter;

    public Driver() {
    }

    public void incTagesumsatz(double receiptTotal){
        tagesumsatz=tagesumsatz+receiptTotal;
    }



    public void incCounter(){
        counter++;
    }
    public int getCounter(){
        return counter;
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Driver driver = new Driver();
        Shop shop = new Shop();
        List<ReceiptItem> aux = new ArrayList<>();
        Path path = Paths.get("output\\items.txt");
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        List<Item> inputListFromFile = new ArrayList<>(Objects.requireNonNull(readAllLines(path)));

        shop.designNameSelection();
        shop.chooseName();
        System.out.println();
        System.out.println();

        while (true) {
            System.out.println();
            System.out.println("WILLKOMMEN BEI " + shop.getShopname() + " - Tools4Pros");
            System.out.println("--------------------------------------");
            System.out.println();
            System.out.println("Items ins Lager hinzufügen = 1");
            System.out.println();
            System.out.println("Items in Lager auflisten = 2");
            System.out.println();
            System.out.println("Items verkaufen = 3");
            System.out.println();
            System.out.println("Buchhaltung aufrufen = 4");
            System.out.println();
            System.out.println("Programm beenden = 9");
            System.out.println();
            System.out.println("Bitte Auswahl treffen: ");
            int auswahl = scanner.nextInt();
            scanner.nextLine();
            switch (auswahl) {
                case 1 -> shop.addItem(path, inputListFromFile);
                case 2 -> shop.displayItems(inputListFromFile);
                case 3 -> {
                    driver.incCounter();
                    List<ReceiptItem> receiptItemListReturned = shop.sellItems(inputListFromFile);
                    for (int j = 0; j < receiptItemListReturned.size(); j++) {
                        aux.add(j, receiptItemListReturned.get(j));
                    }
                    driver.setListAllReceiptItemsDay(aux);// doesn't work

//                  TODO put this in accounting: driver.readoutItem(driver.getListAllReceiptItemsDay());

                    int numberOfItems = 0;
                    for (ReceiptItem item : receiptItemListReturned) {
                        numberOfItems += item.quantity;
                    }
                    double total = shop.getReceiptItemsTotal(receiptItemListReturned);
                    driver.incTagesumsatz(total);
                    Receipt receipt = shop.createReceipt();
                    shop.printReceipt(receipt, receiptItemListReturned, total);
                }

                case 4 -> driver.accountingMenu();
                case 5 -> csvConverter();
                case 9 -> {
                    System.out.println("Das Programm wird beendet!");
                    System.exit(0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + auswahl);
            }
        }
    }


    public void accountingMenu() {

        Shop shop = new Shop();
        System.out.println();
        System.out.println("***********************************************");
        System.out.println("ABRECHNUNG der Firma "+shop.getShopname());
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("Tagesumsatz gesamt: EUR "+getTagesumsatz());
        System.out.println();
        System.out.println("Anzahl der heute getätigten Einkäufe: "+ getCounter());
        System.out.println();
        System.out.println("Die Tagesabrechnung der verkauften Produkte und deren Anzhal :");
//        System.out.println();
//        readoutItem(listAllReceiptItemsDay);
//        System.out.println();
        System.out.println("Tagesumsatz gesamt: EUR "+getTagesumsatz());
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

        System.out.println("Auslesen des return Objektes receipt aus createReceipt:");
        for (ReceiptItem item : receiptItemListReturn) {
            readoutItem(receiptItemListReturn);
            System.out.println(item.stringify());
            System.out.println();
        }
        System.out.println("Auslesen ENde");
    }

    public double getTagesumsatz() {
        return tagesumsatz;
    }

    public List<ReceiptItem> getListAllReceiptItemsDay() {
        return listAllReceiptItemsDay;
    }

    public void setListAllReceiptItemsDay(List<ReceiptItem> listAllReceiptItemsDay) {
        this.listAllReceiptItemsDay = listAllReceiptItemsDay;
    }

    // AUX
    private String convert() {
        return ";";
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
