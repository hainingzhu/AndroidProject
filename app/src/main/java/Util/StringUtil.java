package Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

public final class StringUtil
{
//												ㄱ      ㄲ      ㄴ      ㄷ      ㄸ      ㄹ      ㅁ      ㅂ       ㅃ       ㅅ      ㅆ      ㅇ      ㅈ       ㅉ      ㅊ      ㅋ       ㅌ      ㅍ      ㅎ
	private final static char[] ChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
//												ㅏ      ㅐ      ㅑ      ㅒ      ㅓ      ㅔ      ㅕ      ㅖ       ㅗ       ㅘ      ㅙ      ㅚ      ㅛ       ㅜ      ㅝ      ㅞ       ㅟ      ㅠ      ㅡ      ㅢ      ㅣ
	private final static char[] JungSung = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161,
			0x3162, 0x3163 };
//														ㄱ      ㄲ      ㄳ      ㄴ      ㄵ      ㄶ      ㄷ       ㄹ       ㄺ      ㄻ      ㄼ      ㄽ       ㄾ      ㄿ      ㅀ       ㅁ       ㅂ      ㅄ      ㅅ      ㅆ      ㅇ      ㅈ       ㅊ      ㅋ      ㅌ       ㅍ      ㅎ
	private final static char[] JongSung = { 0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
			0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	
	
	/**
	 * uuid 생성
	 * @return uuid
	 */
	public static String uuid()
	{
		return UUID.randomUUID().toString();
	}
	
	
	/**
	 * 한글의 제일 앞 자음을 가져온다
	 */
	public static String getJaso(String $str)
	{
		int a; // 자소
		String result = $str;
		char ch = $str.charAt(0);
		
		// "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
		if (ch >= 0xAC00 && ch <= 0xD7A3)
		{
			a = (ch - 0xAC00) / (21 * 28);
			result = ChoSung[a] + "";
		}
		return result;
	}
	
	
	/**
	 * 한글 자소 분리
	 * @param s
	 * @return 김태희 -> ㄱㅣㅁㅌㅐㅎㅢ
	 */
	public static String hangulToJaso(String $str)
	{
		// 유니코드 한글 문자열을 입력 받음
		int a, b, c; // 자소 버퍼: 초성/중성/종성 순
		String result = "";
		
		for (int i = 0; i < $str.length(); i++)
		{
			char ch = $str.charAt(0);
			
			if (ch >= 0xAC00 && ch <= 0xD7A3)
			{ // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
				c = ch - 0xAC00;
				a = c / (21 * 28);
				c = c % (21 * 28);
				b = c / 28;
				c = c % 28;
				result = result + ChoSung[a] + JungSung[b];
				if (c != 0)
					result = result + JongSung[c]; // c가 0이 아니면, 즉 받침이 있으면
			}
			else
			{
				result = result + ch;
			}
		}
		return result;
	}
	
	
	/**
	 * json 규격인가
	 * @param $str
	 * @return
	 */
	public static boolean isJsonString(String $str)
	{
		boolean kResult = true;
		
		if (TextUtils.isEmpty($str))
		{
			kResult = false;
		}
		else if (!$str.startsWith("{") && !$str.startsWith("["))
		{
			kResult = false;
		}
		
		return kResult;
	}
	
	
	/**
	 * str 이 length 길이가 될 때까지 왼쪽을 fill 로 채운다.
	 * @param str 값을 채울 문자열
	 * @param fill 빈값을 채울 문자
	 * @param length str이 이 길이가 될때까지
	 * @return length길이 만큼 fill이 채워진 str
	 */
	public static String lpad(String $str, String $fill, int $length)
	{
		if ($str.length() >= $length)
			return $str;
		else
			return pad($str, $fill, $length, true);
	}
	
	
	public static String lpad(int $str, String $fill, int $length)
	{
		return lpad(Integer.toString($str), $fill, $length);
	}
	
	
	/**
	 * str 이 length 길이가 될 때까지 오른쪽을 fill 로 채운다.
	 * @param str 값을 채울 문자열
	 * @param fill 빈값을 채울 문자
	 * @param length str이 이 길이가 될때까지
	 * @return length길이 만큼 fill이 채워진 str
	 */
	public static String rpad(String $str, String $fill, int $length)
	{
		if ($str.length() >= $length)
			return $str;
		else
			return pad($str, $fill, $length, false);
	}
	
	
	/**
	 * str 이 length 길이가 될 때까지 왼쪽이나 오른쪽을 fill 로 채운다.
	 * @param str 값을 채울 문자열
	 * @param fill 빈값을 채울 문자
	 * @param length str이 이 길이가 될때까지
	 * @param isLeft true=왼쪽에
	 * @return
	 */
	private static String pad(String $str, String $fill, int $length, boolean $isLeft)
	{
		String result, fills = "";
		for (int i = 0; i < $length - $str.length(); i++)
			fills += $fill;
		
		if ($isLeft)
			result = fills + $str;
		else
			result = $str + fills;
		
		return result;
	}
	
	
	/**
	 * Url을 UTF-8 형식으로 변환
	 * @param $url
	 * @return
	 */
	public static String getEncodedUrl(String $url)
	{
		if (TextUtils.isEmpty($url) || !$url.contains("/"))
		{
			return $url;
		}
		
		int kLastIndex = $url.lastIndexOf("/") + 1;
		
		String kPreUrl = $url.substring(0, kLastIndex);
		String kSuffUrl = null;
		
		try
		{
			kSuffUrl = URLEncoder.encode($url.substring(kLastIndex), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		kSuffUrl = kSuffUrl.replaceAll("\\+", "%20");
		
		return kPreUrl + kSuffUrl;
	}
}
