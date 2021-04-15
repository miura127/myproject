package com.miura.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.miura.domain.model.Bookmark;

@Mapper
public interface BookmarkMapper {
	//登録
	public boolean insert(Bookmark bookmark);

	//1件取得
	public Bookmark selectOne(int id);

	//全件取得
	public List<Bookmark> selectAll();

	//1件更新
	public boolean updateOne(Bookmark bookmark);

	//1件削除
	public boolean deleteOne(int id);

	//カテゴリ検索
	public List<Bookmark> selectByCategory(String category);

	//キーワードによるあいまい検索
	public List<Bookmark> selectByKeyword(String keyword);

	//件数取得
	public int count();

	//カテゴリ検索の件数取得
	public int countByCategory(String category);

	//キーワード検索の結果取得
	public int countByKeyword(String keyword);
}
