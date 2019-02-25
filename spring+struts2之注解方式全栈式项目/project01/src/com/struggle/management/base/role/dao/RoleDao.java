/**
 * 
 */
package com.struggle.management.base.role.dao;

import java.util.List;
import java.util.Map;

import com.struggle.model.Module;
import com.struggle.model.Role;
import com.struggle.model.Role_Module_Relation;

/**
 * 角色信息接口类
 * 
 * @author yezebin
 * 
 */
public interface RoleDao {

	public List<Role> getRoleInfoList(Map<String, String> condition);

	public Long getRoleInfoListCount(Map<String, String> condition);

	public void insertRole(Role role) throws Exception;

	public Role getById(Long role_id);

	public List<Role_Module_Relation> getByRoleId(Long role_id);

	public void updateRole(Role role) throws Exception;

	public void deleteRole(Map<String, Object> condition) throws Exception;

	public List<Module> getModuleConfigInfo(Map<String, Object> condition);

	public void insertRole_Module_Relation(List<Role_Module_Relation> addList)
			throws Exception;

	public void deleteRole_Module_Relation(List<Role_Module_Relation> removeList)
			throws Exception;

}
