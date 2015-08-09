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
 * 模块信息映射类
 * 
 * @author yzb
 * 
 */
@Entity
@Table(name = "t_struggle_module", catalog = "struggle_database")
public class Module implements java.io.Serializable {

	/**
	 * 模块代号
	 */
	private Long module_id;

	/**
	 * 模块名称
	 */
	private String module_name;

	/**
	 * 模块对应的链接地址
	 */
	private String module_url;

	/**
	 * 上级模块代号
	 */
	private Long module_parentId;

	/**
	 * 节点是否为子节点（0-非子节点；1-子节点）
	 */
	private Integer module_isLeaf;

	public Module() {

	}

	public Module(Long module_id, String module_name) {

		this.module_id = module_id;

		this.module_name = module_name;

	}

	public Module(Long module_id, String module_name, String module_url,
			Long module_parentId, Integer module_isLeaf) {
		this.module_id = module_id;
		this.module_name = module_name;
		this.module_url = module_url;
		this.module_parentId = module_parentId;
		this.module_isLeaf = module_isLeaf;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "module_id", unique = true, nullable = false)
	public Long getModule_id() {
		return module_id;
	}

	public void setModule_id(Long module_id) {
		this.module_id = module_id;
	}

	@Column(name = "module_name", nullable = false, length = 50)
	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	@Column(name = "module_url", length = 60)
	public String getModule_url() {
		return module_url;
	}

	public void setModule_url(String module_url) {
		this.module_url = module_url;
	}

	@Column(name = "module_parentId", nullable = false)
	public Long getModule_parentId() {
		return module_parentId;
	}

	public void setModule_parentId(Long module_parentId) {
		this.module_parentId = module_parentId;
	}

	@Column(name = "module_isLeaf", nullable = false)
	public Integer getModule_isLeaf() {
		return module_isLeaf;
	}

	public void setModule_isLeaf(Integer module_isLeaf) {
		this.module_isLeaf = module_isLeaf;
	}

}
