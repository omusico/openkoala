package org.openkoala.security.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dayatang.querychannel.Page;
import org.openkoala.security.facade.SecurityAccessFacade;
import org.openkoala.security.facade.SecurityConfigFacade;
import org.openkoala.security.facade.dto.MenuResourceDTO;
import org.openkoala.security.facade.dto.PermissionDTO;
import org.openkoala.security.facade.dto.RoleDTO;
import org.openkoala.security.facade.dto.UrlAccessResourceDTO;
import org.openkoala.security.web.filter.ShiroFilerChainManager;
import org.openkoala.security.web.util.AuthUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth/role")
public class RoleController {

	@Inject
	private SecurityAccessFacade securityAccessFacade;

	@Inject
	private SecurityConfigFacade securityConfigFacade;

	@Inject
	private ShiroFilerChainManager shiroFilerChainManager;

	/**
	 * 根据用户名查找所有的角色。
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findRolesByUsername")
	public Map<String, Object> findRoleDtosByUsername() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RoleDTO> roleDtos = securityAccessFacade.findRoleDtosBy(AuthUserUtil.getUserAccount());
		result.put("data", roleDtos);
		return result;
	}

	/**
	 * 根据用户ID查找所有的角色
	 * 
	 * @param page
	 * @param pagesize
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagingQueryByUserId")
	public Page<RoleDTO> pagingQueryRolesByUserId(int page, int pagesize, Long userId) {
		Page<RoleDTO> results = securityAccessFacade.pagingQueryRolesByUserAccount(page, pagesize, userId);
		return results;
	}

	/**
	 * 添加角色
	 * 
	 * @param roleDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.saveRoleDTO(roleDTO);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 更新角色
	 * 
	 * @param roleDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.updateRoleDTO(roleDTO);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * TODO 支持批量撤销,待优化。
	 * 
	 * @param userDTOs
	 */
	@ResponseBody
	@RequestMapping(value = "/terminate", method = RequestMethod.POST, consumes = "application/json")
	public Map<String, Object> terminate(@RequestBody RoleDTO[] roleDTOs) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.terminateRoleDTOs(roleDTOs);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 根据条件分页查询所有的角色
	 * 
	 * @param page
	 * @param pagesize
	 * @param roleDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagingquery")
	public Page<RoleDTO> pagingQuery(int page, int pagesize, RoleDTO roleDTO) {
		Page<RoleDTO> results = securityAccessFacade.pagingQueryRoles(page, pagesize, roleDTO);
		return results;
	}

	@ResponseBody
	@RequestMapping("/findMenuResourceTreeSelectItemByRoleId")
	public Map<String,Object> findMenuResourceTreeSelectItemByRoleId(Long roleId){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		List<MenuResourceDTO> menuResourceDTOs = securityAccessFacade.findMenuResourceTreeSelectItemByRoleId(roleId);
		dataMap.put("data", menuResourceDTOs);
		return dataMap;
	}
	
	/**
	 * 为角色授权菜单资源。
	 * 
	 * @param roleId
	 * @param menuResourceDTOs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grantMenuResources", method = RequestMethod.POST, consumes = "application/json")
	public Map<String, Object> grantMenuResources(Long roleId, @RequestBody List<MenuResourceDTO> menuResourceDTOs) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.grantMenuResourcesToRole(roleId, menuResourceDTOs);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 为角色授权URL资源
	 * 
	 * @param roleId
	 * @param urlAccessResourceIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/grantUrlAccessResources")
	public Map<String, Object> grantUrlAccessResources(Long roleId, Long[] urlAccessResourceIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.grantUrlAccessResourcesToRole(roleId, urlAccessResourceIds);
		shiroFilerChainManager.initFilterChain();// 更新shiro拦截器链。
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 从角色中撤销Url访问资源。
	 * 
	 * @param roleId
	 * @param urlAccessResourceIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/terminateUrlAccessResources")
	public Map<String, Object> terminateUrlAccessResourcesFromRole(Long roleId, Long[] urlAccessResourceIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.terminateUrlAccessResourcesFromRole(roleId, urlAccessResourceIds);
		shiroFilerChainManager.initFilterChain();// 更新shiro拦截器链。
		return dataMap;
	}

	/**
	 * 查出已经授权的URL访问资源。
	 * 
	 * @param page
	 * @param pagesize
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagingQueryGrantUrlAccessResourcesByRoleId")
	public Page<UrlAccessResourceDTO> pagingQueryGrantUrlAccessResourcesByRoleId(int page, int pagesize, Long roleId) {
		Page<UrlAccessResourceDTO> results = securityAccessFacade.pagingQueryGrantUrlAccessResourcesByRoleId(page, pagesize,
				roleId);
		return results;
	}
	
	/**
	 * 查出没有授权的URL访问资源。
	 * @param page
	 * @param pagesize
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagingQueryNotGrantUrlAccessResourcesByRoleId")
	public Page<UrlAccessResourceDTO> pagingQueryNotGrantUrlAccessResourcesByRoleId(int page, int pagesize, Long roleId) {
		Page<UrlAccessResourceDTO> results = securityAccessFacade.pagingQueryNotGrantUrlAccessResourcesByRoleId(page, pagesize,
				roleId);
		return results;
	}

	/**
	 * 为角色授权权限
	 * 
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/grantPermissions")
	public Map<String, Object> grantPermissions(Long roleId, Long[] permissionIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.grantPermissionsToRole(roleId, permissionIds);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 撤销角色的权限
	 * 
	 * @param roleId
	 * @param permssionIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/terminatePermissions")
	public Map<String, Object> terminatePermissions(Long roleId, Long[] permssionIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.terminatePermissionsToRole(roleId, permssionIds);
		dataMap.put("result", "success");
		return dataMap;
	}

	/**
	 * 根据角色ID分页查询没有授权的权限
	 * 
	 * @param page
	 * @param pagesize
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagingQueryNotGrantPermissionsByRole")
	public Page<PermissionDTO> pagingQueryNotGrantPermissionsByRole(int page, int pagesize, Long roleId) {
		return securityAccessFacade.pagingQueryNotGrantPermissionsByRole(page, pagesize, roleId);
	}

	// ==================TODO==================
	// 分配页面元素资源
	public Map<String, Object> grantPageElementResources(Long roleId, Long[] menuResourceIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.grantPageElementResourcesToRole(roleId, menuResourceIds);
		dataMap.put("result", "success");
		return dataMap;
	}

	// 分配方法级别资源
	public Map<String, Object> grantMethodInvocationResources(Long roleId, Long[] menuResourceIds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		securityConfigFacade.grantMethodInvocationResourcesToUser(roleId, menuResourceIds);
		dataMap.put("result", "success");
		return dataMap;
	}
}