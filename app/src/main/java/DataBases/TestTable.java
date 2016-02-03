package DataBases;

import static android.provider.BaseColumns._ID;
import Util.Logg;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TestTable extends BaseTestTable
{
	protected static TestTable _instance;
	
	// Table
	public final static String TABLE = "tests";
	
	// Fields
	public static final String DATE = "date";
	public static final String NAME = "name";
	
	// Frequently used fields
	private static final String[] COLUMNS = { _ID, DATE, NAME };
	
	// Sorting
	private static final String ORDER_BY_DEFAULT = DATE + " asc, " + _ID + " asc";
	private static final String ORDER_BY_NAME = NAME + " asc, " + _ID + " asc";
	private static final String ORDER_BY_DATE_DESC = DATE + " desc, " + _ID + " desc";
	private static final String ORDER_BY_NAME_DESC = NAME + " desc, " + _ID + " desc";
	
	// Query
	private static final String WHERE_BY_ID = _ID + "=?";
	private static final String WHERE_BY_DATE = "strftime('%Y'," + DATE + ")=?";
	
	// Create sql
	public static final String createSql = "CREATE TABLE if not exists " + TABLE + "(" + _ID + " integer primary key autoincrement, " + DATE + " text," + NAME + " text);";
	
	public synchronized static TestTable getInst(Context $context)
	{
		if (_instance == null)
			_instance = new TestTable($context);
		return _instance;
	}
	
	private TestTable(Context $context)
	{
		super($context);
	}
	
	/**
	 * Insert
	 * @param $date 
	 * @param $name 
	 * @return
	 */
	public int insert(String $date, String $name)
	{
		ContentValues values = new ContentValues();
		values.put(DATE, $date.trim());
		values.put(NAME, $name.trim());
		getDB().insertOrThrow(TABLE, null, values);
		return super.insert();
	}
	
	
	/**
	 * sort by name
	 * @param isDesc true = reverse order
	 * @return
	 */
	public Cursor getByName(boolean $isDesc)
	{
		String order = ORDER_BY_NAME;
		if ($isDesc)
			order = ORDER_BY_NAME_DESC;
		Cursor c = getDB().query(TABLE, COLUMNS, null, null, null, null, order);
		
		Logg.i("TestTable | getByName()", c.getCount() + "개");
		
		return c;
	}
	
	
	/**
	 * sort by date
	 * @param isDesc true = reverse order
	 * @return
	 */
	public Cursor getByDate(boolean $isDesc)
	{
		String order = ORDER_BY_DEFAULT;
		if ($isDesc)
			order = ORDER_BY_DATE_DESC;
		Cursor c = getDB().query(TABLE, COLUMNS, null, null, null, null, order);
		
		Logg.i("TestTable | getByDate()", c.getCount() + "개");
		
		return c;
	}
	
	
	/**
	 * Search by year
	 * @param $year 2011
	 * @return
	 */
	public Cursor getByDate(String $year)
	{
		String[] selectionArgs = { $year };
		return getDB().query(TABLE, COLUMNS, WHERE_BY_DATE, selectionArgs, null, null, ORDER_BY_DEFAULT);
	}
	
	
	/**
	 * Update
	 * @param id
	 * @param name
	 * @param date
	 * @return
	 */
	public int update(String $id, String $name, String $date)
	{
		ContentValues values = new ContentValues();
		values.put(NAME, $name);
		values.put(DATE, $date);
		
		String[] whereArgs = { $id };
		
		return getDB().update(TABLE, values, WHERE_BY_ID, whereArgs);
	}
	
	
	/**
	 * Delete all data
	 */
	public void deleteAll()
	{
		getDB().delete(TABLE, null, null);
	}
	
	
	/**
	 * Delete data associated with a certain id
	 * @param $id
	 * @return
	 */
	public int deleteItem(String $id)
	{
		String[] whereArgs = { $id };
		return getDB().delete(TABLE, WHERE_BY_ID, whereArgs);
	}
}
