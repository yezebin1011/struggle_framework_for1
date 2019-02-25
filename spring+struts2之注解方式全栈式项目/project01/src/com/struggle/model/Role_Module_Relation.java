/**
 * 
 */
package com.struggle.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色模块关系映射类
 * @author yezebin
 *
 */
@Entity
@Table(name = "t_struggle_role_rel_module", catalog = "struggle_database")
public class Role_Module_Relation implements java.io.Serializable{
	
	@Id
	private Role_Module_RelationId role_module_relationId;

	public Role_Module_RelationId getRole_module_relationId() {
		return role_module_relationId;
	}

	public void setRole_module_relationId(
			Role_Module_RelationId role_module_relationId) {
		this.role_module_relationId = role_module_relationId;
	}
	
	
	
	

}
