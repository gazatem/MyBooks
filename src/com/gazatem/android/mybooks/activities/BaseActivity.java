package com.gazatem.android.mybooks.activities; 

import com.gazatem.android.mybooks.R;
import com.gazatem.android.mybooks.application.MyBooksApplication;
 
import android.app.Activity;
import android.content.Intent;
import android.view.Menu; 
import android.view.MenuItem;

public class BaseActivity extends Activity {
	final MyBooksApplication app = ((MyBooksApplication) getApplication());
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == R.id.homeMenuOption) {
			Intent i = new Intent(BaseActivity.this, MainActivity.class);
			startActivity(i);
		} else if (item.getItemId() == R.id.searchMenuOption) {
			Intent i = new Intent(BaseActivity.this, SearchActivity.class);
			startActivity(i);
		} else if (item.getItemId() == R.id.listMenuOption) {
			Intent i = new Intent(BaseActivity.this, BookListActivity.class);
			startActivity(i);
		} else {

		}

		return super.onOptionsItemSelected(item);
	}
}
