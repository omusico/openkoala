package org.openkoala.gqc.facade;

import java.util.Map;
import java.util.Set;

import org.dayatang.querychannel.Page;
import org.openkoala.gqc.core.domain.GeneralQuery;
import org.openkoala.gqc.core.domain.GeneralQueryEntity;
import org.openkoala.gqc.facade.dto.GeneralQueryDTO;

/**
 * 
 * 通用查询器应用层接口，提供增删改查功能
 *
 */
public interface GqcFacade {

	/**
	 * 获取通用查询实例
	 * @param clazz
	 * @param id 通用查询主键
	 * @return
	 */
	<T extends GeneralQueryEntity> T getEntity(Class<T> clazz, Long id);
	
	/**
	 * 保存用查询实例
	 * @param entity
	 */
	void saveEntity(GeneralQueryEntity entity);
	
	/**
	 * 更新用查询实例
	 * @param entity
	 */
	void updateEntity(GeneralQueryEntity entity);

	/**
	 * 删除用查询实例
	 * @param entity
	 */
	void removeEntity(GeneralQueryEntity entity);
	
	
	void removeEntity(String ids);
	
	/**
	 * 批量删除用查询实例
	 * @param entities
	 */
	<T extends GeneralQueryEntity> void removeEntities(Set<T> entities);
	
	/**
	 * 分页查询用查询实例
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<GeneralQuery> pagingQueryGeneralQueries(int currentPage, int pagesize);
	
	/**
	 * 获取通用查询实例
	 * @param id 主键
	 * @return
	 */
	GeneralQueryDTO getById(Long id);
	
	/**
	 * 分页查询用查询实例
	 * @param queryName 查询器名称
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	Page<GeneralQuery> pagingQueryGeneralQueriesByQueryName(String queryName, int currentPage, int pagesize);
	
	Page<Map<String, Object>> pagingQuery(GeneralQueryDTO generalQuery, int currentPage, int pagesize);
	
	void saveGeneralQuery(GeneralQueryDTO generalQuery);
}