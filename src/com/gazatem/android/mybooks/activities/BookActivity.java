package com.gazatem.android.mybooks.activities;

import java.io.IOException;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.activities.SearchResultBookActivity.SearchAsyncTask;
import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.EditionSearchAdapter;
import com.gazatem.android.mybooks.utilities.FetchData;
import com.gazatem.android.mybooks.utilities.ImageDownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends BaseActivity {

	Cursor crs;
	static Bitmap coverImage;
	private ImageDownloader mDownloader;
	Button removeBtn;
	Button shareBtn;
	String edition_key;
	String imageUrl;
	String title;
	String dbAuthorName;
	String bookCoverId;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_book_detail);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			edition_key = extras.getString("edition_key");
		}

		DBHelper dbHelper = new DBHelper(this);
		crs = dbHelper.getBookFromDB(edition_key);

		TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
		TextView authorNames = (TextView) findViewById(R.id.authorNames);
		ImageView bookCover = (ImageView) findViewById(R.id.bookCover);

		if (crs.getCount() > 0 || crs != null) {
			title = crs.getString(crs.getColumnIndex("title"));
			dbAuthorName = crs.getString(crs.getColumnIndex("author"));
			bookCoverId = crs.getString(crs.getColumnIndex("cover"));
		} else {
			new SearchAsyncTask().execute(edition_key);
		}
		bookTitle.setText(title);
		authorNames.setText(dbAuthorName);

		if (bookCoverId != null) {

			imageUrl = "http://covers.openlibrary.org/b/id/" + bookCoverId
					+ "-M.jpg";

			mDownloader = new ImageDownloader(imageUrl, bookCover, coverImage,
					new ImageDownloader.ImageLoaderListener() {

						@Override
						public void onImageDownloaded(Bitmap bmp) {
							// TODO Auto-generated method stub
							BookActivity.coverImage = bmp;
						}
					});
			mDownloader.execute();
		}

		shareBtn = (Button) findViewById(R.id.shareBook);
		removeBtn = (Button) findViewById(R.id.removeFromLibrary);
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				DBHelper dbHelper = new DBHelper(BookActivity.this);
				dbHelper.removeBook(edition_key);
				Toast.makeText(BookActivity.this, "Book removed from library",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(BookActivity.this, BookListActivity.class);
				startActivity(i);
			}
		});

		shareBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent intent1 = new Intent();
					intent1.setAction(Intent.ACTION_SEND);
					intent1.setType("text/plain");
					intent1.putExtra(Intent.EXTRA_SUBJECT,
							"I've added a new book to my books application: ");

					String shareText = "I've added a new book to my books application:";
					shareText += title + "/" + dbAuthorName + " " + imageUrl;

					intent1.putExtra(Intent.EXTRA_TEXT, shareText);
					startActivity(Intent.createChooser(intent1, "Share via"));

				} catch (Exception e) {
					// If we failed (not native FB app installed), try share
					// through SEND
					Toast.makeText(
							BookActivity.this,
							"You need to install any social media apps for sharing",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	class SearchAsyncTask extends AsyncTask<String, Void, Boolean> {
		Edition edition;
		ProgressDialog prg = new ProgressDialog(BookActivity.this);

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
			// BookActivity.bookTitle resu
			// authorNames.setText(dbAuthorName);
			title = edition.getTitle();
			prg.dismiss();
		}

		@Override
		protected Boolean doInBackground(String... editionKeys) {
			// TODO Auto-generated method stub
			try {
				edition =  FetchData.searchByEditionKey(editionKeys[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
 
		}
	}

}
