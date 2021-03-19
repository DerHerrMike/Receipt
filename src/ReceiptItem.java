import java.rmi.server.UID;

public class ReceiptItem {

    protected String item;
    protected int quantity;
    protected double price;

    public ReceiptItem(String item, int quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }
    //empty constructor


    public ReceiptItem() {
    }

    public String stringify() {
        return "Produkt: " + getItem() + System.getProperty("line.separator") +
                "Stück: "+ getQuantity() + System.getProperty("line.separator") +
                "Preis pro Stück: " + getPrice() + System.getProperty("line.separator") +
                "----------------------------"+System.getProperty("line.separator") +
                "Gesamtbetrag inkl USt.: "+getGross();
    }

    private double getGross(){
        return getQuantity()*getPrice();
    }


    // G & S
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
