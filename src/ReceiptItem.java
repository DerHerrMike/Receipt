import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ReceiptItem {

    private String item;
    private int quantity;
    private BigDecimal price;

    public ReceiptItem(String item, int quantity, BigDecimal price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price.setScale(2, RoundingMode.HALF_UP );
    }

    public ReceiptItem() {
    }

    public ReceiptItem createOneReceiptItem(Path pathToReceiptItems, List<Item> getItemsFromFile, int anzahl, int skuPosition) throws IOException {

        String item = getItemsFromFile.get(skuPosition).getBrand() + ", " + getItemsFromFile.get(skuPosition).getName();
        double ppu = getItemsFromFile.get(skuPosition).getPpu();
        double grossThisReceiptItem = ppu * anzahl;
        BigDecimal bigDecimalPPU = new BigDecimal(ppu);
        BigDecimal bigDecGrossThisRecItem = new BigDecimal(grossThisReceiptItem); //Price PPU not changed to gross

        writeOneReceiptItemtoFile(pathToReceiptItems, item, bigDecimalPPU, anzahl, bigDecGrossThisRecItem);
        return new ReceiptItem(item, anzahl, bigDecimalPPU);
    }


    public void writeOneReceiptItemtoFile(Path pathToReceiptItems, String oneReceiptItem, BigDecimal ppu, int anzahl, BigDecimal bigDecGrossThisRecItem) throws IOException {

        if (Files.notExists(pathToReceiptItems)) {
            Files.createFile(pathToReceiptItems);
        }
        String ppu1 = String.valueOf(ppu);
        String grossConvert = String.valueOf(bigDecGrossThisRecItem);//TODO  total or gross?
        String anzahl1 = String.valueOf(anzahl);
        String entry = oneReceiptItem + "," + ppu1 + "," + anzahl1 + "," + grossConvert + "\n";       // receiptItem = ITEM+BRAND
        Files.write(
                pathToReceiptItems,
                entry.getBytes(),
                StandardOpenOption.APPEND);
    }

    //TODO USAGE?
    public double getReceiptItemsTotal(List<ReceiptItem> listForReceipt) {

        double total = 0;
        int i = 0;
        while (i < listForReceipt.size()) {
            total += listForReceipt.get(i).getGross();
            i++;
        }
        return total;
    }


    public String stringify() {

        String lineEnding = System.getProperty("line.separator");
        return "Produkt: " + item + lineEnding +
                "Stück: " + quantity + lineEnding +
                "Preis pro Stück: " + price + lineEnding +
                "------------------------------" + lineEnding +
                "Gesamtbetrag Produkt: " + price.toString() + lineEnding;
    }

    // TODO check price vs. gross
    public double getGross() {

        price =  price.multiply(BigDecimal.valueOf(quantity));
        return price.doubleValue();
    }

}
