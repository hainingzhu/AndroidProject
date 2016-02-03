package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class BeforeBedActUnit
{
	public int id;
	public String name;
	
	public BeforeBedActUnit(JsonNode $node)
	{
		id = $node.getInt("id");
		name = $node.getString("name");
	}
}