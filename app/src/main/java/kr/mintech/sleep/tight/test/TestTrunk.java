package kr.mintech.sleep.tight.test;

import java.util.ArrayList;

import kr.mintech.sleep.tight.utils.PreferenceUtil;

public class TestTrunk
{
	private static TestTrunk _instance;
	
	public final String[] numbers = new String[] { "12A", "0:15", "0:30", "0:45", "1A", "1:15", "1:30", "1:45", "2A", "2:15", "2:30", "2:45", "3A", "3:15", "3:30", "3:45", "4A", "4:15", "4:30",
			"4:45", "5A", "5:15", "5:30", "5:45", "6A", "6:15", "6:30", "6:45", "7A", "7:15", "7:30", "7:45", "8A", "8:15", "8:30", "8:45", "9A", "9:15", "9:30", "9:45", "10A", "10:15", "10:30",
			"10:45", "11A", "11:15", "11:30", "11:45", "12P", "12:15", "12:30", "12:45", "13P", "13:15", "13:30", "13:45", "14P", "14:15", "14:30", "14:45", "15P", "15:15", "15:30", "15:45", "16P",
			"16:15", "16:30", "16:45", "17P", "17:15", "17:30", "17:45", "18P", "18:15", "18:30", "18:45", "19P", "19:15", "19:30", "19:45", "20P", "20:15", "20:30", "20:45", "21P", "21:15", "21:30",
			"21:45", "22P", "22:15", "22:30", "22:45", "23P", "23:15", "23:30", "23:45", "24P" };
	public ArrayList<String> activiesLst = new ArrayList<String>();
		
	public static TestTrunk getInst()
	{
		if (_instance == null)
		{
			_instance = new TestTrunk();
		}
		return _instance;
	}
}
