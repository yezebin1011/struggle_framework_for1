/**
 * 
 */
package com.struggle.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 角色模块关系主键类
 * 
 * @author yezebin
 * 
 */
@Embeddable
public class Role_Module_RelationId implements java.io.Serializable{

	private Long role_id;

	private Long module_id;

	@Column(name = "role_id", unique = true, nullable = false)
	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	@Column(name = "module_id", unique = true, nullable = false)
	public Long getModule_id() {
		return module_id;
	}

	public void setModule_id(Long module_id) {
		this.module_id = module_id;
	}

}
