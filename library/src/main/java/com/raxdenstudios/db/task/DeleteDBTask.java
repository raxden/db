package com.raxdenstudios.db.task;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raxdenstudios.db.DBManager;

public class DeleteDBTask extends DBTask<Integer> {
	
	protected String whereClause;
	protected String[] whereArgs;

	public DeleteDBTask(SQLiteOpenHelper oh, String table, DBTaskCallbacks<Integer> callbacks) {
		this(oh, table, null, null, callbacks);
	}
	
	public DeleteDBTask(SQLiteOpenHelper oh, String table, String whereClause, String[] whereArgs, DBTaskCallbacks<Integer> callbacks) {
		super(oh, table, callbacks);
		this.whereClause = whereClause;
		this.whereArgs = whereArgs;
	}

	@Override
	protected SQLiteDatabase getSQLiteDatabase() {
		return oh.getWritableDatabase();
	}	
	
	@Override
	protected Integer executeDBOperation(SQLiteDatabase db) {
		return DBManager.delete(db, table, whereClause, whereArgs);
	}
	
}
