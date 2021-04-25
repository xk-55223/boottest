package ${package.Mapper};

import ${package.Entity}.${entity};

import java.util.List;
import java.util.Map;

/**
 * ${table.comment} Mapper 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} {

    /**
     * 添加
     *
     * @param entity 实体入参
     * @return 影响行数
     */
    int insert(${entity} entity);

    /**
     * 删除
     *
     * @param id 主键id
     * @return 影响行数
     */
    int delete(Integer id);

    /**
     * 更新
     *
     * @param entity 实体入参
     * @return 影响行数
     */
    int updateById(${entity} entity);

    /**
     * 批量插入
     *
     * @param params 批量数据
     * @return 影响行数
     */
    int batchInsert(List<${entity}> record);

    /**
     * 计数
     *
     * @param params 查询条件
     * @return 结果
     */
    int count(Map<String, Object> params);

    /**
     * 分页查询
     *
     */
    List<${entity}> selectPage();

    /**
     * 查询多个实体
     *
     * @param params 查询条件
     * @return 结果集
     */
    List<${entity}> selectList(Map<String, Object> params);

    /**
     * 查询单个实体
     *
     * @param params 查询条件
     * 结果集
     */
    List<${entity}> selectOne(Map<String, Object> params);
}
</#if>
