package com.miura.domain.model.mapstruct;

import org.mapstruct.Mapper;

import com.miura.domain.model.Bookmark;
import com.miura.domain.model.BookmarkForm;

@Mapper(componentModel = "spring")
public interface BookmarkTranslator {

	Bookmark toBookmarkEntity(BookmarkForm bookmarkForm);

	BookmarkForm toBookmarkForm(Bookmark bookmark);
}
