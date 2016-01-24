package ss_api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static ArrayList<Crime> crimes = new ArrayList<Crime>();
	public static HashMap<Integer, ArrayList<Crime>> crimesByCategory = new HashMap<Integer, ArrayList<Crime>>();
	public static HashMap<Integer, ArrayList<Crime>> crimesByTime = new HashMap<Integer, ArrayList<Crime>>();
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		readCSV();
		groupCrimes();
		groupByTime();
	}
	
	public static ArrayList<Crime> findCrimes(double lat, double lon){

		double upperLat = lat+0.167;
		double lowerLat = lat-0.167;
		double upperLon = lon+.011;
		double lowerLon = lon-.011;
		
		ArrayList<Crime> nearbyCrimes = new ArrayList<Crime>();
		for(Crime c : crimes){
			if(c.lat < upperLat && c.lat > lowerLat && c.lon < upperLon && c.lon > lowerLon){
				nearbyCrimes.add(c);
			}
		}
		return nearbyCrimes;
	}
	
	public static ArrayList<Crime> filterCrimes(ArrayList<Crime> nearbyCrimes, int hour){
		
		ArrayList<Crime> sameHourCrimes = new ArrayList<Crime>();
		for(Crime c : nearbyCrimes){
			int cHour = Integer.parseInt(c.time.substring(0, 2));
			if(cHour == hour){
				sameHourCrimes.add(c);
			}
		}
		return sameHourCrimes;
	}
	
	private static void groupByTime(){
		
		for(Crime c : crimes){
			int hour = Integer.parseInt(c.time.substring(0, 2));
			if(!crimesByTime.containsKey(hour)){
				crimesByTime.put(hour, new ArrayList<Crime>());
			}
			crimesByTime.get(hour).add(c);
		}
		
		System.out.println("Categorized by time");
	}
	
	private static void groupCrimes(){
		
		for(Crime c : crimes){
			if(!crimesByCategory.containsKey(c.code)){
				crimesByCategory.put(c.code, new ArrayList<Crime>());
			}
			crimesByCategory.get(c.code).add(c);
		}
		
		System.out.println("Categorized by code");
	}

	private static void readCSV(){
		
		String csvFile = "data/police_inct.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			br.readLine(); //skip headers
			while ((line = br.readLine()) != null) {

				//use comma as separator
				String[] crimeData = line.split(cvsSplitBy);
				try{
					Crime c = new Crime(crimeData[4], crimeData[7], Integer.parseInt(crimeData[8]), 
							crimeData[10], Double.parseDouble(crimeData[12]), Double.parseDouble(crimeData[11]));
					crimes.add(c);
				}
				catch(Exception e){
					
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}
}
