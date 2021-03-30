import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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

    public String stringify(String shop) {

        String lineEnding = System.getProperty("line.separator");
        return lineEnding + shop + " - Ihr Partner im Handwerk!" +
                lineEnding + "Einkauf am/um: " + getTimestamp() +
                lineEnding +
                "Rechnungsnummer " + getNextValue() + ", UID Nr. GB8904321";
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
