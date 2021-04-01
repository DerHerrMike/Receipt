import java.util.List;
import java.util.Scanner;


public class Accounting {


    public void accountingMenu(Shop shop, List<ReceiptItem> listAllReceiptItemsDay, int counter, double tagesumsatz, List<Double> averageReceiptVaDayList, Item item) {
        System.out.println();
        System.out.println("***********************************************");
        System.out.println("ABRECHNUNG der Firma " + shop.getShopname());
        System.out.println("***********************************************");
        System.out.println();
        System.out.println();
        System.out.println("Anzahl der heute getätigten Einkäufe: " + counter);
        System.out.println();
        System.out.println("Die Tagesabrechnung der verkauften Produkte und deren Anzahl :");
        System.out.println();
        readoutItem(listAllReceiptItemsDay);
        System.out.println("Der Tagesumsatz gesamt beträgt: EUR " + tagesumsatz);
        System.out.println();
        double allValues = 0.0f;
        for (Double aDouble : averageReceiptVaDayList) {
            allValues += aDouble;
        }
        double aux = Math.pow(10, 2);
        double average = Math.round((allValues / averageReceiptVaDayList.size()) * aux) / aux;
        System.out.println("Die durchschnittliche Rechnungssumme des Tages beträgt EUR: " + average);
        System.out.println();
        averageReceiptVaDayList.sort(null);
        int listlastpos = (averageReceiptVaDayList.size() - 1);
        System.out.println("Die höchste Rechnung des Tages betrug EUR: " + averageReceiptVaDayList.get(listlastpos));
        System.out.println();
        System.out.println("Die niedrigste Rechnung des Tages betrug EUR: " + averageReceiptVaDayList.get(0));
        System.out.println();
        System.out.println("---TAGESUMSATZLISTE ENDE---");
        System.out.println();
        System.out.println("Zurück zum Menü  mit beliebiger Taste");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void readoutItem(List<ReceiptItem> receiptItemListReturn) {

        for (ReceiptItem item : receiptItemListReturn) {
            System.out.println(item.stringifyReceiptItemsNoArgs());
            System.out.println();
        }
    }

    public double incTagesumsatz(double tagesumsatz, double receiptTotal) {
        return tagesumsatz + receiptTotal;
    }


}
