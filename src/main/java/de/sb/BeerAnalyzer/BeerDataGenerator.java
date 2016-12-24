package de.sb.BeerAnalyzer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeerDataGenerator {

	/**
	 * Generiert eine CSV-Datei mit Testdaten (Kundennummer, Anzahl der getrunkenen
	 * Biere).
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Logger log = Logger.getLogger(BeerDataGenerator.class.getName()); 		
		final int dataCounter = 2000; // number of entries to be created

		log.log(Level.INFO, "Writing " + dataCounter + " entries...");
		try {
			Random r = new Random();
			PrintWriter out = new PrintWriter("beer_data.txt");

			int customerId;
			int numberOfBeers;

			for (int i = 0; i < dataCounter; i++) {

				customerId = r.nextInt(100);

				numberOfBeers = r.nextInt(20);

				// write file
				out.print(customerId);
				out.print(",");
				out.print(numberOfBeers);
				out.print(System.getProperty("line.separator"));
			}

			out.close();
			log.log(Level.INFO, "Done!");

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "Write error!");
			e.printStackTrace();
		}

	}

}
