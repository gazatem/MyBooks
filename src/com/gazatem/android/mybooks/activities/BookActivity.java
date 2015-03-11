package com.gazatem.android.mybooks.activities;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.ImageDownloader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
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
	int bookId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_book_detail);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			bookId = extras.getInt("bookId");
		}

		DBHelper dbHelper = new DBHelper(this);
		crs = dbHelper.getBookFromDB(bookId);

		if (crs != null) {
			TextView bookTitle = (TextView) findViewById(R.id.bookTitle);
			TextView authorNames = (TextView) findViewById(R.id.authorNames);
			
			int colmId = crs.getColumnIndexOrThrow("title");
			String title = crs.getString(colmId);
			bookTitle.setText(title);
 
			int colmAuthorId = crs.getColumnIndexOrThrow("author");
			String dbAuthorName = crs.getString(colmAuthorId);
			authorNames.setText(dbAuthorName);
			
			ImageView bookCover = (ImageView) findViewById(R.id.bookCover);

			int coverColmId = crs.getColumnIndexOrThrow("cover");
			String bookCoverId = crs.getString(coverColmId);
			if (bookCoverId != null) {

				String url = "http://covers.openlibrary.org/b/id/"
						+ bookCoverId + "-M.jpg";

				mDownloader = new ImageDownloader(url, bookCover, coverImage,
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
					dbHelper.removeBook(bookId); 
					Toast.makeText(BookActivity.this,
							"Book removed from library", Toast.LENGTH_LONG)
							.show();
					Intent i = new Intent(BookActivity.this,
							BookListActivity.class);
					startActivity(i);
				}
			});
			 
			
			shareBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
 
					Toast.makeText(BookActivity.this,
							"Share Book removed from library", Toast.LENGTH_LONG)
							.show();
				 
				}
			});			
			
			
			
		}
	}
}
