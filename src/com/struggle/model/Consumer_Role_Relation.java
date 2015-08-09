/**
 * 
 */
package com.struggle.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色关系映射类
 * 
 * @author yezebin
 * 
 */
@Entity
@Table(name = "t_struggle_consumer_rel_role", catalog = "struggle_database")
public class Consumer_Role_Relation implements java.io.Serializable {

	@Id
	private Consumer_Role_RelationId consumer_role_relationId;


	public Consumer_Role_RelationId getConsumer_role_relationId() {
		return consumer_role_relationId;
	}

	public void setConsumer_role_relationId(
			Consumer_Role_RelationId consumer_role_relationId) {
		this.consumer_role_relationId = consumer_role_relationId;
	}


}
