/**
 * 
 */
package com.struggle.management.base.role.dao.impl;

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

import com.struggle.management.base.role.dao.RoleDao;
import com.struggle.model.Module;
import com.struggle.model.Role;
import com.struggle.model.Role_Module_Relation;

/**
 * 角色信息实现类
 * 
 * @author yezebin
 * 
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Role> getRoleInfoList(Map<String, String> condition) {

		List<Role> roleInfoList = new ArrayList<Role>();

		final String querySql = (String) condition.get("queryListInfoSql");

		final int start = Integer.parseInt(condition.get("start"));

		final int number = Integer.parseInt(condition.get("number"));

		roleInfoList = hibernateTemplate.executeFind(new HibernateCallback() {

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

		return roleInfoList;
	}

	@Override
	public Long getRoleInfoListCount(Map<String, String> condition) {

		String queryListCountSql = condition.get("queryListCountSql");

		List<Long> count = hibernateTemplate.find(queryListCountSql);

		return (!count.isEmpty() ? count.get(0) : 0);
	}

	@Override
	public void insertRole(Role role) throws Exception {

		hibernateTemplate.save(role);

	}

	@Override
	public Role getById(Long role_id) {

		Role role = hibernateTemplate.get(Role.class, role_id);

		return role;
	}

	@Override
	public void updateRole(Role role) throws Exception {

		hibernateTemplate.update(role);

	}

	@Override
	public void deleteRole(Map<String, Object> condition) throws Exception {

		final String querySql = (String) condition.get("querySql");

		final Object[] params = (Object[]) condition.get("params");

		hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(querySql);

				query.setParameterList("role_id", params);

				return query.executeUpdate();
			}

		});

	}

	@Override
	public List<Module> getModuleConfigInfo(Map<String, Object> condition) {

		List<Module> list = new ArrayList<Module>();

		String querySql = (String) condition.get("querySql");

		Object[] params = null;

		if (condition.containsKey("params") && condition.get("params") != null) {

			params = (Object[]) condition.get("params");
		}

		list = hibernateTemplate.find(querySql, params);

		return list;
	}

	@Override
	public void insertRole_Module_Relation(
			final List<Role_Module_Relation> addList) throws Exception {

		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				for (int index = 0; index < addList.size(); index++) {

					Role_Module_Relation role_module_rel = addList.get(index);

					Transaction tx = session.beginTransaction();

					session.save(role_module_rel);

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
	public void deleteRole_Module_Relation(
			final List<Role_Module_Relation> removeList) throws Exception {

		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				for (int index = 0; index < removeList.size(); index++) {

					Role_Module_Relation role_module_rel = removeList
							.get(index);

					Transaction tx = session.beginTransaction();

					session.delete(role_module_rel);

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
	public List<Role_Module_Relation> getByRoleId(Long role_id) {

		String querySql = " from Role_Module_Relation t1 where t1.role_module_relationId.role_id = ?";

		List<Role_Module_Relation> list = new ArrayList<Role_Module_Relation>();

		list = hibernateTemplate.find(querySql, new Object[] { role_id });

		return list;
	}

}
