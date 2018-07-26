package com.zd.domain;

import org.apache.commons.lang3.StringUtils;

public class CommonUtils {

	public static String toCommandString(String command) {
		StringBuffer sb = new StringBuffer("");
		if (StringUtils.isNotBlank(command)) {
			sb.append("{(len=").append(command.length()).append(")").append(command).append("}");
			return sb.toString();
		}
		return command;
	}
}
