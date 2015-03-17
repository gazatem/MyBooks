package com.gazatem.android.mybooks.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static DBHelper instance = null;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "myBookLibrary.db";
	public static final String TABLE_NAME = "books";
	public static final String COL_ID = "book_id";
	public static final String COL_EDITION_KEY = "edition_key";
	public static final String COL_TITLE = "title";
	public static final String COL_COVER = "cover";
	public static final String COL_NOTES = "notes";
	public static final String COL_AUTHOR = "author";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public Boolean removeBook(String edition_key) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, COL_EDITION_KEY + " = '" + edition_key + "'",
				null);
		db.close();
		return true;
	} 

	public long saveBook2Library(String edition_key, String title, String cover) {
		
		edition_key = edition_key.replace("/books/", "");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COL_EDITION_KEY, edition_key);
		cv.put(COL_TITLE, title);
		cv.put(COL_COVER, cover);
		long id = db.insertWithOnConflict(TABLE_NAME, null, cv,
				SQLiteDatabase.CONFLICT_ABORT);
		db.close();
		return id;
	}

	public Cursor getBooksFromDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID + " as _id",
				COL_EDITION_KEY, COL_TITLE, COL_AUTHOR, COL_COVER }, null,
				null, null, null, "title ASC");
		if (cursor.getCount() > 0) {
			return cursor;
		} else {
			return null;
		}
	}

	public Cursor getBookFromDB(String edition_key) {

		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String where = COL_EDITION_KEY + "='" + edition_key + "' ";

			Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID,
					COL_EDITION_KEY, COL_TITLE, COL_AUTHOR, COL_COVER }, where,
					null, null, null, null);

			if (null != cursor && cursor.getCount() > 0) {
				cursor.moveToFirst();
				return cursor;
			}
		} catch (Exception ex) {
			Log.d("RST", "This book is not avalaible in database: "
					+ edition_key);
		}

		return null;
	}
	

	public boolean isSavedBook(String edition_key) {
		
		edition_key = edition_key.replace("/books/", "");
		
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String where = COL_EDITION_KEY + "='" + edition_key + "' ";

			Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID,
					COL_EDITION_KEY, COL_TITLE, COL_AUTHOR, COL_COVER }, where,
					null, null, null, null);
			 
				if (cursor.getCount() > 0){
					return true;
				}else{
					return false;
				}
		} catch (Exception ex) {
			Log.d("RST", "This book is not avalaible in database: "
					+ edition_key);
			return false;
		}
	}	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID
				+ " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,"
				+ COL_EDITION_KEY + " VARCHAR," + COL_TITLE + " VARCHAR, "
				+ COL_AUTHOR + " VARCHAR, " + COL_COVER + " VARCHAR, "
				+ COL_NOTES + " TEXT)";
		db.execSQL(CREATE_BOOKS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

}
