## Überblick
Das Beispiel ist das gleiche wie das in Übung 2, allerdings wurde hier eine zusätzliche Testklasse erstellt, die die Funktionen von Mapper und Reducer mit Unit Tests sicherstellt. Außerdem wurde der Code von Test- und produktiven Klassen auf verschiedenen Ordner aufgeteilt und alle Klassen liegen nun in separaten Dateien.

Das Programm berechnet (nach wie vor) für eine Menge an Kundendaten den Promillegehalt jedes Kunden anhand der Anzahl der Biere, die dieser getrunken hat.

## Anleitung

Die Textdatei, die eingelesen wird, muss folgenden Aufbau haben:
`<customerId>,<AnzahlDerBiere>` - beides müssen Integer sein. Eine Beispieldatei mit 2000 Datensätzen ist bereits dabei; ansonsten kann diese manuell oder mithilfe der Klasse *BeerDataGenerator.java* erzeugt werden.

Danach muss die jar-Datei mit Maven erzeugt werden. Im Gegensatz zu Übung 2 sollte hier `Maven install` verwendet werden, weil es wegen der Unit Tests sonst aus irgendwelchen Gründen zu einem Build Error kommt. 

Beide Dateien werden nun (z.B. per WinSCP) auf die Hadoop-VM kopiert. Die Datei mit den Testdaten muss zusätzlich im HDFS abgelegt werden.

Der MapReduce-Job wird wie folgt gestartet:
```
hadoop jar BeerAnalyzer-0.0.1-SNAPSHOT.jar de.sb.BeerAnalyzer.BeerAnalyzer /input/beer_data.txt /output
```

Dabei muss die Eingabedatei bereits im HDFS vorhanden sein, das Ausgabeverzeichnis darf dagegen noch **nicht** da sein (Löschbefehl: `hdfs dfs -rm -r -skipTrash <directory>`).

Mit ``hdfs dfs -cat /output/part-r-00000`` kann man sich das Ergebnis anschauen.
