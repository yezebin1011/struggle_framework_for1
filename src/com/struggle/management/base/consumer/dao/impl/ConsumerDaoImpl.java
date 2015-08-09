/**
 * 
 */
package com.struggle.management.base.consumer.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.struggle.management.base.consumer.dao.ConsumerDao;
import com.struggle.model.Consumer;
import com.struggle.model.Consumer_Role_Relation;
import com.struggle.model.Role;

/**
 * 用户信息实现类
 * 
 * @author yezebin
 * 
 */
@Repository
public class ConsumerDaoImpl implements ConsumerDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Consumer> getConsumerInfo(Map<String, Object> condition) {

		List<Consumer> list = new ArrayList<Consumer>();

		String querySql = (String) condition.get("querySql");

		Object[] params = (Object[]) condition.get("params");

		list = hibernateTemplate.find(querySql, params);

		return list;
	}

	@Override
	public List<Consumer> getConsumerInfoList(Map<String, String> condition) {

		List<Consumer> consumerInfoList = new ArrayList<Consumer>();

		final String querySql = (String) condition.get("queryListInfoSql");

		final int start = Integer.parseInt(condition.get("start"));

		final int number = Integer.parseInt(condition.get("number"));

		consumerInfoList = hibernateTemplate
				.executeFind(new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query query = session.createQuery(querySql);

						query.setFirstResult(start);

						query.setMaxResults(number);

						List list = query.list();

						return list;
					}

				});

		return consumerInfoList;
	}

	@Override
	public Long getConsumerInfoListCount(Map<String, String> condition)
			throws Exception {

		String queryListCountSql = condition.get("queryListCountSql");

		List<Long> count = hibernateTemplate.find(queryListCountSql);

		return (!count.isEmpty() ? count.get(0) : 0);
	}

	@Override
	public void insertConsumer(Consumer consumer) throws Exception {

		hibernateTemplate.save(consumer);

	}

	@Override
	public Consumer getById(Long consumer_id) {

		return hibernateTemplate.get(Consumer.class, consumer_id);
	}

	@Override
	public void updateConsumer(Consumer consumer) throws Exception {

		hibernateTemplate.update(consumer);

	}

	@Override
	public void deleteConsumer(Map<String, Object> condition) throws Exception {

		final String querySql = (String) condition.get("querySql");

		final Object[] params = (Object[]) condition.get("params");

		hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(querySql);

				query.setParameterList("consumer_id", params);

				return query.executeUpdate();
			}

		});

	}

	@Override
	public List<Role> getRoleConfigInfo(Map<String, Object> condition) {

		List<Role> list = new ArrayList<Role>();

		String querySql = (String) condition.get("querySql");

		Object[] params = null;

		if (condition.containsKey("params") && condition.get("params") != null) {

			params = (Object[]) condition.get("params");
		}

		list = hibernateTemplate.find(querySql, params);

		return list;
	}

	@Override
	public void insertConsumer_Role_RelForBatch(
			final List<Consumer_Role_Relation> addList) throws Exception {

		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Transaction tx = session.beginTransaction();

				for (int index = 0; index < addList.size(); index++) {

					Consumer_Role_Relation consumer_role_rel = addList
							.get(index);

					session.save(consumer_role_rel);

					tx.commit();

					if (index != 0 && index % 50 == 0) {

						session.flush();

						session.clear();

					}
				}

				return null;
			}

		});

	}

	@Override
	public void deleteConsumer_Role_RelForBatch(
			final List<Consumer_Role_Relation> deleteList) throws Exception {

		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Transaction tx = session.beginTransaction();

				for (int index = 0; index < deleteList.size(); index++) {

					Consumer_Role_Relation consumer_role_rel = deleteList
							.get(index);

					session.delete(consumer_role_rel);

					tx.commit();

					if (index != 0 && index % 50 == 0) {

						session.flush();

						session.clear();

					}

				}

				return null;
			}

		});

	}

	@Override
	public Consumer_Role_Relation getByConsumerId(Long consumer_id) {

		String querySql = "from Consumer_Role_Relation t1 where t1.consumer_role_relationId.consumer_id = ?";

		List<Consumer_Role_Relation> list = new ArrayList<Consumer_Role_Relation>();

		Consumer_Role_Relation consumer_role_rel = new Consumer_Role_Relation();

		list = hibernateTemplate.find(querySql, new Object[] { consumer_id });

		if (!list.isEmpty()) {

			consumer_role_rel = list.get(0);
		}

		return consumer_role_rel;
	}

}
