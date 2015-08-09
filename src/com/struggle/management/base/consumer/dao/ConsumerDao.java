/**
 * 
 */
package com.struggle.management.base.consumer.dao;

import java.util.List;
import java.util.Map;

import com.struggle.model.Consumer;
import com.struggle.model.Consumer_Role_Relation;
import com.struggle.model.Role;

/**
 * 用户信息接口
 * 
 * @author yezebin
 * 
 */
public interface ConsumerDao {

	public List<Consumer> getConsumerInfo(Map<String, Object> condition);

	public List<Consumer> getConsumerInfoList(Map<String, String> condition);

	public Long getConsumerInfoListCount(Map<String, String> condition)
			throws Exception;

	public void insertConsumer(Consumer consumer) throws Exception;

	public Consumer getById(Long consumer_id);
	
	public Consumer_Role_Relation getByConsumerId(Long consumer_id);

	public void updateConsumer(Consumer consumer) throws Exception;

	public void deleteConsumer(Map<String, Object> condition) throws Exception;

	public List<Role> getRoleConfigInfo(Map<String, Object> condition);

	public void insertConsumer_Role_RelForBatch(
			List<Consumer_Role_Relation> addList) throws Exception;

	public void deleteConsumer_Role_RelForBatch(
			List<Consumer_Role_Relation> deleteList) throws Exception;

}
