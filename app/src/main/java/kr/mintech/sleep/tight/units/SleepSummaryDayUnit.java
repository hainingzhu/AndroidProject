package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;

public class SleepSummaryDayUnit {
	public String id;
	public String diaryDate;
	public int sleepQuality;
	public int sleeepRitual;
	public int sleepTimeToFallAsleep;
	public int sleepDuration;
	public int sleepEfficiency;
	public int awakeCount;
	public String toBedTime;
	public String outBedTime;
	
	
	/*
	 * A week-long info for the sleep summary page
	 */
	
	public SleepSummaryDayUnit(JsonNode $node) {
		id = $node.getString("id");
		diaryDate = $node.getString("diary_date");
		sleepDuration = $node.getInt("sleep_duration");
		sleepQuality = $node.getInt("sleep_quality");
		sleeepRitual = $node.getInt("sleep_ritual");
		sleepTimeToFallAsleep = $node.getInt("sleep_latency");
		sleepEfficiency = $node.getInt("sleep_efficiency");
		awakeCount = $node.getInt("awake_count");
		toBedTime = $node.getString("in_bed_time");
		outBedTime = $node.getString("out_of_bed_time");
	}
}
