package ${package.model};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Data;


/**
 * <p>
 * ${table.comment!}
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Data
<#if swagger2>
@ApiModel(value = "${table.modelName}对象", description = "${table.comment!}")
</#if>
<#if superModelClass??>
public class ${table.modelName} extends ${superModelClass} {
<#else>
public class ${table.modelName} implements Serializable {
</#if>
<#list table.fields as field>

    <#if field.comment!?length gt 0>
        <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /**
    * ${field.comment}
    */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
}
