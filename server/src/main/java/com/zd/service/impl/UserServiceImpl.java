package com.zd.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.zd.entity.User;
import com.zd.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public int insert(User user) {
		String sql1 = "insert into user(name,sex,score1,score2) values (?,?,?,?)";
		Object[] params1 = new Object[] { user.getName(), user.getSex(), user.getScore1(), user.getScore2() };
		int update = jdbcTemplate.update(sql1, params1);
		return update;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int batchInsert(List<User> list) {
		final List<User> dataList = list;
		String sql = "insert into user(name,sex,score1,score2) values (?,?,?,?)";
		int[] i = jdbcTemplate.batchUpdate(sql, new MyBatchPreparedStatementSetter(dataList));
		return i.length;
	}

	class MyBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

		final ArrayList<User> dataList;

		public MyBatchPreparedStatementSetter(List<User> list) {
			dataList = (ArrayList<User>) list;
		}

		public int getBatchSize() {
			return dataList.size();
		}

		public void setValues(PreparedStatement ps, int i) throws SQLException {

			User user = dataList.get(i);

			ps.setString(1, user.getName());
			ps.setString(2, user.getSex());
			ps.setBigDecimal(3, user.getScore1());
			ps.setBigDecimal(4, user.getScore2());
		}

	}

	@Override
	public int deleteAll() {
		String sql = "delete from user";
		int update = jdbcTemplate.update(sql);
		return update;
	}

	/**
	 * 合并Sql插入
	 */
	@Override
	public int combineInsert(List<User> list) {
		String preSql="insert into user(name,sex,score1,score2) values";
		StringBuilder sb = new StringBuilder(preSql);
		if (list != null && list.size() > 0) {
			int count=10000;
			for(int i=0;i<list.size();i++) {
				User user=list.get(i);
				sb.append("(").append("'").append(user.getName()).append("'").append(",").append("'")
				.append(user.getSex()).append("'").append(",").append(user.getScore1()).append(",")
				.append(user.getScore2()).append(")").append(",");
				if(i%count==0||i==list.size()-1) {
					String s = sb.toString();
					String sql = s.substring(0, s.length() - 1);
					sb.delete(0, sb.length());
					sb.append(preSql);
					jdbcTemplate.update(sql);
				}
			}
			
		}
		return 1;
	}
	
	/**
	 * 声明式事务+合并Sql
	 */
	@Transactional
	@Override
	public int declCombineInsert(List<User> list) {
		String preSql="insert into user(name,sex,score1,score2) values";
		StringBuilder sb = new StringBuilder(preSql);
		if (list != null && list.size() > 0) {
			int count=10000;
			for(int i=0;i<list.size();i++) {
				User user=list.get(i);
				sb.append("(").append("'").append(user.getName()).append("'").append(",").append("'")
				.append(user.getSex()).append("'").append(",").append(user.getScore1()).append(",")
				.append(user.getScore2()).append(")").append(",");
				if(i%count==0||i==list.size()-1) {
					String s = sb.toString();
					String sql = s.substring(0, s.length() - 1);
					sb.delete(0, sb.length());
					sb.append(preSql);
					jdbcTemplate.update(sql);
				}
			}
		}
		return 1;
	}
	
	
	/**
	 * 显式事务+合并Sql
	 */
	@Override
	public void exTsCombineInsert(List<User> list) {
		String preSql="insert into user(name,sex,score1,score2) values";
		StringBuilder sb = new StringBuilder(preSql);
		TransactionTemplate tt =
				new TransactionTemplate(transactionManager);
		tt.execute(new TransactionCallback<User>() {

			@Override
			public User doInTransaction(TransactionStatus ts) {
				if (list != null && list.size() > 0) {
					int count=10000;
					for(int i=0;i<list.size();i++) {
						User user=list.get(i);
						sb.append("(").append("'").append(user.getName()).append("'").append(",").append("'")
						.append(user.getSex()).append("'").append(",").append(user.getScore1()).append(",")
						.append(user.getScore2()).append(")").append(",");
						if(i%count==0||i==list.size()-1) {
							String s = sb.toString();
							String sql = s.substring(0, s.length() - 1);
							sb.delete(0, sb.length());
							sb.append(preSql);
							jdbcTemplate.update(sql);
						}
					}
				}
				return null;
			}});
	}
	
	/**
	 * 显式事务插入数据
	 */
	@Override
	public void transInsert(List<User> list) {
		TransactionTemplate tt =
				new TransactionTemplate(transactionManager);
		tt.execute(new TransactionCallback<User>() {

			@Override
			public User doInTransaction(TransactionStatus ts) {
				for(User user:list) {
					String sql1 = "insert into user(name,sex,score1,score2) values (?,?,?,?)";
					Object[] params1 = new Object[] { user.getName(), user.getSex(), user.getScore1(), user.getScore2() };
					jdbcTemplate.update(sql1, params1);
				}
				return null;
			}});
	}
}
