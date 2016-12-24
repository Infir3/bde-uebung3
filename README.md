## Überblick
Das Programm berechnet für eine Menge an Kundendaten den Promillegehalt jedes Kunden anhand der Anzahl der Biere, die dieser getrunken hat.

Der Sourcecode befindet sich im package *de.sb.BeerAnalyzer*, das Beispiel von Apache (https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html#MapReduce_Tutorial) im package *WordCount*.

## Anleitung

Die Textdatei, die eingelesen wird, muss folgenden Aufbau haben:
`<customerId>,<AnzahlDerBiere>` - beides müssen Integer sein. Eine Beispieldatei mit 2000 Datensätzen ist bereits dabei; ansonsten kann diese manuell oder mithilfe der Klasse *BeerDataGenerator.java* erzeugt werden.

Danach muss die jar-Datei mit Maven erzeugt werden. Als Maven Goals können z.B. `clean compile package` verwendet werden.

Beide Dateien werden nun (z.B. per WinSCP) auf die Hadoop-VM kopiert. Die Datei mit den Testdaten muss zusätzlich im HDFS abgelegt werden.

Der MapReduce-Job wird wie folgt gestartet:
```
hadoop jar BeerAnalyzer-0.0.1-SNAPSHOT.jar de.sb.BeerAnalyzer.BeerAnalyzer /input/beer_data.txt /output
```

Dabei muss die Eingabedatei bereits im HDFS vorhanden sein, das Ausgabeverzeichnis darf dagegen noch **nicht** da sein (Löschbefehl: `hdfs dfs -rm -r -skipTrash <directory>`).

Mit ``hdfs dfs -cat /output/part-r-00000`` kann man sich das Ergebnis anschauen.
