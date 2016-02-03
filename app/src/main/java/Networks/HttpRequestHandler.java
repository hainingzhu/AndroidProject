package Networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import Util.Logg;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class HttpRequestHandler
{
	protected DefaultHttpClient _client;
	protected static final int _DEFAULT_TIME_OUT = 6000;
	
	
	public HttpRequestHandler()
	{
		this(_DEFAULT_TIME_OUT);
	}
	
	
	public HttpRequestHandler(int $timeOut)
	{
		HttpParams kParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(kParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(kParams, HTTP.UTF_8);
		//--response time-out 설정 
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
	 * Server communication 
	 * @param $hostAddr Server host address
	 * @param $content Content to be saved to the server
	 * @return Data received from the server
	 */
	
	public String requestPut(String $hostAddr, String $content)
	{
		return requestPut($hostAddr, $content, "Close");
	}//Method
	
	
	public String requestPut(String $hostAddr, String $content, String $connectPolicy)
	{
		String kResult = null;
		BufferedReader kReader = null;
		try
		{
			HttpPut kRequest = new HttpPut($hostAddr);
			
			kRequest.setHeader("Content-Type", "application/json");
			kRequest.setHeader("Connection", $connectPolicy);
			StringEntity kStringEntity = new StringEntity($content, HTTP.UTF_8);
			
			kRequest.setEntity(kStringEntity);
			
			HttpResponse kResponse = _client.execute(kRequest);
			kResult = EntityUtils.toString(kResponse.getEntity());
//			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
//			kResult = convertReaderToString(kReader);
			
			// CLEANUP // Logg.i("HttpRequestHandler | requestPut()", "request put response : " + kResult);
		} catch (Exception $e)
		{
			kResult = getExceptionResult($e);
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
		return kResult;
	}
	
	public String requestPost(String $hostAddr, String $content)
	{
		return requestPost($hostAddr, $content, "Close");
	}
	
	public String requestPost(String $hostAddr, String $content, String $connectPolicy)
	{
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
			kResult = EntityUtils.toString(kResponse.getEntity());
//			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
//			kResult = convertReaderToString(kReader);
			
			// CLEANUP // Logg.i("HttpRequestHandler | requestPost()", "request post response : " + kResult);
		} catch (Exception $e)
		{
			kResult = getExceptionResult($e);
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
		return kResult;
	}//Method
	
	
	public String requestPostWithJson(String $hostAddr, String $content)
	{
		return requestPostWithJson($hostAddr, $content, "Close");
	}//Method
	
	public String requestPostWithJson(String $hostAddr, String $content, String $connectPolicy)
	{
		String kResult = null;
		BufferedReader kReader = null;
		try
		{
			HttpPost kRequest = new HttpPost($hostAddr);
			
			kRequest.setHeader("Content-Type", "application/json");
			kRequest.setHeader("Connection", $connectPolicy);
			StringEntity kStringEntity = new StringEntity($content, HTTP.UTF_8);
			
			kRequest.setEntity(kStringEntity);
			
			HttpResponse kResponse = _client.execute(kRequest);
			kResult = EntityUtils.toString(kResponse.getEntity());
//			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
//			kResult = convertReaderToString(kReader);
			
			// CLEANUP // Logg.i("HttpRequestHandler | requestPost()", "request post response : " + kResult);
		} catch (Exception $e)
		{
			kResult = getExceptionResult($e);
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
			kResult = EntityUtils.toString(kResponse.getEntity());
//			kReader = new BufferedReader(new InputStreamReader(kResponse.getEntity().getContent()));
//			kResult = convertReaderToString(kReader);
			
			// CLEANUP // Logg.i("HttpRequestHandler | requestGet()", "request get  response : " + kResult);
		} catch (Exception $e)
		{
			kResult = getExceptionResult($e);
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
		return kResult;
	}//Method
	
	
	/**
	 * For loading bitmap
	 * @param $hostAddr
	 * @return BitmapDrawable
	 */
	
	public BitmapDrawable requestGetImage(String $hostAddr, String $contentType)
	{
		// CLEANUP // Logg.i("HttpRequestHandler | requestGetImage()", "requestGetImage host Address ================ " + $hostAddr);
		InputStream kInputStream = null;
		BitmapDrawable kDrawable = null;
		try
		{
			HttpGet kRequest = new HttpGet();
			kRequest.setURI(new URI($hostAddr));
			kRequest.setHeader("Connection", "Close");
			kRequest.setHeader("Content-Type", $contentType);
			
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
		closeConnections();
		return kDrawable;
	}//Method
	
	
	//For Override
	protected String getExceptionResult(Exception $e)
	{
		return null;
	}
	
	
	protected String convertReaderToString(BufferedReader $reader) throws Exception
	{
		StringBuilder kResult = new StringBuilder();
		String kLine = null;
		
		while ((kLine = $reader.readLine()) != null)
		{
			kResult.append(kLine);
		}
		
		return kResult.toString();
	}
}
