package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class ActionUnit
{
	public int id;
	public int activitiyId;
	public String color;
	public boolean defaultType;
	public String name;
	public int userId;
	public boolean isHide;
	
	public ActionUnit(JsonNode $node)
	{
		id = $node.getInt("id");
		activitiyId = $node.getInt("activity_id");
		color = $node.getString("color");
		defaultType = $node.getBoolean("default_type");
		name = $node.getString("name");
		userId = $node.getInt("user_id");
		isHide = $node.getBoolean("hide");
	}
}
