/**
 * 
 */
package com.struggle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户信息映射类
 * 
 * @author yezebin
 * 
 */
@Entity
@Table(name = "t_struggle_consumer", catalog = "struggle_database")
public class Consumer implements java.io.Serializable {

	/**
	 * 用户ID
	 */
	private Long consumer_id;

	/**
	 * 用户姓名
	 */
	private String consumer_name;

	/**
	 * 用户密码
	 */
	private String consumer_pwd;

	/**
	 * 用户真实姓名
	 */
	private String consumer_realName;
	
	public Consumer(){
		
		
	}

	public Consumer(Long consumer_id, String consumer_name) {

		this.consumer_id = consumer_id;

		this.consumer_name = consumer_name;

	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "consumer_id", unique = true, nullable = false)
	public Long getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Long consumer_id) {
		this.consumer_id = consumer_id;
	}

	@Column(name = "consumer_name", nullable = false, length = 45)
	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	@Column(name = "consumer_pwd", nullable = false, length = 45)
	public String getConsumer_pwd() {
		return consumer_pwd;
	}

	public void setConsumer_pwd(String consumer_pwd) {
		this.consumer_pwd = consumer_pwd;
	}

	@Column(name = "consumer_realName", nullable = false, length = 60)
	public String getConsumer_realName() {
		return consumer_realName;
	}

	public void setConsumer_realName(String consumer_realName) {
		this.consumer_realName = consumer_realName;
	}

}
