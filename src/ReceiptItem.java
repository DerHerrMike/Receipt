import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReceiptItem {

    private String item;
    private int quantity;
    private BigDecimal price;

    public ReceiptItem(String item, int quantity, BigDecimal price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price.setScale(2, RoundingMode.HALF_UP );
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String stringify() {

        String lineEnding = System.getProperty("line.separator");
        return "Produkt: " + item + lineEnding +
                "Stück: " + quantity + lineEnding +
                "Preis pro Stück: " + price + lineEnding +
                "------------------------------" + lineEnding +
                "Gesamtbetrag Produkt: " + price.toString() + lineEnding;
    }

    public double getGross() {

        price =  price.multiply(BigDecimal.valueOf(quantity));
        return price.doubleValue();
    }

    public int getQuantity() {
        return quantity;
    }


}
