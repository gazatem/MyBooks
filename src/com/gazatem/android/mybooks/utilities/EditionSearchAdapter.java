package com.gazatem.android.mybooks.utilities;

import java.util.ArrayList;

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

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.contracts.Edition;

public class EditionSearchAdapter extends ArrayAdapter<Edition> {


	ArrayList<Edition> liste;
	Context ctx;
	Bitmap bookCover;

	public EditionSearchAdapter(Context context, ArrayList<Edition> objects, Bitmap bookCover) {
		super(context, android.R.layout.simple_list_item_1, objects); 
		// TODO Auto-generated constructor stub
		liste = objects;
		ctx = context;
		this.bookCover = bookCover;
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
		TextView  subTitle = (TextView) view.findViewById(R.id.subTitle); 
		
		imgBook = (ImageView) view.findViewById(R.id.imgBook);
		Edition entity = liste.get(position);
		txtName.setText(entity.getTitle());
		txtAuthor.setText(entity.getAuthor());
		subTitle.setText(entity.getSubtitle()); 
		
		if (bookCover != null){ 
			imgBook.setImageBitmap(bookCover);
		}
		
		return view;
	}

 

}
