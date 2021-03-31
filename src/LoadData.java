import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadData {


   public static List<Item> loadAllItems() throws IOException {

        BufferedReader reader = null;
        List<Item> itemsExFile = new ArrayList<>();
        Path path = Paths.get("output\\items.txt");
        String[] ausgeleseneZeile;
        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {
            try {
                reader = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = reader.readLine();
                while (line != null && !line.isEmpty()) {
                    ausgeleseneZeile = line.split(",");
                    //SKU
                    String skuF = ausgeleseneZeile[0];
                    System.out.println(skuF);
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

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }

            }
        }
        return itemsExFile;
    }       //depends on readAllLines

    static List<Receipt> loadAllReceipts(Path path) throws IOException {

        BufferedReader reader;
        List<Receipt> receiptsExFile = new ArrayList<>();

        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {

            try {
                reader = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = reader.readLine();
                while (line != null) {
                    String[] ausgeleseneZeile = line.split(",");
                    //ShopName
                    String shopNameF = ausgeleseneZeile[0];
                    //ReceiptNo
                    String receiptNoF = ausgeleseneZeile[1];
                    //LDT
                    String ldt = ausgeleseneZeile[2];

                    LocalDateTime ldtConvert = LocalDateTime.parse(ldt);
                    int receiptNoConvert = Integer.parseInt(receiptNoF);

                    Receipt ReceiptObjectExFile = new Receipt(ldtConvert, shopNameF, receiptNoConvert);

                    receiptsExFile.add(ReceiptObjectExFile);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receiptsExFile;
    }

    static List<ReceiptItem> loadAllReceiptsItems(Path path) throws IOException {

        BufferedReader reader;
        List<ReceiptItem> receiptItemsExFile = new ArrayList<>();

        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {

            try {
                reader = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = reader.readLine();
                while (line != null) {
                    String[] ausgeleseneZeile = line.split(",");
                    //Brand
                    String brandF = ausgeleseneZeile[0];
                    //Item
                    String itemF = ausgeleseneZeile[1];
                    String brandItem = brandF + " " + itemF;
                    //ppu
                    double ppu = Double.parseDouble(ausgeleseneZeile[2]);
                    BigDecimal ppuBigDecimal = new BigDecimal(ppu);
                    //Gross
                    String gross = ausgeleseneZeile[3];
                    int grossF = Integer.parseInt(gross);
                    //Quantity
                    int quantity = (int) (grossF / ppu);

                    ReceiptItem ReceiptItemObjectExFile = new ReceiptItem(brandItem, quantity, ppuBigDecimal);

                    receiptItemsExFile.add(ReceiptItemObjectExFile);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receiptItemsExFile;
    }

}



