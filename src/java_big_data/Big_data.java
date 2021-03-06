package java_big_data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
/**
 * Has useful big data utilities
 * @author K.Ataman
 *
 */
public class Big_data {
	/**
	 * Creates the enumerated list of {String[]} data
	 * @param data
	 * @return {int[]}
	 */
	public int[] enumerate(String[] data) {
		Map<String, Integer> dictionary = new HashMap<String, Integer>();
		//get unique elements of data
		String[] temp_list = Arrays.stream(data).distinct().toArray(String[]::new);
		
		//fill the dictionary
		for (int i=0;i<temp_list.length;i++) {
			dictionary.put(temp_list[i], i);
		}
		//enumerate
		int[] result = new int[data.length];
		for (int i = 0; i < temp_list.length ; i++) {
			result[i] = dictionary.get(data[i]);
		}
		return result;
	}
	/**
	 * Creates the enumerated list of {int[]} data
	 * @param data {int[]}
	 * @return {int[]}
	 */
	public int[] enumerate(int[] data) {
		Map<Integer, Integer> dictionary = new HashMap<Integer, Integer>();
		//get unique elements of data
		int[] temp_list = Arrays.stream(data).distinct().toArray();
		
		//fill the dictionary
		for (int i=0;i<temp_list.length;i++) {
			dictionary.put(temp_list[i], i);
		}
		//enumerate
		int[] result = new int[data.length];
		for (int i = 0; i < temp_list.length ; i++) {
			result[i] = dictionary.get(data[i]);
		}
		return result;
	}
	/**
	 * 
	 * @param data - {String[]}
	 * @return A string array consisting one of each element
	 */
	public static String[] find_uniques(String[] data) {
		return Arrays.stream(data).distinct().toArray(String[]::new);
	}
	/**
	 * 
	 * @param data - {int[]}
	 * @return An int array consisting one of each element
	 */
	public static int [] find_uniques(int[] data) {
		return Arrays.stream(data).distinct().toArray();
	}
	/**
	 * Contains csv reading and writing utilities
	 * @author K.Ataman
	 *
	 */
	class csv_utilities{
		/**
		 * Converts csv with headers to a string matrix without the headers
		 * @param SAMPLE_CSV_FILE_PATH - {String} the folder location of csv
		 * @param num_columns -  {int} number of columns of the file
		 * @param num_rows -  {int} number of rows of the file
		 * @return {String[][]}
		 * @throws IOException
		 */
		public String[][] csv_reader_with_header(String SAMPLE_CSV_FILE_PATH,
				int num_columns, int num_rows, String[] column_names) throws IOException{
			
			Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
		    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
		    		.withFirstRecordAsHeader()
		            .withIgnoreHeaderCase()
		            .withTrim());
		    Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		    String[][] output = new String [num_columns][num_rows];
		    int index = 0;
		    for (CSVRecord csvRecord : csvRecords) {
		    	for (int i=0; i < column_names.length; i++) {
		    		output[i][index] = csvRecord.get(column_names[i]);}
		    	index += 1;
		    }
		    csvParser.close();
			return output;
		}
		/**
		 * Converts csv without headers to a string matrix 
		 * @param SAMPLE_CSV_FILE_PATH - {String} the folder location of csv
		 * @param num_columns -  {int} number of columns of the file
		 * @param num_rows -  {int} number of rows of the file
		 * @return {String[][]}
		 * @throws IOException
		 */
		public String[][] csv_reader_without_header(String SAMPLE_CSV_FILE_PATH,
				int num_columns, int num_rows) throws IOException{
			
			Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
		    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
		    		.withFirstRecordAsHeader()
		            .withIgnoreHeaderCase()
		            .withTrim());
		    Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		    String[][] output = new String [num_columns][num_rows];
		    int index = 0;
		    for (CSVRecord csvRecord : csvRecords) {
		    	for (int i=0; i < num_columns; i++) {
		    		output[i][index] = csvRecord.get(i);
		    	}
		    	index += 1;
		    }
		    csvParser.close();
			return output;
		}
		/**
		 Writes a csv file
		 @param {String} location - the folder directory in which the data will be saved
		 @param {String} name - name of the csv file that will be saved
		 @param {String[]} column_names - Header names
		 @param {String[][]} data - the data matrix that will be converted to csv
		 @return {csv} - a csv file at the designated location
		 */
		public void csv_writer_with_header(String location, String name, String[] column_names, String[][] data) throws IOException {
			
			FileWriter fileWriter = null;
			final String NEW_LINE_SEPARATOR = "\n";
			 CSVPrinter csvFilePrinter = null;
			 String fileName = location + name;
		
			//Create the CSVFormat object with "\n" as a record delimiter
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
					
			//initialize FileWriter object
			fileWriter = new FileWriter(fileName);
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        //Create CSV file header
	        List header = new ArrayList();
	        for (int j=0; j < column_names.length; j++) {
	        	header.add(column_names[j]);
	        }
	        csvFilePrinter.printRecord(header);
			
			//Write a new student object list to the CSV file
			for (int i = 0; i < data.length; i++) {
				List line = new ArrayList();
				for (int j=0; j < data[1].length; j++) {
					line.add(data[i][j]);
				}
	            csvFilePrinter.printRecord(line);
			}
			csvFilePrinter.close();
		}
		/**
		 Writes a csv file
		 @param {String} location - the folder directory in which the data will be saved
		 @param {String} name - name of the csv file that will be saved
		 @param {String[]} column_names - Header names
		 @param {String[][]} data - the data matrix that will be converted to csv
		 @return {csv} - a csv file at the designated location
		 */
		public void csv_writer_with_header(String location, String name, String[] column_names, String[] data) throws IOException {
			
			FileWriter fileWriter = null;
			final String NEW_LINE_SEPARATOR = "\n";
			 CSVPrinter csvFilePrinter = null;
			 String fileName = location + name;
		
			//Create the CSVFormat object with "\n" as a record delimiter
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
					
			//initialize FileWriter object
			fileWriter = new FileWriter(fileName);
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        //Create CSV file header
	        List header = new ArrayList();
	        for (int j=0; j < column_names.length; j++) {
	        	header.add(column_names[j]);
	        }
	        csvFilePrinter.printRecord(header);
			
			//Write a new student object list to the CSV file
			for (int i = 0; i < data.length; i++) {
				List line = new ArrayList();
					line.add(data[i]);
	            csvFilePrinter.printRecord(line);
			}
			csvFilePrinter.close();
		}
		/**
		 Writes a csv file
		 @param {String} location - the folder directory in which the data will be saved
		 @param {String} name - name of the csv file that will be saved
		 @param {String[]} column_names - Header names
		 @param {String[][]} data - the data matrix that will be converted to csv
		 @return {csv} - a csv file at the designated location
		 */
		public void csv_writer_with_header(String location, String name, String[] column_names, int[][] data) throws IOException {
			
			FileWriter fileWriter = null;
			final String NEW_LINE_SEPARATOR = "\n";
			 CSVPrinter csvFilePrinter = null;
			 String fileName = location + name;
		
			//Create the CSVFormat object with "\n" as a record delimiter
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
					
			//initialize FileWriter object
			fileWriter = new FileWriter(fileName);
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        //Create CSV file header
	        List header = new ArrayList();
	        for (int j=0; j < column_names.length; j++) {
	        	header.add(column_names[j]);
	        }
	        csvFilePrinter.printRecord(header);
			
			//Write a new student object list to the CSV file
			for (int i = 0; i < data[0].length; i++) {
				List line = new ArrayList();
				for (int j=0; j < data[1].length; j++) {
					line.add(data[i][j]);
				}
	            csvFilePrinter.printRecord(line);
			}
			csvFilePrinter.close();
		}
	}
	
	
	/**
	 * Calculates important staitistical variables such as mean and variance
	 * @author K.Ataman
	 *
	 */
	public static class statistics{
		/**
		 * 
		 * @param {float[]} data
		 * @return {float} the mean of data
		 */
		public static float mean(float[] data) {
			float mean = 0;
			for (int i=0; i < data.length; i++) {
				mean += data[i];
			}
			return (float) (mean/data.length);
		}
		/**
		 * calculates variance (aka square of std deviation)
		 * @param data
		 * @return {float} 
		 */
		public static float variance (float[] data) {
			float variance = 0;
			float mean = mean(data);
			for (int i = 0; i < data.length; i++) {
				variance += (float)((data[i] - mean) * (data[i] - mean) / (data.length - 1));
			}
			return (float) variance;
		}
		/**
		 * calculates standard deviation
		 * @param data
		 * @return {float} 
		 */
		public static float stdev(float[] data) {
			return (float) Math.sqrt(variance(data));
		}
	}
	
}
