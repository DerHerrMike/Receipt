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

public class LoadData {


    public static List<Item> loadAllItems(Path path) throws IOException {

        BufferedReader reader = null;
        List<Item> itemsExFile = new ArrayList<>();

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
    }


    public static List<Receipt> loadAllReceipts(Path path) throws IOException {

        BufferedReader readerReceipts = null;
        List<Receipt> receiptsExFile = new ArrayList<>();

        String[] ausgeleseneZeileReceipts;
        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {
            try {
                readerReceipts = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = readerReceipts.readLine();
                while (line != null && !line.isEmpty()) {
                    ausgeleseneZeileReceipts = line.split(",");
                    //ShopName
                    String shopNameF = ausgeleseneZeileReceipts[0];
                    //ReceiptNo
                    String receiptNoF = ausgeleseneZeileReceipts[1];
                    //LDT
                    String ldt = ausgeleseneZeileReceipts[2];

                    int receiptNoConvert = Integer.parseInt(receiptNoF);
                    LocalDateTime ldtConvert = LocalDateTime.parse(ldt);


                    Receipt ReceiptObjectExFile = new Receipt(ldtConvert, shopNameF, receiptNoConvert);

                    receiptsExFile.add(ReceiptObjectExFile);
                    line = readerReceipts.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (readerReceipts != null) {
                    readerReceipts.close();
                }
            }
        }
        return receiptsExFile;
    }

    public static List<ReceiptItem> loadAllReceiptsItems(Path path) throws IOException {

        BufferedReader readerReceiptItem = null;
        List<ReceiptItem> receiptItemsExFile = new ArrayList<>();

        if (Files.size(path) < 1) {
            System.out.println("Kein Eintrag in Datei!");
            return null;
        } else {

            try {
                readerReceiptItem = new BufferedReader(new FileReader(String.valueOf(path)));
                String line = readerReceiptItem.readLine();
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
                    line = readerReceiptItem.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (readerReceiptItem != null) {
                    readerReceiptItem.close();
                }
            }
        }
        return receiptItemsExFile;
    }

}



