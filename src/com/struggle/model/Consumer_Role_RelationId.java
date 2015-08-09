/**
 * 
 */
package com.struggle.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 用户角色关系主键类
 * 
 * @author yezebin
 * 
 */
@Embeddable
public class Consumer_Role_RelationId implements java.io.Serializable {

	private Long consumer_id;

	private Long role_id;

	@Column(name = "consumer_id", unique = true, nullable = false)
	public Long getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Long consumer_id) {
		this.consumer_id = consumer_id;
	}

	@Column(name = "role_id", unique = true, nullable = false)
	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof Consumer_Role_RelationId) {

			Consumer_Role_RelationId key = (Consumer_Role_RelationId) o;

			if (this.role_id.equals(key.getRole_id())
					&& this.consumer_id.equals(key.getConsumer_id())) {

				return true;
			}

		}

		return false;
	}

	@Override
	public int hashCode() {

		return this.role_id.hashCode();

	}

}
