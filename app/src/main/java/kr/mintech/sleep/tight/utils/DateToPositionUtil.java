package kr.mintech.sleep.tight.utils;

import java.util.ArrayList;
import java.util.Collections;

import kr.mintech.sleep.tight.test.TestTrunk;
import android.util.Log;

public class DateToPositionUtil
{
	public static int TimeToPosition(String $time)
	{
		Log.w("DateToPositionUtil | TimeToPosition()", "Time : " + $time);
		int kPosition = 0;
		ArrayList<String> kTimesArr = new ArrayList<String>();
		String[] kTimes = TestTrunk.getInst().numbers;
		if ($time.contains(":"))
		{
			String[] kSplitTimeArr = $time.split(":");
			String kHourString = kSplitTimeArr[0];
			String kMinuteString = kSplitTimeArr[1];
			
			int kHour = Integer.parseInt(kHourString);
			int kMin = Integer.parseInt(kMinuteString);
			
			if (kHour == 0)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 0;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 1;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 2;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 3;
				}
			}
			else if (kHour == 1)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 4;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 5;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 6;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 7;
				}
			}
			else if (kHour == 2)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 8;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 9;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 10;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 11;
				}
			}
			else if (kHour == 3)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 12;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 13;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 14;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 15;
				}
			}
			else if (kHour == 4)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 16;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 17;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 18;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 19;
				}
			}
			else if (kHour == 5)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 20;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 21;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 22;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 23;
				}
			}
			else if (kHour == 6)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 24;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 25;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 26;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 27;
				}
			}
			else if (kHour == 7)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 28;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 29;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 30;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 31;
				}
			}
			else if (kHour == 8)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 32;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 33;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 34;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 35;
				}
			}
			else if (kHour == 9)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 36;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 37;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 38;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 39;
				}
			}
			else if (kHour == 10)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 40;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 41;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 42;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 43;
				}
			}
			else if (kHour == 11)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 44;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 45;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 46;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 47;
				}
			}
			else if (kHour == 12)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 48;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 49;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 50;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 51;
				}
			}
			else if (kHour == 13)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 52;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 53;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 54;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 55;
				}
			}
			else if (kHour == 14)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 56;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 57;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 58;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 59;
				}
			}
			else if (kHour == 15)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 60;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 61;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 62;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 63;
				}
			}
			else if (kHour == 16)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 64;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 65;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 66;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 67;
				}
			}
			else if (kHour == 17)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 68;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 69;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 70;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 71;
				}
			}
			else if (kHour == 18)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 72;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 73;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 74;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 75;
				}
			}
			else if (kHour == 19)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 76;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 77;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 78;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 79;
				}
			}
			else if (kHour == 20)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 80;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 81;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 82;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 83;
				}
			}
			else if (kHour == 21)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 84;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 85;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 86;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 87;
				}
			}
			else if (kHour == 22)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 88;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 89;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 90;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 91;
				}
			}
			else if (kHour == 23)
			{
				if (kMin == 0 && kMin < 15)
				{
					kPosition = 92;
				}
				else if (14 < kMin && kMin < 30)
				{
					kPosition = 92;
				}
				else if (29 < kMin && kMin < 45)
				{
					kPosition = 93;
				}
				else if (44 < kMin && kMin < 60)
				{
					kPosition = 94;
				}
			}
			else
			{
				
			}
		}
		else
		{
			for (int i = 0; i < kTimesArr.size(); i++)
			{
				if (kTimesArr.get(i).equals($time))
				{
					kPosition = i;
				}
			}
		}
		return kPosition;
	}
	
	
	public static String PositionToTime(int $position)
	{
		String kTime = null;
		ArrayList<String> kTimesArr = new ArrayList<String>();
		String[] kTimes = TestTrunk.getInst().numbers;
		Collections.addAll(kTimesArr, kTimes);
		
		kTime = kTimesArr.get($position);
		return kTime;
	}
}
