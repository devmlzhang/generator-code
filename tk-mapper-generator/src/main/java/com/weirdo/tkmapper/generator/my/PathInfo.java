package com.weirdo.tkmapper.generator.my;

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
    private String page;
    private String pageResult;
    private String controller;
    private String ajax;
}
