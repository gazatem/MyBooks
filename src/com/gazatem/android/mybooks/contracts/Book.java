package com.gazatem.android.mybooks.contracts;

public class Book {
	private String title;
	private String author;
	private String key;
	public String[] covers; 
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		if (covers != null && covers.length > 0) {
			return covers[0];
		}
		return null;
	}
 

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

 

}
