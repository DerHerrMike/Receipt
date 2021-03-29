import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Item {

    protected String sku;
    protected String brand;
    protected String name;
    protected double ppu;
    List<String> brandsList;
    List<String> countryList;


    public Item(String sku, String brand, String name, double ppu) {
        this.sku = sku;
        this.brand = brand;
        this.name = name;
        this.ppu = ppu;
    }

    // empty constructor to create instance of Item
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


    public Item itemCreator (Path path, Path brands, List<Item> itemsList) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Bitte die Daten des Items eingeben!");
        System.out.println("SKU: ");
        setSku(scanner.nextLine());
        System.out.println("Brand: ");
        setBrand(scanner.nextLine());
        writeBrandsToFile(brands);      //TODO this call adds comma to csv file

        System.out.println("Name:");
        setName(scanner.nextLine());
        System.out.println("Price per Unit: ");
        setPpu(scanner.nextDouble());
        System.out.println("Alle Daten zu diesem Item erfasst!");
        System.out.println();
        Item item = new Item(getSku(), getBrand(), getName(), getPpu());
        itemsList.add(item);
        writeToFile(path);
        System.out.println();
        return item;
    }


    // AUX
    private String convertSimple() {
        return ",";
    }

    public void writeBrandsToFile(Path brands) throws IOException {

        String object = convertSimple();

        if (Files.notExists(brands)) {
            Files.createFile(brands);
        }

        Files.write(
                brands,
                object.getBytes(),
                StandardOpenOption.APPEND);
    }


    private String convert() {
        return getSku() +
                ";" + getBrand()
                +
                ";" +
                getName() +
                ";" +
                getPpu() +
                ";" +
                "\n";
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



