package com.raxdenstudios.db.task;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raxdenstudios.db.DBManager;

public class UpdateDBTask extends DBTask<Integer> {

	private ContentValues contentValues;
	private String whereClause;
	private String[] whereArgs;	
	
	public UpdateDBTask(SQLiteOpenHelper oh, String table, ContentValues contentValues, String whereClause, String[] whereArgs, DBTaskCallbacks<Integer> callbacks) {
		super(oh, table, callbacks);
		this.contentValues = contentValues;
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
	}
	
	@Override
	protected SQLiteDatabase getSQLiteDatabase() {
		return oh.getWritableDatabase();
	}

	@Override
	protected Integer executeDBOperation(SQLiteDatabase db) {
		return DBManager.update(db, table, contentValues, whereClause, whereArgs);
	}	
		
}
