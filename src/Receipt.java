import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Receipt {

    protected LocalDateTime timestamp;
    public String shopname;
    protected int receiptNumber = 0;

    // standard constructor
    public Receipt(LocalDateTime timestamp, String shopname, int receiptNumber) {
        this.timestamp = timestamp;
        this.shopname = shopname;
        this.receiptNumber = receiptNumber;
    }

    // no-arg constructor
    public Receipt() {
    }


    public int getNextValue() {
        return receiptNumber + 1;
    }

    public String stringify() {
        Shop shop = new Shop();
        return System.getProperty("line.separator") + shop.getShopname() + " - Ihr Partner im Handwerk!" +
                System.getProperty("line.separator") + "Einkauf am/um: " + getTimestamp() +
                System.getProperty("line.separator") +
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
        return ";";
    }

    public String getTimestamp() {
        var format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return timestamp.format(format);
    }

}
