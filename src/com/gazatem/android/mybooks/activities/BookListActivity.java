package com.gazatem.android.mybooks.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.utilities.DBHelper;
import com.gazatem.android.mybooks.utilities.ImageDownloader;

public class BookListActivity extends BaseActivity {

	ListView bookList;
	String[] columnNames = { DBHelper.COL_TITLE, DBHelper.COL_AUTHOR,
			DBHelper.COL_COVER };
	int[] resourceID = { R.id.txtBookName, R.id.txtAuthor, R.id.imgBook };
	static Drawable draw;
	Cursor crs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		draw = this.getResources().getDrawable(R.drawable.book);

		bookList = (ListView) findViewById(R.id.book_search_list);

		DBHelper dbhelper = new DBHelper(this);
		crs = dbhelper.getBooksFromDB();
		if (crs != null) {
			SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
					R.layout.search_result_item, crs, columnNames, resourceID,
					CursorAdapter.FLAG_AUTO_REQUERY);

			cursorAdapter.setViewBinder(new MyViewBinder());

			bookList.setAdapter(cursorAdapter);
		} else {
			Toast.makeText(this, "Your library is empty!", Toast.LENGTH_SHORT)
					.show();
		}

		bookList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BookListActivity.this, BookActivity.class);
				int bookId = crs.getColumnIndexOrThrow("_id");
				int _bookId = crs.getInt(bookId);
				i.putExtra("bookId", _bookId);
				startActivity(i);
			}
		});
	}

	class MyViewBinder implements SimpleCursorAdapter.ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			if (columnIndex == cursor.getColumnIndex(DBHelper.COL_COVER)) {
				String data = cursor.getString(columnIndex);
			 
				if (data != null) {
					String coverUrl = "http://covers.openlibrary.org/b/id/"
							+ data + "-S.jpg";
					new DownloadBokCoverAsyncTask(view).execute(coverUrl);
				} else {
					((ImageView) view).setImageDrawable(draw);
				}
				return true;
			}
			return false;

		}

		class DownloadBokCoverAsyncTask extends AsyncTask<String, Void, Void> {

			View view;

			public DownloadBokCoverAsyncTask(View view) {
				this.view = view;
			}

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub

				try {
					Bitmap coverImage = ImageDownloader
							.getBitmapFromURL(params[0]);
					((ImageView) view).setImageBitmap(coverImage);
				} catch (Exception e) {
					Log.d("RST", "Can't download image: " + e.getMessage());
				}

				return null;
			}
		}
	}
}
