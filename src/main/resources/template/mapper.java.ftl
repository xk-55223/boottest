package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

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
    public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
    int insert(${entity} entity);

    int delete(String recordCode);

    int update(${entity} record);

    int batchInsert(List<${entity}> record);

    int count(${entity} record);

    List<${entity}> selectPage();
    }
</#if>
