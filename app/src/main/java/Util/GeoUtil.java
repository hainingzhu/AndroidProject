package Util;



public class GeoUtil
{
	
	public static int getAt(String[] $arr, String $str, int $default)
	{
		int kLen = $arr.length;
		for (int i = 0; i < kLen; i++)
		{
			if ($str.equalsIgnoreCase($arr[i]))
			{
				return i;
			}
		}
		return $default;
	}
	
	public static int getAt(int[] $arr, int $int, int $default)
	{
		int kLen = $arr.length;
		for (int i = 0; i < kLen; i++)
		{
			if ($int == $arr[i])
			{
				return i;
			}
		}
		return $default;
	}
	
	
	
	public static float getRadianFromAngle(float $angle)
	{
		return (float) ($angle / (180 / Math.PI));
	}
	
	public static double getDegreeWithAnchorAndMoveXY(float $anchorX, float $anchorY, float $moveX, float $moveY)
	{
		float kDisX = $moveX - $anchorX;
		float kDisY = $moveY - $anchorY;
		
		double kRadian = Math.atan2((double) kDisY, (double) kDisX);
		double kDegree = Math.toDegrees(kRadian);
		
		return kDegree;
		
	}
	
	public static double getRadianWithAnchorAndMoveXY(float $anchorX, float $anchorY, float $moveX, float $moveY)
	{
		float kDisX = $moveX - $anchorX;
		float kDisY = $moveY - $anchorY;
		
		double kRadian = Math.atan2((double) kDisY, (double) kDisX);
		
		return kRadian;
	}
	
	
	
	public static float getScaleRatioToFit(float $originalWid, float $originalHei, float $frameWid, float $frameHei,
			boolean $stretch)
	{
		float kWidRatio = $originalWid / $frameWid;
		float kHeiRatio = $originalHei / $frameHei;
		float kRatio = 1.0f;
		if ($stretch)
		{
			float kMin = Math.min(kWidRatio, kHeiRatio);
			kRatio = 1.0f / kMin;
		}
		else
		{
			if (kWidRatio < 1 && kHeiRatio < 1)
			{
				kRatio = 1.0f;
			}
			else
			{
				float kMax = Math.max(kWidRatio, kHeiRatio);
				kRatio = 1.0f / kMax;
			}
		}
		return kRatio;
	}
	
	
	
	/**
	 * 랜덤 int 구하기
	 * @param min 최소값
	 * @param max 최대값
	 * @return
	 */
	public static int getRandomNumber(int $min, int $max)
	{
		return $min + (int) (Math.random() * ($max - $min));
	}
	
	/**
	 * 랜덤 float 구하기
	 * @param min 최소값
	 * @param max 최대값
	 * @return
	 */
	public static float getRandomNumber(float $min, float $max)
	{
		return (float) ($min + (Math.random() * ($max - $min)));
	}
	
	
}
