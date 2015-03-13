package com.raxdenstudios.db.task;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTaskImproved;
import android.util.Log;

public abstract class DBTask<T> extends AsyncTaskImproved<Object, Void, T> {

    private static final String TAG = DBTask.class.getSimpleName();
	
	public interface DBTaskCallbacks<T> {
		public void onBeginTransaction(SQLiteDatabase db);
		public void onPreFinalizeTransaction(SQLiteDatabase db, T result);
		public void onCompletion(T result);
	};
		
	protected DBTaskCallbacks<T> callbacks;
	protected SQLiteOpenHelper oh;
	protected String table;
	
	public DBTask(SQLiteOpenHelper oh, String table, DBTaskCallbacks<T> callbacks) {
		super();
		this.oh = oh;
		this.table = table;
		this.callbacks = callbacks;
	}
		
	@Override
	protected T doInBackground(Object... params) {
		T data = null;
		SQLiteDatabase db = null;
		try {
			db = getSQLiteDatabase();
			onBeginTransaction(db);
			data = executeDBOperation(db);
			onTransactionSuccessfull(db, data);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (db != null) db.endTransaction();
		}

		return data;
	}

	protected void onBeginTransaction(SQLiteDatabase db) {
		db.beginTransaction();
		if (callbacks != null) {
			callbacks.onBeginTransaction(db);
		}
	}

	protected void onTransactionSuccessfull(SQLiteDatabase db, T data) {
		if (callbacks != null) {
			callbacks.onPreFinalizeTransaction(db, data);
		}
		db.setTransactionSuccessful();
	}
	
	protected abstract SQLiteDatabase getSQLiteDatabase();
	protected abstract T executeDBOperation(SQLiteDatabase db);

	@Override
	protected void onPostExecute(T result) {
		if (callbacks != null) {
			callbacks.onCompletion(result);
		}
	}
}
