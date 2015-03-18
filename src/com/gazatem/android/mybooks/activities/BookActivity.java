package com.gazatem.android.mybooks.activities;
 

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.contracts.Edition;
import com.gazatem.android.mybooks.data.BookData;
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
	static String edition_key;
	String imageUrl;
	String title;
	String dbAuthorName;
	String bookCoverId;
	Button save2LibraryBtn;

	TextView bookTitle;
	TextView authorNames;
	ImageView bookCover;
	BookData data;
	Edition edition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_book_detail);
		shareBtn = (Button) findViewById(R.id.shareBook);
		removeBtn = (Button) findViewById(R.id.removeFromLibrary);
		save2LibraryBtn = (Button) findViewById(R.id.save2Library);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			edition_key = extras.getString("edition_key");
		}

		bookTitle = (TextView) findViewById(R.id.bookTitle);
		authorNames = (TextView) findViewById(R.id.authorNames);
		bookCover = (ImageView) findViewById(R.id.bookCover);

		data = new BookData(BookActivity.this);
		edition = data.getBook(edition_key);
		String author = data.getAuthors();
		authorNames.setText(author);
		boolean isSaved = data.isSavedBook();

		if (isSaved == true) {
			save2LibraryBtn.setVisibility(View.GONE);
		} else {
			removeBtn.setVisibility(View.GONE);
		}

		bookTitle.setText(edition.getTitle());
		bookCoverId = edition.getCover();

		if (!bookCoverId.equals("")) {
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

		save2LibraryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean rst = data.saveBook2Library(edition.getKey(),
						edition.getTitle(), edition.getCover());
				if (rst) {
					Toast.makeText(BookActivity.this,
							"Book has saved to your library!",
							Toast.LENGTH_SHORT).show();
					save2LibraryBtn.setVisibility(View.GONE);
					removeBtn.setVisibility(View.VISIBLE);
				} else {
					Toast.makeText(BookActivity.this, "Book can not be saved!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Boolean rst = data
						.removeBookFromLibrary(BookActivity.edition_key);
				if (rst) {
					Toast.makeText(BookActivity.this,
							"Book has removed from your library!",
							Toast.LENGTH_SHORT).show();
					removeBtn.setVisibility(View.GONE);
					save2LibraryBtn.setVisibility(View.VISIBLE);
				} else {
					Toast.makeText(BookActivity.this,
							"Book can not be removed!", Toast.LENGTH_SHORT)
							.show();
				}
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

}
