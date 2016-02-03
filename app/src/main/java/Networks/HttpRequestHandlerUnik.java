package Networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import Util.Logg;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class HttpRequestHandlerUnik
{
	private DefaultHttpClient _client;
	private static HttpRequestHandlerUnik _instance;
	
	
	//private final static int _READ_TIME_OUT = 5000;
	
	public static HttpRequestHandlerUnik newInst()
	{
		if (_instance != null)
			_instance.closeConnections();
		
		_instance = new HttpRequestHandlerUnik();
		
		return _instance;
	}
	
	
	public static HttpRequestHandlerUnik getInst()
	{
		if (_instance == null)
			_instance = new HttpRequestHandlerUnik();
		
		return _instance;
	}
	
	
	private HttpRequestHandlerUnik()
	{
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		_client = new DefaultHttpClient(params);
	}
	
	
	private void closeConnections()
	{
		if (_client != null)
		{
			_client.getConnectionManager().closeExpiredConnections();
		}
	}
	
	public String requestPost(String $hostAddr, String $content)
	{
		return requestPost($hostAddr, $content, "Close");
	}
	
	
	public String requestPost(String $hostAddr, String $content, String $connectPolicy)
	{
		Logg.i("HttpRequestHandlerUnik | requestPost()", "requestPost content : " + $content);
		String kResult = null;
		BufferedReader kReader = null;
		try
		{
			
			HttpPost kRequest = new HttpPost($hostAddr);
			
			kRequest.setHeader("Content-Type", "application/atom+xml");
			kRequest.setHeader("Connection", $connectPolicy);
			StringEntity kStringEntity = new StringEntity($content, HTTP.UTF_8);
			
			kRequest.setEntity(kStringEntity);
			
			HttpResponse kResponse = _client.execute(kRequest);
			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
			kResult = convertReaderToString(kReader);
			
			Logg.i("HttpRequestHandlerUnik | requestPost()", "request post response : " + kResult);
			
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
		//closeConnections();
		return kResult;
	}//Method
	
	public String requestGet(String $hostAddr)
	{
		String kResult = null;
		BufferedReader kReader = null;
		try
		{
			
			HttpGet kRequest = new HttpGet();
			kRequest.setURI(new URI($hostAddr));
			kRequest.setHeader("Connection", "Close");
			
			HttpResponse kResponse = _client.execute(kRequest);
			
			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
			
			kResult = convertReaderToString(kReader);
			
			Logg.i("HttpRequestHandlerUnik | requestGet()", "request get  response : " + kResult);
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
		// closeConnections();
		return kResult;
	}//Method
	
	
	/**
	 * For loading bitmap
	 * @param $hostAddr
	 * @return BitmapDrawable
	 */
	
	public BitmapDrawable requestGetImage(String $hostAddr)
	{
		Logg.i("HttpRequestHandlerUnik | requestGetImage()", "requestGetImage host Address ================ " + $hostAddr);
		InputStream kInputStream = null;
		BitmapDrawable kDrawable = null;
		try
		{
			HttpGet kRequest = new HttpGet();
			kRequest.setURI(new URI($hostAddr));
			kRequest.setHeader("Connection", "Close");
			
			HttpResponse kResponse = _client.execute(kRequest);
			
			HttpEntity kEntity = kResponse.getEntity();
			BufferedHttpEntity kBufEntity = new BufferedHttpEntity(kEntity);
			
			kInputStream = kBufEntity.getContent();
			Bitmap kBmp = BitmapFactory.decodeStream(kInputStream);
			if (kBmp != null)
			{
				kDrawable = new BitmapDrawable(kBmp);
			}
		} catch (Exception $e)
		{
			$e.printStackTrace();
		} finally
		{
			if (kInputStream != null)
			{
				try
				{
					kInputStream.close();
				} catch (IOException $e)
				{
					$e.printStackTrace();
				}
			}
		}
		//closeConnections();
		return kDrawable;
	}//Method
	
	
	private String convertReaderToString(BufferedReader $reader) throws Exception
	{
		String kLine = $reader.readLine();
		return kLine;
	}
}
