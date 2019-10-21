package ${package.entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * ${table.comment!}
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
@Table(name = "${table.name}")
<#if superEntityClass??>
public class ${table.entityName} extends ${superEntityClass} {
<#else>
public class ${table.entityName} implements Serializable {
</#if>
<#list table.fields as field>

    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @Id
    @KeySql(useGeneratedKeys = true)
        <#else>
    @Id
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
}
