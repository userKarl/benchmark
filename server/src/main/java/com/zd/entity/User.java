package com.zd.entity;

import java.math.BigDecimal;

import com.zd.common.utils.StringUtils;

import lombok.Data;

@Data
public class User {

	private int id;
	private String name;
	private String sex;
	private BigDecimal score1;
	private BigDecimal score2;
	private BigDecimal result; 
	
	public String MyToString() {
		return this.id+"@"+this.name+"@"+this.sex+"@"+this.score1.toString()+"@"+this.score2.toString()+"@"+this.result;
	}
	
	public void MyReadString(String temp) {
		if(StringUtils.isNotBlank(temp)) {
			String[] split = temp.split("@");
			if(split!=null) {
				if(split.length>0) {
					this.id=Integer.parseInt(split[0]);
				}
				if(split.length>1) {
					this.name=split[1];
				}
				if(split.length>2) {
					this.sex=split[2];
				}
				if(split.length>3) {
					this.score1=new BigDecimal(split[3]);
				}
				if(split.length>4) {
					this.score2=new BigDecimal(split[4]);
				}
				if(split.length>5) {
					this.result=new BigDecimal(split[5]);
				}
			}
		}
	}
}
