import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Driver {


    public Driver() {
    }


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Shop shop = new Shop();
        Accounting accounts = new Accounting();
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
            System.out.println("WILLKOMMEN BEI " + shop.getShopname());
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
            switch (auswahl) {
                case 1 -> shop.addItem(path,inputListFromFile);
                case 2 -> shop.listItems(inputListFromFile);
                case 3 -> {
                    shop.sellItems(inputListFromFile);
                    shop.createReceipt(shop.getNumberOfItems(),shop.sellItems(inputListFromFile));
                    Receipt returnedReceipt = shop.createReceipt(shop.getNumberOfItems(),shop.sellItems(inputListFromFile));
                            shop.listOfReceipts.add(returnedReceipt);
                }
                case 4 -> accounts.accounting();
                case 9 -> {
                    System.out.println("Das Programm wird beendet!");
                    System.exit(0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + auswahl);
            }
        }
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
