package com.gazatem.android.mybooks.activities;

import java.io.IOException;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.utilities.EditionSearchAdapter;
import com.gazatem.android.mybooks.utilities.FetchData;
import com.gazatem.android.mybooks.utilities.ImageDownloader;

public class SearchEditionsOfBookActivity extends BaseActivity {

	String[] editionKeys;
	String key;
	String title;
	String cover_id;
	String author_names;
	ListView searchlist;

	ArrayList<Edition> editions = new ArrayList<Edition>();
	Bitmap coverImage;
	private ImageDownloader mDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_result_detail);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			key = extras.getString("key");
			editionKeys = extras.getStringArray("editionKey");
			title = extras.getString("title");
			cover_id = extras.getString("cover_i");
			author_names = extras.getString("author_names");
		}

		TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
		bookTitle.setText(title);
		TextView authorNames = (TextView) findViewById(R.id.authorNames);
		authorNames.setText(author_names);
		ImageView bookCover = (ImageView) findViewById(R.id.bookCover);
		searchlist = (ListView) findViewById(R.id.book_search_list);
		
		searchlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SearchEditionsOfBookActivity.this,
						BookActivity.class);
				Edition entity = editions.get(position);

				i.putExtra("edition_key", entity.getKey()
						.replace("/books/", ""));
				startActivity(i);
			}
		});

		if (cover_id != null) {
			String url = "http://covers.openlibrary.org/b/id/" + cover_id
					+ "-M.jpg";

			mDownloader = new ImageDownloader(url, bookCover, coverImage,
					new ImageDownloader.ImageLoaderListener() {

						@Override
						public void onImageDownloaded(Bitmap bmp) {
							// TODO Auto-generated method stub
							if (bmp != null) {
								coverImage = bmp;
							}
						}
					});   
			mDownloader.execute();
		}

		new SearchAsyncTask().execute(editionKeys);
	}

	class SearchAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog prg = new ProgressDialog( 
				SearchEditionsOfBookActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg.setTitle("Searching editions of selected book!");
			prg.show();
		}   
   
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			searchlist.setAdapter(new EditionSearchAdapter(
					SearchEditionsOfBookActivity.this, editions, coverImage)); 
			prg.dismiss();
		}

		@Override
		protected Boolean doInBackground(String... editionKeys) {
			// TODO Auto-generated method stub
			
			for (String item : editionKeys) {
				try {
					Log.d("RST", "Eee "+ item);
					editions.add(FetchData.searchByEditionKey(item));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("RST", "Edition of book can not accessed : " + item);
				}
			}
			return true;
		}
	}
}
