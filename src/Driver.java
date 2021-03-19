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


// TODO read items from list so that more items can be printed on receipts, create shopping, finish to part one end.

public class Driver {

    public static void main(String[] args) throws IOException {

        Item item = new Item();
        LocalDateTime lcd = LocalDateTime.now();
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

                int units = item.defIterator();
                for (int i = 0; i < units; i++) {
                    item.itemCreator(path, inputList);
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
                shopping(inputList, shopname);

            }
        }

        Receipt r = new Receipt(lcd, shopname, 1);
        ReceiptItem positionOnReceipt = new ReceiptItem(item.getName(), 2, item.getPpu());
        String rConvert = r.stringify();
        String porConvert = positionOnReceipt.stringify();
        System.out.println(rConvert);
        System.out.println();
        System.out.println(porConvert);
    }

private static void displayItemsOnStorage(List<Item> inputList){

    System.out.println("Items auf Lager: ");
    System.out.println();

    for (int j = 0; j < inputList.size(); j++) {
        System.out.println("Item ID: " + (j + 1));
        System.out.print("Brand: " + inputList.get(j).getBrand() + " || ");
        System.out.print("Name: " + inputList.get(j).getName() + " || ");
        System.out.println("Stückpreis EUR: " + inputList.get(j).getPpu());
        System.out.println();
    }
    System.out.println("Alle Items auf Lager ausgegeben.");
}

    private static void shopping(List<Item> inputList, String shopname) {

        ReceiptItem itemsOnReceipt = new ReceiptItem();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Willkommen bei " + shopname + " - Tools4Pros");
        System.out.println();
        System.out.println("Folgende Produkte sind aktuell verfügbar:");
        System.out.println();
        for (int j = 0; j < inputList.size(); j++) {
            System.out.println("Item ID: " + (j + 1));
            System.out.print("Brand: " + inputList.get(j).getBrand() + " || ");
            System.out.print("Name: " + inputList.get(j).getName() + " || ");
            System.out.println("Stückpreis EUR: " + inputList.get(j).getPpu());
            System.out.println();
        }
        System.out.println("Welches Produkt möchtest du kaufen? Bitte ID eingeben: ");
        int id = scanner.nextInt();
        System.out.println();
        System.out.println("Bitte gewünschte Anzahl eingeben: ");
        itemsOnReceipt.setQuantity(scanner.nextInt());
        scanner.nextLine();

    }

    private static int userSelect() {

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

}
