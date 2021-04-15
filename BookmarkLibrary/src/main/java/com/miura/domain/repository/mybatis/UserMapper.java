package com.miura.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.miura.domain.model.User;

@Mapper
public interface UserMapper {
	//登録
	public boolean insert(User user);
	//1件取得
	public User selectOne(String userId);
	//全件取得
	public List<User> selectAll();
	//1件更新
	public boolean updateOne(User user);
	//1県削除
	public boolean deleteOne(String userId);
}
