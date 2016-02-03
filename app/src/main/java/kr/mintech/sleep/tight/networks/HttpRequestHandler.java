package kr.mintech.sleep.tight.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.mintech.sleep.tight.utils.PreferenceUtil;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import Util.ContextUtil;
import Util.Logg;
import Util.SystemUtil;

public class HttpRequestHandler
{
	protected DefaultHttpClient _client;
	protected static final int _DEFAULT_TIME_OUT = 15000;
	
	
	public HttpRequestHandler()
	{
		this(_DEFAULT_TIME_OUT);
	}
	
	
	public HttpRequestHandler(int $timeOut)
	{
		HttpParams kParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(kParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(kParams, HTTP.UTF_8);
		//--response time-out setting 
		HttpConnectionParams.setSoTimeout(kParams, $timeOut);
		HttpConnectionParams.setConnectionTimeout(kParams, $timeOut);
		_client = new DefaultHttpClient(kParams);
	}
	
	
	private void closeConnections()
	{
		if (_client != null)
		{
			_client.getConnectionManager().closeExpiredConnections();
		}
	}
	
	
	/**
	 * Request post response
	 * @param $hostAddr
	 * @param $content
	 * @return
	 */
	public NetworkResultUnit requestPost(String $hostAddr, String $content)
	{
		BufferedReader kReader = null;
		NetworkResultUnit kUnit = new NetworkResultUnit();
		
		try
		{
			HttpPost kRequest = new HttpPost($hostAddr);
			
			kRequest.setHeader("Content-type", "application/json");
			kRequest.setHeader("Connection", "Close");
			kRequest.setHeader("x-uuid", PreferenceUtil.getAndroidId());
			StringEntity kStringEntity = new StringEntity($content, HTTP.UTF_8);
			
			kRequest.setEntity(kStringEntity);
			
			HttpResponse kResponse = _client.execute(kRequest);
			int kStatusCode = kResponse.getStatusLine().getStatusCode();
			String kResult = EntityUtils.toString(kResponse.getEntity());
			// CLEANUP // Logg.i("HttpRequestHandler | requestPost()", "request post response : " + kStatusCode + " | " + (kStatusCode == HttpStatus.SC_OK) + " | " + kResult);
			
			kUnit.putResult(kStatusCode, kResult);
		} catch (Exception $e)
		{
			$e.getMessage();
		} finally
		{
			if (kReader != null)
			{
				try
				{
					kReader.close();
				} catch (IOException $e)
				{
				}
			}
		}
		closeConnections();
		return kUnit;
	}//Method
	
	
	/**
	 * Request get response
	 *
	 * @return
	 */
	public NetworkResultUnit requestGet(String $hostAddr)
	{
		BufferedReader kReader = null;
		NetworkResultUnit kUnit = new NetworkResultUnit();

		try
		{
			HttpGet kRequest = new HttpGet();
			kRequest.setURI(new URI($hostAddr));
			kRequest.setHeader("Connection", "Close");
			kRequest.setHeader("x-uuid", PreferenceUtil.getAndroidId());
			// CLEANUP // Logg.w("HttpRequestHandler | requestGet()", "UUID : " + PreferenceUtil.getAndroidId());
			HttpResponse kResponse = _client.execute(kRequest);
			int kStatusCode = kResponse.getStatusLine().getStatusCode();
			String kResult = EntityUtils.toString(kResponse.getEntity());
			// CLEANUP // Logg.i("HttpRequestHandler | requestGet()", "request get response : " + kStatusCode + " | " + (kStatusCode == HttpStatus.SC_OK) + " | " + kResult);
			
			kUnit.putResult(kStatusCode, kResult);
		} catch (Exception $e)
		{
			$e.printStackTrace();
		} finally
		{
			if (kReader != null)
			{
				try
				{
					kReader.close();
				} catch (IOException $e)
				{
					$e.printStackTrace();
				}
			}
		}
		closeConnections();
		return kUnit;
	}//Method
	
	
	/**
	 * Request put response
	 * @param $hostAddr : server host address
	 * @param $content : content to be updated to the server
	 * @return data received from the server
	 */
	
	public NetworkResultUnit requestPut(String $hostAddr, String $content)
	{
		return requestPut($hostAddr, $content, "Close");
	}//Method
	
	
	public NetworkResultUnit requestPut(String $hostAddr, String $content, String $connectPolicy)
	{
		String kResult = null;
		BufferedReader kReader = null;
		NetworkResultUnit kUnit = new NetworkResultUnit();
		
		try
		{
			HttpPut kRequest = new HttpPut($hostAddr);
			
			kRequest.setHeader("Content-Type", "application/json");
			kRequest.setHeader("Connection", $connectPolicy);
			kRequest.setHeader("x-uuid", PreferenceUtil.getAndroidId());
			
			HttpResponse kResponse = _client.execute(kRequest);
			int kStatusCode = kResponse.getStatusLine().getStatusCode();
			// CLEANUP // Logg.i("HttpRequestHandler | requestPut()", "http status : " + kStatusCode + " | " + (kStatusCode == HttpStatus.SC_OK));
			
			kResult = EntityUtils.toString(kResponse.getEntity());
			// CLEANUP // Logg.i("HttpRequestHandler | requestPut()", "request put response : " + kResult);
			kUnit.putResult(kStatusCode, kResult);
		} catch (Exception $e)
		{
			kResult = $e.getMessage();
		} finally
		{
			if (kReader != null)
			{
				try
				{
					kReader.close();
				} catch (IOException $e)
				{
				}
			}
		}
		closeConnections();
		return kUnit;
	}//Method
	
	
	/**
	 * Delete request
	 * @param $hostAddr : server host address
	 * @return data received from the server
	 */
	
	public NetworkResultUnit requestDelete(String $hostAddr)
	{
		return requestDelete($hostAddr, "Close");
	}//Method
	
	
	public NetworkResultUnit requestDelete(String $hostAddr, String $connectPolicy)
	{
		String kResult = null;
		BufferedReader kReader = null;
		NetworkResultUnit kUnit = new NetworkResultUnit();
		try
		{
			HttpDelete kRequest = new HttpDelete($hostAddr);
			
			kRequest.setHeader("Content-Type", "application/json");
			kRequest.setHeader("Connection", $connectPolicy);
			kRequest.setHeader("x-uuid", PreferenceUtil.getAndroidId());
			
			HttpResponse kResponse = _client.execute(kRequest);
			int kStatusCode = kResponse.getStatusLine().getStatusCode();
			// CLEANUP // Logg.i("HttpRequestHandler | requestDelete()", "http status : " + kStatusCode + " | " + (kStatusCode == HttpStatus.SC_OK));
			
			kResult = EntityUtils.toString(kResponse.getEntity());
			// CLEANUP // Logg.i("HttpRequestHandler | requestDelete()", "request Delete response : " + kResult);
			
			kUnit.putResult(kStatusCode, kResult);
		} catch (Exception $e)
		{
			kResult = $e.getMessage();
		} finally
		{
			if (kReader != null)
			{
				try
				{
					kReader.close();
				} catch (IOException $e)
				{
				}
			}
		}
		closeConnections();
		return kUnit;
	}//Method
	
}
