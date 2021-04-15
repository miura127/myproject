package com.miura.domain.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class BookmarkForm {

	private int id;

	private String category;

	private String[] categorys;

	@NotBlank
	@URL
	private String url;

	private String comment;
}
