package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class EditSleepDisturbUnit
{
	public int id;
	public String name;
	
	public EditSleepDisturbUnit(JsonNode $node)
	{
		id = $node.getInt("id");
		name = $node.getString("name");
	}
}