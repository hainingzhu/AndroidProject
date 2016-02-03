package Util;

import java.util.ArrayList;

public class GestureUtil {
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int CW = 2;
	public final static int CCW = 3;

	
	private ArrayList<Integer> _xLst;
	private ArrayList<Integer> _yLst;
	private final int _EVENT_TIME = 5;
	private final int _IGNORE_DIS = 10;
	private int _centerX;
	private int _centerY;
	
	public GestureUtil(int $centerX, int $centerY){
		initializePosArr();
		_centerX = $centerX;
		_centerY = $centerY;
	}
	
	private void initializePosArr(){
		_xLst = new ArrayList<Integer>();
		_yLst = new ArrayList<Integer>();
	}
		
	
	public int getGestureDirection(int $x, int $y){
	
		_xLst.add($x);
		_yLst.add($y);
		
		if(_xLst.size()<_EVENT_TIME) return -1;
		
		double kDisX = _xLst.get(_EVENT_TIME-1) - _xLst.get(0);
		double kDisY = _yLst.get(_EVENT_TIME-1) - _yLst.get(0);
		
		double kDis = Math.sqrt((double)(kDisX*kDisX)+(double)(kDisY*kDisY));
		
		if(kDis<_IGNORE_DIS)	{
			initializePosArr();
			return -1;
		}
		
		//---------------------------------
		int kDir = -1;
		
		double kAbsDisX = Math.abs(kDisX);
		double kAbsDisY = Math.abs(kDisY);
		try{
			if(kAbsDisY<_IGNORE_DIS/2){
				if(kDisX>0){
					kDir =  RIGHT;
				}else{
					kDir =  LEFT;
				}
			}else if(kAbsDisX<_IGNORE_DIS/2){
					//--상하로 움직이는 것은 아무런 의미가 없다.
					kDir =  -1;
			}else{
				double kRadian_0 = GeoUtil.getRadianWithAnchorAndMoveXY((float)_centerX, (float)_centerY, (float)_xLst.get(0), (float)_yLst.get(0));
				double kRadian_1 = GeoUtil.getRadianWithAnchorAndMoveXY((float)_centerX, 
						(float)_centerY, 
						(float)_xLst.get(_EVENT_TIME-1), 
						(float)_yLst.get(_EVENT_TIME-1));
				
				if(kRadian_1>kRadian_0){
					kDir =  CW;
				}else{
					kDir =  CCW;
				}
			}
		}catch(Exception $e){
			
		}
		
		
		initializePosArr();
		return kDir;
	}
	
}
