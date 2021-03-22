import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Receipt {

    protected LocalDateTime timestamp;
    public String shopname;
    protected int receiptNumber;
    protected static AtomicInteger atomicInteger = new AtomicInteger(0);

    ArrayList<ReceiptItem> receiptItemArrayList = new ArrayList<>();

    // standard constructor


    public Receipt(LocalDateTime timestamp, String shopname, int receiptNumber) {
        this.timestamp = timestamp;
        this.shopname = shopname;
        this.receiptNumber = receiptNumber;
    }

    // no-arg constructor
    public Receipt() {
    }

    public int getNextAtomicValue() {
        return atomicInteger.incrementAndGet();
    }

    public int getNextValue() {
        return receiptNumber + 1;
    }

    public String stringify() {
        return System.getProperty("line.separator") + getShopname() + " - Ihr Partner im Handwerk!" +
                System.getProperty("line.separator") + "Einkauf am/um: " + getTimestamp() +
                System.getProperty("line.separator") +
                System.getProperty("line.separator") +
                "Rechnungsnummer " + getReceiptNumber() + ", UID Nr. GB8904321";
    }

    public String getTimestamp() {
        var format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return timestamp.format(format);
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber + 1;
    }
}
