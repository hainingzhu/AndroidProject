package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class UserUnit
{
	public boolean isAdmin;
	public int birthYear;
	public String gender;
	public int id;
	public String sleepCondition;
	public String name;
	
	
	public UserUnit(JsonNode $node)
	{
		id = $node.getInt("id");
		isAdmin = $node.getBoolean("admin");
		birthYear = $node.getInt("birth_year");
		gender = $node.getString("gender");
		sleepCondition = $node.getString("sleep_condition");
		name = $node.getString("name");
	}
}
