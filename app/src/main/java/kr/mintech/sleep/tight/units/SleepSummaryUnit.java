package kr.mintech.sleep.tight.units;

import java.util.ArrayList;

import kr.mintech.sleep.tight.networks.JsonNode;

import org.json.JSONArray;

import Util.Logg;

public class SleepSummaryUnit
{
	public String sleepDurationAvg;
	public String sleepEfficiencyAvg;
	public String sleepTimeToFallAsleepAvg;
	public String sleepQualityAvg;
	public String awake_countAvg;
	public String toBedTimeAvg;
	public String outBedTimeAvg;
	
	public ArrayList<SleepSummaryDayUnit> dayUnits;
	
	
	public SleepSummaryUnit(JsonNode $node)
	{
		sleepDurationAvg = $node.getString("sleep_durations_avg");
		sleepEfficiencyAvg = $node.getString("sleep_efficiency_avg");
		sleepTimeToFallAsleepAvg = $node.getString("sleep_latency_avg");
		sleepQualityAvg = $node.getString("sleep_quality_avg");
		awake_countAvg = $node.getString("awake_count_avg");
		toBedTimeAvg = $node.getString("in_bed_time_avg");
		outBedTimeAvg = $node.getString("out_of_bed_time_avg");
		
		dayUnits = new ArrayList<SleepSummaryDayUnit>();
		parseDayUnit($node);
	}
	
	
	private void parseDayUnit(JsonNode $node)
	{
		JSONArray kArr = $node.getJsonArray("sleep_tracks");
		
		try
		{
			for (int i = 0; i < kArr.length(); i++)
			{
				JsonNode kColorNode = new JsonNode(kArr.get(i).toString());
				SleepSummaryDayUnit kColor = new SleepSummaryDayUnit(kColorNode);
				dayUnits.add(kColor);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
