/**
 * 
 */
package com.struggle.vo;

/**
 * 模块信息列表显示VO
 * 
 * @author yezebin
 * 
 */
public class ModuleInfoList {

	private Long module_id;

	private String module_name;

	private String module_parentName;
	
	private String module_url;
	
	private Long module_parentId;
	
	private Integer module_isLeaf;
	
	public ModuleInfoList(){
		
	}
	
	public ModuleInfoList(Long module_id, String module_name,
			String module_parentName) {
		this.module_id = module_id;
		this.module_name = module_name;
		this.module_parentName = module_parentName;
	}

	public ModuleInfoList(Long module_id, String module_name,
			String module_parentName, String module_url, Long module_parentId,
			Integer module_isLeaf) {
		this.module_id = module_id;
		this.module_name = module_name;
		this.module_parentName = module_parentName;
		this.module_url = module_url;
		this.module_parentId = module_parentId;
		this.module_isLeaf = module_isLeaf;
	}

	public Long getModule_id() {
		return module_id;
	}

	public void setModule_id(Long module_id) {
		this.module_id = module_id;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getModule_parentName() {
		return module_parentName;
	}

	public void setModule_parentName(String module_parentName) {
		this.module_parentName = module_parentName;
	}

	public String getModule_url() {
		return module_url;
	}

	public void setModule_url(String module_url) {
		this.module_url = module_url;
	}

	public Long getModule_parentId() {
		return module_parentId;
	}

	public void setModule_parentId(Long module_parentId) {
		this.module_parentId = module_parentId;
	}

	public Integer getModule_isLeaf() {
		return module_isLeaf;
	}

	public void setModule_isLeaf(Integer module_isLeaf) {
		this.module_isLeaf = module_isLeaf;
	}
	
	

}
