package de.sb.BeerAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class TestBeerAnalyzer {

	MapDriver<Object, Text, IntWritable, IntWritable> mapDriver;
	ReduceDriver<IntWritable, IntWritable, IntWritable, FloatWritable> reduceDriver;
	MapReduceDriver<Object, Text, IntWritable, IntWritable, IntWritable, FloatWritable> mapReduceDriver;

	@Before
	public void setUp() {
		BeerMapper mapper = new BeerMapper();
		BeerReducer reducer = new BeerReducer();
		mapDriver = MapDriver.newMapDriver(mapper);
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
		mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}

	@Test
	public void testMapper() throws IOException {
		mapDriver.withInput(new LongWritable(), new Text("1,5"));
		mapDriver.withOutput(new IntWritable(1), new IntWritable(5));
		mapDriver.runTest();
	}

	@Test
	public void testReducer() throws IOException {
		List<IntWritable> values = new ArrayList<IntWritable>();
		values.add(new IntWritable(4));
		values.add(new IntWritable(6));
		reduceDriver.withInput(new IntWritable(1), values);
		reduceDriver.withOutput(new IntWritable(1), new FloatWritable((float) 1.0));
		reduceDriver.runTest();
	}

	@Test
	/*
	 * Simuliert eine CSV-Datei mit folgenden Zeilen:
	 * Kundennummer / Anzahl Biere
	 * 1,4
	 * 2,1
	 * 1,6
	 * Erwartetes Ergebnis (Kundennummer / Blutalkoholspiegel: 
	 * 1	1,1
	 * 2	0,1
	 */
	public void testMapReduce() throws IOException {
		mapReduceDriver.withInput(new LongWritable(), new Text("1,4"));
		mapReduceDriver.withInput(new LongWritable(), new Text("2,1"));
		mapReduceDriver.withInput(new LongWritable(), new Text("1,6"));
		mapReduceDriver.withOutput(new IntWritable(1), new FloatWritable((float) 1.0));
		mapReduceDriver.withOutput(new IntWritable(2), new FloatWritable((float) 0.1));
		mapReduceDriver.runTest();
	}

}
