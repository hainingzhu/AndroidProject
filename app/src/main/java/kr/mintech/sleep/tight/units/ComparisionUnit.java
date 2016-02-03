package kr.mintech.sleep.tight.units;

import java.util.ArrayList;

import kr.mintech.sleep.tight.networks.JsonNode;

import org.json.JSONArray;

import android.util.Log;

public class ComparisionUnit
{
	public String sleepQualityGood, sleepQualityGoodStr;
	public String sleepQualityNeutral, sleepQualityNeutralStr;
	public String sleepQualityBad, sleepQualityBadStr;
	
	public String sleepDurationGood, sleepDurationGoodStr;
	public String sleepDurationNeutral, sleepDurationNeutralStr;
	public String sleepDurationBad, sleepDurationBadStr;
	
	public String sleepEfficiencyGood, sleepEfficiencyGoodStr;
	public String sleepEfficiencyNeutral, sleepEfficiencyNeutralStr;
	public String sleepEfficiencyBad, sleepEfficiencyBadStr;
	
	public String timeToFallAsleepGood, timeToFallAsleepGoodStr;
	public String timeToFallAsleepNeutral, timeToFallAsleepNeutralStr;
	public String timeToFallAsleepBad, timeToFallAsleepBadStr;
	
	public ArrayList<ComparisionLastActInfoUnit> activityInfos;
	public ArrayList<Top5RitualUnit> top5RitualsGood;
	public ArrayList<Top5RitualUnit> top5RitualsNeutral;
	public ArrayList<Top5RitualUnit> top5RitualsPoor;
	
	public ComparisionUnit(JsonNode $node)
	{
		sleepQualityGood = $node.getString("sleep_quality_good");
		sleepQualityGoodStr = $node.getString("sleep_quality_good_string");
		sleepQualityNeutral = $node.getString("sleep_quality_neutral");
		sleepQualityNeutralStr = $node.getString("sleep_quality_neutral_string");
		sleepQualityBad = $node.getString("sleep_quality_bad");
		sleepQualityBadStr = $node.getString("sleep_quality_bad_string");
		
		sleepDurationGood = $node.getString("sleep_duration_good");
		sleepDurationGoodStr = $node.getString("sleep_duration_good_string");
		sleepDurationNeutral = $node.getString("sleep_duration_neutral");
		sleepDurationNeutralStr = $node.getString("sleep_duration_neutral_string");
		sleepDurationBad = $node.getString("sleep_duration_bad");
		sleepDurationBadStr = $node.getString("sleep_duration_bad_string");
		
		sleepEfficiencyGood = $node.getString("sleep_efficiency_good");
		sleepEfficiencyGoodStr = $node.getString("sleep_efficiency_good_string");
		sleepEfficiencyNeutral = $node.getString("sleep_efficiency_neutral");
		sleepEfficiencyNeutralStr = $node.getString("sleep_efficiency_neutral_string");
		sleepEfficiencyBad = $node.getString("sleep_efficiency_bad");
		sleepEfficiencyBadStr = $node.getString("sleep_efficiency_bad_string");
		
		timeToFallAsleepGood = $node.getString("sleep_latency_good");
		timeToFallAsleepGoodStr = $node.getString("sleep_latency_good_string");
		timeToFallAsleepNeutral = $node.getString("sleep_latency_neutral");
		timeToFallAsleepNeutralStr = $node.getString("sleep_latency_neutral_string");
		timeToFallAsleepBad = $node.getString("sleep_latency_bad");
		timeToFallAsleepBadStr = $node.getString("sleep_latency_bad_string");
		
		activityInfos = new ArrayList<ComparisionLastActInfoUnit>();
		parseActivityInfoUnit($node);
		
		top5RitualsGood = new ArrayList<Top5RitualUnit>();
		parseTop5RitualUnit($node, "top5_rituals_good", top5RitualsGood);
		top5RitualsNeutral = new ArrayList<Top5RitualUnit>();
		parseTop5RitualUnit($node, "top5_rituals_neutral", top5RitualsNeutral);
		top5RitualsPoor = new ArrayList<Top5RitualUnit>();
		parseTop5RitualUnit($node, "top5_rituals_poor", top5RitualsPoor);
	}
	
	
	private void parseActivityInfoUnit(JsonNode $node)
	{
		JSONArray kArr = $node.getJsonArray("activities_info");
		
		try
		{
			for (int i = 0; i < kArr.length(); i++)
			{
				JsonNode kColorNode = new JsonNode(kArr.get(i).toString());
				ComparisionLastActInfoUnit kColor = new ComparisionLastActInfoUnit(kColorNode);
				activityInfos.add(kColor);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void parseTop5RitualUnit(JsonNode $node, String category, ArrayList<Top5RitualUnit> unitArray)
	{
		JSONArray kArr = $node.getJsonArray(category);
		
		try
		{
			for (int i = 0; i < kArr.length(); i++)
			{
				JsonNode kColorNode = new JsonNode(kArr.get(i).toString());
				Top5RitualUnit kColor = new Top5RitualUnit(kColorNode);
				unitArray.add(kColor);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}