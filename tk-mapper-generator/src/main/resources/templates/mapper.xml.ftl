<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package.mapper}.${table.mapperName}">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.entity}.${table.entityName}">
        <#list  table.fields  as  field>
          <#if field.keyFlag>
             <id column="${field.name}" property="${field.propertyName}" jdbcType="${field.columnType}"/>
          <#else>
             <result column="${field.name}" property="${field.propertyName}" jdbcType="${field.columnType}"/>
          </#if>
        </#list>
    </resultMap>


</mapper>
