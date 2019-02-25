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
 * 角色信息映射类
 * 
 * @author yzb
 * 
 */
@Entity
@Table(name = "t_struggle_role", catalog = "struggle_database")
public class Role implements java.io.Serializable {

	/**
	 * 角色代号
	 */
	private Long role_id;

	/**
	 * 角色名称
	 */
	private String role_name;

	public Role() {

	}

	public Role(Long role_id, String role_name) {

		this.role_id = role_id;

		this.role_name = role_name;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "role_id", unique = true, nullable = false)
	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	@Column(name = "role_name", nullable = false, length = 50)
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}
