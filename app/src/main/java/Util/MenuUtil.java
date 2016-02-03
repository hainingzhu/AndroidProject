package Util;

import android.view.Menu;
import android.view.MenuItem.OnMenuItemClickListener;

public class MenuUtil
{
	/**
	 * 메뉴 아이템 등록
	 * @param $menu 메뉴
	 * @param $order 순서
	 * @param $title 제목
	 * @param $listener 클릭 리스너
	 * @param $icon 아이콘
	 */
	public static void addMenu(Menu $menu, int $order, String $title, OnMenuItemClickListener $listener, int $icon)
	{
		$menu.add(Menu.NONE, $order, $order, $title).setOnMenuItemClickListener($listener).setIcon($icon);
	}
	
	
	/**
	 * 메뉴 아이템 등록
	 * @param $menu
	 * @param $order
	 * @param $title
	 * @param $listener
	 */
	public static void addMenu(Menu $menu, int $order, String $title, OnMenuItemClickListener $listener)
	{
		$menu.add(Menu.NONE, $order, $order, $title).setOnMenuItemClickListener($listener);
	}
	
	
	/**
	 * 메뉴 아이템 등록
	 * @param $menu
	 * @param $title
	 * @param $listener
	 */
	public static void addMenu(Menu $menu, String $title, OnMenuItemClickListener $listener)
	{
		$menu.add($title).setOnMenuItemClickListener($listener);
	}
}
