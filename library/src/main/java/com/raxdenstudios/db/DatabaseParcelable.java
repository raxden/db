package com.raxdenstudios.db;

import android.content.ContentValues;
import android.database.Cursor;

public interface DatabaseParcelable {

	ContentValues readContentValues();
	
	interface Creator<T> {
		T createFromCursor(Cursor cursor);
	}

}