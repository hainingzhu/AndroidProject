package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;
import Util.Logg;

public class BinInfoLastActivityUnit {
	public String name;
	public int count;
	public String endTime;
	public String content;
	
	/*
	 * When was the last time the user did a certain activity?  
	 */
	public BinInfoLastActivityUnit(JsonNode $node) {
		name = $node.getString("name");
		count = $node.getInt("count");
		endTime = $node.getString("end_time");
	}
	
	public BinInfoLastActivityUnit() {
	}
}
