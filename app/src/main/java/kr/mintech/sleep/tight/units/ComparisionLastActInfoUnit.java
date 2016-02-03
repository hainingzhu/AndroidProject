package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;
import Util.Logg;

public class ComparisionLastActInfoUnit {
	public String name;
	public String color;
	public Float goodCount;
	public String goodEndTime;
	public Float neutralCount;
	public String neutralEndTime;
	public Float badCount;
	public String badEndTime;
	
	public ComparisionLastActInfoUnit(JsonNode $node) {
		name = $node.getString("name");
		color = $node.getString("color");
		goodCount = $node.getDouble("good_count");
		goodEndTime = $node.getString("good_last_action_at");
		neutralCount = $node.getDouble("neutral_count");
		neutralEndTime = $node.getString("neutral_last_action_at");
		badCount = $node.getDouble("bad_count");
		badEndTime = $node.getString("bad_last_action_at");
	}
}
