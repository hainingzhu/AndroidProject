package DataBases;

import Util.Logg;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseTestTable
{
	protected SQLiteOpenHelper _helper;
	
	
	protected BaseTestTable(Context $context)
	{
		_helper = new BaseDatabaseHelper($context);
	}
	
	
	protected SQLiteDatabase getDB()
	{
		return _helper.getWritableDatabase();
	}
	
	
	/**
	 * Close db
	 */
	public void close()
	{
		getDB().close();
	}
	
	
	protected int insert()
	{
		SQLiteDatabase db = _helper.getWritableDatabase();
		// Returns the last rowid
		String sql = "SELECT last_insert_rowid();";
		Cursor c = db.rawQuery(sql, null);
		
		int result = 0;
		c.moveToFirst();
		if (c.getCount() > 0)
			result = c.getInt(0);
		c.close();
		
		Logg.w("BaseTestTable | insert()", "lowid : " + result +" inserted");
		return result;
	}
}
