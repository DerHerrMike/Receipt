# Receipt
Items und Kundenbelege erstellen und abrechnen

Schreibe das Programm “OOP_Bsp4_Rechnung”. 

Verwende, unter anderem, die folgenden Klassen:
Receipt
Number, ReceiptItems, 
Item
SKU, Brand, Name, Price per unit
Brand
Name, Country
ReceiptItem
Item, Quantity

Lege vier Produkte (Items) an welche in deinem Programm verfügbar sind mit diesen Attributen:

SKU
Brand
Name
Price per unit

Es können beliebig viele Produkte im Programm angelegt werden.

Beim Start des Programms werden Kunden aufgefordert anzugeben welche Produkte gekauft werden sollen. Zeige den Kunden deshalb welche Produkte verfügbar sind. 
Bei ungültigen Benutzereingaben soll die Eingabe so lange wiederholt werden bis gültige Werte eingegeben wurden. 

Kunden können pro Einkauf beliebig viele Produkte in verschiedensten Mengen kaufen. 

Gebe die Rechnung auf der Konsole aus. Die Rechnung sollte zumindest aus den folgenden Informationen bestehen:
Aktuelles Datum und Uhrzeit
Name des Geschäfts
Fortlaufende Rechnungsnummer
Jedes gekaufte Produkt
Name
Einzelpreis
Menge
Summe des Einkaufs (Rechnungs-Preis)
Anzahl der gekauften Produkte


Nach dem Einkauf können die nächsten Kunden einkaufen. 

Am Ende des Tages kann eine Abrechnung erstellt werden die folgende Informationen beinhaltet:
Anzahl der Einkäufe
Welche Produkte verkauft wurden und die Anzahl
Tagesumsatz


Erweiterung 1:
Speichere alles Hersteller in einer CSV-Datei (brands.csv)
Speichere alle verfügbaren Produkte in einer CSV-Datei (products.csv).
Lade beim Programmstart die Produkte (und Hersteller) aus der CSV-Datei in das Programm. 
Schreibe die Rechnungen in eine CSV-Datei (Receipts.csv). 
ReceiptItems der Rechnung werden in einer eigenen CSV-Datei gespeichert (ReceiptItems.csv)
Beim Programmstart sollen alle bestehenden Hersteller, Produkte, und die alten Rechnungen geladen werden. 
Implementiere die Funktion, dass für jeden Tag die durchschnittliche Rechnungssumme ausgegeben wird. 
Implementiere die Funktion, dass für jeden Tag die Rechnung mit der größten und die Rechnung mit der kleinsten Rechnungssumme ausgegeben wird. 

Inspiriert von:
https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Kassenbon%2B%2B
