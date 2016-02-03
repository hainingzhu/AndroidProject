package kr.mintech.sleep.tight.networks;

import org.apache.http.HttpStatus;

public class NetworkResultUnit
{
	/**
	 * HTTP STATUS CODE (200, 403, 404, 500,....)
	 */
	public int resultCode = HttpStatus.SC_BAD_REQUEST;
	
	/**
	 * resultCode = 200 (success)?
	 */
	public boolean isSuccess = false;
	
	/**
	 * Received string
	 */
	public String resultString;
	
	
	/**
	 * Network result
	 * @param $resultCode HttpStatusCode
	 * @param $resultString Received string
	 */
	public void putResult(int $resultCode, String $resultString)
	{
		resultCode = $resultCode;
		resultString = $resultString.replaceAll("\\\\r", "");
		
		isSuccess = $resultCode < HttpStatus.SC_BAD_REQUEST;
	}
}