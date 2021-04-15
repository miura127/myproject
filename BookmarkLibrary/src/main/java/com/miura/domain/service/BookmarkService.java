package com.miura.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miura.domain.model.Bookmark;
import com.miura.domain.repository.mybatis.BookmarkMapper;

@Service
public class BookmarkService {

	@Autowired
	BookmarkMapper bookmarkMapper;

	public boolean insert(Bookmark bookmark) {
		return bookmarkMapper.insert(bookmark);
	}

	public Bookmark selectOne(int id) {
		return bookmarkMapper.selectOne(id);
	}

	public List<Bookmark> selectAll(){
		return bookmarkMapper.selectAll();
	}

	public boolean updateOne(Bookmark bookmark) {
		return bookmarkMapper.updateOne(bookmark);
	}

	public boolean deleteOne(int id) {
		return bookmarkMapper.deleteOne(id);
	}

	public List<Bookmark> selectByCategory(String category){
		return bookmarkMapper.selectByCategory(category);
	}

	public List<Bookmark> selectByKeyword(String keyword){
		return bookmarkMapper.selectByKeyword(keyword);
	}

	public int count() {
		return bookmarkMapper.count();
	}

	public int countByCategory(String category) {
		return bookmarkMapper.countByCategory(category);
	}

	public int countByKeyword(String keyword) {
		return bookmarkMapper.countByKeyword(keyword);
	}
}
