package com.raxdenstudios.db.task;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raxdenstudios.db.DBManager;

public class InsertDBTask extends DBTask<Long> {
			
	private ContentValues contentValues;
	
	public InsertDBTask(SQLiteOpenHelper oh, String table, ContentValues contentValues, DBTaskCallbacks<Long> callbacks) {
		super(oh, table, callbacks);
		this.contentValues = contentValues;
	}

	@Override
	protected SQLiteDatabase getSQLiteDatabase() {
		return oh.getWritableDatabase();
	}

	@Override
	protected Long executeDBOperation(SQLiteDatabase db) {
		return DBManager.insert(db, table, contentValues);
	}

}
