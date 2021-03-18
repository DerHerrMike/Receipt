import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
    // empty constructor to create instance of Item
    public Item() {
    }

    private int defIterator(){

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Wie viele Items wollen Sie hinzufügen: ");
        int units = scanner.nextInt();
        scanner.nextLine();
        return units;
    }


    private Item itemCreator(Path path, List<Item> itemsList) throws IOException {

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("Bitte die Daten des Items eingeben!");
            System.out.println("SKU: ");
            sku = scanner.nextLine();
            System.out.println("Brand: ");
            brand = scanner.nextLine();
            System.out.println("Name:" );
            name = scanner.nextLine();
            System.out.println("Price per Unit: ");
            ppu = scanner.nextDouble();
            System.out.println("Alle Daten zu diesem Item erfasst!");
            System.out.println();
            Item item = new Item(sku, brand, name, ppu);
            itemsList.add(item);
            item.writeToFile(path);
            System.out.println("Items in Datei "+path+" geschrieben!");
            System.out.println();
        return item;
    }

    public static void main(String[] args) throws IOException {

        List<Item> inputList = new ArrayList<>();
        List<Item> itemsFile = new ArrayList<>();
//        Files.createFile(Path.of("output\\items.txt"));       HOW DOES IT WORK?
        Path path = Paths.get("output\\items.txt");
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Item item = new Item();
        int units= item.defIterator();
        for ( int i=0;i<units;i++){
            item.itemCreator(path, inputList);
        }
        for ( int i=0;i<units;i++){
            System.out.println(inputList.get(i).getSku());
            System.out.println(inputList.get(i).getBrand());
            System.out.println(inputList.get(i).getName());
            System.out.println(inputList.get(i).getPpu());
            System.out.println();
        }




    }

    // AUX
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



