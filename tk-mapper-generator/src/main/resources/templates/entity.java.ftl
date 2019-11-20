package ${package.entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import lombok.Data;
import javax.persistence.Column;

/**
 * <p>
 * ${table.comment!}
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Data
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
        <#else>
    @Id
        </#if>
    </#if>
    @Column(name = "${field.name}")
    private ${field.propertyType} ${field.propertyName};
</#list>
}
