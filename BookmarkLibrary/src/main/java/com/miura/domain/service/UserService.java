package com.miura.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miura.domain.model.User;
import com.miura.domain.repository.mybatis.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userMapper.insert(user);
	}

	public User selectOne(String userId) {
		return userMapper.selectOne(userId);
	}

	public List<User> selectAll(){
		return userMapper.selectAll();
	}

	public boolean updateOne(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userMapper.updateOne(user);
	}

	public boolean deleteOne(String userId) {
		return userMapper.deleteOne(userId);
	}
}
