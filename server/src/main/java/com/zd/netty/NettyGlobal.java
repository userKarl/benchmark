package com.zd.netty;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class NettyGlobal {
	
	//与指令服务器连接的map
	public static ConcurrentHashMap<String,ChannelHandlerContext> ctrChannelMap=new ConcurrentHashMap<String,ChannelHandlerContext>();
	
}
