package benchmark;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.zd.common.utils.StringUtils;
import com.zd.entity.User;

public class Cacl {

	private static final Logger logger=LoggerFactory.getLogger(Cacl.class);
//	private volatile static Map<String,String> map=Maps.newHashMap();
//	private volatile static LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>();
	
	public static void bm() {
		int n=10000;
		long start=System.nanoTime();
		Map<String,String> map=Maps.newHashMap();
		LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>();
		for(int i=0;i<n;i++) {
			User user=new User();
			if(i%2==0) {
				user.setSex("1");
			}else {
				user.setSex("0");
			}
			user.setName(UUID.randomUUID().toString());
			user.setScore1(new BigDecimal(""+Math.random()*10000));
			user.setScore2(new BigDecimal(""+Math.random()*10000));
			user.setResult(new BigDecimal("0"));
			queue.add(user.MyToString());
		}
		long anlyStart=System.nanoTime();
		long constructTime=anlyStart-start;
		
		while(true) {
			String poll = queue.poll();
			if(StringUtils.isNotBlank(poll)) {
				User user=new User();
				user.MyReadString(poll);
				user.setResult(BigDecimal.valueOf((Math.pow(user.getScore1().divide(user.getScore2(),4,BigDecimal.ROUND_HALF_UP).doubleValue(),2)+Math.sqrt(user.getScore1().doubleValue()))*(Math.abs(user.getScore1().subtract(user.getScore2()).doubleValue()))));
				map.put(user.getName(), user.MyToString());
			}else {
				long end=System.nanoTime();
				long anlyTime=end-anlyStart;
				long totalTime=end-start;
				logger.info("构造{}条User数据的队列耗时：{} ms，解析并添加至Map耗时：{} ms，总耗时：{} ms",n,constructTime/1e6,anlyTime/1e6,totalTime/1e6);
				break;
			}
		}
	}
	public static void main(String[] args) {
		long start=System.nanoTime();
		for(int i=0;i<100;i++) {
			bm();
		}
		long end=System.nanoTime();
		logger.info("总耗时：{} ms",(end-start)/1e6);
	}
}
