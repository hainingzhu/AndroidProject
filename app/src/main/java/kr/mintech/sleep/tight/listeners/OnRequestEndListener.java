package kr.mintech.sleep.tight.listeners;

/**
 * After finishing the download 
 * @author susemi99
 */
public interface OnRequestEndListener
{
	
	void onRequest(Object $unit);
	
	
	void onRequestEnded(int $tag, Object $object);
	
	
	void onRequestError(int $tag, String $errorStr);
}
