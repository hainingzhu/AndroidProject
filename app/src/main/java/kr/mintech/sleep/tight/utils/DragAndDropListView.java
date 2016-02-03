package kr.mintech.sleep.tight.utils;

import kr.mintech.sleep.tight.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class DragAndDropListView extends ListView
{
	
	private Context mContext;
	private ImageView mDragView;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mWindowParams;
	private int mDragPos; // �巡�� �������� ��ġ
	private int mFirstDragPos; // �巡�� �������� �� ��ġ
	private int mDragPoint;
	private int mCoordOffset; // ��ũ�������� ��ġ�� �䳻������ ��ġ�� ����
	private DragListener mDragListener;
	private DropListener mDropListener;
	private int mUpperBound;
	private int mLowerBound;
	private int mHeight;
	private Rect mTempRect = new Rect();
	private Bitmap mDragBitmap;
	private final int mTouchSlop;
	private int mItemHeightNormal;
	private int mItemHeightExpanded;
	private int dndViewId;
	private int dragImageX = 0;
	
	
	public DragAndDropListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if (mDragListener != null || mDropListener != null)
		{
			switch (ev.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					int x = (int) ev.getX();
					int y = (int) ev.getY();
					int itemnum = pointToPosition(x, y);
					if (itemnum == AdapterView.INVALID_POSITION)
					{
						break;
					}
					View item = (View) getChildAt(itemnum - getFirstVisiblePosition()); // �巡�� ������
					mItemHeightNormal = item.getHeight(); // �������� ����
					mItemHeightExpanded = mItemHeightNormal * 2; // �������� �巡�� �Ҷ� ������� ����
					mDragPoint = y - item.getTop();
					mCoordOffset = ((int) ev.getRawY()) - y;
					View dragger = item.findViewById(dndViewId); // �巡�� �̺�Ʈ�� �� �����۳������� ��
					if (dragger == null)
						dragger = item;
					Rect r = mTempRect;
					dragger.getDrawingRect(r);
					if (x < r.right * 2)
					{
						item.setDrawingCacheEnabled(true);
						// �巡�� �ϴ� �������� �̹��� ĸ��
						Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
						startDragging(bitmap, y);
						mDragPos = itemnum;
						mFirstDragPos = mDragPos;
						mHeight = getHeight();
						// ��ũ�Ѹ��� ���� �� ȹ��
						int touchSlop = mTouchSlop;
						mUpperBound = Math.min(y - touchSlop, mHeight / 3);
						mLowerBound = Math.max(y + touchSlop, mHeight * 2 / 3);
						return false;
					}
					mDragView = null;
					break;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if ((mDragListener != null || mDropListener != null) && mDragView != null)
		{
			int action = ev.getAction();
			switch (action)
			{
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					Rect r = mTempRect;
					mDragView.getDrawingRect(r);
					stopDragging();
					if (mDropListener != null && mDragPos <= 10 && mDragPos < getCount())
					{
						mDropListener.drop(mFirstDragPos, mDragPos);
					}
					unExpandViews(false);
					break;
				
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					int x = (int) ev.getX();
					int y = (int) ev.getY();
					dragView(x, y);
					int itemnum = getItemForPosition(y);
					if (itemnum >= 0)
					{
						if (action == MotionEvent.ACTION_DOWN || itemnum != mDragPos)
						{
							if (mDragListener != null)
							{
								mDragListener.drag(mDragPos, itemnum);
							}
							mDragPos = itemnum;
							doExpansion(); // ó�� �巡���� �����۰� �ٸ� ��ġ�� ���� ��� �������� �Ѵ�.
						}
						
						int speed = 0;
						adjustScrollBounds(y); // ��ũ�� ���
						if (y > mLowerBound)
						{
							// ��ũ�� �ֻ���
							speed = y > (mHeight + mLowerBound) / 2 ? 16 : 4;
						}
						else if (y < mUpperBound)
						{
							// ��ũ�� ������
							speed = y < mUpperBound / 2 ? -16 : -4;
						}
						if (speed != 0)
						{
							int ref = pointToPosition(0, mHeight / 2);
							if (ref == AdapterView.INVALID_POSITION)
							{
								//we hit a divider or an invisible view, check somewhere else
								ref = pointToPosition(0, mHeight / 2 + getDividerHeight() + 64);
							}
							View v = getChildAt(ref - getFirstVisiblePosition());
							if (v != null)
							{
								int pos = v.getTop();
								setSelectionFromTop(ref, pos - speed);
							}
						}
					}
					break;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	
	private int getItemForPosition(int y)
	{
		int adjustedy = y - mDragPoint - 32;
		int pos = myPointToPosition(0, adjustedy);
		if (pos >= 0)
		{
			if (pos <= mFirstDragPos)
			{
				pos += 1;
			}
		}
		else if (adjustedy < 0)
		{
			pos = 0;
		}
		return pos;
	}
	
	
	private int myPointToPosition(int x, int y)
	{
		Rect frame = mTempRect;
		final int count = getChildCount();
		for (int i = count - 1; i >= 0; i--)
		{
			final View child = getChildAt(i);
			child.getHitRect(frame);
			if (frame.contains(x, y))
			{
				return getFirstVisiblePosition() + i;
			}
		}
		return INVALID_POSITION;
	}
	
	
	private void adjustScrollBounds(int y)
	{
		if (y >= mHeight / 3)
		{
			mUpperBound = mHeight / 3;
		}
		if (y <= mHeight * 2 / 3)
		{
			mLowerBound = mHeight * 2 / 3;
		}
	}
	
	
	private void doExpansion()
	{
		int childnum = mDragPos - getFirstVisiblePosition();
		if (mDragPos > mFirstDragPos)
		{
			childnum++;
		}
		
		View first = getChildAt(mFirstDragPos - getFirstVisiblePosition());
		
		for (int i = 0;; i++)
		{
			View vv = getChildAt(i);
			if (vv == null)
			{
				break;
			}
			int height = mItemHeightNormal;
			int visibility = View.VISIBLE;
			if (vv.equals(first))
			{
				
				if (mDragPos == mFirstDragPos)
				{
					visibility = View.INVISIBLE;
				}
				else
				{
					height = 1;
				}
			}
			else if (i == childnum)
			{
				if (mDragPos < getCount() - 1)
				{
					height = mItemHeightExpanded;
				}
			}
			ViewGroup.LayoutParams params = vv.getLayoutParams();
			params.height = height;
			vv.setLayoutParams(params);
			vv.setVisibility(visibility);
		}
	}
	
	
	private void unExpandViews(boolean deletion)
	{
		for (int i = 0;; i++)
		{
			View v = getChildAt(i);
			if (v == null)
			{
				if (deletion)
				{
					int position = getFirstVisiblePosition();
					int y = getChildAt(0).getTop();
					setAdapter(getAdapter());
					setSelectionFromTop(position, y);
				}
				layoutChildren();
				v = getChildAt(i);
				if (v == null)
				{
					break;
				}
			}
			ViewGroup.LayoutParams params = v.getLayoutParams();
			params.height = mItemHeightNormal;
			v.setLayoutParams(params);
			v.setVisibility(View.VISIBLE);
		}
	}
	
	
	// �巡�� ����
	private void startDragging(Bitmap bm, int y)
	{
		stopDragging();
		
		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP;
		mWindowParams.x = dragImageX;
		mWindowParams.y = y - mDragPoint + mCoordOffset;
		
		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		mWindowParams.format = PixelFormat.TRANSLUCENT;
		mWindowParams.windowAnimations = 0;
		
		ImageView v = new ImageView(mContext);
		int backGroundColor = getResources().getColor(R.color.LightGray);
		v.setBackgroundColor(backGroundColor);
		v.setImageBitmap(bm);
		mDragBitmap = bm;

		////Context.WINDOW_SERVICE  Change "window"
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mWindowManager.addView(v, mWindowParams);
		mDragView = v;
	}
	
	
	// �巡�׸� ���� ����� �� ���� �̵�
	private void dragView(int x, int y)
	{
		mWindowParams.y = y - mDragPoint + mCoordOffset;
		mWindowManager.updateViewLayout(mDragView, mWindowParams);
	}
	
	
	// �巡�� ���� ó��
	private void stopDragging()
	{
		if (mDragView != null)
		{
			//////Context.WINDOW_SERVICE  Change "window"
			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			wm.removeView(mDragView);
			mDragView.setImageDrawable(null);
			mDragView = null;
		}
		if (mDragBitmap != null)
		{
			mDragBitmap.recycle();
			mDragBitmap = null;
		}
	}
	
	
	/**
	 * �巡�� �̺�Ʈ ������ ���
	 * @param l �巡�� �̺�Ʈ ������
	 */
	public void setDragListener(DragListener l)
	{
		mDragListener = l;
	}
	
	
	/**
	 * ��� �̺�Ʈ ������ ���
	 * @param l ��� �̺�Ʈ ������
	 */
	public void setDropListener(DropListener l)
	{
		mDropListener = l;
	}
	
	
	/**
	 * ����Ʈ �����ۿ� �ִ� ��� �� �巡�� ��� �̺�Ʈ�� �߻��ų ���� ����
	 * @param id �巡�� ��� �̺�Ʈ�� �߻��ų ���� ���̵�
	 */
	public void setDndView(int id)
	{
		dndViewId = id;
	}
	
	
	/**
	 * �巡�׽� ǥ�õǴ� ���� ��ũ�������� left padding
	 * @param x ��ũ�������� left padding, �������ϸ� 0
	 */
	public void setDragImageX(int x)
	{
		dragImageX = x;
	}
	
	public interface DragListener
	{
		void drag(int from, int to);
	}
	
	public interface DropListener
	{
		void drop(int from, int to);
	}
}
