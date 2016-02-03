package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class Top5RitualUnit
{
	public int id;
	public String name;
	public int frequency;
	
	public Top5RitualUnit(JsonNode $node)
	{
		id = $node.getInt("ritual_id");
		name = $node.getString("ritual_name");
		frequency = $node.getInt("frequency");
	}
}