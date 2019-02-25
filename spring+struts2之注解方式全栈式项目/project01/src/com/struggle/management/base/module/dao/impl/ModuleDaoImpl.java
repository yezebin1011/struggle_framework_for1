/**
 * 
 */
package com.struggle.management.base.module.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.struggle.management.base.module.dao.ModuleDao;
import com.struggle.model.Module;

/**
 * 模块信息实现类
 * 
 * @author yzb
 * 
 */
@Repository
public class ModuleDaoImpl implements ModuleDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Module> getModuleList(Map<String, String> condition) {

		List<Module> moduleList = new ArrayList<Module>();

		Long module_id = Long.valueOf(condition.get("module_id"));

		String consumer_name = condition.get("consumer_name");

		String querySql = " select new Module(t1.module_id,t1.module_name,t1.module_url,t1.module_parentId,t1.module_isLeaf)"
				+ " from Module t1,Role_Module_Relation t2,Consumer_Role_Relation t3,Consumer t4"
				+ " where t1.module_id = t2.role_module_relationId.module_id "
				+ " and t2.role_module_relationId.role_id = t3.consumer_role_relationId.role_id"
				+ " and t3.consumer_role_relationId.consumer_id = t4.consumer_id"
				+ " and t1.module_parentId = ?" + " and t4.consumer_name = ?";

		moduleList = hibernateTemplate.find(querySql, new Object[] { module_id,
				consumer_name });

		return moduleList;
	}

	@Override
	public List<Module> getModuleInfoList(Map<String, String> condition) {

		List<Module> moduleList = new ArrayList<Module>();

		final String querySql = (String) condition.get("queryListInfoSql");

		final int start = Integer.parseInt(condition.get("start"));

		final int number = Integer.parseInt(condition.get("number"));

		moduleList = hibernateTemplate.executeFind(new HibernateCallback() {

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

		return moduleList;
	}

	@Override
	public Long getModuleInfoListCount(Map<String, String> condition) {

		String queryListCountSql = condition.get("queryListCountSql");

		List<Long> count = hibernateTemplate.find(queryListCountSql);

		return (!count.isEmpty() ? count.get(0) : 0);
	}

	@Override
	public List<Module> getModuleSelectList(Map<String, String> condition) {

		List<Module> list = new ArrayList<Module>();

		Long module_id = Long.parseLong(condition.get("module_id"));

		String querySql = " from Module t where t.module_parentId = ?";

		list = hibernateTemplate.find(querySql, new Object[] { module_id });

		return list;
	}

	@Override
	public void insertModule(Module module) throws Exception {

		hibernateTemplate.save(module);

	}

	@Override
	public void updateModule(Module module) throws Exception {

		hibernateTemplate.update(module);

	}

	@Override
	public void deleteModule(Map<String, Object> condition) throws Exception {

		final String querySql = (String) condition.get("querySql");

		final Object[] params = (Object[]) condition.get("params");

		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(querySql);

				query.setParameterList("module_id", params);

				return query.executeUpdate();
			}

		});

	}

	@Override
	public List<Module> getModuleCheckedSelectList(Map<String, String> condition) {

		List<Module> list = new ArrayList<Module>();

		Long role_id = Long.parseLong(condition.get("role_id"));

		String querySql = " select new Module(t1.module_id,t1.module_name) from Module t1, Role_Module_Relation t2"
				+ " where t1.module_id = t2.role_module_relationId.module_id "
				+ " and t2.role_module_relationId.role_id = ?";

		list = hibernateTemplate.find(querySql, new Object[] { role_id });

		return list;
	}

	@Override
	public Module getById(Long module_id) {

		return hibernateTemplate.get(Module.class, module_id);
	}

}
