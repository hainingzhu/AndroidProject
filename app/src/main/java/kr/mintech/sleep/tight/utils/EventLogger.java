package kr.mintech.sleep.tight.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

/**
 * Helper class for logging UI events.
 * @author mjskay
 *
 */
public class EventLogger {
	private static String EVENT_TAG = "event";
	private static Thread logger = null; 
	
	/**
	 * Start the background logger writing logs to disk
	 */
	public static synchronized void startWritingToDisk() {
        //write logs to disk in the background
		if (logger == null || logger.getState() == Thread.State.TERMINATED) {
	        logger = new Thread("LogWriter") {
	        	public void run() {
	        		PrintWriter logWriter = null;
	        	    try {
	                    File logDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
	                    logDir.mkdirs();
	                    File logFile = new File(logDir, "logcat.log");
	                    Log.d(EVENT_TAG, logFile.toString());
	                    logWriter = new PrintWriter(new FileWriter(logFile, true));
	
	                    String[] args = new String[] {"logcat",
	        	    			"-v", "threadtime",
	        	    			"*:D"};
	        	    	Process process = Runtime.getRuntime().exec(args);
	        	    	BufferedReader logReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	
	        	    	String line;
	        	    	while ((line = logReader.readLine()) != null) {
	        	    		logWriter.println(line);
	        	    	}
	        	    } catch (IOException e) {
	        	    	e.printStackTrace();
	        	    } finally {
	        	    	if (logWriter != null) logWriter.close();
	        	    }
	        	}
	        };
	        logger.start();
	        log("logger_started");
		}
	}
	
	/**
	 * Log a named event with a set of key/value pairs. For example:
	 * 
	 * Logg.event("add_activity", 
	 * 				"id", "467", 
	 * 				"type", "frequency"
	 * 				)
	 * 
	 * Would log:
	 *  "add_activity: id=467, type=frequency,"
	 * 
	 *  @param name		name of the event
	 *  @param keys_values		alternating
	 */
	public static void log(String name, Object... keys_values) {
		StringBuilder logMessage = new StringBuilder();

		logMessage.append(name);
		logMessage.append(": ");
		for (int i = 0; i < keys_values.length; i += 2) {
			String key = keys_values[i].toString();
			String value = keys_values[i + 1].toString();
			logMessage.append(key);
			logMessage.append("=");
			logMessage.append(value);
			logMessage.append(", ");
		}
		
		Log.d(EVENT_TAG, logMessage.toString());
	}
}
