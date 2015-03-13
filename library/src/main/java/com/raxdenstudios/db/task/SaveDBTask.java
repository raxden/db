package com.raxdenstudios.db.task;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raxdenstudios.db.DBManager;
import com.raxdenstudios.db.DatabaseParcelable;

public class SaveDBTask extends DBTask<Object> {

	private DatabaseParcelable databaseParcelable;
	private ContentValues contentValues;
	private String whereClause;
	private String[] whereArgs;	
	
	@Deprecated
	public SaveDBTask(SQLiteOpenHelper oh, String table, ContentValues contentValues, String whereClause, String[] whereArgs, DBTaskCallbacks<Object> callbacks) {
		super(oh, table, callbacks);
		this.contentValues = contentValues;
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
	}
	
	public SaveDBTask(SQLiteOpenHelper oh, String table, DatabaseParcelable databaseParcelable, String whereClause, String[] whereArgs, DBTaskCallbacks<Object> callbacks) {
		super(oh, table, callbacks);
		this.databaseParcelable = databaseParcelable;
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
	}	
	
	@Override
	protected SQLiteDatabase getSQLiteDatabase() {
		return oh.getWritableDatabase();
	}

	@Override
	protected Object executeDBOperation(SQLiteDatabase db) {
		if (databaseParcelable != null) {
			contentValues = databaseParcelable.readContentValues();
		}
		return DBManager.save(db, table, contentValues, whereClause, whereArgs);
	}	
		
}