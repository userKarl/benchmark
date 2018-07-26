package com.zd;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zd.netty.NettyClient;

@SpringBootApplication
public class BenchmarkApplication implements CommandLineRunner{

	@Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(BenchmarkApplication.class, args);
	}

	@Autowired
	private NettyClient client;

	@Value("${netty.server.port}")
	private int port;

	@Value("${netty.server.host}")
	private String host;
	
	@Bean
	public NettyClient nettyClient() {
		return new NettyClient();
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
//		client.start(host,port);
//		new Thread(new MarketThread()).start();
	}
}
