package com.gazatem.android.mybooks.data;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gazatem.android.mybooks.contracts.Author;
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

	public String getAuthors() {
		String authors = "";
		for (int i = 0; i < edition.authors.size(); i++) {
			Author author;
			String key = edition.authors.get(i).key;
			key  = key.replace("/authors/", "");
			 
			try {
				author = new FetchAuthorAsyncTask(context).execute(key).get();
				authors += author.name + ", "; 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return authors.substring(0, (authors.length() - 2));
	}

	public Edition getBook(String edition_key) {
		try {
			edition = new FetchEditionAsyncTask(context).execute(edition_key)
					.get();
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

	public Boolean saveBook2Library(String edition_key, String title,
			String cover) {
		Long id = dbHelper.saveBook2Library(edition_key, title, cover);
		if (id > 0) {
			return true;
		}
		return false;
	}

	public Boolean removeBookFromLibrary(String edition_key) {
		return dbHelper.removeBook(edition_key);
	}

	class FetchEditionAsyncTask extends AsyncTask<String, Void, Edition> {
		Edition edition;
		Context context;

		// ProgressDialog prg = new ProgressDialog(context);

		public FetchEditionAsyncTask(Context context) {
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

	class FetchAuthorAsyncTask extends AsyncTask<String, Void, Author> {
		Author author;
		Context context;

		// ProgressDialog prg = new ProgressDialog(context);

		public FetchAuthorAsyncTask(Context context) {
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
		protected Author doInBackground(String... editionKeys) {
			// TODO Auto-generated method stub
			try {
				author = FetchData.searchByAuthorKey(editionKeys[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return author;
		}
	}

}