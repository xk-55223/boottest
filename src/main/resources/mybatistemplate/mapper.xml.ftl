<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

    </#if>
    <#if baseResultMap>
        <!-- 通用查询映射结果 -->
        <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <id column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <result column="${field.name}" property="${field.propertyName}"/>
                </#if>
            </#list>
            <#list table.commonFields as field><#--生成公共字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#list>
        </resultMap>

    </#if>
    <#if baseColumnList>
        <!-- 通用查询结果列 -->
        <sql id="Base_Column_List">
            <#list table.fields as field>
                ${field.name},
            </#list>
            <#list table.commonFields as field>
                ${field.name}<#if field_has_next>,</#if>
            </#list>
        </sql>

    </#if>
    <insert id="insert">
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name},
                    </if>
                </#if>
            </#list>
            <#list table.commonFields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name}<#if field_has_next>,</#if>
                    </if>
                </#if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${r'#{'}${field.propertyName}},
                    </if>
                </#if>
            </#list>
            <#list table.commonFields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${r'#{'}${field.propertyName}}<#if field_has_next>,</#if>
                    </if>
                </#if>
            </#list>
        </trim>
    </insert>

    <update id="delete">
        UPDATE ${table.name}
        SET del_flag = 1
        WHERE id = ${r'#{id}'}
    </update>

    <update id="updateById">
        UPDATE ${table.name}
        <set>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name} = ${r'#{'}${field.propertyName}},
                    </if>
                </#if>
            </#list>
            <#list table.commonFields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name} = ${r'#{'}${field.propertyName}}<#if field_has_next>,</#if>
                    </if>
                </#if>
            </#list>
        </set>
        WHERE del_flag = 0
        AND id = ${r'#{id}'};
    </update>

    <insert id="batchInsert">
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name},
                    </if>
                </#if>
            </#list>
            <#list table.commonFields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <if test="${field.propertyName} != null">
                        ${field.name}<#if field_has_next>,</#if>
                    </if>
                </#if>
            </#list>
        </trim>
        VALUES
        <foreach collection="list" item="item" open="(" separator="),(" close=")">
            <#list table.fields as field>
                <choose>
                    <when test="item.${field.propertyName} != null">${r'#{item.'}${field.propertyName}},</when>
                    <otherwise>DEFAULT,</otherwise>
                </choose>
            </#list>
            <#list table.commonFields as field>
                <choose>
                    <when test="item.${field.propertyName} != null">${r'#{item.'}${field.propertyName}},</when>
                    <otherwise>DEFAULT<#if field_has_next>,</#if></otherwise>
                </choose>
            </#list>
        </foreach>
    </insert>

    <select id="count" resultType="int">
        SELECT COUNT(*) FROM ${table.name}
        <where>
            del_flag = 0
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
            <#list table.commonFields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
        </where>
    </select>

    <select id="selectList" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        <where>
            del_flag = 0
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
            <#list table.commonFields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
        </where>
    </select>

    <select id="selectOne" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        <where>
            del_flag = 0
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
            <#list table.commonFields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
        </where>
    </select>

    <select id="selectPage" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        <where>
            del_flag = 0
            <#list table.fields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
            <#list table.commonFields as field>
                <if test="${field.propertyName} != null">
                    AND ${field.name} = ${r'#{'}${field.propertyName}}
                </if>
            </#list>
        </where>
    </select>

</mapper>
