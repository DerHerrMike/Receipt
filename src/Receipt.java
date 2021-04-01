import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class Receipt {

    private LocalDateTime timestamp;
    public String shopname;
    private int receiptNumber = 0;

    // standard constructor
    public Receipt(LocalDateTime timestamp, String shopname, int receiptNumber) {
        this.timestamp = timestamp;
        this.shopname = shopname;
        this.receiptNumber = receiptNumber;
    }

    public Receipt() {
    }

    public int getNextValue() {
        return (getReceiptNumber() + 1);
    }

    public Receipt createReceipt(Receipt newReceipt) {

        LocalDateTime lcd = LocalDateTime.now();
        return new Receipt(lcd, shopname, newReceipt.getNextValue());
    }


    public void printReceipt(Shop shop, Receipt receipt, List<ReceiptItem> listOfItemsForReceipt, double total, Item forreceipt) {

        Scanner scanner = new Scanner(System.in);
        String receiptConverter = receipt.stringifyReceiptHeader(shop.getShopname(), shop);
        System.out.println(receiptConverter);
        System.out.println();

        for (int i = 0; i<listOfItemsForReceipt.size();i++){

            System.out.println(listOfItemsForReceipt.get(i).stringifyReceiptItemsNoArgs());
        }

        System.out.println("______________________________");
        System.out.println("______________________________" + System.getProperty("line.separator") +
                "Total inkl. USt.: " + Math.round(total * 100.0) / 100.0 + " EUR.");
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
        scanner.nextLine();
    }

    public double calculateAverageReceiptsValue(double furtherTotal) {

        return +furtherTotal;
    }

    public String stringifyReceiptHeader(String shop, Shop shop2t) {

        int recNo = shop2t.getPurchaseCounter();
        String lineEnding = System.getProperty("line.separator");
        return lineEnding + shop + " - Ihr Partner im Handwerk!" +
                lineEnding + "Einkauf am/um: " + getTimestamp() +
                lineEnding +
                "Rechnungsnummer " + recNo + ", UID Nr. GB8904321";
    }

    public String getTimestamp() {
        var format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return timestamp.format(format);
    }

    public String getShopname() {
        return shopname;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }
}
