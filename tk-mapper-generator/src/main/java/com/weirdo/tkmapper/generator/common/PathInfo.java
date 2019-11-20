package com.weirdo.tkmapper.generator.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *  路径信息
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class PathInfo {
    private String entity;
    private String model;
    private String service;
    private String serviceImpl;
    private String mapper;
    //private String mapperXml;
    private String controller;
}
