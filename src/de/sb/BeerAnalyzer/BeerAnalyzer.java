package de.sb.BeerAnalyzer;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BeerAnalyzer {

	/* Liest die customerId und die Anzahl der Biere aus der Textzeile. */
	public static class BeerMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

		private IntWritable customerId = new IntWritable();
		private IntWritable amountOfBeers = new IntWritable();

		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String[] strings = value.toString().split(Pattern.quote(","));
			customerId.set(Integer.parseInt(strings[0])); // set ist schneller als ein neues Objekt zu erzeugen
			amountOfBeers.set(Integer.parseInt(strings[1]));

			context.write(customerId, amountOfBeers);
		}

	}

	/*
	 * Berechnet den möglichen Promillegehalt eines Kunden für alle Biere, die
	 * er gekauft hat.
	 */
	public static class BeerReducer extends Reducer<IntWritable, IntWritable, IntWritable, FloatWritable> {
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

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "beer analysis");
		job.setJarByClass(BeerAnalyzer.class);
		job.setMapperClass(BeerMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);		
		// wegen der unterschiedlichen Ausgabetypen von Mapper und Reducer
		// dürfen wir den Reducer nicht als Combiner verwenden
		// job.setCombinerClass(BeerReducer.class);
		job.setReducerClass(BeerReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(FloatWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
