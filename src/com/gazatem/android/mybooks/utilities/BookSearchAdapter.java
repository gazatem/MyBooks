package com.gazatem.android.mybooks.utilities;

import java.util.ArrayList;
import java.util.List;

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.activities.SearchActivity;
import com.gazatem.android.mybooks.contracts.BookEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookSearchAdapter extends ArrayAdapter<BookEntity> {

	ArrayList<BookEntity> liste;
	Context ctx;

	public BookSearchAdapter(Context context, ArrayList<BookEntity> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
		// TODO Auto-generated constructor stub
		liste = objects;
		ctx = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater
				.inflate(R.layout.search_result_item, parent, false);

		ImageView imgBook;
		TextView txtName = (TextView) view.findViewById(R.id.txtBookName);
		TextView txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
		imgBook = (ImageView) view.findViewById(R.id.imgBook);

		BookEntity entity = liste.get(position);
		txtName.setText(entity.title);
		if (entity.author_names != null) {
			txtAuthor.setText(entity.author_names);
		}
 
		if (entity.cover_i != null) {
			String coverUrl = "http://covers.openlibrary.org/b/id/"
					+ entity.cover_i + "-S.jpg";

			String[] params = { coverUrl, entity.author_names };
			new DownloadBokCoverAsyncTask(imgBook).execute(params);
		}
		return view;
	}

	class DownloadBokCoverAsyncTask extends AsyncTask<String, Void, Void> {

		ImageView imgBook;

		public DownloadBokCoverAsyncTask(ImageView imgBook) {
			this.imgBook = imgBook;
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				Bitmap coverImage = ImageDownloader.getBitmapFromURL(params[0]);
				imgBook.setImageBitmap(coverImage);
			} catch (Exception e) {
				Log.d("RST", "Can't download image: " + e.getMessage());
			}

			return null;
		} 
	}

}
