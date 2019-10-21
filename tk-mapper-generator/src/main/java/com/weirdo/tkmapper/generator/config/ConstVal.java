package com.weirdo.tkmapper.generator.config;


import com.weirdo.tkmapper.generator.my.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  定义常量
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public interface ConstVal {

    String ENTITY = "Entity";
    String MODEL = "Model";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String CONTROLLER = "Controller";
    String CORE = "Core";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;

    String TEMPLATE_ENTITY = "/templates/entity.java";
    String TEMPLATE_MODEL = "/templates/model.java";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_MAPPER_FACTORY = "/templates/mapperFactory.java";
    String TEMPLATE_PAGE = "/templates/page.java";
    String TEMPLATE_PAGE_RESULT = "/templates/pageResult.java";
    String TEMPLATE_AJAX = "/templates/ajax.java";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "tk.mybatis.mapper.common.Mapper";
    String MAPPER_FACTORY_NAME = "MapperFactory";
    String PAGE_NAME = "Page";
    String PAGE_RESULT_NAME = "PageResult";
    String AJAX_NAME = "Ajax";
}
