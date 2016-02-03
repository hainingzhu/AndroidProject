package Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public final class NumberUtil
{
	/**
	 * 콤마를 넣는다.
	 * @param $value
	 * @return
	 */
	public static String addComma(String $value)
	{
		String kResult = $value;
		
		DecimalFormat kFormat = new DecimalFormat("#,###");
		
		try
		{
			// 소수점이 있을 땐, 소수점 앞 쪽의 숫자만 변환
			int kIndex = $value.indexOf(".");
			if ($value.indexOf(".") >= 0)
			{
				String kFront = $value.substring(0, kIndex);
				Number kNum = NumberFormat.getInstance().parse(kFront);
				kFront = kFormat.format(kNum).toString();
				String kEnd = $value.substring(kIndex + 1, $value.length());
				
				kResult = kFront + "." + kEnd;
			}
			else
			{
				Number kNum = NumberFormat.getInstance().parse($value);
				kResult = kFormat.format(kNum).toString();
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return kResult;
	}
	
	
	/**
	 * 콤마를 넣는다
	 * @param $value
	 * @return
	 */
	public static String addComma(int $value)
	{
		return addComma(Integer.toString($value));
	}
	
	
	/**
	 * 1000으로 나누고 소수점 붙인 (줄인 숫자) 30000원 -> 30.0
	 * @param $value
	 * @return
	 */
	public static String addCommaWithPoint(String $value)
	{
		String kResult = $value;
		
		DecimalFormat kFormat = new DecimalFormat("#,###0");
		try
		{
			int kIndex = $value.indexOf(".");
			if ($value.indexOf(".") >= 0)
			{
				String kFront = $value.substring(0, kIndex);
				Number kNum = NumberFormat.getInstance().parse(kFront);
				kFront = kFormat.format(kNum).toString();
				String kEnd = $value.substring(kIndex + 1, $value.length());
				
				kResult = kFront + "." + kEnd;
			}
			else
			{
				Number kNum = NumberFormat.getInstance().parse($value);
				kResult = kFormat.format(kNum).toString();
			}
			
			if (kResult.length() == 3)
			{
				kResult = kResult.substring(0, 2) + "." + kResult.substring(2);
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return kResult;
	}
	
	
	/**
	 * 콤마를 없앤다
	 * @param $value 콤마가 들어간 숫자
	 * @return
	 */
	public static String removeComma(String $value)
	{
		if ($value == null)
			return null;
		
		NumberFormat format = NumberFormat.getInstance();
		Number num;
		try
		{
			num = format.parse($value);
			return num.toString();
		} catch (ParseException e)
		{
			return null;
		}
	}
	
	
	/**
	 * 랜덤 int 구하기
	 * @param min 최소값
	 * @param max 최대값
	 * @return
	 */
	public static int random(int $min, int $max)
	{
		return $min + (int) (Math.random() * ($max - $min));
	}
	
	
	/**
	 * 랜덤 float 구하기
	 * @param min 최소값
	 * @param max 최대값
	 * @return
	 */
	public static float random(float $min, float $max)
	{
		return (float) ($min + (Math.random() * ($max - $min)));
	}
	
	
	/**
	 * 숫자인가?
	 * @param $object
	 * @return
	 */
	public static boolean isNumber(Object $object)
	{
		try
		{
			NumberFormat num = NumberFormat.getInstance();
			num.parse($object.toString());
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
}
