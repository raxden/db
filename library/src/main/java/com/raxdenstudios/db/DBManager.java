package com.raxdenstudios.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public abstract class DBManager<T> {

    private static final String TAG = DBManager.class.getSimpleName();
	
	public static final int CONFLICT_ALGORITHM = SQLiteDatabase.CONFLICT_IGNORE;

	public interface DBFindCallbacks<T> {
		public void dataFound(T data);
	}

	public interface DBFindAllCallbacks<T> {
		public void dataFound(List<T> data);
	}
	
	public interface DBSaveCallbacks<T> {
		public void dataSaved(T data);
	}	
	
	public interface DBDeleteCallbacks<T> {
		public void dataDeleted(T data);
	}
	
	public interface DBDeleteAllCallbacks<T> {
		public void dataDeleted(List<T> data);
	}
	
	private SQLiteOpenHelper oh;
		
	public DBManager(SQLiteOpenHelper oh) {
		this.oh = oh;
	}	
	
	public abstract void find(String id, final DBFindCallbacks<T> callbacks);
	public abstract void findAll(final DBFindAllCallbacks<T> callbacks);
	public abstract void save(final T data, final DBSaveCallbacks<T> callbacks);
	public abstract void delete(final T data, final DBDeleteCallbacks<T> callbacks);
	public abstract void deleteAll(final DBDeleteAllCallbacks<T> callbacks);
	
	public void close() {
		if (oh != null) {
			Log.d(TAG, "OpenHelper was closed");
			oh.close();
		}
	}
	
	public SQLiteOpenHelper getOpenHelper() {
		return oh;
	}	
	
	/* SELECT OPERATIONS */
	
	public static Cursor selectAll(SQLiteOpenHelper oh, String table) {
		return selectAll(oh.getReadableDatabase(), table);
	}

	public static Cursor selectAll(SQLiteDatabase db, String table) {
		return select(db, table, null, null, null, null, null);
	}	

	public static Cursor select(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs) {
		return select(oh.getReadableDatabase(), table, selection, selectionArgs, null, null, null);
	}
	
	public static Cursor select(SQLiteDatabase db, String table, String selection, String[] selectionArgs) {
		return select(db, table, selection, selectionArgs, null, null, null);
	}	
	
	public static Cursor select(SQLiteOpenHelper oh, String table, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return select(oh.getReadableDatabase(), table, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	public static Cursor select(SQLiteDatabase db, String table, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = db.query(table, null, selection, selectionArgs, groupBy, having, orderBy);
		Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
		return cursor;
	}
	
	public static Cursor select(SQLiteOpenHelper oh, String sql, String[] selectionArgs) {
		return select(oh.getReadableDatabase(), sql, selectionArgs);
	}	
	
	public static Cursor select(SQLiteDatabase db, String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
		return cursor;
	}	
		
	/* INSERT OPERATIONS */
	
	public static Long insert(SQLiteOpenHelper oh, String table, ContentValues contentValues) {
		return insert(oh.getWritableDatabase(), table, contentValues);
	}
	
	public static Long insert(SQLiteDatabase db, String table, ContentValues contentValues) {
		return db.insertWithOnConflict(table, null, contentValues, CONFLICT_ALGORITHM);
	}
	
	/* UPDATE OPERATIONS */
	
	public static Integer update(SQLiteOpenHelper oh, String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
		return update(oh.getWritableDatabase(), table, contentValues, whereClause, whereArgs);
	}
	
	public static Integer update(SQLiteDatabase db, String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
		return db.updateWithOnConflict(table, contentValues, whereClause, whereArgs, CONFLICT_ALGORITHM);
	}
	
	/* SAVE OPERATIONS */
	
	public static Object save(SQLiteDatabase db, String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
		int rows = 0;
		if ((rows = update(db, table, contentValues, whereClause, whereArgs)) <= 0) {
			return insert(db, table, contentValues);
		}
		return rows;
	}	
	
	/* DELETE OPERATIONS */
	
	public static Integer deleteAll(SQLiteOpenHelper oh, String table) {
		return deleteAll(oh.getWritableDatabase(), table);
	}
	
	public static Integer deleteAll(SQLiteDatabase db, String table) {
		return delete(db, table, null, null);
	}
	
	public static Integer delete(SQLiteOpenHelper oh, String table, String whereClause, String[] whereArgs) {
		return delete(oh.getWritableDatabase(), table, whereClause, whereArgs);
	}
	
	public static Integer delete(SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
		return db.delete(table, whereClause, whereArgs);
	}
	
}
