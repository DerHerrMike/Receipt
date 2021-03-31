import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Item {

    private String sku;
    private String brand;
    private String name;
    private double ppu;


    public Item(String sku, String brand, String name, double ppu) {
        this.sku = sku;
        this.brand = brand;
        this.name = name;
        this.ppu = ppu;
    }

    public Item() {

    }
    public int defIterator() {

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Wie viele Items wollen Sie hinzufügen: ");
        int units = scanner.nextInt();
        scanner.nextLine();
        return units;
    }

    public void addItem(Path path, Path brands, Path itemPath, List<Item> getItemsFromFile) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Wie viele Items wollen Sie hinzufügen: ");
        int units = scanner.nextInt();
        scanner.nextLine();
        Item item = new Item();
        int i = 0;
        while (i < units) {
            Item insert = item.itemCreator(path, brands, itemPath);
            getItemsFromFile.add(insert);
            item.writeToFile(path); //THIS is to .txt file
            i++;
        }
        System.out.println("Alle Items hinzugefügt und in Datei geschrieben!");
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
        scanner.nextLine();
    }

    public Item itemCreator(Path path, Path pathToBrands, Path pathToItem) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Bitte die Daten des Items eingeben!");
        System.out.println("SKU: ");
        setSku(scanner.nextLine());
        System.out.println("Brand: ");
        setBrand(scanner.nextLine());
        writeBrandsToFile(pathToBrands, brand);
        System.out.println("Name:");
        setName(scanner.nextLine());
        writeItemsToFile(pathToItem, name);     // THIS is to .csv file
        System.out.println("Price per Unit: ");
        setPpu(scanner.nextDouble());
        System.out.println("Alle Daten zu diesem Item erfasst!");
        System.out.println();
        Item item = new Item(getSku(), getBrand(), getName(), getPpu());
        writeToFile(path);  // This is for CSV
        System.out.println();
        return item;
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

    /* This is to TXT file */ public void writeToFile(Path path) throws IOException {

        String object = convert();

        if (Files.notExists(path)) {
            Files.createFile(path);
        }

        Files.write(
                path,
                object.getBytes(),
                StandardOpenOption.APPEND);
    }

    /* AUX for writeToFile */ private String convert() {
        return getSku() +
                "," + getBrand()
                +
                "," +
                getName() +
                "," +
                getPpu() +
                "\n";
    }

    /* THIS IS FOR CSV */  public void writeItemsToFile(Path pathToItem, String item) throws IOException {

        if (Files.notExists(pathToItem)) {
            Files.createFile(pathToItem);
        }
        String entry = item + "\n";
        Files.write(
                pathToItem,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }

    public void writeBrandsToFile(Path pathToBrand, String brand) throws IOException {

        if (Files.notExists(pathToBrand)) {
            Files.createFile(pathToBrand);
        }
        String entry = brand + "\n";
        Files.write(
                pathToBrand,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }



    // G& S
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPpu() {
        return ppu;
    }

    public void setPpu(double ppu) {
        this.ppu = ppu;
    }
}



