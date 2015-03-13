package com.raxdenstudios.db;

import android.content.ContentValues;
import android.database.Cursor;

public interface DatabaseParcelable {

	public abstract ContentValues readContentValues();
	
	public static abstract interface Creator<T> {
		public abstract T createFromCursor(Cursor cursor);
	}
}