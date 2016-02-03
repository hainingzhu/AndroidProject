package kr.mintech.sleep.tight.units;

import java.util.ArrayList;

import kr.mintech.sleep.tight.networks.JsonNode;

import org.json.JSONArray;

public class RegisterUnit
{
	//Register when "isNewUser = true" 
	public boolean isNewUser;
	public ArrayList<UserUnit> userUnit;
	
	
	public RegisterUnit(JsonNode $node)
	{
		isNewUser = $node.getBoolean("new_user");
		
		//User Info
		userUnit = new ArrayList<UserUnit>();
		parseUsers($node);
	}
	
	
	private void parseUsers(JsonNode $node)
	{
		JSONArray kArray = $node.getJsonArray("user");
		
		try
		{
			for (int i = 0; i < kArray.length(); i++)
			{
				JsonNode kNode = new JsonNode(kArray.get(i).toString());
				UserUnit kUnit = new UserUnit(kNode);
				userUnit.add(kUnit);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
