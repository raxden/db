package com.raxdenstudios.db.task;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raxdenstudios.commons.util.Utils;
import com.raxdenstudios.db.DBManager;

public class SelectDBTask extends DBTask<Cursor> {

    private static final String TAG = SelectDBTask.class.getSimpleName();
	
	private String selection;
	private String[] selectionArgs;
	private String groupBy;
	private String having;
	private String orderBy;
	
	private String sql;
	
	private boolean writableDatabase;
	
	public SelectDBTask(SQLiteOpenHelper oh, String table, DBTaskCallbacks<Cursor> callbacks) {
		this(oh, table, null, null, callbacks);
	}
		
	public SelectDBTask(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs, DBTaskCallbacks<Cursor> callbacks) {
		this(oh, table, selection, selectionArgs, null, callbacks);
	}
	
	public SelectDBTask(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs, String groupBy, DBTaskCallbacks<Cursor> callbacks) {
		this(oh, table, selection, selectionArgs, groupBy, null, callbacks);
	}
	
	public SelectDBTask(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs, String groupBy, String having, DBTaskCallbacks<Cursor> callbacks) {
		this(oh, table, selection, selectionArgs, groupBy, having, null, callbacks);
	}
	
	public SelectDBTask(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, DBTaskCallbacks<Cursor> callbacks) {
		super(oh, table, callbacks);
		this.selection = selection;
		this.selectionArgs = selectionArgs;
		this.groupBy = groupBy;
		this.having = having;
		this.orderBy = orderBy;
	}
	
	public SelectDBTask(SQLiteOpenHelper oh, String sql, String[] selectionArgs, DBTaskCallbacks<Cursor> callbacks) {
		super(oh, null, callbacks);
		this.sql = sql;
		this.selectionArgs = selectionArgs;
	}
	
	@Override
	protected Cursor doInBackground(Object... params) {
		if (params != null && params.length > 0 && params[0] != null && params[0] instanceof Boolean) {
			writableDatabase = (Boolean)params[0];
		}
		return super.doInBackground(params);
	}
	
	@Override
	protected void onPostExecute(Cursor cursor) {
		super.onPostExecute(cursor);
		if (cursor != null && !cursor.isClosed()) {
			Log.d(TAG, "closing cursor...");
			cursor.close();
		}
	}

	@Override
	protected SQLiteDatabase getSQLiteDatabase() {
		return writableDatabase ? oh.getWritableDatabase() : oh.getReadableDatabase(); 
	}

	@Override
	protected Cursor executeDBOperation(SQLiteDatabase db) {
		if (Utils.hasValue(table)) {
			return DBManager.select(db, table, selection, selectionArgs, groupBy, having, orderBy);
		} else {
			return DBManager.select(db, sql, selectionArgs);
		}
	}

	@Deprecated
	public void setWritableDatabase(boolean writableDatabase) {
		this.writableDatabase = writableDatabase;
	}

}
