package com.miura.domain.model;

import lombok.Data;

@Data
public class Bookmark {

	private int id;
	private String category;
	private String url;
	private String comment;

	private String registerUser;
}
