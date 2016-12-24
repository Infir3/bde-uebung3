package de.sb.BeerAnalyzer;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/* Liest die customerId und die Anzahl der Biere aus der Textzeile. */
public class BeerMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

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