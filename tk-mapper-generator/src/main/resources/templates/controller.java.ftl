package ${package.controller};

import ${package.service}.${table.serviceName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
<#if swagger2>
import io.swagger.annotations.Api;
</#if>
import org.springframework.web.bind.annotation.RestController;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("/${table.lowerName}/")
<#if swagger2>
@Api(value = "/${table.lowerName}/",description = "${table.comment!}")
</#if>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${table.lowerServiceName};


}
