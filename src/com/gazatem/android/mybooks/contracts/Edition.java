package com.gazatem.android.mybooks.contracts;

public class Edition {

	private String title;
	private String author;
	private String key;
	private String cover;
	private String subtitle;
	public Boolean isSaved;
	private String[] covers;

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
		if (covers.length > 0) {
			return covers[0];
		}
		return null;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String[] getCovers() {
		return covers;
	}

	public void setCovers(String[] covers) {
		this.covers = covers;
	}

}
