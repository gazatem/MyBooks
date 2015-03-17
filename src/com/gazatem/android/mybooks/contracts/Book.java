package com.gazatem.android.mybooks.contracts;

public class Book {
	private String title;
	private String author;
	private String key;
	private String cover;
	private String[] covers;
	private String[] authors;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String[] getCovers() {
		return covers;
	}

	public void setCovers(String[] covers) {
		this.covers = covers;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

}
