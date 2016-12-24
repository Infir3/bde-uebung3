package de.sb.BeerAnalyzer;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * Berechnet den möglichen Promillegehalt eines Kunden für alle Biere, die
 * er gekauft hat.
 */
public class BeerReducer extends Reducer<IntWritable, IntWritable, IntWritable, FloatWritable> {
	private FloatWritable result = new FloatWritable();

	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		float sum = 0;
		float bloodAlcohol; // Blutalkohol in Promille
		for (IntWritable val : values) {
			sum += val.get();
		}

		// Promillegehalt berechnen: wir gehen pro Bier von 0,1 Promille aus
		bloodAlcohol = (float) (sum * 0.1);

		result.set(bloodAlcohol);

		context.write(key, result);
	}

}
