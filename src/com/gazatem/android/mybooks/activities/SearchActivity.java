package com.gazatem.android.mybooks.activities;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;

import com.gazatem.android.mybooks.contracts.BookEntity;
import com.gazatem.android.mybooks.utilities.BookSearchAdapter;
import com.gazatem.android.mybooks.utilities.FetchData;
import com.gazatem.android.mybooks.R;

public class SearchActivity extends BaseActivity {

	ListView searchlist;
	String searchTerm;

	static ArrayList<BookEntity> books = new ArrayList<BookEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		Bundle extras = getIntent().getExtras();
		searchTerm = extras.getString("searchTerm");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		searchlist = (ListView) findViewById(R.id.book_search_list);
		Log.d("RST", "kelime:" + searchTerm); 
		new SearchAsyncTask().execute(searchTerm);
	}

	private void searchListView(ArrayList<BookEntity> books) {
		searchlist
				.setAdapter(new BookSearchAdapter(SearchActivity.this, books));

		searchlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SearchActivity.this,
						SearchEditionsOfBookActivity.class);
				BookEntity entity = SearchActivity.books.get(position);
				i.putExtra("key", entity.key);
				i.putExtra("editionKey", entity.edition_key);
				i.putExtra("title", entity.title);
				i.putExtra("author_names", entity.author_names);
				i.putExtra("cover_i", entity.cover_i);
				startActivity(i);
			}
		});
	}

	class SearchAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog prg = new ProgressDialog(SearchActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg.setTitle("Searching ...");
			prg.show();
		} 

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			searchListView(books);
			prg.dismiss();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub

			FetchData fetchData = new FetchData();
			try {
				SearchActivity.books = fetchData.searchByTitle(params[0]);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

}
