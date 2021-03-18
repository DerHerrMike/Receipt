import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args) throws IOException {

        List<Item> inputList = new ArrayList<>();
        List<Item> itemsFile = new ArrayList<>();
//        Files.createFile(Path.of("output\\items.txt"));       HOW DOES IT WORK?
        Path path = Paths.get("output\\items.txt");
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Item item = new Item();
        int units = item.defIterator();
        for (int i = 0; i < units; i++) {
            item.itemCreator(path, inputList);
        }
        for (int i = 0; i < units; i++) {
            System.out.println(inputList.get(i).getSku());
            System.out.println(inputList.get(i).getBrand());
            System.out.println(inputList.get(i).getName());
            System.out.println(inputList.get(i).getPpu());
            System.out.println();
        }

        LocalDateTime lcd = LocalDateTime.now();
        Receipt r = new Receipt(lcd,"MPS Tools Ltd.", 1);
        ReceiptItem positionOnReceipt = new ReceiptItem(item.getName(), 2, item.getPpu());
        String rConvert = r.stringify();
        String porConvert = positionOnReceipt.stringify();
        System.out.println(rConvert);
        System.out.println();
        System.out.println(porConvert);
}

    private void shopping(){

    }


    // AUX
    private String convert() {
        return ";" ;
        //        return getSku() +


//                        + getBrand()
//                +
//                ";" +
//                getName() +
//                ";" +
//                getPpu() +
//                ";" +
//                "\n";
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

}
