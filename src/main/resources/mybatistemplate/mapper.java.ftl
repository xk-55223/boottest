package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import java.util.List;

/**
 * <p>
 * ${table.comment} Mapper 接口
 * </p>
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
     */
    int insert(${entity} entity);

    /**
     * 删除
     */
    int delete(String recordCode);

    /**
     * 更新
     */
    int update(${entity} record);

    /**
     * 批量插入
     */
    int batchInsert(List<${entity}> record);

    /**
     * 计数
     */
    int count(${entity} record);

    /**
     * 分页查询
     */
    List<${entity}> selectPage();

    /**
     * 查询多个实体
     */
    List<${entity}> selectList(${entity} entity);

    /**
     * 查询单个实体
     */
    List<${entity}> selectOne(${entity} entity);
}
</#if>
