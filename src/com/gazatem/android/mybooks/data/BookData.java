package com.gazatem.android.mybooks.data;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.gazatem.android.mybooks.contracts.Author;
import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.FetchData;

public class BookData {

	Context context;
	DBHelper dbHelper;

	public BookData(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
	}

	public Edition getBook(String edition_key) {
		Edition edition;
		try {
			edition = FetchData.searchByEditionKey(edition_key);
			return edition;  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getAuthors(ArrayList<Author> editionAuthors) {
		String authors = "";
		for (int i = 0; i < editionAuthors.size(); i++) {
			Author author;
			String key = editionAuthors.get(i).key;
			key = key.replace("/authors/", "");

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

	public boolean isSavedBook(String editionKey) {
		return dbHelper.isSavedBook(editionKey);
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
		private Edition edition;
		private Context context;

		ProgressDialog prg;

		public FetchEditionAsyncTask(Context ctx) {
			// context = ctx;
			// prg = new ProgressDialog(context);
		}

		@Override
		protected void onPostExecute(Edition result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// prg.dismiss();
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
		ProgressDialog prg;

		@Override
		protected void onPostExecute(Author result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// prg.dismiss();
		}

		public FetchAuthorAsyncTask(Context ctx) {
			this.context = ctx;
			// prg = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// prg.setTitle("Getting Author of book!");
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