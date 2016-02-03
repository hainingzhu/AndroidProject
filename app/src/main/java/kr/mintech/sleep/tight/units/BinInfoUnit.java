package kr.mintech.sleep.tight.units;

import java.util.ArrayList;

import org.json.JSONArray;

import kr.mintech.sleep.tight.networks.JsonNode;

public class BinInfoUnit
{
	public String toBedTime;
	public String timeToFallAsleep;
	public String sleepDuration;
	public String sleepEfficiency;
	public String sleepQuality;
	public String sleepDisturb;
	public String sleepBeforeBedAct;
	public ArrayList<BinInfoLastActivityUnit> _binActivityUnit;
	
	
	public BinInfoUnit(JsonNode $node)
	{
		toBedTime = $node.getString("in_bed_time");
		timeToFallAsleep = $node.getString("sleep_latency");
		sleepDuration = $node.getString("sleep_duration");
		sleepEfficiency = $node.getString("sleep_efficiency");
		sleepQuality = $node.getString("sleep_quality");
		
		sleepDisturb = $node.getString("sleep_disturbances_info");
		sleepBeforeBedAct = $node.getString("sleep_rituals_info");
		
		_binActivityUnit = new ArrayList<BinInfoLastActivityUnit>();
		parseLastActivityUnit($node);
	}
	
	private void parseLastActivityUnit(JsonNode $node)
	{
		JSONArray kArr = $node.getJsonArray("activity_tracks_info");
		
		try
		{
			for (int i = 0; i < kArr.length(); i++)
			{
				JsonNode kNode = new JsonNode(kArr.get(i).toString());
				BinInfoLastActivityUnit kUnit = new BinInfoLastActivityUnit(kNode);
				_binActivityUnit.add(kUnit);
			}
			
			BinInfoLastActivityUnit kUnit = new BinInfoLastActivityUnit();
			kUnit.name = "Before Bed Activity";
			kUnit.content = sleepBeforeBedAct;
			_binActivityUnit.add(kUnit);
			
			BinInfoLastActivityUnit kDisturbUnit = new BinInfoLastActivityUnit();
			kDisturbUnit.name = "Sleep Disturbance";
			kDisturbUnit.content = sleepDisturb;
			_binActivityUnit.add(kDisturbUnit);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
