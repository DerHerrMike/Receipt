import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
        return receiptNumber + 1;
    }

    public Receipt createReceipt() throws IOException {

        Receipt r = new Receipt();
        LocalDateTime lcd = LocalDateTime.now();
        return new Receipt(lcd, shopname, r.getNextValue());
    }//TODO receiptnumber check


    public void printReceipt(Receipt receipt, List<ReceiptItem> listOfItemsForReceipt, double total) {

        Scanner scanner = new Scanner(System.in);
        String receiptConverter = receipt.stringify(shopname);
        System.out.println(receiptConverter);
        System.out.println();
        for (ReceiptItem item : listOfItemsForReceipt) {

            String returnForSringify = item.stringify();
            System.out.println(returnForSringify);
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

    public String stringify(String shop) {

        String lineEnding = System.getProperty("line.separator");
        return lineEnding + shop + " - Ihr Partner im Handwerk!" +
                lineEnding + "Einkauf am/um: " + getTimestamp() +
                lineEnding +
                "Rechnungsnummer " + getNextValue() + ", UID Nr. GB8904321";
    }   //TODO receiptnumber or value wrong


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

    private String convert() {
        return ",";
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
