package com.gazatem.android.mybooks.data;

import android.content.Context;
import android.os.AsyncTask;

import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.FetchData;

public class LibraryData {

	Edition[] editions;
	Context context;
	DBHelper dbHelper;

	public LibraryData(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
	}
}
