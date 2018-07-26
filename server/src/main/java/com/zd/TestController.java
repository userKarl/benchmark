package com.zd;

import java.util.Map.Entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zd.domain.CommandCode;
import com.zd.domain.CommonUtils;
import com.zd.domain.NetInfo;
import com.zd.netty.NettyGlobal;

import io.netty.channel.ChannelHandlerContext;

@RestController
public class TestController {

	@GetMapping("test")
	public void test() {
		for(Entry<String,ChannelHandlerContext> entry:NettyGlobal.ctrChannelMap.entrySet()) {
			NetInfo netInfo=new NetInfo();
			netInfo.code = CommandCode.MARKET01;
//			netInfo.systemCode = "0";
			netInfo.todayCanUse = "++";
			entry.getValue().channel().writeAndFlush(CommonUtils.toCommandString(netInfo.MyToString()));
		}
	}
}
