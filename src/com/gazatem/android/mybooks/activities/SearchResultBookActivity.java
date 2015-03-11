package com.gazatem.android.mybooks.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.ImageDownloader;

public class SearchResultBookActivity extends BaseActivity {

	String editionKey;
	String title;
	String cover_id;
	String author_names;

	Button save2LibraryButton;

	static Bitmap coverImage;
	private ImageDownloader mDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_result_detail);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			editionKey = extras.getString("editionKey");
			title = extras.getString("title");
			cover_id = extras.getString("cover_i");
			author_names = extras.getString("author_names");
		}

		TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
		bookTitle.setText(title);

		TextView authorNames = (TextView) findViewById(R.id.authorNames);
		authorNames.setText(author_names);

		ImageView bookCover = (ImageView) findViewById(R.id.bookCover);

		if (cover_id != null) {
			String url = "http://covers.openlibrary.org/b/id/" + cover_id
					+ "-M.jpg";
 
			mDownloader = new ImageDownloader(url, bookCover, coverImage,
					new ImageDownloader.ImageLoaderListener() {

						@Override
						public void onImageDownloaded(Bitmap bmp) {
							// TODO Auto-generated method stub
							if (bmp != null) {
								SearchResultBookActivity.coverImage = bmp;
							}
						}
					});
			mDownloader.execute();
		}

		save2LibraryButton = (Button) findViewById(R.id.save2Library);

		save2LibraryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBHelper dbHelper = new DBHelper(SearchResultBookActivity.this);

				long id = dbHelper.addBook( new String[] { title,
				 author_names, cover_id, editionKey });
				Intent i = new Intent(SearchResultBookActivity.this,
						BookListActivity.class);
				startActivity(i);
			}
		});
		
		
 

	}
}
