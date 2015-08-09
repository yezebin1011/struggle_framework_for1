/**
 * 
 */
package com.struggle.management.base.module.dao;

import java.util.List;
import java.util.Map;

import com.struggle.model.Module;

/**
 * 模块信息接口
 * 
 * @author yzb
 * 
 */
public interface ModuleDao {

	public List<Module> getModuleList(Map<String, String> condition);

	public List<Module> getModuleInfoList(Map<String, String> condition);

	public Long getModuleInfoListCount(Map<String, String> condition);

	public List<Module> getModuleSelectList(Map<String, String> condition);

	public Module getById(Long module_id);

	public void insertModule(Module module) throws Exception;

	public void updateModule(Module module) throws Exception;

	public void deleteModule(Map<String, Object> condition) throws Exception;

	public List<Module> getModuleCheckedSelectList(Map<String, String> condition);

}
