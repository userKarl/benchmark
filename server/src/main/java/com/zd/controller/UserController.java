package com.zd.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zd.common.utils.json.JacksonUtil;
import com.zd.entity.User;
import com.zd.jpa.JpaUserDao;
import com.zd.redis.RedisService;
import com.zd.service.UserService;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JpaUserDao userDao;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * redis循环插入数据
	 * @param n
	 */
	@GetMapping("redis/user/loopInsert")
	public void redisLoopInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				redisService.set(user.getName(), JacksonUtil.objToJson(user));
			}
			long end = System.nanoTime();
			logger.info("使用Redis,循环插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * Redis管道方式插入数据
	 * @param n
	 */
	@GetMapping("redis/user/pipeInsert")
	public void redispipeInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			Map<String,String> map=Maps.newHashMap();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				map.put(user.getName(), JacksonUtil.objToJson(user));
			}
			redisService.executePipelined(map);
			long end = System.nanoTime();
			logger.info("使用Redis管道,批量插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * Jpa循环插入数据
	 * @param n
	 */
	@GetMapping("jpa/user/loopInsert")
	public void jpaloopInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				com.zd.jpa.User user = new com.zd.jpa.User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				userDao.save(user);
			}
			long end = System.nanoTime();
			logger.info("使用jpa循环插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC使用声明式事务+合并Sql方式插入数据
	 * @param n
	 */
	@GetMapping("user/declCombineInsert")
	public void declCombineInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			List<User> list=Lists.newArrayList();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				list.add(user);
			}
			userService.declCombineInsert(list);
			long end = System.nanoTime();
			logger.info("使用JDBC声明式事务+合并SQL，插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC使用显式事务+合并Sql添加数据
	 * @param n
	 */
	@GetMapping("user/tsCombineInsert")
	public void tsCombineInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			List<User> list=Lists.newArrayList();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				list.add(user);
			}
			userService.exTsCombineInsert(list);
			long end = System.nanoTime();
			logger.info("使用JDBC显式事务+合并Sql，插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC使用显式事务添加数据
	 * @param n
	 */
	@GetMapping("user/tsInsert")
	public void tsInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			List<User> list=Lists.newArrayList();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				list.add(user);
			}
			userService.transInsert(list);
			long end = System.nanoTime();
			logger.info("使用JDBC显式事务，插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC合并Sql方式插入数据
	 * @param n
	 */
	@GetMapping("user/combineInsert")
	public void combineInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			List<User> list=Lists.newArrayList();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				list.add(user);
			}
			userService.combineInsert(list);
			long end = System.nanoTime();
			logger.info("使用JDBC合并SQL，插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC循环插入数据
	 * @param n
	 */
	@GetMapping("user/loopInsert")
	public void loopInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				userService.insert(user);
			}
			long end = System.nanoTime();
			logger.info("使用List循环插入{}条数据，耗时：{} ms", n, (end - start) / 1e6);
		}
		
	}
	
	/**
	 * JDBC批量插入数据
	 * @param n
	 */
	@GetMapping("user/batchInsert")
	public void batchInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			List<User> list = Lists.newArrayList();
			for (int i = 0; i < n; i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				list.add(user);
			}
			long insertStart = System.nanoTime();
			int batchInsert = userService.batchInsert(list);
			if (batchInsert == list.size()) {
				long end = System.nanoTime();
				logger.info("使用List批量插入{}条数据，构造数据耗时：{} ms，插入数据耗时：{} ms，总耗时：{} ms", n, (insertStart - start) / 1e6,
						(end - insertStart) / 1e6, (end - start) / 1e6);
			}
		}
		
	}

	@GetMapping("user/deleteAll")
	public void deleteAll() {
		userService.deleteAll();
	}
	
	/**
	 * JDBC使用队列，循环插入数据
	 * @param n
	 */
	@GetMapping("user/queue/loopInsert")
	public void queueLoopInsert(int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			long start = System.nanoTime();
			LinkedBlockingQueue<User> queue=new LinkedBlockingQueue<>();
			for(int i=0;i<n;i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				queue.add(user);
			}
			long insertStart = System.nanoTime();
			while(true) {
				User poll = queue.poll();
				if(poll!=null) {
					userService.insert(poll);
				}else {
					long end = System.nanoTime();
					logger.info("使用队列插入{}条数据，构造数据耗时：{} ms，插入数据耗时：{} ms，总耗时：{} ms", n, (insertStart - start) / 1e6,
							(end - insertStart) / 1e6, (end - start) / 1e6);
					break;
				}
				
			}
		}
		
	}
	
	/**
	 * JDBC使用队列，多线程方式插入数据
	 * @param threadCount
	 * @param n
	 */
	@GetMapping("user/queue/multiThreadInsert")
	public void multiThreadInsert(int threadCount, int loopCount,int n) {
		for(int m=0;m<loopCount;m++) {
			ExecutorService exec = Executors.newFixedThreadPool(threadCount);
			CountDownLatch cdl=new CountDownLatch(threadCount);
			long start = System.nanoTime();
			LinkedBlockingQueue<User> queue=new LinkedBlockingQueue<>();
			for(int i=0;i<n;i++) {
				User user = new User();
				user.setName(UUID.randomUUID().toString().replace("-",""));
				user.setSex("1");
				user.setScore1(BigDecimal.valueOf(Math.random() * 100));
				user.setScore2(BigDecimal.valueOf(Math.random() * 100));
				queue.add(user);
			}
			long insertStart = System.nanoTime();
			for(int i=0;i<threadCount;i++) {
				exec.execute(new Runnable() {
					
					@Override
					public void run() {
						while(true) {
							User poll = queue.poll();
							if(poll!=null) {
								userService.insert(poll);
							}else {
								cdl.countDown();
								break;
							}
							
						}
					}
				});
			}
			try {
				cdl.await();
			} catch (Exception e) {
				exec.shutdown();
			}
			long end = System.nanoTime();
			logger.info("使用队列,{}线程插入{}条数据，构造数据耗时：{} ms，插入数据耗时：{} ms，总耗时：{} ms",threadCount, n, (insertStart - start) / 1e6,
					(end - insertStart) / 1e6, (end - start) / 1e6);
			
		}
		
		
		
	}
	
}
