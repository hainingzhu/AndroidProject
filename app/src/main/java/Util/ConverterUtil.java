package Util;

public class ConverterUtil
{
	public static byte[] hexaToByteArray(String $hexa)
	{
		if ($hexa == null || $hexa.length() == 0 || $hexa.length() % 2 == 1)
			return null;
		
		byte[] kByteArr = new byte[$hexa.length() / 2];
		for (int i = 0; i < kByteArr.length; i++)
			kByteArr[i] = (byte) Short.parseShort($hexa.substring(2 * i, 2 * i + 2), 16);
		
		return kByteArr;
	}
	
	
	public static String intToIP(int $num)
	{
		return ($num & 0xFF) + "." + (($num >> 8) & 0xFF) + "." + (($num >> 16) & 0xFF) + "." + (($num >> 24) & 0xFF);
	}
	
	
	public static String intToString(int $num)
	{
		return Integer.toString($num);
	}
	
	
	public static int stringToInt(String $str)
	{
		int kNum = -1;
		try
		{
			kNum = Integer.parseInt($str);
		} catch (NumberFormatException $e)
		{
			
		}
		return kNum;
	}
	
	
	public static double stringToDouble(String $str)
	{
		return Double.valueOf($str).doubleValue();
	}
	
	
	public static double stringToFloat(String $str)
	{
		return Float.valueOf($str).floatValue();
	}
	
	
	public static String decimalToBinary(int $num)
	{
		return Integer.toBinaryString($num);
	}
	
	
	public static String decimalToHexa(int $num)
	{
		return Integer.toString($num, 16);
	}
	
	
	public static int hexaToInt(String $hexa)
	{
		return Integer.parseInt($hexa, 16);
	}
	
	
	public static String AsciiToString(int $code)
	{
		return Character.valueOf((char) ($code)).toString();
	}
	
	
	public static boolean stringToBoolean(String $str)
	{
		boolean result = false;
		
		boolean isInt = false;
		int intResult = 0;
		
		try
		{
			intResult = Integer.parseInt($str);
			isInt = true;
		} catch (Exception e)
		{
		}
		
		if (isInt)
		{
			result = intResult == 1;
		}
		else
		{
			result = Boolean.parseBoolean($str);
		}
		
		return result;
	}
	
	
	public static boolean intToBoolean(int $int)
	{
		return stringToBoolean($int + "");
	}
}
