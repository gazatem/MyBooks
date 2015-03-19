package com.gazatem.android.mybooks.activities;

import com.gazatem.android.mybooks.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

	Button searchQueryButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		searchQueryButton = (Button) findViewById(R.id.searchQueryButton);

		final TextView view = (TextView) findViewById(R.id.link2gz);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.gazatem.com"));
				startActivity(browserIntent);
			}
		});		


		searchQueryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText searchTerm = (EditText) findViewById(R.id.searchQuery);
				Intent i = new Intent(MainActivity.this, SearchActivity.class);
				i.putExtra("searchTerm", searchTerm.getText().toString());
				startActivity(i);
			}
		});
	}

}