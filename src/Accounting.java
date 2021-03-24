//TODO Am Ende des Tages kann eine Abrechnung erstellt werden die folgende Informationen beinhaltet:
//        Anzahl der Einkäufe
//        Welche Produkte verkauft wurden und die Anzahl
//        Tagesumsatz

//NEEDED for Accounting:
//                System.out.println("Auslesen des return Objektes receipt aus createReceipt:");
//                System.out.println(listOfReceipts.get(0).stringify());
//                System.out.println();
//                System.out.println(returned.get(0).stringify());
//                System.out.println(returned.get(1).stringify());
//                System.out.println(returned.get(2).stringify());//create loop

public class Accounting {

    //no-args constructor
    public Accounting() {
    }


    public void displayDailyNumberofPurchases() {
        Shop shop = new Shop();
        System.out.println("Heute wurden " + shop.getPurchaseCounter() + "Einkaufsvorgänge getätigt");
    }


    public void accounting() {
        System.out.println("Buchhaltung");
        System.out.println();
        System.out.println();
        System.out.println("Zurück zum Menü mit beliebiger Taste!");
    }


}
