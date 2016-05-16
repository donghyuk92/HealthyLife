package android.example.com.healthylife.PhotoDiary;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDiary {
	DatabaseHelper mHelper;

	/**
    * The database that the provider uses as its underlying data store
    */
    private static final String DATABASE_NAME = "diary.db";
    
    /**
     * The database that the provider uses as its underlying data store
     */
 	private static final String DATABASE_TABLE_NAME = "tblDiary";
 	
 	/**
     * Column name for the id of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ID = "id";
    
    /**
     * Column name for the requestCode of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATE = "date";
    
    public static final String COLUMN_NAME_DATEOFWEEK = "weekdate";
    
 	/**
     * Column name for the title of the alarm
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_IMAGE = "image";

    /**
     * Column name of the date
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CONTENT = "content";


	public DBDiary(Context context)
	{
		mHelper = new DatabaseHelper(context);
	}
	
	/*
	 * Insert new record to database 
	 */
	public void INSERT(String date, String weekday, String image, String content)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ").append(DATABASE_TABLE_NAME).append(" VALUES ");
		sb.append("(").append("null").append(", ");
		sb.append("'").append(date).append("', ");
		sb.append("'").append(weekday).append("', ");
		sb.append("'").append(image).append("', ");
		sb.append("'").append(content).append("'");
		sb.append("); ");  
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.execSQL(sb.toString());

		db.close();
		mHelper.close();

	}
	/*
	public boolean checkExisted(String cDate) {
		ArrayList<Diary> list = new ArrayList<Diary>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor;
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ").append(DATABASE_TABLE_NAME).append(" WHERE ");
		sb.append(COLUMN_NAME_DATE).append(" LIKE ").append("'" + cDate + "%'");
		
		cursor = db.rawQuery(sb.toString(), null);
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);  
			String date = cursor.getString(1);  
			String weekdate = cursor.getString(2);  
			String image = cursor.getString(3);  
			String content = cursor.getString(4);  
			
			list.add(new Diary(Integer.parseInt(id), date, weekdate, image, content));
		}
		
		cursor.close();
		mHelper.close();
		
		if(list.size() > 0) {
			return true;
		}
		
		return false;
	}
	*/
	public Diary getOneRow(String cDate) {
		Diary row = null;
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor;
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ").append(DATABASE_TABLE_NAME).append(" WHERE ");
		sb.append(COLUMN_NAME_DATE).append(" LIKE ").append("'" + cDate + "'");
		
		cursor = db.rawQuery(sb.toString(), null);
		cursor.moveToNext();
		
		if(cursor != null && cursor.getCount() > 0) {
			String id = cursor.getString(0);  
			String date = cursor.getString(1);  
			String weekdate = cursor.getString(2);  
			String image = cursor.getString(3);  
			String content = cursor.getString(4);  
			
			row = new Diary(Integer.parseInt(id), date, weekdate, image, content);
		}
		db.close();
		cursor.close();
		mHelper.close();
		
		return row;
	}
	
	/*
	 * Update existed record in database 
	 */
	public void UPDATE(int id, String image, String content)
	{
		
		// Gets the current system time in milliseconds
        
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ").append(DATABASE_TABLE_NAME).append(" SET ");
		sb.append(COLUMN_NAME_IMAGE).append("=");
		sb.append("'").append(image).append("', ");
		sb.append(COLUMN_NAME_CONTENT).append("=");
		sb.append("'").append(content).append("' ");
		sb.append("WHERE id=").append(id);
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.execSQL(sb.toString());

		db.close();
		mHelper.close();

	}
	
	/*
	 * Query to get list of note in database
	 */
	public ArrayList<Diary> query(String cDate) {
		ArrayList<Diary> list = new ArrayList<Diary>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ").append(DATABASE_TABLE_NAME).append(" WHERE ").append(COLUMN_NAME_DATE).append(" LIKE ").append("'%").append(cDate).append("%'");
		
		cursor = db.rawQuery(sb.toString(), null);
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);  
			String date = cursor.getString(1);  
			String weekdate = cursor.getString(2);  
			String image = cursor.getString(3);  
			String content = cursor.getString(4);  
			
			list.add(new Diary(Integer.parseInt(id), date, weekdate, image, content));
		}

		cursor.close();
		mHelper.close();
		
		return list;
	}
	
	/*
	 * Clear all data in table if it's existed
	 */
	public void DROP()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ").append(DATABASE_TABLE_NAME).append(";");
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.execSQL(sb.toString());
		mHelper.close();
	}
	
	class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		// Declare table with 4 column: (id - int auto increment, title - String , note - String, date - integer)
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + " ("
	                   + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	                   + COLUMN_NAME_DATE + " INTEGER,"
	                   + COLUMN_NAME_DATEOFWEEK + " TEXT, "
	                   + COLUMN_NAME_IMAGE + " TEXT,"
	                   + COLUMN_NAME_CONTENT + " TEXT"
	                   + ");");
		}

		// Drop table if it's existed
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			StringBuffer sb = new StringBuffer();
			sb.append("DROP TABLE IF EXISTS ").append(DATABASE_NAME).append("");
			
			db.execSQL(sb.toString());
			onCreate(db);
		}
	}
}
