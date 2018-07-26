package com.zd.service;

import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zd.common.utils.StringUtils;
import com.zd.config.Global;

public class MarketThread implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(MarketThread.class);
	
	@Override
	public void run() {
		int i=0;
		long start=0;
		long end=0;
		while(true) {
			System.out.println(Global.marketQueue.size());
			if(Global.marketQueue.size()>10000) {
				break;
			}
			
//			String poll = Global.marketQueue.poll();
//			if(StringUtils.isNotBlank(poll)) {
//				i++;
//			}
//			if(i==1) {
//				start=System.nanoTime();
////				logger.info("start:{}",System.nanoTime());
//			}
//			if(i>=1000) {
//				end=System.nanoTime();
////				logger.info("end:{}",System.nanoTime());
//				break;
//			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		System.out.println(end-start);
	}

	public static void main(String[] args) throws InterruptedException {
		
		
		
//		for(int m=0;m<10;m++) {
			LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>();
			for(int i=0;i<10000;i++) {
				queue.add("{(len=18)TEST0001@@@@@@@@&9asdsadasdadsadaasssssssssssssssssssssssssssssssssssssssssssssssssssssss}"+i);
			}
			int i=0;
			
			BigDecimal start=new BigDecimal("0");
			BigDecimal end=new BigDecimal("0");
			while(true) {
				queue.poll();
//				if(StringUtils.isNotBlank(poll)) {
					i++;
//				}
				
				if(i==1) {
					start=BigDecimal.valueOf(System.nanoTime());
				}
				if(i>=10000) {
					end=BigDecimal.valueOf(System.nanoTime());
					break;
				}
			}
			System.out.println(end.subtract(start).divide(new BigDecimal("1000000000"),8,BigDecimal.ROUND_HALF_UP));
//		}
		
	}
}
