package com.weirdo.tkmapper.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *   模板路径配置项
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    private String entity = ConstVal.TEMPLATE_ENTITY;
    private String model = ConstVal.TEMPLATE_MODEL;

    private String service = ConstVal.TEMPLATE_SERVICE;

    private String serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;

    private String mapper = ConstVal.TEMPLATE_MAPPER;
    private String mapperXml = ConstVal.TEMPLATE_MAPPER_XML;

    private String controller = ConstVal.TEMPLATE_CONTROLLER;
}
