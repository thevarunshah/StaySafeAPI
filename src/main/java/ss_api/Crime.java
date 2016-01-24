package ss_api;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Crime {

	public String time;
	@JsonIgnore
	public String block;
	public int code;
	@JsonIgnore
	public String desc;
	public double lat;
	public double lon;
	
	public Crime(String time, String block, int code, String desc, double lat, double lon){
		this.time = time;
		this.block = block;
		this.code = code;
		this.desc = desc;
		this.lat = lat;
		this.lon = lon;
	}
	
	public String toString(){
		return this.code + ": " + this.lat + ", " + this.lon;
	}
}
