package com.zd.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Table(name="user")
@Entity
public class User {

	private int id;
	private String name;
	private String sex;
	private BigDecimal score1;
	private BigDecimal score2;
	private BigDecimal result;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name="score1")
	public BigDecimal getScore1() {
		return score1;
	}
	public void setScore1(BigDecimal score1) {
		this.score1 = score1;
	}
	@Column(name="score2")
	public BigDecimal getScore2() {
		return score2;
	}
	public void setScore2(BigDecimal score2) {
		this.score2 = score2;
	}
	@Transient
	public BigDecimal getResult() {
		return result;
	}
	public void setResult(BigDecimal result) {
		this.result = result;
	}
	
	
	
}
