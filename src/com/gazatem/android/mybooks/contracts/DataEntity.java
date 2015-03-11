package com.gazatem.android.mybooks.contracts;

public class DataEntity {
	public String title_suggest;
	public String subtitle;
	public String title;
	public String[] author_name;
	public String[] edition_key;
	public String key;
	public String cover_i;

	public String getAuthorNames() {
		String author = "";
		try {
			for (String value : author_name) {
				if (value.length() > 0){
					author += value + ", ";
				}
			}
			return author.substring(0, (author.length() - 2));
		} catch (Exception e) {

		}
		return null;

	}
}
