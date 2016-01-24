package ss_api;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping("/check")
	public boolean check(@RequestParam(value="lat", required=true) double lat, @RequestParam(value="lon", required=true) double lon){
			
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		ArrayList<Crime> nearbyCrimes = Application.findCrimes(lat, lon);
		System.out.println(nearbyCrimes.size());
		if(nearbyCrimes.size() > 1000){
			ArrayList<Crime> sameHourCrimes = Application.filterCrimes(nearbyCrimes, hour);
			System.out.println(sameHourCrimes.size());
			if(sameHourCrimes.size() > 100){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/byCategory")
	public ArrayList<Crime> byCategory(@RequestParam(value="code", required=true) int code){
		return Application.crimesByCategory.get(code);
	}
	
	@RequestMapping("/byTime")
	public ArrayList<Crime> byTime(@RequestParam(value="hour", required=true) int hour){
		return Application.crimesByTime.get(hour);
	}
}
