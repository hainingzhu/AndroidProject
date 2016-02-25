package kr.mintech.sleep.tight.units;

import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.utils.DateTime;

public class ActivityTrackUnit
{
	public int id;
	public String activityName;
	public String trackType;
	public int activityId;
	public String actionStartedAt;
	public String actionEndedAt;
	public DateTime actionStartedAtDateTime;
	public DateTime actionEndedAtDateTime;
	public int sortPosition;
	public String recordType;
	public String color;
	
	public ActivityTrackUnit() {
	}
	
	public ActivityTrackUnit(JsonNode $node)
	{
		id = $node.getInt("id");
		activityId = $node.getInt("activity_id");
		activityName = $node.getString("activity_name");
		trackType = $node.getString("track_type");
		actionStartedAt = $node.getString("action_started_at");
		actionStartedAtDateTime = new DateTime(actionStartedAt);
		actionEndedAt = $node.getString("action_ended_at");
		actionEndedAtDateTime = new DateTime(actionEndedAt);
		sortPosition = $node.getInt("sort_position");
		recordType = $node.getString("record_type");
		color = $node.getString("color");
	}
	
	public String toString() {
		return id + ", "+
		activityId + ", "+
		activityName + ", "+
		trackType + ", "+
		actionStartedAt + ", "+
		actionEndedAt + ", "+
		sortPosition + ", "+
		recordType + ", "+
		color;
	}
}
