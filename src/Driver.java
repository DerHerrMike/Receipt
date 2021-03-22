import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Driver {

    public static void main(String[] args) throws IOException {

        Item itemBlank = new Item();

        Scanner scanner = new Scanner(System.in);
//      Files.createFile(Path.of("output\\items.txt"));       HOW DOES IT WORK?
        Path path = Paths.get("output\\items.txt");
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        List<Item> inputList = new ArrayList<>(Objects.requireNonNull(readAllLines(path)));
        System.out.println("Bitte Shopname festlegen: ");
        String shopname = scanner.nextLine();
        System.out.println();
        switch (userSelect()) {

            case 1: {

                int units = itemBlank.defIterator();
                for (int i = 0; i < units; i++) {
                    itemBlank.itemCreator(path, inputList);
                }
                System.out.println("Alle Items hinzugefügt!");
                System.out.println();
                System.out.println("Zurück zur Auswahl mit 1, Programm beenden mit beliebiger Taste: ");
                if (scanner.nextLine().equals("1")) {
                    userSelect();
                } else {
                    System.out.println("--- Programm wird beendet ---");
                    System.exit(0);
                    break;
                }
            }
            case 2: {
                displayItemsOnStorage(inputList);

                System.out.println("Zurück zur Auswahl mit 1, Programm beenden mit beliebiger Taste: ");
                if (scanner.nextLine().equals("1")) {
                    userSelect();
                } else {
                    System.out.println("--- Programm wird beendet ---");
                    System.exit(0);
                    break;
                }
            }
            case 3: {

                List<ReceiptItem> returned = shopping(inputList, shopname);
                int numberOfItemsPurchased = returned.size();
                System.out.println("numberOfItemsPurchased = " + numberOfItemsPurchased + " vor aufruf createREceipt");
                createReceipt(returned, shopname, numberOfItemsPurchased);

            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + userSelect());
        }
    }

    private static void createReceipt(List<ReceiptItem> shoppingList, String shopname, int numberOfItemsPurchased) {

        LocalDateTime lcd = LocalDateTime.now();
        int receiptPositionCounter = 1;
        int position = 0;
        Receipt r = new Receipt(lcd, shopname, receiptPositionCounter);
        String rConvert = r.stringify();
        System.out.println(rConvert);
        System.out.println();

        for (int i = 0; i < numberOfItemsPurchased; i++) {
            ReceiptItem pos = new ReceiptItem(shoppingList.get(position).getItem(), shoppingList.get(position).getQuantity(), shoppingList.get(position).getPrice());
            String porConvert = pos.stringify();
            System.out.println(porConvert);
            position++;
        }

        System.out.println("Ende");
    }


    private static List<ReceiptItem> shopping(List<Item> inputList, String shopname) {

        ReceiptItem item4ShoppingList = new ReceiptItem();
        List<ReceiptItem> shoppingList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Willkommen bei " + shopname + " - Tools4Pros");
        System.out.println();
        System.out.println("Folgende Produkte sind aktuell verfügbar:");
        System.out.println();
        for (Item item : inputList) {
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
            for (int i = 0; i < inputList.size(); i++) {
                String compareSKU = inputList.get(iterationCounter).getSku();
                if (!compareSKU.equalsIgnoreCase(selectedSKU)) {
                    iterationCounter++;
                }
            }
            System.out.println();
            System.out.println("Bitte gewünschte Anzahl eingeben: ");
            int anzahl = scanner.nextInt();
            scanner.nextLine();
            int skuPosition = iterationCounter;
            item4ShoppingList.setItem(inputList.get(skuPosition).getBrand() + ", " + inputList.get(skuPosition).getName());
            item4ShoppingList.setPrice(inputList.get(skuPosition).getPpu());
            String selectedItemBrandName = inputList.get(skuPosition).getBrand() + ", " + inputList.get(skuPosition).getName();
            double selectedItemPrice = inputList.get(skuPosition).getPpu();
//        item4ShoppingList.setItem(selectedItemBrandName);
//        item4ShoppingList.setPrice(selectedItemPrice);
            System.out.println();
            ReceiptItem bought = new ReceiptItem(selectedItemBrandName, anzahl, selectedItemPrice);
            shoppingList.add(bought);           //for one item test
            System.out.println("Weiteres Produkt kaufen? (j/n): ");
            String furtherItems = scanner.nextLine();
            if (furtherItems.equalsIgnoreCase("n")) {
                abbruch = true;
            }
        }
        System.out.println(shoppingList.size());
        return shoppingList;
    }

        private static void displayItemsOnStorage (List < Item > inputList) {

            System.out.println("Items auf Lager: ");
            System.out.println();

            for (Item item : inputList) {
                System.out.print("SKU: " + item.getSku() + " || ");
                System.out.print("Brand: " + item.getBrand() + " || ");
                System.out.print("Name: " + item.getName() + " || ");
                System.out.println("Stückpreis EUR: " + item.getPpu());
                System.out.println();
            }
            System.out.println("Alle Items auf Lager ausgegeben.");
        }

        private static int userSelect () {

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("Was möchtest du tun?");
            System.out.println();
            System.out.println("1 für hinzufügen von Items ins Lager, 2 um Items auf Lager abzufragen, 3 um Items zu kaufen, 0 um Programm zu beenden: ");
            String auswahlU = scanner.nextLine();
            int auswahl = Integer.parseInt(auswahlU);
            if (auswahlU.equals("0")) {
                System.out.println("--- Programm wird beendet ---");
                System.exit(0);
            } else

                System.out.println();
            return auswahl;
        }

        public static List<Item> readAllLines (Path path) throws IOException {

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

        // AUX
        private String convert () {
            return ";";
        }

        public void writeToFile (Path path) throws IOException {

            String object = convert();

            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            Files.write(
                    path,
                    object.getBytes(),
                    StandardOpenOption.APPEND);
        }

    }
