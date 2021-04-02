import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    private String shopname;
    private int purchaseCounter;


    public void designNameSelection() {
        System.out.println("PROGRAMM ZU EIN- UND VERKAUF EINES KLEINEN UNTERNEHMENS");
        System.out.println("*******************************************************");
        System.out.println();
        System.out.println("Bitte zu Beginn den Firmennamen aus Auswahl oder selbst festlegen: ");
    }

    public void chooseName() {

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Firma 'Steh und Schau GmbH' = 1");
        System.out.println("Firma 'MPS Tools (EUROPE) Ltd.' = 2");
        System.out.println("Firma 'GURU SHOP 24' = 3");
        System.out.println("Eigenen Namen festlegen = 4");
        System.out.println();
        System.out.println("Bitte Auswahl treffen: ");
        System.out.println();
        int nameselection = scanner.nextInt();
        scanner.nextLine();
        switch (nameselection) {

            case 1 ->setShopname("Steh und Schau GmbH");
            case 2 ->setShopname("MPS Tools (EUROPE) Ltd.");
            case 3-> setShopname("GURU SHOP 24");
            case 4 -> {

                System.out.println("Bitte Firmennamen eingeben: ");
                String chosenName = scanner.nextLine();
                setShopname(chosenName);
            }

            default -> throw new IllegalStateException("Unexpected value: " + nameselection);
        }
    }

    public List<ReceiptItem> sellItems(List<Item> getItemsFromFile, ReceiptItem rI) throws IOException {

        Scanner scanner = new Scanner(System.in);
        List<ReceiptItem> receiptItemList = new ArrayList<>();
        Path pathToReceiptItems = Paths.get("output\\receiptItems.csv");

        System.out.println();
        System.out.println("Willkommen bei " + getShopname() + " - Tools4Pros");
        System.out.println();
        System.out.println("Folgende Produkte sind aktuell verfügbar:");
        System.out.println();
        for (Item item : getItemsFromFile) {
            System.out.print("SKU: " + item.getSku() + " || ");
            System.out.print("Brand: " + item.getBrand() + " || ");
            System.out.print("Name: " + item.getName() + " || ");
            System.out.println("Stückpreis EUR: " + item.getPpu());
            System.out.println();
        }
        boolean userSelect = false;
        boolean abbruch = false;
        while (!abbruch) {
            int iterationCounter = 0;
            while (!userSelect) {

                System.out.println("Welches Produkt möchtest du kaufen? Bitte SKU eingeben: ");
                String selectedSKU = scanner.nextLine();
                iterationCounter = 0;
                for (int i = 0; i < getItemsFromFile.size(); i++) {//gets SKUs
                    String compareSKU = getItemsFromFile.get(iterationCounter).getSku();
                    if (!compareSKU.equalsIgnoreCase(selectedSKU)) {
                        iterationCounter++;
                    } else {
                        userSelect = true;
                    }
                    if (getItemsFromFile.size() <= iterationCounter) {
                        System.out.println("Kein Produkt mit dieser SKU gefunden! Weiter mit beliebiger Taste.");
                        System.out.println();
                        scanner.nextLine();
                    }
                }
            }
            System.out.println();
            int anzahl;
            while (true) {

                System.out.println("Bitte gewünschte Anzahl eingeben: ");
                String selection = scanner.nextLine();
                try {
                    anzahl = Integer.parseInt(selection);
                    break;

                } catch (NumberFormatException e) {
                    System.out.println();
                    System.out.println("Bitte einen ganzzahligen Wert eingeben! Weiter mit beliebiger Taste.");
                    scanner.nextLine();
                }
            }
            int skuPosition = iterationCounter;
            ReceiptItem receiptItem = rI.createOneReceiptItem(pathToReceiptItems, getItemsFromFile, anzahl, skuPosition);
            receiptItemList.add(receiptItem);
            System.out.println();
            System.out.println("Weiteres Produkt kaufen? (j/n): ");
            String furtherItems = scanner.nextLine();
            if (furtherItems.equalsIgnoreCase("n")) {
                int purchaseCount = (getPurchaseCounter() + 1);
                setPurchaseCounter(purchaseCount);
                System.out.println();
                System.out.println("Dies war heute der " + getPurchaseCounter() + ". Einkauf.");
                abbruch = true;
            }
        }
        return receiptItemList;
    }

    // G&S
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getPurchaseCounter() {
        return purchaseCounter;
    }

    public void setPurchaseCounter(int purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }
}
