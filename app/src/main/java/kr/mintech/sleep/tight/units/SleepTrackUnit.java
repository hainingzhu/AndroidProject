package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class SleepTrackUnit
{
	public int awakeCount;
	public int id;
	public int totalTimeAwake;
	public int userId;
	public String inBedTime;
	public String outOfBedTime;
	public String wakeUpTime;
	public String sleepTime;
	public String diaryDate;
	public int sleepQuality;
	public int sleepDuration;
	
	
	public SleepTrackUnit(JsonNode $node)
	{
		awakeCount = $node.getInt("awake_count");
		id = $node.getInt("id");
		totalTimeAwake = $node.getInt("total_time_awake");
		userId = $node.getInt("user_id");
		sleepQuality = $node.getInt("sleep_quality");
		
		inBedTime = $node.getString("in_bed_time");
		outOfBedTime = $node.getString("out_of_bed_time");
		wakeUpTime = $node.getString("wake_up_time");
		sleepTime = $node.getString("sleep_time");
		diaryDate = $node.getString("diary_date");
		
		sleepDuration = $node.getInt("sleep_duration");
	}
}
