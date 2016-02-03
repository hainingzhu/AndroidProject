package Util;

import android.content.Context;
import android.os.Vibrator;


public class SystemTool {
	
	
	public static void vibrate(Context $context, long $milli){
		try{
			Vibrator kVibrator = (Vibrator) $context.getSystemService(Context.VIBRATOR_SERVICE);
			kVibrator.vibrate($milli);
		}catch(Exception $e){
			
		}
		
	}
	
}
