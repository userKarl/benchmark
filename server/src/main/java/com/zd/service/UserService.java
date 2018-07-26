package com.zd.service;

import java.util.List;

import com.zd.entity.User;

public interface UserService {

	int insert(User user);
	int update(User user);
	int delete(User user);
	List<User> findAll();
	User findById(int id);
	int batchInsert(List<User> list);
	int deleteAll();
	int combineInsert(List<User> list);
	void transInsert(List<User> list);
	void exTsCombineInsert(List<User> list);
	int declCombineInsert(List<User> list);
}
