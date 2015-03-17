package com.gazatem.android.mybooks.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.FetchData;

public class BookData {

	Edition edition;
	Context context;
	DBHelper dbHelper;

	public BookData(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
	}

	public Edition getBook(String edition_key) {
		try {
			edition = new FetchAsyncTask(context).execute(edition_key).get();
			return edition;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean isSavedBook() {
		return dbHelper.isSavedBook(edition.getKey()); 
	}

	public Boolean saveBook2Library(String edition_key, String title, String cover) {
		Long id = dbHelper.saveBook2Library(edition_key, title, cover);
		if (id > 0) {
			return true;
		}
		return false;
	}

	public Boolean removeBookFromLibrary(String edition_key) {
		return dbHelper.removeBook(edition_key);
	}

	class FetchAsyncTask extends AsyncTask<String, Void, Edition> {
		Edition edition;
		Context context;

		// ProgressDialog prg = new ProgressDialog(context);

		public FetchAsyncTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// prg.setTitle("Getting information of book!");
			// prg.show();
		}

		@Override
		protected Edition doInBackground(String... editionKeys) {
			// TODO Auto-generated method stub
			try {
				edition = FetchData.searchByEditionKey(editionKeys[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return edition;
		}
	}
}